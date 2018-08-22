package app.jittapan.betterfonts.mixin;

import app.jittapan.betterfonts.BetterFonts;
import app.jittapan.betterfonts.ConfigParser;
import app.jittapan.betterfonts.IFont;
import app.jittapan.betterfonts.renderer.StringCache;
import app.jittapan.betterfonts.renderer.StringRenderer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("unused")
@Mixin(value = FontRenderer.class, priority = 500)
public abstract class MixinFontRenderer
{
    private static boolean dropShadowEnabled = true;

    private StringRenderer stringRenderer;
    private Logger logger = BetterFonts.getLogger();

    @Shadow
    private boolean bidiFlag;

    @Shadow
    abstract String bidiReorder(String text);

    @Shadow
    abstract float renderStringAtPos(String p_renderStringAtPos_1_, float p_renderStringAtPos_2_, float p_renderStringAtPos_3_, int p_renderStringAtPos_4_, boolean p_renderStringAtPos_5_);

    @Inject(method="<init>", at = @At("RETURN"))
    private void constructor(TextureManager textureManager, Font font, CallbackInfo ci) {
        IFont fnt = (IFont)font;
        if(fnt.getType().equals("default") && this.stringRenderer == null) {
            logger.info("Initializing BetterFonts renderer.");
            ConfigParser config = new ConfigParser();
            int fontSize = 18;
            boolean antiAlias = true;
            String fontName = "SansSerif";
            if(config.loadConfig("/config/BetterFonts.cfg")) {
                fontName = config.getFontName(fontName);
                fontSize = config.getFontSize(fontSize);
                antiAlias = config.getBoolean("font.antialias", true);
                dropShadowEnabled = config.getBoolean("font.dropshadow", true);
                logger.info("FontName: " + fontName + ", fontSize = " + fontSize);
                logger.info("Successfully loaded configuration.");
            } else {
                logger.info("Failed to load configuration. Falling back to defaults.");
            }
            int[] colorCodes = new int[32];

//            Hardcoded Hex values
            colorCodes[0] = 0x000000;
            colorCodes[1] = 0x0000AA;
            colorCodes[2] = 0x00AA00;
            colorCodes[3] = 0x00AAAA;
            colorCodes[4] = 0xAA0000;
            colorCodes[5] = 0xAA00AA;
            colorCodes[6] = 0xFFAA00;
            colorCodes[7] = 0xAAAAAA;
            colorCodes[8] = 0x555555;
            colorCodes[9] = 0x5555FF;
            colorCodes[10] = 0x55FF55;
            colorCodes[11] = 0x55FFFF;
            colorCodes[12] = 0xFF5555;
            colorCodes[13] = 0xFF55FF;
            colorCodes[14] = 0xFFFF55;
            colorCodes[15] = 0xFFFFFF;
            for(int i = 16; i < 32; ++i) {
                int tmp = colorCodes[i - 16];
                int r = (tmp >> 16) & 0xFF;
                int g = (tmp >> 8) & 0xFF;
                int b = tmp & 0xFF;
                colorCodes[i] = (r / 4) << 16 | (g / 4) << 8 | (b / 4);
            }

            StringCache cache = new StringCache();
            cache.setDefaultFont(fontName, fontSize, antiAlias);
            stringRenderer = new StringRenderer(cache, colorCodes);
            logger.info("Initialized!");
        }
    }

    @Inject(method="bidiReorder", at = @At("HEAD"), cancellable = true)
    protected void onBidiReorder(String text, CallbackInfoReturnable<String> ci) {
        if(stringRenderer != null) {
            ci.setReturnValue(text);
        }
    }

    @Overwrite
    public int drawStringWithShadow(String p_drawStringWithShadow_1_, float p_drawStringWithShadow_2_, float p_drawStringWithShadow_3_, int p_drawStringWithShadow_4_) {
        GlStateManager.enableAlpha();
        return this.renderString(p_drawStringWithShadow_1_, p_drawStringWithShadow_2_, p_drawStringWithShadow_3_, p_drawStringWithShadow_4_, dropShadowEnabled);
    }

    @Overwrite
    private int renderString(String p_renderString_1_, float p_renderString_2_, float p_renderString_3_, int p_renderString_4_, boolean p_renderString_5_) {
        if (p_renderString_1_ == null) {
            return 0;
        } else {
            if (this.bidiFlag) {
                p_renderString_1_ = this.bidiReorder(p_renderString_1_);
            }

            if ((p_renderString_4_ & -67108864) == 0) {
                p_renderString_4_ |= -16777216;
            }
            if(stringRenderer != null) {
                p_renderString_2_ = stringRenderer.renderString(p_renderString_1_, p_renderString_2_, p_renderString_3_, p_renderString_4_, p_renderString_5_);
            } else {
                if (p_renderString_5_) {
                    this.renderStringAtPos(p_renderString_1_, p_renderString_2_, p_renderString_3_, p_renderString_4_, true);
                }

                p_renderString_2_ = this.renderStringAtPos(p_renderString_1_, p_renderString_2_, p_renderString_3_, p_renderString_4_, false);
            }
            return (int)p_renderString_2_ + (p_renderString_5_ ? 1 : 0);
        }
    }

    @Inject(method = "getStringWidth", at = @At("HEAD"), cancellable = true)
    protected void onGetStringWidth(String text, CallbackInfoReturnable<Integer> ci) {
        if(stringRenderer != null) {
            ci.setReturnValue(stringRenderer.getStringWidth(text));
        }
    }

    @Inject(method="sizeStringToWidth", at = @At("HEAD"), cancellable = true)
    protected void onSizeStringToWidth(String text, int n, CallbackInfoReturnable<Integer> ci) {
        if(stringRenderer != null) {
            ci.setReturnValue(stringRenderer.sizeStringToWidth(text, n));
        }
    }

    @Inject(method="Lnet/minecraft/client/gui/FontRenderer;trimStringToWidth(Ljava/lang/String;IZ)Ljava/lang/String;", at = @At("HEAD"), cancellable = true)
    public void onTrimStringToWidth(String p_trimStringToWidth_1_, int p_trimStringToWidth_2_, boolean p_trimStringToWidth_3_, CallbackInfoReturnable<String> ci) {
        if(stringRenderer != null) {
            ci.setReturnValue(stringRenderer.trimStringToWidth(p_trimStringToWidth_1_, p_trimStringToWidth_2_, p_trimStringToWidth_3_));
        }
    }
}
