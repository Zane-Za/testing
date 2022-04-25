package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntityAxeAttack;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.HumanoidArm;

/**
 * Created by BobMowzie on 4/14/2017.
 */
public class ModelAxeAttack<T extends EntityAxeAttack> extends AdvancedModelBase<T> {
    public AdvancedModelRenderer axeBase;
    public AdvancedModelRenderer axeHandle;
    public AdvancedModelRenderer axeBladeRight;
    public AdvancedModelRenderer axeBladeLeft;
    public AdvancedModelRenderer axeBladeRight1;
    public AdvancedModelRenderer axeBladeRight2;
    public AdvancedModelRenderer axeBladeRight3;
    public AdvancedModelRenderer axeBladeLeft1;
    public AdvancedModelRenderer axeBladeLeft2;
    public AdvancedModelRenderer axeBladeLeft3;

    public ModelAxeAttack() {
        this.texWidth = 128;
        this.texHeight = 128;
        this.axeHandle = new AdvancedModelRenderer(this, 0, 22);
        this.axeHandle.setPos(3.0F, 0.0F, 1.0F);
        this.axeHandle.addBox(-1.5F, -44.0F, -1.5F, 3, 50, 3, 0.0F);
        this.axeBladeRight3 = new AdvancedModelRenderer(this, 56, 0);
        this.axeBladeRight3.setPos(17.7F, 2.3F, -0.01F);
        this.axeBladeRight3.addBox(-5.5F, 0.0F, -1.0F, 11, 17, 2, 0.0F);
        this.setRotateAngle(axeBladeRight3, 0.0F, 0.0F, 2.6179938779914944F);
        this.axeBladeLeft1 = new AdvancedModelRenderer(this, 84, 0);
        this.axeBladeLeft1.setPos(0.0F, 0.0F, 0.0F);
        this.axeBladeLeft1.addBox(0.0F, -4.5F, -1.0F, 10, 8, 2, 0.0F);
        this.axeBladeRight = new AdvancedModelRenderer(this, 0, 0);
        this.axeBladeRight.setPos(0.0F, -37.0F, 0.0F);
        this.axeBladeRight.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(axeBladeRight, 0.0F, -0.7853981633974483F, 0.0F);
        this.axeBladeLeft = new AdvancedModelRenderer(this, 0, 0);
        this.axeBladeLeft.setPos(0.0F, -37.0F, 0.0F);
        this.axeBladeLeft.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(axeBladeLeft, 0.0F, -3.9269908169872414F, 0.0F);
        this.axeBladeRight1 = new AdvancedModelRenderer(this, 84, 0);
        this.axeBladeRight1.setPos(0.0F, 0.0F, 0.0F);
        this.axeBladeRight1.addBox(0.0F, -4.5F, -1.0F, 10, 8, 2, 0.0F);
        this.axeBladeLeft3 = new AdvancedModelRenderer(this, 56, 0);
        this.axeBladeLeft3.setPos(17.7F, 2.3F, -0.01F);
        this.axeBladeLeft3.addBox(-5.5F, 0.0F, -1.0F, 11, 17, 2, 0.01F);
        this.setRotateAngle(axeBladeLeft3, 0.0F, 0.0F, 2.6179938779914944F);
        this.axeBase = new AdvancedModelRenderer(this, 0, 0);
        this.axeBase.setPos(0.0F, 0.0F, 0.0F);
        this.axeBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.axeBladeRight2 = new AdvancedModelRenderer(this, 56, 0);
        this.axeBladeRight2.mirror = true;
        this.axeBladeRight2.setPos(17.7F, -3.2F, 0.01F);
        this.axeBladeRight2.addBox(-5.5F, 0.0F, -1.0F, 11, 17, 2, 0.0F);
        this.setRotateAngle(axeBladeRight2, 0.0F, 0.0F, 0.5235987755982988F);
        this.axeBladeLeft2 = new AdvancedModelRenderer(this, 56, 0);
        this.axeBladeLeft2.mirror = true;
        this.axeBladeLeft2.setPos(17.7F, -3.2F, 0.01F);
        this.axeBladeLeft2.addBox(-5.5F, 0.0F, -1.0F, 11, 17, 2, 0.0F);
        this.setRotateAngle(axeBladeLeft2, 0.0F, 0.0F, 0.5235987755982988F);

        this.axeBase.addChild(this.axeHandle);
        this.axeBladeLeft.addChild(this.axeBladeLeft1);
        this.axeBladeRight.addChild(this.axeBladeRight2);
        this.axeBladeLeft.addChild(this.axeBladeLeft3);
        this.axeBladeLeft.addChild(this.axeBladeLeft2);
        this.axeHandle.addChild(this.axeBladeLeft);
        this.axeBladeRight.addChild(this.axeBladeRight3);
        this.axeHandle.addChild(this.axeBladeRight);
        this.axeBladeRight.addChild(this.axeBladeRight1);

        axeBase.y += 18f;
        axeBase.xRot -= Math.PI/2;
        axeHandle.yRot += Math.PI/4;
        axeHandle.x -= 3;
        axeHandle.y -= 6;
        axeBase.scaleChildren = true;

        updateDefaultPose();
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float frame, float netHeadYaw, float headPitch) {
        resetToDefaultPose();

        if (!entityIn.getVertical()) {
            float swingArc = 2;
            float scale = (float) ((1 / (1 + Math.exp(2f * (-frame + EntityAxeAttack.SWING_DURATION_HOR / 5f)))) - (1 / (1 + Math.exp(2f * (-frame + 4 * EntityAxeAttack.SWING_DURATION_HOR / 5f)))));
            float handFlip = entityIn.getCaster().getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
            axeBase.yRot -= handFlip * swingArc * 1 / (1 + Math.exp(1.3f * (-frame + EntityAxeAttack.SWING_DURATION_HOR / 2f)));
            axeBase.yRot += handFlip * swingArc / 2;
            axeBase.setScale(scale, scale, scale);
        }
        else {
            float swingArc = 2;
            float scale = (float) ((1 / (1 + Math.exp(2f * (-frame + EntityAxeAttack.SWING_DURATION_VER / 5f)))) - (1 / (1 + Math.exp(2f * (-frame + 4 * EntityAxeAttack.SWING_DURATION_VER / 5f)))));
            float animCurve = (float) Math.min(1, Math.pow(0.06 * frame, 5));
            axeHandle.yRot -= Math.PI/2;
            axeBase.xRot += -2 + 2 * animCurve;
//            axeBase.rotationPointY -= 16 * animCurve;
            axeBase.setScale(scale, scale, scale);
        }
    }

    public void setRotateAngle(AdvancedModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        axeBase.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void setRotationAngles(EntityAxeAttack entity, float f5, float delta) {

    }
}
