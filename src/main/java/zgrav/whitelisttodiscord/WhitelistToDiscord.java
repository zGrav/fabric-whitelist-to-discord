package zgrav.whitelisttodiscord;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WhitelistToDiscord implements ModInitializer {
    private static final String MODID = "zgrav-fabric-whitelist-to-discord";
    private static final Logger LOGGER = LogManager.getLogger(MODID);

    @Override
    public void onInitialize() {
        LOGGER.info("Loaded!");
    }
}
