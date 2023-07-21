package tenykotowsky.blissfulbutterflies.block.blocks;

import com.google.common.eventbus.Subscribe;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import tenykotowsky.blissfulbutterflies.entity.ModEntities;
import tenykotowsky.blissfulbutterflies.entity.custom.ButterflyEntity;
import tenykotowsky.blissfulbutterflies.entity.variant.ButterflyVariant;

public class CocoonBlock extends Block {
    public static Integer TICKS = 0;
    public CocoonBlock(Settings settings) {
        super(settings);
    }

    private static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(
            Block.createCuboidShape(6.5, 13.5, 3.5, 10.5, 15.5, 7.5),
            Block.createCuboidShape(6, 7.5, 3, 11.5, 13.5, 8.5),
            BooleanBiFunction.OR
    );

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())) != Blocks.AIR.getDefaultState();
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        TICKS = TICKS + 1;
        if (TICKS >= 70) {
            ButterflyEntity butterfly = new ButterflyEntity(ModEntities.BUTTERFLY, world);
            int variant = Random.create().nextBetween(1,5);
            switch (variant) {
                case 1 -> butterfly.setVariant(ButterflyVariant.MONARCH);
                case 2 -> butterfly.setVariant(ButterflyVariant.BLUE);
                case 3 -> butterfly.setVariant(ButterflyVariant.PURPLE);
                case 4 -> butterfly.setVariant(ButterflyVariant.ORANGE);
                case 5 -> butterfly.setVariant(ButterflyVariant.WHITE);
            }
            butterfly.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            world.spawnEntity(butterfly);
            world.breakBlock(pos, false);
            world.emitGameEvent((Entity)butterfly, GameEvent.BLOCK_DESTROY, pos);
        }
    }

}
