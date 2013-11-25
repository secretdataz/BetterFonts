package me.isuzutsuki.betterfonts;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion(value = "1.6.4")
public class BetterFontsCore implements IFMLLoadingPlugin{
	
	public static File location;
	
	public String[] getASMTransformerClass() {
	return new String[]{BTFClassTransformer.class.getName()};
	}
	
	@Override
	public void injectData(Map<String, Object> data) {
	location = (File) data.get("coremodLocation");
	}

	@Override
	public	String[] getLibraryRequestClass() {
		return null;
	}

	@Override
	public String getModContainerClass() {
		return BTFDummyContainer.class.getName();
	}

	@Override
	public String getSetupClass() {
		return null;
	}


	
}
