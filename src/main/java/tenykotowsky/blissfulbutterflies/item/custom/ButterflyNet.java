package tenykotowsky.blissfulbutterflies.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import tenykotowsky.blissfulbutterflies.BlissfulButterflies;
import tenykotowsky.blissfulbutterflies.entity.ModEntities;
import tenykotowsky.blissfulbutterflies.entity.custom.ButterflyEntity;
import tenykotowsky.blissfulbutterflies.entity.variant.ButterflyVariant;

public class ButterflyNet extends Item {
    public ButterflyNet(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!(world instanceof ServerWorld)) {
            return ActionResult.SUCCESS;
        }
        ItemStack itemStack = context.getStack();
        if (itemStack.getOrCreateNbt().get("Variant") != null) {
            BlockPos blockPos = context.getBlockPos();
            Direction direction = context.getSide();
            BlockState blockState = world.getBlockState(blockPos);

            BlockPos blockPos2 = blockState.getCollisionShape(world, blockPos).isEmpty() ? blockPos : blockPos.offset(direction);
            String variant = itemStack.getOrCreateNbt().getString("Variant"); // Use getString instead of toString
            BlissfulButterflies.LOGGER.info(variant);

            ButterflyEntity butterfly = new ButterflyEntity(ModEntities.BUTTERFLY, world);
            switch (variant) {
                case "MONARCH" -> butterfly.setVariant(ButterflyVariant.MONARCH);
                case "BLUE" -> butterfly.setVariant(ButterflyVariant.BLUE);
                case "PURPLE" -> butterfly.setVariant(ButterflyVariant.PURPLE);
                case "ORANGE" -> butterfly.setVariant(ButterflyVariant.ORANGE);
                case "WHITE" -> butterfly.setVariant(ButterflyVariant.WHITE);
            }

            butterfly.setPosition(blockPos2.getX() + 0.5, blockPos2.getY(), blockPos2.getZ() + 0.5);

            world.spawnEntity(butterfly);
            world.emitGameEvent((Entity) context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);

            itemStack.getOrCreateNbt().remove("Variant");
            itemStack.getOrCreateNbt().putInt("CustomModelData", 0);
            context.getPlayer().playSound(SoundEvents.BLOCK_BEEHIVE_EXIT, SoundCategory.PLAYERS, 1.0f, 1.0f);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
