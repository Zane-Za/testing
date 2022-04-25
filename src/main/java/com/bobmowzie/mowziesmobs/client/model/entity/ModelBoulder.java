package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.BlockModelRenderer;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityBoulder;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.entity.Entity;

import java.util.Random;

/**
 * Created by BobMowzie on 4/14/2017.
 */
public class ModelBoulder<T extends EntityBoulder> extends AdvancedModelBase {

    public BlockModelRenderer boulder0block1;
    public AdvancedModelRenderer boulder1;
    public BlockModelRenderer boulder1block1;
    public BlockModelRenderer boulder1block2;
    public BlockModelRenderer boulder1block3;
    public BlockModelRenderer boulder1block4;
    public BlockModelRenderer boulder1block5;
    public BlockModelRenderer boulder1block6;
    public AdvancedModelRenderer boulder2;
    public BlockModelRenderer boulder2block1;
    public BlockModelRenderer boulder2block2;
    public BlockModelRenderer boulder2block3;
    public BlockModelRenderer boulder2block4;
    public BlockModelRenderer boulder2block5;
    public BlockModelRenderer boulder2block6;
    public BlockModelRenderer boulder2block7;
    public BlockModelRenderer boulder2block8;
    public BlockModelRenderer boulder2block9;
    public BlockModelRenderer boulder2block10;
    public BlockModelRenderer boulder2block11;
    public BlockModelRenderer boulder2block12;
    public BlockModelRenderer boulder2block13;
    public BlockModelRenderer boulder2block14;
    public BlockModelRenderer boulder2block15;
    public BlockModelRenderer boulder2block16;

    public AdvancedModelRenderer boulder3;
    public BlockModelRenderer boulder3block1;
    public BlockModelRenderer boulder3block2;
    public BlockModelRenderer boulder3block3;
    public BlockModelRenderer boulder3block4;
    public BlockModelRenderer boulder3block5;
    public BlockModelRenderer boulder3block6;
    public BlockModelRenderer boulder3block7;
    public BlockModelRenderer boulder3block8;
    public BlockModelRenderer boulder3block9;
    public BlockModelRenderer boulder3block10;
    public BlockModelRenderer boulder3block11;
    public BlockModelRenderer boulder3block12;
    public BlockModelRenderer boulder3block13;
    public BlockModelRenderer boulder3block14;
    public BlockModelRenderer boulder3block15;
    public BlockModelRenderer boulder3block16;
    public BlockModelRenderer boulder3block17;
    public BlockModelRenderer boulder3block18;
    public BlockModelRenderer boulder3block19;
    public BlockModelRenderer boulder3block20;
    public BlockModelRenderer boulder3block21;
    public BlockModelRenderer boulder3block22;
    public BlockModelRenderer boulder3block23;
    public BlockModelRenderer boulder3block24;
    public BlockModelRenderer boulder3block25;
    public BlockModelRenderer boulder3block26;
    public BlockModelRenderer boulder3block27;
    public BlockModelRenderer boulder3block28;

    public BlockModelRenderer[] blockModels;

    private EntityBoulder.BoulderSizeEnum size;

    public ModelBoulder() {
        this.texWidth = 16;
        this.texHeight = 16;
        boulder0block1 = new BlockModelRenderer(this);
        boulder0block1.setPos(0F, -8F, 0F);
        boulder0block1.addBox(-8F, 8F, -8F, 0, 0, 0, 0.0F);
        boulder1 = new AdvancedModelRenderer(this);
        boulder1.setPos(0F, 0F, 0F);
        boulder1block1 = new BlockModelRenderer(this);
        boulder1block1.setPos(0, 0, 0);
        boulder0block1.addBox(-8F, 8F, -8F, 0, 0, 0, 0.0F);
        boulder1block2 = new BlockModelRenderer(this);
        boulder1block2.setPos(0, -10, 0);
        boulder1block2.addBox(-8F, 8F, -8F, 0, 0, 0, 0.0F);
        boulder1block2.setScale(0.99f, 1, 0.99f);
        boulder1block3 = new BlockModelRenderer(this);
        boulder1block3.setPos(0, -0.01f, 8);
        boulder1block3.addBox(-8F, 8F, -8F, 0, 0, 0, 0.0F);
        boulder1block4 = new BlockModelRenderer(this);
        boulder1block4.setPos(8, -0.01f, 0);
        boulder1block4.addBox(-8F, 8F, -8F, 0, 0, 0, 0.0F);
        boulder1block5 = new BlockModelRenderer(this);
        boulder1block5.setPos(-8, -0.01f, 0);
        boulder1block5.addBox(-8F, 8F, -8F, 0, 0, 0, 0.0F);
        boulder1block6 = new BlockModelRenderer(this);
        boulder1block6.setPos(0, -0.01f, -8);
        boulder1block6.addBox(-8F, 8F, -8F, 0, 0, 0, 0.0F);
        
        this.boulder2block9 = new BlockModelRenderer(this);
        this.boulder2block9.setPos(10.0F, 16.0F, -10.0F);
        this.boulder2block9.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder2block15 = new BlockModelRenderer(this);
        this.boulder2block15.setPos(8.0F, 0.0F, 0.0F);
        this.boulder2block15.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder2block3 = new BlockModelRenderer(this);
        this.boulder2block3.setPos(0.0F, 16.0F, 0.0F);
        this.boulder2block3.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder2block5 = new BlockModelRenderer(this);
        this.boulder2block5.setPos(-16.0F, 16.0F, 0.0F);
        this.boulder2block5.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder2block12 = new BlockModelRenderer(this);
        this.boulder2block12.setPos(-10.0F, 16.0F, -10.0F);
        this.boulder2block12.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder2block16 = new BlockModelRenderer(this);
        this.boulder2block16.setPos(0.0F, 0.0F, 8.0F);
        this.boulder2block16.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder2block4 = new BlockModelRenderer(this);
        this.boulder2block4.setPos(16.0F, 16.0F, 0.0F);
        this.boulder2block4.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder2block8 = new BlockModelRenderer(this);
        this.boulder2block8.setPos(10.0F, 16.0F, 10.0F);
        this.boulder2block8.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder2 = new AdvancedModelRenderer(this);
        this.boulder2.setPos(0.0F, 0.0F, 0.0F);
        this.boulder2block10 = new BlockModelRenderer(this);
        this.boulder2block10.setPos(-10.0F, 16.0F, -10.0F);
        this.boulder2block10.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder2block7 = new BlockModelRenderer(this);
        this.boulder2block7.setPos(0.0F, 16.0F, 16.0F);
        this.boulder2block7.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder2block6 = new BlockModelRenderer(this);
        this.boulder2block6.setPos(0.0F, 16.0F, -16.0F);
        this.boulder2block6.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder2block11 = new BlockModelRenderer(this);
        this.boulder2block11.setPos(-10.0F, 16.0F, 10.0F);
        this.boulder2block11.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder2block1 = new BlockModelRenderer(this);
        this.boulder2block1.setPos(0.0F, -8.0F, 0.0F);
        this.boulder2block1.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder2block14 = new BlockModelRenderer(this);
        this.boulder2block14.setPos(-8.0F, 0.0F, 0.0F);
        this.boulder2block14.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder2block13 = new BlockModelRenderer(this);
        this.boulder2block13.setPos(0.0F, 0.0F, -8.0F);
        this.boulder2block13.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder2block2 = new BlockModelRenderer(this);
        this.boulder2block2.setPos(0.0F, 0.0F, 0.0F);
        this.boulder2block2.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);

        this.boulder3block9 = new BlockModelRenderer(this);
        this.boulder3block9.setPos(10.0F, 16.0F, -10.0F);
        this.boulder3block9.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block15 = new BlockModelRenderer(this);
        this.boulder3block15.setPos(8.0F, 0.0F, 0.0F);
        this.boulder3block15.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block3 = new BlockModelRenderer(this);
        this.boulder3block3.setPos(0.0F, 16.0F, 0.0F);
        this.boulder3block3.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block5 = new BlockModelRenderer(this);
        this.boulder3block5.setPos(-16.0F, 16.0F, 0.0F);
        this.boulder3block5.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block16 = new BlockModelRenderer(this);
        this.boulder3block16.setPos(0.0F, 0.0F, 8.0F);
        this.boulder3block16.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block4 = new BlockModelRenderer(this);
        this.boulder3block4.setPos(16.0F, 16.0F, 0.0F);
        this.boulder3block4.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block8 = new BlockModelRenderer(this);
        this.boulder3block8.setPos(10.0F, 16.0F, 10.0F);
        this.boulder3block8.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3 = new AdvancedModelRenderer(this);
        this.boulder3.setPos(0.0F, 0.0F, 0.0F);
        this.boulder3block10 = new BlockModelRenderer(this);
        this.boulder3block10.setPos(-10.0F, 16.0F, -10.0F);
        this.boulder3block10.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block7 = new BlockModelRenderer(this);
        this.boulder3block7.setPos(0.0F, 16.0F, 16.0F);
        this.boulder3block7.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block6 = new BlockModelRenderer(this);
        this.boulder3block6.setPos(0.0F, 16.0F, -16.0F);
        this.boulder3block6.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block11 = new BlockModelRenderer(this);
        this.boulder3block11.setPos(-10.0F, 16.0F, 10.0F);
        this.boulder3block11.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block12 = new BlockModelRenderer(this);
        this.boulder3block12.setPos(0.0F, -16.0F, 0.0F);
        this.boulder3block12.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block1 = new BlockModelRenderer(this);
        this.boulder3block1.setPos(0.0F, -8.0F, 0.0F);
        this.boulder3block1.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block14 = new BlockModelRenderer(this);
        this.boulder3block14.setPos(-8.0F, 0.0F, 0.0F);
        this.boulder3block14.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block13 = new BlockModelRenderer(this);
        this.boulder3block13.setPos(0.0F, 0.0F, -8.0F);
        this.boulder3block13.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block2 = new BlockModelRenderer(this);
        this.boulder3block2.setPos(0.0F, 0.0F, 0.0F);
        this.boulder3block2.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block17 = new BlockModelRenderer(this);
        this.boulder3block17.setPos(0.0F, 0.0F, -8.0F);
        this.boulder3block17.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block18 = new BlockModelRenderer(this);
        this.boulder3block18.setPos(0.0F, 0.0F, 8.0F);
        this.boulder3block18.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block19 = new BlockModelRenderer(this);
        this.boulder3block19.setPos(-8.0F, 0.0F, 0.0F);
        this.boulder3block19.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block20 = new BlockModelRenderer(this);
        this.boulder3block20.setPos(8.0F, 0.0F, 0.0F);
        this.boulder3block20.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block21 = new BlockModelRenderer(this);
        this.boulder3block21.setPos(8.0F, 0.0F, 8.0F);
        this.boulder3block21.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block22 = new BlockModelRenderer(this);
        this.boulder3block22.setPos(-8.0F, 0.0F, 8.0F);
        this.boulder3block22.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block23 = new BlockModelRenderer(this);
        this.boulder3block23.setPos(-8.0F, 0.0F, -8.0F);
        this.boulder3block23.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block24 = new BlockModelRenderer(this);
        this.boulder3block24.setPos(8.0F, 0.0F, -8.0F);
        this.boulder3block24.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block25 = new BlockModelRenderer(this);
        this.boulder3block25.setPos(16.0F, 16.0F, 0.0F);
        this.boulder3block25.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block26 = new BlockModelRenderer(this);
        this.boulder3block26.setPos(-16.0F, 16.0F, 0.0F);
        this.boulder3block26.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block27 = new BlockModelRenderer(this);
        this.boulder3block27.setPos(0.0F, 16.0F, -16.0F);
        this.boulder3block27.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        this.boulder3block28 = new BlockModelRenderer(this);
        this.boulder3block28.setPos(0.0F, 16.0F, 16.0F);
        this.boulder3block28.addBox(-8.0F, -8.0F, -8.0F, 0, 0, 0, 0.0F);
        
        this.boulder3.addChild(this.boulder3block9);
        this.boulder3.addChild(this.boulder3block15);
        this.boulder3.addChild(this.boulder3block3);
        this.boulder3.addChild(this.boulder3block5);
        this.boulder3.addChild(this.boulder3block16);
        this.boulder3.addChild(this.boulder3block4);
        this.boulder3.addChild(this.boulder3block8);
        this.boulder3.addChild(this.boulder3block10);
        this.boulder3.addChild(this.boulder3block7);
        this.boulder3.addChild(this.boulder3block6);
        this.boulder3.addChild(this.boulder3block11);
        this.boulder3.addChild(this.boulder3block1);
        this.boulder3.addChild(this.boulder3block14);
        this.boulder3.addChild(this.boulder3block12);
        this.boulder3.addChild(this.boulder3block13);
        this.boulder3.addChild(this.boulder3block2);
        this.boulder3.addChild(this.boulder3block17);
        this.boulder3.addChild(this.boulder3block18);
        this.boulder3.addChild(this.boulder3block19);
        this.boulder3.addChild(this.boulder3block20);
        this.boulder3.addChild(this.boulder3block21);
        this.boulder3.addChild(this.boulder3block22);
        this.boulder3.addChild(this.boulder3block23);
        this.boulder3.addChild(this.boulder3block24);
        this.boulder3.addChild(this.boulder3block25);
        this.boulder3.addChild(this.boulder3block26);
        this.boulder3.addChild(this.boulder3block27);
        this.boulder3.addChild(this.boulder3block28);

        this.boulder2.addChild(this.boulder2block9);
        this.boulder2.addChild(this.boulder2block15);
        this.boulder2.addChild(this.boulder2block3);
        this.boulder2.addChild(this.boulder2block5);
        this.boulder2.addChild(this.boulder2block12);
        this.boulder2.addChild(this.boulder2block16);
        this.boulder2.addChild(this.boulder2block4);
        this.boulder2.addChild(this.boulder2block8);
        this.boulder2.addChild(this.boulder2block10);
        this.boulder2.addChild(this.boulder2block7);
        this.boulder2.addChild(this.boulder2block6);
        this.boulder2.addChild(this.boulder2block11);
        this.boulder2.addChild(this.boulder2block1);
        this.boulder2.addChild(this.boulder2block14);
        this.boulder2.addChild(this.boulder2block13);
        this.boulder2.addChild(this.boulder2block2);

        boulder1.addChild(boulder1block1);
        boulder1.addChild(boulder1block2);
        boulder1.addChild(boulder1block3);
        boulder1.addChild(boulder1block4);
        boulder1.addChild(boulder1block5);
        boulder1.addChild(boulder1block6);

        blockModels = new BlockModelRenderer[]{boulder0block1, boulder1block1, boulder1block2, boulder1block3, boulder1block4, boulder1block5, boulder1block6, boulder2block1, boulder2block2, boulder2block3, boulder2block4, boulder2block5, boulder2block6, boulder2block7, boulder2block8, boulder2block9, boulder2block10, boulder2block11, boulder2block12, boulder2block13, boulder2block14, boulder2block15, boulder2block16,
            boulder3block1, boulder3block2, boulder3block3, boulder3block4, boulder3block5, boulder3block6, boulder3block7, boulder3block8, boulder3block9, boulder3block10, boulder3block11, boulder3block12, boulder3block13, boulder3block14, boulder3block15, boulder3block16, boulder3block17, boulder3block18, boulder3block19, boulder3block20, boulder3block21, boulder3block22, boulder3block23, boulder3block24, boulder3block25, boulder3block26, boulder3block27, boulder3block28
        };
        Random rng = new Random(0x11c08b85b1943001L);
        for (BlockModelRenderer blockModel: blockModels) {
            float scale = rng.nextFloat() * 0.01f - 0.005f;
            blockModel.setScale(1 + scale, 1 + scale, 1 + scale);
        }
        updateDefaultPose();
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (size == EntityBoulder.BoulderSizeEnum.SMALL) boulder0block1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        else  if (size == EntityBoulder.BoulderSizeEnum.MEDIUM) boulder1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        else  if (size == EntityBoulder.BoulderSizeEnum.LARGE) boulder2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        else boulder3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityIn instanceof EntityBoulder) {
            EntityBoulder entity = (EntityBoulder) entityIn;
            size = entity.getBoulderSize();
            resetToDefaultPose();
            int tick = Math.max(entity.tickCount, 0);
            float delta = ageInTicks - entity.tickCount;
            for (int i = 0; i < blockModels.length; i++) {
                blockModels[i].setBlockState(entity.getBlock());
            }
            boulder0block1.y += -32 * (float) (Math.pow(0.6 * (tick + delta + 1), -3));
            boulder0block1.y += 2 * Math.cos(0.1f * (entity.tickCount + entity.animationOffset + delta));

            boulder1.y += -32 * (float) (Math.pow(0.2 * (tick + delta + 1), -3));
            boulder1.y += 2.4 * Math.cos(0.07f * (entity.tickCount + entity.animationOffset + delta));

            boulder2.y += -8 * (float) (Math.pow(0.05 * (tick + delta + 1), -1));
            boulder2.y += 2.8 * Math.cos(0.04f * (entity.tickCount + entity.animationOffset + delta));

            boulder3.y += -90 + Math.min(90, 1.2 * (tick + delta));
            boulder3.y += 3.2 * Math.cos(0.03f * (entity.tickCount + entity.animationOffset + delta));

            boulder3.y += 16;
            boulder3block1.y -= 8;
            boulder3block2.y -= 8;
            boulder3block4.x += 8;
            boulder3block5.x -= 8;
            boulder3block6.z -= 8;
            boulder3block7.z += 8;
            boulder3block8.x += 6;
            boulder3block8.z += 6;
            boulder3block9.x += 6;
            boulder3block9.z -= 6;
            boulder3block10.x -= 6;
            boulder3block10.z -= 6;
            boulder3block11.x -= 6;
            boulder3block11.z += 6;
            boulder3block12.y -= 8;
            boulder3block13.z -= 8;
            boulder3block14.x -= 8;
            boulder3block15.x += 8;
            boulder3block16.z += 8;
            boulder3block17.y -= 16;
            boulder3block18.y -= 16;
            boulder3block19.y -= 16;
            boulder3block20.y -= 16;
        }
    }
}
