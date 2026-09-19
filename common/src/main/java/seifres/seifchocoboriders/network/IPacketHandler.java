package seifres.seifchocoboriders.network;

public interface IPacketHandler {
    void sendJumpToServer();

    void sendTrainingDataRequest(int containerId);
}
