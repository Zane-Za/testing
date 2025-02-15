package com.bobmowzie.mowziesmobs.server.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.IntermittentAnimation;
import com.bobmowzie.mowziesmobs.client.sound.BossMusicSound;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.world.spawn.SpawnHandler;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import com.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.Tag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.sounds.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ChunkPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.*;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public abstract class MowzieEntity extends PathfinderMob implements IEntityAdditionalSpawnData, IAnimatedEntity, IntermittentAnimatableEntity {
    private static final byte START_IA_HEALTH_UPDATE_ID = 4;
    private static final byte MUSIC_PLAY_ID = 67;
    private static final byte MUSIC_STOP_ID = 68;

    public int frame;
    public float targetDistance = -1;
    public float targetAngle = -1;
    public boolean active;
    public LivingEntity blockingEntity = null;
    private int animationTick;
    private Animation animation = NO_ANIMATION;
    private final List<IntermittentAnimation<?>> intermittentAnimations = new ArrayList<>();
    public boolean playsHurtAnimation = true;
    protected boolean dropAfterDeathAnim = true;
    public boolean hurtInterruptsAnimation = false;

    @OnlyIn(Dist.CLIENT)
    public Vec3[] socketPosArray;

    protected boolean prevOnGround;
    protected boolean prevPrevOnGround;
    protected boolean willLandSoon;
    
    private int killDataRecentlyHit;
    private DamageSource killDataCause;
    private Player killDataAttackingPlayer;

    private final MMBossEventServer bossInfo = new MMBossEventServer(this);

    public static BossMusicSound bossMusic;

    public MowzieEntity(EntityType<? extends MowzieEntity> type, Level world) {
        super(type, world);
        if (level.isClientSide) {
            socketPosArray = new Vec3[]{};
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.ATTACK_DAMAGE);
    }

    protected ConfigHandler.SpawnConfig getSpawnConfig() {
        return null;
    }

    public static boolean spawnPredicate(EntityType type, WorldGenLevel level, MobSpawnType reason, BlockPos spawnPos, Random random) {
        ConfigHandler.SpawnConfig spawnConfig = SpawnHandler.spawnConfigs.get(type);
        if (spawnConfig != null) {
            if (random.nextDouble() > spawnConfig.extraRarity.get()) return false;

            // Dimension check
            List<? extends String> dimensionNames = spawnConfig.dimensions.get();
            ResourceLocation currDimensionName = ((ServerLevel)level).dimension().location();
            if (!dimensionNames.contains(currDimensionName.toString())) {
                return false;
            }

            // Height check
            float heightMax = spawnConfig.heightMax.get();
            float heightMin = spawnConfig.heightMin.get();
            if (spawnPos.getY() > heightMax && heightMax >= 0) {
                return false;
            }
            if (spawnPos.getY() < heightMin) {
                return false;
            }

            // Light level check
            if (spawnConfig.needsDarkness.get() && !Monster.isDarkEnoughToSpawn(level, spawnPos, random)) {
                return false;
            }

            // Block check
            Block block = level.getBlockState(spawnPos.below()).getBlock();
            ResourceLocation blockName = block.getRegistryName();
            List<? extends String> allowedBlocks = spawnConfig.allowedBlocks.get();
            List<? extends String> allowedBlockTags = spawnConfig.allowedBlockTags.get();
            if (blockName == null) return false;
            if (!allowedBlocks.isEmpty() && !allowedBlocks.contains(blockName.toString()) && !allowedBlocks.contains(blockName.getPath())) return false;
            if (!allowedBlockTags.isEmpty() && !isBlockTagAllowed(allowedBlockTags, block)) return false;

            // See sky
            if (spawnConfig.needsSeeSky.get() && !level.canSeeSky(spawnPos)) {
                return false;
            }
            if (spawnConfig.needsCantSeeSky.get() && level.canSeeSky(spawnPos)) {
                return false;
            }

            List<? extends String> avoidStructures = spawnConfig.avoidStructures.get();
            for (String structureName : avoidStructures) {
                StructureFeature<?> structure = ForgeRegistries.STRUCTURE_FEATURES.getValue(new ResourceLocation(structureName));
                if (structure == null) continue;
                BlockPos pos = ((ServerLevel) level).findNearestMapFeature(structure, spawnPos, 3, false);
                if (pos == null) continue;
                double dist = spawnPos.offset(0, -spawnPos.getY(), 0).distSqr(pos);
                if (dist < 900) return false;
            }
        }
        return true;
    }

    private static boolean isBlockTagAllowed(List<? extends String> allowedBlockTags, Block block) {
        for (String allowedBlockTag : allowedBlockTags) {
            Tag<Block> tag = BlockTags.getAllTags().getTag(new ResourceLocation(allowedBlockTag));
            if (tag != null && tag.contains(block)) return true;
        }
        return false;
    }

    @Override
    public void tick() {
        prevPrevOnGround = prevOnGround;
        prevOnGround = onGround;
        super.tick();
        frame++;
        if (getAnimation() != NO_ANIMATION) {
            animationTick++;
            if (level.isClientSide && animationTick >= animation.getDuration()) {
                setAnimation(NO_ANIMATION);
            }
        }
        if (tickCount % 4 == 0) bossInfo.update();
        if (getTarget() != null) {
            targetDistance = distanceTo(getTarget()) - getTarget().getBbWidth() / 2f;
            targetAngle = (float) getAngleBetweenEntities(this, getTarget());
        }
        willLandSoon = !onGround && level.noCollision(getBoundingBox().move(getDeltaMovement()));

        if (!level.isClientSide && getBossMusic() != null) {
            if (canPlayMusic()) {
                this.level.broadcastEntityEvent(this, MUSIC_PLAY_ID);
            }
            else {
                this.level.broadcastEntityEvent(this, MUSIC_STOP_ID);
            }
        }
    }

    protected boolean canPlayMusic() {
        return !isSilent() && getTarget() instanceof Player;
    }

    protected boolean canPlayerHearMusic(Player player) {
        return player != null
                && canAttack(player)
                && distanceTo(player) < 2500;
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
    }

    protected void onAnimationFinish(Animation animation) {}

    @Override
    public void writeSpawnData(FriendlyByteBuf buf) {
        buf.writeInt(ArrayUtils.indexOf(this.getAnimations(), this.getAnimation()));
        buf.writeInt(this.getAnimationTick());
    }

    @Override
    public void readSpawnData(FriendlyByteBuf buf) {
        yRotO = getYRot();
        yBodyRotO = yBodyRot = yHeadRotO = yHeadRot;
        int animOrdinal = buf.readInt();
        int animTick = buf.readInt();
        this.setAnimation(animOrdinal == -1 ? IAnimatedEntity.NO_ANIMATION : this.getAnimations()[animOrdinal]);
        this.setAnimationTick(animTick);
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        return this.doHurtTarget(entityIn, 1.0F, 1.0f);
    }

//    @Override
//    public boolean isNotColliding() {
//        return !this.world.containsAnyLiquid(this.getBoundingBox()) && this.world.isUnobstructed(this.getBoundingBox(), this);
//    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn, CompoundTag dataTag) {
//        System.out.println("Spawned " + getName().getString() + " at " + getPosition());
//        System.out.println("Block " + level.getBlockState(getPosition().below()).toString());
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public boolean doHurtTarget(Entity entityIn, float damageMultiplier, float applyKnockbackMultiplier) {
        return doHurtTarget(entityIn, damageMultiplier, applyKnockbackMultiplier, false);
    }

    public boolean doHurtTarget(Entity entityIn, float damageMultiplier, float applyKnockbackMultiplier, boolean canDisableShield) { // TODO use attribute modifiers to no longer copy from mob class
        float f = (float)this.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * damageMultiplier;
        float f1 = (float)this.getAttribute(Attributes.ATTACK_KNOCKBACK).getValue() * applyKnockbackMultiplier;
        if (entityIn instanceof LivingEntity) {
            f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity)entityIn).getMobType());
            f1 += (float)EnchantmentHelper.getKnockbackBonus(this);
        }

        int i = EnchantmentHelper.getFireAspect(this);
        if (i > 0) {
            entityIn.setSecondsOnFire(i * 4);
        }

        boolean flag = entityIn.hurt(DamageSource.mobAttack(this), f);
        if (flag) {
            if (f1 > 0.0F && entityIn instanceof LivingEntity) {
                ((LivingEntity)entityIn).knockback((double)(f1 * 0.5F), (double)Mth.sin(this.getYRot() * ((float)Math.PI / 180F)), (double)(-Mth.cos(this.getYRot() * ((float)Math.PI / 180F))));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }

            if (entityIn instanceof Player) {
                Player player = (Player)entityIn;
                this.maybeDisableShield(player, canDisableShield ? Items.IRON_AXE.getDefaultInstance() : ItemStack.EMPTY, player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY);
            }

            this.doEnchantDamageEffects(this, entityIn);
            this.setLastHurtMob(entityIn);
        }

        return flag;

        return flag;
    }

    public float getHealthRatio() {
        return this.getHealth() / this.getMaxHealth();
    }

    public double getAngleBetweenEntities(Entity first, Entity second) {
        return Math.atan2(second.getZ() - first.getZ(), second.getX() - first.getX()) * (180 / Math.PI) + 90;
    }

    public List<Player> getPlayersNearby(double distanceX, double distanceY, double distanceZ, double radius) {
        List<Entity> nearbyEntities = level.getEntities(this, getBoundingBox().grow(distanceX, distanceY, distanceZ));
        List<Player> listEntityPlayers = nearbyEntities.stream().filter(entityNeighbor -> entityNeighbor instanceof Player && distanceTo(entityNeighbor) <= radius + entityNeighbor.getBbWidth() / 2f).map(entityNeighbor -> (Player) entityNeighbor).collect(Collectors.toList());
        return listEntityPlayers;
    }

    public List<LivingEntity> getAttackableEntityLivingBaseNearby(double distanceX, double distanceY, double distanceZ, double radius) {
        List<Entity> nearbyEntities = level.getEntities(this, getBoundingBox().expandTowards(distanceX, distanceY, distanceZ));
        List<LivingEntity> listEntityLivingBase = nearbyEntities.stream().filter(entityNeighbor -> entityNeighbor instanceof LivingEntity && ((LivingEntity)entityNeighbor).attackable() && (!(entityNeighbor instanceof Player) || !((Player)entityNeighbor).isCreative()) && distanceTo(entityNeighbor) <= radius + entityNeighbor.getBbWidth() / 2f).map(entityNeighbor -> (LivingEntity) entityNeighbor).collect(Collectors.toList());
        return listEntityLivingBase;
    }

    public  List<LivingEntity> getEntityLivingBaseNearby(double distanceX, double distanceY, double distanceZ, double radius) {
        return getEntitiesNearby(LivingEntity.class, distanceX, distanceY, distanceZ, radius);
    }

    public <T extends Entity> List<T> getEntitiesNearby(Class<T> entityClass, double r) {
        return level.getEntitiesOfClass(entityClass, getBoundingBox().expandTowards(r, r, r), e -> e != this && distanceTo(e) <= r + e.getBbWidth() / 2f);
    }

    public <T extends Entity> List<T> getEntitiesNearby(Class<T> entityClass, double dX, double dY, double dZ, double r) {
        return level.getEntitiesOfClass(entityClass, getBoundingBox().expandTowards(dX, dY, dZ), e -> e != this && distanceTo(e) <= r + e.getBbWidth() / 2f && e.getY() <= getY() + dY);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (getHealth() <= 0.0F) {
            Animation death;
            if ((death = getDeathAnimation()) != null) {
                onDeathUpdate(death.getDuration() - 20);   
            } else {
                onDeathUpdate(20);
            }
        }
    }

    private void onDeathUpdate(int deathDuration) { // TODO copy from entityLiving
        onDeathAIUpdate();

        ++this.deathTime;
        if (this.deathTime == deathDuration) {
            attackingPlayer = killDataAttackingPlayer;
            recentlyHit = killDataRecentlyHit;
            if (!level.isClientSide() && dropAfterDeathAnim && killDataCause != null) {
                spawnDrops(killDataCause);
            }

            this.remove(false);

            for(int i = 0; i < 20; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level.addParticle(ParticleTypes.POOF, this.getPosXRandom(1.0D), this.getPosYRandom(), this.getPosZRandom(1.0D), d0, d1, d2);
            }
        }
    }

    protected void onDeathAIUpdate() {}

    @Override
    protected final void onDeathUpdate() {}

    @Override
    public void onDeath(DamageSource cause) // TODO copy from entityLiving
    {
        if (net.minecraftforge.common.ForgeHooks.onLivingDeath(this, cause)) return;
        if (!this.dead) {
            Entity entity = cause.getTrueSource();
            LivingEntity livingentity = this.getAttackingEntity();
            if (this.scoreValue >= 0 && livingentity != null) {
                livingentity.awardKillScore(this, this.scoreValue, cause);
            }

            if (this.isSleeping()) {
                this.wakeUp();
            }

            this.dead = true;
            this.getCombatTracker().reset();
            if (this.world instanceof ServerLevel) {
                if (entity != null) {
                    entity.killed((ServerLevel)this.world, this);
                }

                if (!dropAfterDeathAnim)
                    this.spawnDrops(cause);
                this.createWitherRose(livingentity);
            }
            killDataCause = cause;
            killDataRecentlyHit = this.recentlyHit;
            killDataAttackingPlayer = attackingPlayer;

            this.world.setEntityState(this, (byte)3);
            this.setPose(Pose.DYING);
            bossInfo.update();
        }
    }
    
    @Override
    public boolean hurt(DamageSource source, float damage) {
        boolean attack = super.hurt(source, damage);
        if (attack) {
            if (getHealth() > 0.0F && (getAnimation() == NO_ANIMATION || hurtInterruptsAnimation) && playsHurtAnimation) {
                AnimationHandler.INSTANCE.sendAnimationMessage(this, getHurtAnimation());
            } else if (getHealth() <= 0.0F) {
                AnimationHandler.INSTANCE.sendAnimationMessage(this, getDeathAnimation());
            }
        }
        return attack;
    }

    protected void addIntermittentAnimation(IntermittentAnimation animation) {
        animation.setID((byte) intermittentAnimations.size());
        intermittentAnimations.add(animation);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id >= START_IA_HEALTH_UPDATE_ID && id - START_IA_HEALTH_UPDATE_ID < intermittentAnimations.size()) {
            intermittentAnimations.get(id - START_IA_HEALTH_UPDATE_ID).start();
        }
        else if (id == MUSIC_PLAY_ID) {
            SoundEvent soundEvent = getBossMusic();
            if (soundEvent != null && this.isAlive()) {
                Player player = Minecraft.getInstance().player;
                if (bossMusic != null) {
                    float f2 = Minecraft.getInstance().options.getSoundSourceVolume(SoundSource.MUSIC);
                    if (f2 <= 0) {
                        bossMusic = null;
                    }
                    else if (bossMusic.getBoss() == this && !canPlayerHearMusic(player)) {
                        bossMusic.setBoss(null);
                    }
                }
                else {
                    if (canPlayerHearMusic(player)) {
                        if (bossMusic == null) {
                            bossMusic = new BossMusicSound(getBossMusic(), this);
                        }
                        else if (bossMusic.getBoss() == null && bossMusic.getSoundEvent() == soundEvent) {
                            bossMusic.setBoss(this);
                        }
                    }
                }
                if (bossMusic != null && !Minecraft.getInstance().getSoundManager().isActive(bossMusic)) {
                    Minecraft.getInstance().getSoundManager().play(bossMusic);
                }
            }
        }
        else if (id == MUSIC_STOP_ID) {
            if (bossMusic != null && bossMusic.getBoss() == this)
                bossMusic.setBoss(null);
        }
        else super.handleEntityEvent(id);
    }

    @Override
    public byte getOffsetEntityState() {
        return START_IA_HEALTH_UPDATE_ID;
    }

    public void circleEntity(Entity target, float radius, float speed, boolean direction, int circleFrame, float offset, float moveSpeedMultiplier) {
        int directionInt = direction ? 1 : -1;
        double t = directionInt * circleFrame * 0.5 * speed / radius + offset;
        Vec3 movePos = target.position().add(radius * Math.cos(t), 0, radius * Math.sin(t));
        this.getNavigation().moveTo(movePos.x(), movePos.y(), movePos.z(), speed * moveSpeedMultiplier);
    }

    protected void repelEntities(float x, float y, float z, float radius) {
        List<LivingEntity> nearbyEntities = getEntityLivingBaseNearby(x, y, z, radius);
        for (Entity entity : nearbyEntities) {
            if (entity.canBeCollidedWith() && !entity.noPhysics) {
                double angle = (getAngleBetweenEntities(this, entity) + 90) * Math.PI / 180;
                entity.setDeltaMovement(-0.1 * Math.cos(angle), entity.getDeltaMovement().y, -0.1 * Math.sin(angle));
            }
        }
    }

    @Override
    public int getAnimationTick() {
        return this.animationTick;
    }

    @Override
    public void setAnimationTick(int tick) {
        this.animationTick = tick;
    }

    @Override
    public Animation getAnimation() {
        return this.animation;
    }

    @Override
    public void setAnimation(Animation animation) {
        if (animation == NO_ANIMATION) {
            onAnimationFinish(this.animation);
        }
        this.animation = animation;
        setAnimationTick(0);
    }

    public abstract Animation getDeathAnimation();

    public abstract Animation getHurtAnimation();

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (this.hasCustomName()) {
            this.bossInfo.setName(this.getDisplayName());
        }
    }

    @Override
    public void setCustomName(@Nullable Component p_20053_) {
        super.setCustomName(p_20053_);
        this.bossInfo.setName(this.getDisplayName());
    }

    protected boolean hasBossBar() {
        return false;
    }

    protected BossEvent.BossBarColor bossBarColor() {
        return BossEvent.BossBarColor.PURPLE;
    }

    @OnlyIn(Dist.CLIENT)
    public void setSocketPosArray(int index, Vec3 pos) {
        if (socketPosArray != null && socketPosArray.length > index) {
            socketPosArray[index] = pos;
        }
    }

    public boolean isPushableByEntity(Entity entity) {
        return true;
    }

    // TODO: Copied from parent classes
    @Override
    public void applyEntityCollision(Entity entityIn) {
        if (!this.isSleeping()) {
            if (!this.isRidingSameEntity(entityIn)) {
                if (!entityIn.noClip && !this.noClip) {
                    double d0 = entityIn.getX() - this.getX();
                    double d1 = entityIn.getZ() - this.getZ();
                    double d2 = Mth.absMax(d0, d1);
                    if (d2 >= (double)0.01F) {
                        d2 = (double)Mth.sqrt(d2);
                        d0 = d0 / d2;
                        d1 = d1 / d2;
                        double d3 = 1.0D / d2;
                        if (d3 > 1.0D) {
                            d3 = 1.0D;
                        }

                        d0 = d0 * d3;
                        d1 = d1 * d3;
                        d0 = d0 * (double)0.05F;
                        d1 = d1 * (double)0.05F;
                        d0 = d0 * (double)(1.0F - this.entityCollisionReduction);
                        d1 = d1 * (double)(1.0F - this.entityCollisionReduction);
                        if (!this.isBeingRidden()) {
                            if (isPushableByEntity(entityIn)) {
                                this.push(-d0, 0.0D, -d1);
                            }
                        }

                        if (!entityIn.isBeingRidden()) {
                            entityIn.push(d0, 0.0D, d1);
                        }
                    }

                }
            }
        }
    }

    public SoundEvent getBossMusic() {
        return null;
    }
}
