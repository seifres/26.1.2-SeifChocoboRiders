package seifres.seifchocoboriders.services;

import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.services.types.IAttributeRegistryHelper;
import seifres.seifchocoboriders.services.types.IMenuOpener;
import seifres.seifchocoboriders.services.types.IPlatformHelper;
import seifres.seifchocoboriders.services.types.IRegistryHelper;

import java.util.ServiceLoader;

public class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IRegistryHelper REGISTRY = load(IRegistryHelper.class);
    public static final IAttributeRegistryHelper ATTRIBUTES = load(IAttributeRegistryHelper.class);
    public static final IMenuOpener MENU_OPENER = load(IMenuOpener.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz, Services.class.getClassLoader())
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}