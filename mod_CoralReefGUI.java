package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import static cpw.mods.fml.common.Side.CLIENT;

import cpw.mods.fml.common.asm.SideOnly;

public class mod_CoralReefGUI extends BaseMod {
	
	public static boolean clientSide;
	public static KeyBinding openKey;

	@Override
	public String getName() {
		return "CoralReef GUI";
	}

	@Override
	public String getVersion() {
		return "1.4.3";
	}

	@SideOnly(CLIENT)
	@Override
	public void keyboardEvent(KeyBinding event) {
		Minecraft game = ModLoader.getMinecraftInstance();
		if (game.currentScreen == null) {
			game.displayGuiScreen(new GuiCoralReef());
		}
	}
	
	@Override
	public void load() {
		if(!mod_coral.clientSide) {
			System.out.println(getName() + " disabled.");
			return;
		}
		startCoralThread();
		openKey = new KeyBinding("Coral Reef GUI", Keyboard.KEY_C);
		ModLoader.registerKey(this, openKey, false);
	}

	private void startCoralThread() {
		Thread thread = new ThreadCoralReefGUIHelper(Minecraft.getMinecraft(), "Coral Reef GUI Helper Thread");
		thread.setDaemon(true);
		thread.start();
	}

}