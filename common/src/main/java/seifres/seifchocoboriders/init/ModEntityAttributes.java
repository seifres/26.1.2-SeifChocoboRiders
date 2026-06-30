package seifres.seifchocoboriders.init;

import seifres.seifchocoboriders.entities.ChocoboEntity;
import seifres.seifchocoboriders.services.Services;

public final class ModEntityAttributes {
    private ModEntityAttributes() {}

    public static void load(){
        Services.ATTRIBUTES.registerEntityAttributes(ModEntityTypes.CHOCOBO_ENTITY, ChocoboEntity::createAttributes);
    }
}
