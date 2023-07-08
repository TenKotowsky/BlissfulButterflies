package tenykotowsky.blissfulbutterflies.entity.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import tenykotowsky.blissfulbutterflies.BlissfulButterflies;
import tenykotowsky.blissfulbutterflies.entity.custom.ButterflyEntity;

public class ButterflyModel extends GeoModel<ButterflyEntity> {

    @Override
    public Identifier getModelResource(ButterflyEntity animatable) {
        return new Identifier(BlissfulButterflies.MOD_ID, "geo/monarch_butterfly.geo.json");
    }

    @Override
    public Identifier getTextureResource(ButterflyEntity animatable) {
        return ButterflyRenderer.LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public Identifier getAnimationResource(ButterflyEntity animatable) {
        return new Identifier(BlissfulButterflies.MOD_ID, "animations/monarch_butterfly.animation.json");
    }
}