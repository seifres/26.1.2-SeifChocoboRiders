package seifres.seifchocoboriders.network;

public class PacketHandler {
    private static IPacketHandler INSTANCE;

    public static void register(IPacketHandler handler) {
        INSTANCE = handler;
    }

    public static void sendJumpToServer() {
        if (INSTANCE != null) INSTANCE.sendJumpToServer();
    }

    public static void sendTrainingDataRequest(int containerId) {
        if (INSTANCE != null) INSTANCE.sendTrainingDataRequest(containerId);
    }
}
