package me.isuzutsuki.betterfonts;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class BTFClassTransformer implements net.minecraft.launchwrapper.IClassTransformer{

	@Override
	public byte[] transform(String arg0, String arg1, byte[] arg2) {
		if (arg0.equals("bag") || arg0.equals("net.minecraft.client.gui.FontRenderer") || arg0.equals("bed") || arg0.equals("net.minecraft.client.gui.inventory.GuiEditSign")) {
			System.out.println("[BetterFonts] Transformer is about to patch : " + arg0);
			arg2 = patchClassInJar(arg0, arg2, arg0, BetterFontsCore.location);
		}
			return arg2;

	}

	public byte[] patchClassInJar(String name, byte[] bytes, String ObfName, File location){
		try {
			//open the jar as zip
			ZipFile zip = new ZipFile(location);			
			ZipEntry entry = zip.getEntry(name.replace('.', '/') + ".class");


			if (entry == null) {
			System.out.println(name + " not found in " + location.getName());
			} else {

			//serialize the class file into the bytes array
				InputStream zin = zip.getInputStream(entry);
				int size = (int) entry.getSize();
				byte[] newbytes = new byte[size];
				int pos = 0;
				while (pos < size) {
					int len = zin.read(newbytes,pos,size-pos);
					if (len == 0)
						throw new IOException();
					pos += len;
				}
				if(!newbytes.equals(bytes))bytes=newbytes;
				zin.close();
			System.out.println("[" + "BetterFonts" + "]: " + "Class " + name + " patched!");
			}
			zip.close();
			} catch (Exception e) {
			throw new RuntimeException("Error overriding " + name + " from " + location.getName(), e);
			}

			//return the new bytes
			return bytes;
	}
}
