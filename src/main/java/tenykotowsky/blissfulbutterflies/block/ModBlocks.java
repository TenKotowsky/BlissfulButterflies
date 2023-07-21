package tenykotowsky.blissfulbutterflies.block;

import net.fabricmc.fabric.api.block.v1.FabricBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import tenykotowsky.blissfulbutterflies.BlissfulButterflies;
import tenykotowsky.blissfulbutterflies.block.blocks.CocoonBlock;

public class ModBlocks {

    public static final Block COCOON = registerBlock("cocoon",
            new CocoonBlock(FabricBlockSettings.create().nonOpaque().breakInstantly().pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.CANDLE)), "NATURAL");

    private static Block registerBlock(String name, Block block, String category) {
        Item item = registerBlockItem(name, block);
        if (category.equals("NATURAL")) {
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
                entries.add(item);
            });
        }
        return Registry.register(Registries.BLOCK, new Identifier(BlissfulButterflies.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(BlissfulButterflies.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        BlissfulButterflies.LOGGER.info("Registering ModBlocs for Blissful Butterflies!");
    }

}
