package tenykotowsky.blissfulbutterflies.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import tenykotowsky.blissfulbutterflies.BlissfulButterflies;
import tenykotowsky.blissfulbutterflies.entity.custom.ButterflyEntity;

public class ModEntities {
    public static final EntityType<ButterflyEntity> BUTTERFLY = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(BlissfulButterflies.MOD_ID, "butterfly"),
            FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, ButterflyEntity::new)
                    .dimensions(EntityDimensions.fixed(1.1f, 1.1f)).build());
}
