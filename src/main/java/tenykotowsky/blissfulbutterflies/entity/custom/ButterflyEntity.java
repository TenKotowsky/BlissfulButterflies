package tenykotowsky.blissfulbutterflies.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import tenykotowsky.blissfulbutterflies.entity.ModEntities;

import java.util.EnumSet;

public class ButterflyEntity extends PathAwareEntity implements GeoEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public ButterflyEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);

        this.moveControl = new FlightMoveControl(this, 10, true);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return FlyingEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2.0D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 1.2f);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_GENERIC_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GENERIC_DEATH;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new FlyRandomlyGoal(this));
    }

    static class FlyRandomlyGoal
            extends Goal {
        private final ButterflyEntity butterfly;

        public FlyRandomlyGoal(ButterflyEntity butterfly) {
            this.butterfly = butterfly;
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            MoveControl moveControl = this.butterfly.getMoveControl();
            BlockPos entityPos = this.butterfly.getBlockPos();
            World world = this.butterfly.getEntityWorld();

            int highestY = -1;
            for (int y = entityPos.getY(); y >= 0; y--) {
                BlockPos blockPos = new BlockPos(entityPos.getX(), y, entityPos.getZ());
                BlockState blockState = world.getBlockState(blockPos);
                if (!blockState.isAir()) {
                    highestY = y;
                    break;
                }
            }

            // Check if the entity is below a certain height threshold
            if (this.butterfly.getY() < highestY - 2.0) {
                // Allow the entity to start flying randomly
                if (!moveControl.isMoving()) {
                    return true;
                } else if (this.butterfly.getRandom().nextInt(10) == 0) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public boolean shouldContinue() {
            if (this.butterfly.getRandom().nextInt(10) == 0) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void start() {
            Random random = this.butterfly.getRandom();
            double d = this.butterfly.getX() + (double)((random.nextFloat() * 2.0f - 1.0f) * 16.0f);
            double e = this.butterfly.getY() + (double)((random.nextFloat() * 2.0f - 1.0f) * 16.0f);
            double f = this.butterfly.getZ() + (double)((random.nextFloat() * 2.0f - 1.0f) * 16.0f);

            double maxHeight = this.butterfly.getY() + 3.0;
            if (e > maxHeight) {
                e = maxHeight;
            }

            this.butterfly.getMoveControl().moveTo(d, e, f, this.butterfly.getAttributeBaseValue(EntityAttributes.GENERIC_FLYING_SPEED));
        }
    }

    @Nullable
    public ButterflyEntity createChild(ServerWorld world, PathAwareEntity entity) {
        return ModEntities.BUTTERFLY.create(world);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.monarch_butterfly.fly", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
