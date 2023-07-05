package tenykotowsky.blissfulbutterflies.entity.client;

import net.minecraft.entity.AnimationState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import tenykotowsky.blissfulbutterflies.BlissfulButterflies;
import tenykotowsky.blissfulbutterflies.entity.custom.ButterflyEntity;

public class ButterflyModel extends GeoModel<ButterflyEntity> {
    @Override
    public Identifier getModelResource(ButterflyEntity animatable) {
        return new Identifier(BlissfulButterflies.MOD_ID, "geo/monarch_butterfly.geo.json");
    }

    @Override
    public Identifier getTextureResource(ButterflyEntity animatable) {
        return new Identifier(BlissfulButterflies.MOD_ID, "textures/entity/monarch_butterfly.png");
    }

    @Override
    public Identifier getAnimationResource(ButterflyEntity animatable) {
        return new Identifier(BlissfulButterflies.MOD_ID, "animations/monarch_butterfly.animation.json");
    }
}