package tenykotowsky.blissfulbutterflies.entity.custom;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import tenykotowsky.blissfulbutterflies.entity.ModEntities;
import tenykotowsky.blissfulbutterflies.entity.variant.ButterflyVariant;

import java.util.EnumSet;

public class ButterflyEntity extends PathAwareEntity implements GeoEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public ButterflyEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);

        this.moveControl = new FlightMoveControl(this, 12, true);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.isLogicalSideForUpdatingMovement()) {
            if (this.isTouchingWater()) {
                this.updateVelocity(0.04f, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.8f));
            } else if (this.isInLava()) {
                this.updateVelocity(0.04f, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.5));
            } else {
                float f = 0.91f;
                if (this.isOnGround()) {
                    f = this.getWorld().getBlockState(this.getVelocityAffectingPos()).getBlock().getSlipperiness() * 0.91f;
                }
                float g = 0.16277137f / (f * f * f);
                f = 0.91f;
                if (this.isOnGround()) {
                    f = this.getWorld().getBlockState(this.getVelocityAffectingPos()).getBlock().getSlipperiness() * 0.91f;
                }
                this.updateVelocity(this.isOnGround() ? 0.1f * g : 0.02f, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(f));
            }
        }
        this.updateLimbs(false);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return FlyingEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2.0D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 1.4f);
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
        this.goalSelector.add(1, new FlyRandomlyGoal(this));}

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
            return !moveControl.isMoving();
        }

        @Override
        public boolean shouldContinue() {
            return false;
        }

        @Override
        public void start() {
            Random random = this.butterfly.getRandom();
            double d = this.butterfly.getX() + (double)((random.nextFloat() * 2.0f - 1.0f) * 64.0f) * 8;
            double e = this.butterfly.getY() + (double)((random.nextFloat() * 2.0f - 1.0f) * 2.0f);
            double f = this.butterfly.getZ() + (double)((random.nextFloat() * 2.0f - 1.0f) * 64.0f) * 8;
            this.butterfly.getMoveControl().moveTo(d, e, f, this.butterfly.getAttributeBaseValue(EntityAttributes.GENERIC_FLYING_SPEED));
        }
    }

    @Nullable
    public ButterflyEntity createChild(ServerWorld world, PathAwareEntity entity) {
        return ModEntities.BUTTERFLY.create(world);
    }

    public static boolean canSpawn(EntityType<? extends LivingEntity> type, ServerWorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(BlockTags.ANIMALS_SPAWNABLE_ON);
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

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
    }

    /* VARIANTS */

    private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
            DataTracker.registerData(ButterflyEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
                                 SpawnReason spawnReason, @Nullable EntityData entityData,
                                 @Nullable NbtCompound entityNbt) {
        ButterflyVariant variant = Util.getRandom(ButterflyVariant.values(), this.random);
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public ButterflyVariant getVariant() {
        return ButterflyVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(ButterflyVariant variant) {
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }
}
