package seifres.seifchocoboriders.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import seifres.seifchocoboriders.init.ModAttributes;
import seifres.seifchocoboriders.init.ModEntityTypes;
import seifres.seifchocoboriders.init.ModItems;
import seifres.seifchocoboriders.init.ModSounds;
import seifres.seifchocoboriders.items.ChocoboScrollTrainingItem;
import seifres.seifchocoboriders.services.Services;

import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;


public class ChocoboEntity extends TamableAnimal {
    private static final EntityDataAccessor<Integer> VARIANT;
    private static final EntityDataAccessor<Integer> FLAP_REQUEST_TICKS;
    private static final EntityDataAccessor<Boolean> IS_GLIDING;
    private static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> OWNER_UUID_DATA;
    private int remainingFlaps = 0;
    private boolean holdGliding;
    private int flapCooldownTicks;
    private UUID boundPlayerUuid;
    private boolean trainingMenuOpen = false;
    private final ChocoboTrainingContainer trainingContainer = new ChocoboTrainingContainer(this);

    //Ingredient temptItems = Ingredient.of(ModItems.GYSAHL_GREENS.get());

    public ChocoboEntity(EntityType<? extends TamableAnimal> type, Level level) {
        super( type, level);
    }

    public static AttributeSupplier.@NonNull Builder createAttributes(){
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.12D)
                .add(Attributes.JUMP_STRENGTH, 1.0D)
                .add(Attributes.SAFE_FALL_DISTANCE, 1000D)
                .add(BuiltInRegistries.ATTRIBUTE.wrapAsHolder(ModAttributes.CHOCOBO_FLIGHT_SPEED.get()), 0.02D)
                .add(BuiltInRegistries.ATTRIBUTE.wrapAsHolder(ModAttributes.CHOCOBO_FLAP_CAPACITY.get()), 2.0D);
    }

    @Override
    protected void registerGoals(){
        this.goalSelector.addGoal(0, new StayStillForTrainingGoal());
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new TamableAnimalPanicGoal( 1.25D, DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.addGoal(3, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0D, 10, 2));
        this.goalSelector.addGoal(5, new BreedGoal(this, 1.2D));
      //  this.goalSelector.addGoal(5, new TemptGoal(this, 1.0, temptItems, true));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0D, 120, false));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    //-----Chocobo Interaction---------------------------------------

    @Override
    public @NonNull InteractionResult mobInteract(@NonNull Player player, @NonNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level().isClientSide()) {

            //-----Taming: Right Click on wild adult Chocobo with Gysahl Greens------------------
            if (!isTame() && itemStack.is(ModItems.GYSAHL_GREENS.get()) && !isBaby()) {
                itemStack.consume(1, player);
                this.setOwner(player);
                tame(player);
                this.navigation.stop();
                setTarget(null);
                setOrderedToSit(true);
                level().broadcastEntityEvent(this, EntityEvent.TAMING_SUCCEEDED);
                return InteractionResult.SUCCESS;
            }

            if (isTame() && isOwnedBy(player) && itemStack.is(ModItems.CHOCOBO_TRAINING_WHIP.get())) {
                if (player instanceof ServerPlayer serverPlayer) {
                    this.setTrainingMenuOpen(true);

                    Services.MENU_OPENER.createMenuProviderForChocoboEntity(player, this, getId());
                }
                return InteractionResult.SUCCESS;
            }

            if (isTame() && isOwnedBy(player)) {
                    // ---- Shift + Right-click: toggle sit ---------------------------
                if (player.isShiftKeyDown()){
                    setOrderedToSit(!isOrderedToSit());
                    this.jumping = false;
                    this.navigation.stop();
                    setTarget(null);

                    // Dismount rider if sitting down
                    if (isOrderedToSit()) {
                        ejectPassengers();
                    }
                    return InteractionResult.SUCCESS;
                }

                // ---- Right-click: Mount / dismount ----------------------------
                if (!isOrderedToSit()) {
                    if (!isVehicle()) {
                        setOrderedToSit(false);  //stand up if sitting
                        this.navigation.stop();
                        player.startRiding(this);
                    } else {
                        return super.mobInteract(player, hand);
                    }
                    return InteractionResult.SUCCESS;
                }

                // --- Tamed but not by owner
                if (isTame()) {
                    InteractionResult result = super.mobInteract(player, hand);
                    if (result.consumesAction() || !isOwnedBy(player))
                        return result;
                }
            }
        }

        return isOwnedBy(player) || isTame() || itemStack.is(ModItems.GYSAHL_GREENS.get())
                ? InteractionResult.CONSUME
                : InteractionResult.PASS;
    }

    public void setTrainingMenuOpen(boolean open) {
        this.trainingMenuOpen = open;
        if (open) {
            this.navigation.stop();
            this.getMoveControl().setWait();
        }
    }

    private class StayStillForTrainingGoal extends Goal {
        public StayStillForTrainingGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return trainingMenuOpen;
        }

        @Override
        public boolean canContinueToUse() {
            return trainingMenuOpen;
        }

        @Override
        public void start() {
            navigation.stop();
        }
    }

    public void travel(@NonNull Vec3 movementInput) {

        LivingEntity passenger = this.getControllingPassenger();
        if (this.isAlive() && passenger instanceof Player rider) {
            this.syncRiderRotation(rider);
            float sideways = rider.xxa * 0.5F;
            float forward = rider.zza;
            if (forward < 0.0F) {
                forward *= 0.25F;
            }

            if (!this.isGliding() && this.shouldRecoverFlight()) {
                this.setGliding(true);
            }

            if (this.isGliding()) {
                this.travelAir(rider, sideways, forward);
            } else {
                this.travelGround(rider, sideways, forward, movementInput);
            }

        } else {
            this.setGliding(false);
            this.holdGliding = false;
            super.travel(movementInput);
        }

        if (this.onGround() && this.getDeltaMovement().y <= 0.08) {
            this.setGliding(false);
            this.holdGliding = false;
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.72, 1.0F, 0.72));
            this.hurtMarked = true;
        }

    }

    @Override
    public void tick() {
        super.tick();

        if (this.entityData.get(FLAP_REQUEST_TICKS) > 0) {
            this.entityData.set(FLAP_REQUEST_TICKS, this.entityData.get(FLAP_REQUEST_TICKS) - 1);
        }

        if (flapCooldownTicks > 0) {
            flapCooldownTicks--;
        }
    }

    private void syncRiderRotation(Player rider) {
        float maxTurn = this.isGliding() ? 7.5F : 11.0F;
        if (!this.isGliding() && this.getDeltaMovement().horizontalDistanceSqr() < 0.0025) {
            maxTurn = 6.5F;
        }

        float smoothedYaw = stepAngle(this.getYRot(), rider.getYRot(), maxTurn);
        this.setYRot(smoothedYaw);
        this.setYBodyRot(smoothedYaw);
        this.setYHeadRot(smoothedYaw);
    }

    private static float stepAngle(float current, float target, float maxStep) {
        float delta = Mth.wrapDegrees(target - current);
        return current + Mth.clamp(delta, -maxStep, maxStep);
    }

    private int getMaxFlapsFromAttribute() {
        double value = this.getAttributeValue(BuiltInRegistries.ATTRIBUTE.wrapAsHolder(ModAttributes.CHOCOBO_FLAP_CAPACITY.get()));
        return Mth.floor(value);
    }

    private void travelGround(Player rider, float sideways, float forward, Vec3 movementInput) {
        setRemaining_Flaps(getMaxFlapsFromAttribute());
        this.setNoGravity(false);
        this.setXRot(0.0F);
        double speed = this.getAttributeValue(Attributes.MOVEMENT_SPEED);

        this.setSpeed((float)speed);

        if (!this.shouldLaunch()) {
            super.travel(new Vec3(sideways, movementInput.y, forward));
        } else {
            this.setGliding(true);
            boolean takeoffRun = forward > 0.15F || this.getDeltaMovement().horizontalDistanceSqr() > 0.015;
            double launchBoost = (takeoffRun ? 0.36 : 0.10) ;


            double launchScale = getLaunchScale();

            Vec3 launch = this.horizontalForwardVector().scale(launchBoost);
            Vec3 velocity = this.getDeltaMovement().multiply(.35, 0.0F, .35).add(launch.x, 0.55 * launchScale, launch.z);
            this.setDeltaMovement(velocity);
            this.hurtMarked = true;
            this.fallDistance = 0.0F;
        }
    }

    private double getLaunchScale() {
       double launchStrengthBase = this.getAttributeValue(Attributes.JUMP_STRENGTH);
        return launchStrengthBase / 5.0; // 1.0x at base, scales up as trained
    }

    private boolean shouldLaunch() {
        return this.entityData.get(FLAP_REQUEST_TICKS) > 0 && this.onGround();
    }

    private boolean shouldRecoverFlight() {
        return this.entityData.get(FLAP_REQUEST_TICKS) > 0 && !this.onGround() && this.getDeltaMovement().y < 0.08;
    }


    @Override
    protected float getJumpPower() {
        return 0.42F * this.getBlockJumpFactor();
    }

    private void travelAir(Player rider, float sideways, float forward) {
        this.fallDistance = 0.0F;
        this.setNoGravity(true);
        this.setXRot(Mth.clamp(rider.getXRot() * 0.33F, -22.0F, 22.0F));
        Vec3 velocity = this.getDeltaMovement();
        Vec3 forwardVec = this.horizontalForwardVector();
        Vec3 rightVec = new Vec3(forwardVec.z, 0.0F, -forwardVec.x);

        double flightSpeed = this.getAttributeValue(BuiltInRegistries.ATTRIBUTE.wrapAsHolder(ModAttributes.CHOCOBO_FLIGHT_SPEED.get()));
        double thrust = flightSpeed * (0.32 + Math.max(0.0F, forward) * 0.96);

        velocity = velocity.add(forwardVec.scale(thrust));
        velocity = velocity.add(rightVec.scale(sideways * 0.022));
        double vertical = velocity.y - 0.028;
        if (rider.getXRot() < -12.0F) {
            vertical += 0.018;
        } else if (rider.getXRot() > 18.0F) {
            vertical -= 0.024;
        }

        if (this.consumeFlap()) {
            double launchScale = getLaunchScale();
            vertical = Math.max(vertical, 0.35 * launchScale) + 0.4 * launchScale;
        }

        if (this.holdGliding) {
            vertical -= 0.17;
        }

        vertical = Mth.clamp(vertical, -0.68, 0.92);
        velocity = new Vec3(velocity.x * 0.93, vertical * 0.96, velocity.z * 0.93);
        this.setDeltaMovement(velocity);
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.hurtMarked = true;
        if (this.horizontalCollision) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 0.85, 0.6));
        }

        if (this.onGround() && this.getDeltaMovement().y <= 0.08) {
            this.setGliding(false);
            this.holdGliding = false;
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.72, 1.0F, 0.72));
            this.hurtMarked = true;
        }
    }

    private boolean consumeFlap() {
        if (this.entityData.get(FLAP_REQUEST_TICKS) > 0 && this.flapCooldownTicks <= 0 && remainingFlaps > 0) {
            this.entityData.set(FLAP_REQUEST_TICKS, 0);
            this.flapCooldownTicks = 7;
            --remainingFlaps;
            return true;
        }
        return false;
    }

    private Vec3 horizontalForwardVector() {
        float yawRad = this.getYRot() * ((float)Math.PI / 180F);
        return new Vec3(-Mth.sin(yawRad), 0.0F, Mth.cos(yawRad));
    }

    public float getWingAnimationSpeed() {
        if (!this.isGliding()) {
            return 0.0F;
        } else {
            double horizontalSpeed = this.getDeltaMovement().horizontalDistance();
            return (float)Mth.clamp(1.1 + horizontalSpeed * 3.2 + (this.flapCooldownTicks > 4 ? (double)0.75F : (double)0.0F), 1.1, 2.35);
        }
    }

    public float getWingAnimationStrength() {
        if (!this.isGliding()) {
            return 0.0F;
        } else {
            double verticalMotion = Math.abs(this.getDeltaMovement().y);
            return (float)Mth.clamp(0.45 + verticalMotion * 1.9 + (this.holdGliding ? 0.18 : (double)0.0F) + (this.flapCooldownTicks > 4 ? 0.28 : (double)0.0F), 0.45, 1.0F);
        }
    }

    protected @NonNull Vec3 getPassengerAttachmentPoint(@NonNull Entity passenger, @NonNull EntityDimensions dimensions, float scaleFactor) {
        return new Vec3(0.0F, 0.625F, -0.48F);
    }

    protected void positionRider(@NonNull Entity passenger, Entity.@NonNull MoveFunction moveFunction) {
        if (this.hasPassenger(passenger)) {
            Vec3 offset = (new Vec3(0.0F, 0.625F, -0.48F)).yRot(-this.getYRot() * ((float)Math.PI / 180F));
            moveFunction.accept(passenger, this.getX() + offset.x, this.getY() + offset.y, this.getZ() + offset.z);
        }
    }

    // --- Variants ---------------------------------------
    public ChocoboVariant getVariant() {
        return ChocoboVariant.byID(this.entityData.get(VARIANT));
    }

    public void setVariant(ChocoboVariant variant) {
        this.entityData.set(VARIANT, variant.getID());
    }

    // Assign random variant when first spawned in the world
    @Override
    public SpawnGroupData finalizeSpawn(@NonNull ServerLevelAccessor level,
                                        @NonNull DifficultyInstance difficulty,
                                        @NonNull EntitySpawnReason spawnType,
                                        @Nullable SpawnGroupData spawnData) {
        ChocoboVariant[] variants = ChocoboVariant.values();
        setVariant(variants[this.random.nextInt(variants.length)]);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData);
    }

    @Override
    public void addAdditionalSaveData(@NonNull ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("Variant", this.entityData.get(VARIANT));
        if (this.boundPlayerUuid != null) {
            int[] uuidArray = UUIDUtil.uuidToIntArray(this.boundPlayerUuid);
            output.putIntArray("Owner", uuidArray);
        }
        trainingContainer.save(output.child("Training"));
    }

    @Override
    public void readAdditionalSaveData(@NonNull ValueInput input) {
        super.readAdditionalSaveData(input);
        setVariant(ChocoboVariant.byID(input.getIntOr("Variant", 0)));
        input.child("Training").ifPresent(trainingContainer::load);
        recalculateTrainingBonuses(); // rebuild attribute modifiers from loaded slot contents
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
        super.defineSynchedData(builder); // ← must be first
        builder.define(IS_GLIDING, false);
        builder.define(FLAP_REQUEST_TICKS, 0);
        builder.define(VARIANT, 0);
        builder.define(OWNER_UUID_DATA,Optional.empty());
    }

    @Nullable
    public Player getOwnerPlayer() {
        if (this.level() instanceof ServerLevel serverLevel && this.boundPlayerUuid != null){
            return serverLevel.getPlayerByUUID(this.boundPlayerUuid);
        }
        return null;
    }

    // --- Reward for Taming -------------------------------------------
    @Override
    public void tame(@NonNull Player player) {
        super.tame(player);
        if (!level().isClientSide()) {
            ItemStack rewardItem = new ItemStack(Items.NAME_TAG);
            System.out.println("Pet of " + player.getDisplayName());
            System.out.println("Use this tag to change your Chocobo's name. ");
            if (!player.getInventory().add(rewardItem)) {
                player.drop(rewardItem, false);
            }
        }
    }

     // --- Identify the controlling passenger ------------------------------------
    @Override
    public @Nullable LivingEntity getControllingPassenger() {
        if (getFirstPassenger() instanceof Player p) return p;
        return super.getControllingPassenger();
    }

    // --- Prevent the Chocobo from being pushed while ridden -----------------------------
    @Override
    public boolean isPushable() {
        return !isVehicle();
    }

    // --- Eject rider if the Chocobo sits or dies ----------------------------------------
    @Override
    public void setOrderedToSit(boolean sit) {
        super.setOrderedToSit(sit);
        if (sit) ejectPassengers();
    }

    @Override
    public int getMaxHeadYRot() {
        return 40;
    }

    @Override
    public int getHeadRotSpeed() {
        return 15;
    }

    @Override
    public boolean isFood(@NonNull ItemStack itemStack) {
        return itemStack.is(ModItems.GYSAHL_GREENS.get());
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NonNull ServerLevel serverLevel, @NonNull AgeableMob ageableMob) {
        return new ChocoboEntity(ModEntityTypes.CHOCOBO_ENTITY.get(),serverLevel);
    }

    @Override
    public boolean shouldTryTeleportToOwner() {
        return false; // Disables the teleport check entirely
    }

    protected SoundEvent getAmbientSound() {
        return ModSounds.CHOCOBO_KWEH.get();
    }

    protected SoundEvent getHurtSound(@NonNull DamageSource source) {
        return ModSounds.CHOCOBO_KWEH.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.CHOCOBO_KWEH.get();
    }

    protected void playStepSound(@NonNull BlockPos pos, @NonNull BlockState state) {
        this.playSound(SoundEvents.HORSE_STEP_WOOD, 0.12F, 1.35F);
    }

    public void recalculateTrainingBonuses() {
        for (ChocoboStat stat : ChocoboStat.values()) {
            AttributeInstance instance = this.getAttribute(stat.attribute());
            if (instance == null) continue;

            double total = 0.0;
            int firstSlot = stat.ordinal() * ChocoboTrainingContainer.SLOTS_PER_STAT;
            for (int i = firstSlot; i < firstSlot + ChocoboTrainingContainer.SLOTS_PER_STAT; i++) {
                ItemStack stack = trainingContainer.getItem(i);
                if (stack.getItem() instanceof ChocoboScrollTrainingItem feather) {
                    total += stat.amountForLevel(feather.getLevel());
                }
            }

            instance.removeModifier(stat.modifierId());
            if (total > 0) {
                instance.addPermanentModifier(new AttributeModifier(stat.modifierId(), total, AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }

    public ChocoboTrainingContainer getTrainingContainer() {
        return trainingContainer;
    }

    public void applyFlightInput(boolean flapPressed, boolean holdGliding) {
        if (flapPressed) {
            this.entityData.set(FLAP_REQUEST_TICKS, 5);
        }
        this.holdGliding = holdGliding;
    }

    public boolean isGliding() {
        return (Boolean)this.entityData.get(IS_GLIDING);
    }

    protected void setGliding(boolean gliding) {
        this.entityData.set(IS_GLIDING, gliding);
        this.setNoGravity(gliding);
        if (!gliding) {
            this.setXRot(0.0F);
            this.fallDistance = (double)0.0F;
        }
    }

    public boolean isOwnedBy(@NonNull LivingEntity entity) {
        return entity == this.getOwner();
    }

    public void setRemaining_Flaps(int newFlaps){
        remainingFlaps = newFlaps;
    }

    public int getRemainingFlaps() {
        return remainingFlaps;
    }

    public int getMax_Flaps() {
        return getMaxFlapsFromAttribute();
    }

    public boolean isTrainingMenuOpen() {
        return trainingMenuOpen;
    }

    static {
        VARIANT = SynchedEntityData.defineId(ChocoboEntity.class, EntityDataSerializers.INT);
        IS_GLIDING = SynchedEntityData.defineId(ChocoboEntity.class, EntityDataSerializers.BOOLEAN);
        FLAP_REQUEST_TICKS = SynchedEntityData.defineId(ChocoboEntity.class, EntityDataSerializers.INT);
        OWNER_UUID_DATA = SynchedEntityData.defineId(ChocoboEntity.class,
                EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);
    }
}

