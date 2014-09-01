package me.isuzutsuki.betterfonts;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.io.File;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion(value = "1.7.10")
public class BetterFontsCore implements IFMLLoadingPlugin{
	
	public static File location;
	
	public String[] getASMTransformerClass() {
	return new String[]{BTFClassTransformer.class.getName()};
	}
	
	@Override
	public void injectData(Map<String, Object> data) {
	location = (File) data.get("coremodLocation");
	}

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

	@Override
	public String getAccessTransformerClass() {
		// TODO Auto-generated method stub
		return null;
	}


	
}
