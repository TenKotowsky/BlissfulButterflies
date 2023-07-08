package tenykotowsky.blissfulbutterflies.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import tenykotowsky.blissfulbutterflies.entity.ModEntities;
import tenykotowsky.blissfulbutterflies.entity.custom.ButterflyEntity;

public class ModEntityGeneration {
    public static void addSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.PLAINS), SpawnGroup.AMBIENT,
                ModEntities.BUTTERFLY, 30, 5, 11);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), SpawnGroup.AMBIENT,
                ModEntities.BUTTERFLY, 30, 5, 11);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SUNFLOWER_PLAINS), SpawnGroup.AMBIENT,
                ModEntities.BUTTERFLY, 30, 5, 11);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.CHERRY_GROVE), SpawnGroup.AMBIENT,
                ModEntities.BUTTERFLY, 30, 5, 11);

        SpawnRestriction.register(ModEntities.BUTTERFLY, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.WORLD_SURFACE, ButterflyEntity::canSpawn);
    }
}
