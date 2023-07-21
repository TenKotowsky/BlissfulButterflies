package tenykotowsky.blissfulbutterflies.world.gen;

import net.minecraft.block.Blocks;
import net.minecraft.block.CherryLeavesBlock;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.CherryFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.AttachedToLeavesTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunk.CherryTrunkPlacer;
import tenykotowsky.blissfulbutterflies.BlissfulButterflies;
import tenykotowsky.blissfulbutterflies.block.ModBlocks;

import java.util.*;

public class ModWorldGeneration {

    public static final RegistryKey<ConfiguredFeature<?, ?>> CHERRY_COCOON_TREE_KEY = registerKey("cherry_cocoon_tree");

    static List<Direction> directions = new ArrayList<>(Collections.singleton(Direction.DOWN));
    static List<TreeDecorator> decorators = new ArrayList<>(Collections.singleton(new AttachedToLeavesTreeDecorator(0.01f, 1, 1, BlockStateProvider.of(ModBlocks.COCOON.getDefaultState()), 1, directions)));
    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, CHERRY_COCOON_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(Blocks.CHERRY_LOG),
                new CherryTrunkPlacer(4, 1, 1, ConstantIntProvider.create(2), ConstantIntProvider.create(3), UniformIntProvider.create(-2, -1), ConstantIntProvider.create(1)),
                BlockStateProvider.of(Blocks.CHERRY_LEAVES),
                new CherryFoliagePlacer(ConstantIntProvider.create(4), ConstantIntProvider.create(1), ConstantIntProvider.create(4), 0.05f, 0.05f, 0.15f, 0.05f),
                new TwoLayersFeatureSize(1, 0, 1)).decorators(decorators).build());
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(BlissfulButterflies.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    public static void generateModWorldGen() {
        ModEntityGeneration.addSpawns();
        ModTreeGeneration.generateTrees();
    }
}
