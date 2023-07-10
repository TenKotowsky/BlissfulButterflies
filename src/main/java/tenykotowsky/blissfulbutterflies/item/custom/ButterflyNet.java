package tenykotowsky.blissfulbutterflies.item.custom;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.ParseResults;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.message.SentMessage;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import tenykotowsky.blissfulbutterflies.BlissfulButterflies;
import tenykotowsky.blissfulbutterflies.entity.ModEntities;
import tenykotowsky.blissfulbutterflies.entity.custom.ButterflyEntity;
import tenykotowsky.blissfulbutterflies.entity.variant.ButterflyVariant;

import javax.imageio.plugins.tiff.BaselineTIFFTagSet;
import java.util.Objects;
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
            String variant = itemStack.getOrCreateNbt().get("Variant").toString();
            BlissfulButterflies.LOGGER.info(variant);

            ButterflyEntity butterfly = new ButterflyEntity(ModEntities.BUTTERFLY, world);
            NbtCompound nbtComp = new NbtCompound();
            switch (variant) {
                case "MONARCH" -> nbtComp.putInt("Variant", 0);
                case "BLUE" -> nbtComp.putInt("Variant", 1);
                case "PURPLE" -> nbtComp.putInt("Variant", 2);
                case "ORANGE" -> nbtComp.putInt("Variant", 3);
                case "WHITE" -> nbtComp.putInt("Variant", 4);
            }
            butterfly.writeNbt(nbtComp);

            butterfly.setPosition(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());

            world.spawnEntity(butterfly);
            world.emitGameEvent((Entity)context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);

            itemStack.getOrCreateNbt().remove("Variant");
            context.getPlayer().playSound(SoundEvents.BLOCK_BEEHIVE_EXIT, 1.0f, 1.0f);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
