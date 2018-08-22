package app.jittapan.betterfonts.mixin;

import app.jittapan.betterfonts.IFont;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Font.class)
public abstract class MixinFont implements IFont {

    @Shadow
    private ResourceLocation field_211192_d;

    public String getType() {
        return field_211192_d.getPath();
    }
}
