package tenykotowsky.blissfulbutterflies;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;
import tenykotowsky.blissfulbutterflies.block.ModBlocks;
import tenykotowsky.blissfulbutterflies.entity.ModEntities;
import tenykotowsky.blissfulbutterflies.entity.custom.ButterflyEntity;
import tenykotowsky.blissfulbutterflies.item.ModItems;
import tenykotowsky.blissfulbutterflies.world.gen.ModWorldGeneration;

public class BlissfulButterflies implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "blissful-butterflies";
    public static final Logger LOGGER = LoggerFactory.getLogger("blissful-butterflies");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		GeckoLib.initialize();

		ModBlocks.registerModBlocks();

		ModItems.registerModItems();

		ModWorldGeneration.generateModWorldGen();

		FabricDefaultAttributeRegistry.register(ModEntities.BUTTERFLY, ButterflyEntity.setAttributes());

		LOGGER.info("Hello Fabric world!");
	}
}