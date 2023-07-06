package tenykotowsky.blissfulbutterflies.entity.client;

import com.google.common.collect.Maps;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tenykotowsky.blissfulbutterflies.BlissfulButterflies;
import tenykotowsky.blissfulbutterflies.entity.custom.ButterflyEntity;
import tenykotowsky.blissfulbutterflies.entity.variant.ButterflyVariant;

import java.util.Map;

public class ButterflyRenderer extends GeoEntityRenderer<ButterflyEntity> {

    public static final Map<ButterflyVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(ButterflyVariant.class), (map) -> {
                map.put(ButterflyVariant.MONARCH,
                        new Identifier(BlissfulButterflies.MOD_ID, "textures/entity/monarch_butterfly.png"));
                map.put(ButterflyVariant.BLUE,
                        new Identifier(BlissfulButterflies.MOD_ID, "textures/entity/blue_butterfly.png"));
                map.put(ButterflyVariant.PURPLE,
                        new Identifier(BlissfulButterflies.MOD_ID, "textures/entity/purple_butterfly.png"));
                map.put(ButterflyVariant.ORANGE,
                        new Identifier(BlissfulButterflies.MOD_ID, "textures/entity/orange_butterfly.png"));
                map.put(ButterflyVariant.WHITE,
                        new Identifier(BlissfulButterflies.MOD_ID, "textures/entity/white_butterfly.png"));
            });


    public ButterflyRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ButterflyModel());
    }

    @Override
    public Identifier getTextureLocation(ButterflyEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }

    @Override
    public void render(ButterflyEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.scale(0.5f, 0.5f, 0.5f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
