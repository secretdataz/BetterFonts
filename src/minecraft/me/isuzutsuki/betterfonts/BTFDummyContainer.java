package me.isuzutsuki.betterfonts;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class BTFDummyContainer extends DummyModContainer{

	public BTFDummyContainer() {
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = "BetterFonts";
		meta.name = "BetterFonts";
		meta.version = "1.0.2"; //String.format("%d.%d.%d.%d", majorVersion, minorVersion, revisionVersion, buildVersion);
		meta.credits = "thvortex for original codes";
		meta.authorList = Arrays.asList("iSuzutsuki");
		meta.description = "";
		meta.url = "http://www.siamminecraft.com";
		meta.updateUrl = "";
		meta.screenshots = new String[0];
		meta.logoFile = "";

	}
	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
	return true;
	}
	@Subscribe
public void modConstruction(FMLConstructionEvent evt){

}

@Subscribe
public void init(FMLInitializationEvent evt) {

}

@Subscribe
public void preInit(FMLPreInitializationEvent evt) {

}

@Subscribe
public void postInit(FMLPostInitializationEvent evt) {

}
	
}
