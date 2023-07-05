package tenykotowsky.blissfulbutterflies.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tenykotowsky.blissfulbutterflies.BlissfulButterflies;
import tenykotowsky.blissfulbutterflies.entity.custom.ButterflyEntity;

public class ButterflyRenderer extends GeoEntityRenderer<ButterflyEntity> {
    public ButterflyRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ButterflyModel());
    }

    @Override
    public Identifier getTextureLocation(ButterflyEntity animatable) {
        return new Identifier(BlissfulButterflies.MOD_ID, "textures/entity/monarch_butterfly.png");
    }

    @Override
    public void render(ButterflyEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
