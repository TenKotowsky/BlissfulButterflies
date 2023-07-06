package tenykotowsky.blissfulbutterflies.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import tenykotowsky.blissfulbutterflies.BlissfulButterflies;
import tenykotowsky.blissfulbutterflies.entity.ModEntities;

public class ModItems {
    public static final Item BUTTERFLY_SPAWN_EGG = registerItem("butterfly_spawn_egg",
            new SpawnEggItem(ModEntities.BUTTERFLY, 0xEC5C0F, 0x111010,
                    new FabricItemSettings()));

    public static void addItemsToItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.add(BUTTERFLY_SPAWN_EGG);
        });
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(BlissfulButterflies.MOD_ID, name), item);
    }

    public static void registerModItems() {
        BlissfulButterflies.LOGGER.info("Registered Mod Items");

        addItemsToItemGroups();
    }
}
