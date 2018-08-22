package app.jittapan.betterfonts;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dimdev.rift.listener.MinecraftStartListener;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;
import org.dimdev.riftloader.listener.InitializationListener;

@SuppressWarnings("unused")
public class BetterFonts implements InitializationListener {

    private static final Logger log = LogManager.getLogger("BetterFonts");

    @Override
    public void onInitialization() {
        log.info("BetterFonts-Rift is loading!");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.betterfonts.json");
    }

    public static Logger getLogger() {
        return log;
    }
}
