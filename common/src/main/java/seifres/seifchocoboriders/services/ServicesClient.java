package seifres.seifchocoboriders.services;

import seifres.seifchocoboriders.services.types.client.IClientRegistryHelper;

public class ServicesClient {
    public static final IClientRegistryHelper CLIENT_REGISTRY = Services.load(IClientRegistryHelper.class);
    private ServicesClient(){

    }
}
