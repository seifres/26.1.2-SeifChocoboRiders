package seifres.seifchocoboriders;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import seifres.seifchocoboriders.services.NeoForgeRegistryHelper;
import seifres.seifchocoboriders.services.Services;
import seifres.seifchocoboriders.services.types.IAttributeRegistryHelper;

@Mod(Constants.MOD_ID)
public class SeifChocoboRiders {

    public SeifChocoboRiders(IEventBus eventBus) {

        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();
        eventBus.addListener(SeifChocoboRiders::onEntityAttributeCreation);
        eventBus.addListener(SeifChocoboRidersDatagen::onGatherClientData);
        NeoForgeRegistryHelper.register(eventBus);
    }

    private static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        Services.ATTRIBUTES.applyEntityAttributeRegistrations(new IAttributeRegistryHelper.EntityAttributeRegistrar() {
            @Override
            public <T extends LivingEntity> void register(EntityType<T> entityType, AttributeSupplier.Builder builder) {
                event.put(entityType, builder.build());
            }
        });
    }

}