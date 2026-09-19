package seifres.seifchocoboriders;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.common.NeoForge;
import seifres.seifchocoboriders.entities.ChocoboEntity;
import seifres.seifchocoboriders.entities.ChocoboTrainingScreen;
import seifres.seifchocoboriders.init.ModMenus;
import seifres.seifchocoboriders.network.ChocoboJumpPacket;
import seifres.seifchocoboriders.network.PacketHandler;
import seifres.seifchocoboriders.services.NeoForgePacketHandler;
import seifres.seifchocoboriders.services.ServicesClient;
import seifres.seifchocoboriders.sounds.NeoForgeModSounds;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public final class SeifChocoboRidersClient {
    private SeifChocoboRidersClient(){}
    private static boolean previousJumpPressed = false;
    private static Boolean controllableLoaded = null;
    private static Field controllableJumpField = null;
    private static Method controllableIsButtonDownMethod = null;

    public static void init(IEventBus eventBus){
        NeoForge.EVENT_BUS.addListener(SeifChocoboRidersClient::clientTick);

        NeoForgeModSounds.SOUND_EVENTS.register(eventBus); // register to bus
        NeoForgeModSounds.init();

        PacketHandler.register(new NeoForgePacketHandler());
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        SeifChocoboRidersClientCommon.init();
        ServicesClient.CLIENT_REGISTRY.applyEntityRendererRegistrations(event::registerEntityRenderer);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event){
        SeifChocoboRidersClientCommon.init();
        ServicesClient.CLIENT_REGISTRY.applyModelLayerRegistrations(event::registerLayerDefinition);
    }

    @SubscribeEvent
    public static void onRegisterScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.CHOCOBO_TRAINING.get(), ChocoboTrainingScreen::new);
    }

    private static void tickFlightControls(Minecraft minecraft) {
        if (minecraft.player != null) {
            Entity var2 = minecraft.player.getVehicle();
            if (!(var2 instanceof ChocoboEntity chocobo)) {
                previousJumpPressed = false;
            } else {
                KeyMapping jumpKey = minecraft.options.keyJump;
                boolean jumpPressed = jumpKey.isDown() || isControllableJumpPressed();
                boolean flapPressed = jumpPressed && !previousJumpPressed;
                chocobo.applyFlightInput(flapPressed, false);
                if (flapPressed) {
                    ClientPacketDistributor.sendToServer(new ChocoboJumpPacket(true, false), new CustomPacketPayload[0]);
                }

                previousJumpPressed = jumpPressed;
            }
        } else {
            previousJumpPressed = false;
        }
    }

    private static boolean isControllableJumpPressed() {
        if (controllableLoaded == null) {
            controllableLoaded = false;

            try {
                Class<?> buttonBindingsClass = Class.forName("com.mrcrayfish.controllable.client.binding.ButtonBindings");
                Class<?> buttonBindingClass = Class.forName("com.mrcrayfish.controllable.client.binding.ButtonBinding");
                controllableJumpField = buttonBindingsClass.getField("JUMP");
                controllableIsButtonDownMethod = buttonBindingClass.getMethod("isButtonDown");
                controllableLoaded = true;
            } catch (ReflectiveOperationException var2) {
            }
        }

        if (Boolean.TRUE.equals(controllableLoaded) && controllableJumpField != null && controllableIsButtonDownMethod != null) {
            try {
                Object jumpBinding = controllableJumpField.get((Object)null);
                return jumpBinding != null && Boolean.TRUE.equals(controllableIsButtonDownMethod.invoke(jumpBinding));
            } catch (ReflectiveOperationException var3) {
                return false;
            }
        } else {
            return false;
        }
    }

    private static void clientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        tickFlightControls(minecraft);
    }
}
