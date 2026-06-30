package seifres.seifchocoboriders.init.client;

import seifres.seifchocoboriders.client.model.ChocoboEntityModel;
import seifres.seifchocoboriders.services.ServicesClient;

public class ModModelLayers {
    private ModModelLayers(){}

    public static void load(){
        ServicesClient.CLIENT_REGISTRY.registerModelLayer(ChocoboEntityModel.LAYER_LOCATION, ChocoboEntityModel::createBodyLayer);

    }
}
