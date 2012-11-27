package net.minecraft.src;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class ThreadCoralReefGUIHelper extends Thread
{
	/** A reference to the Minecraft object. */
	final Minecraft mc;

	private static boolean listening = true;

	public ThreadCoralReefGUIHelper(Minecraft par1Minecraft, String par2Str) {
		super(par2Str);
		this.mc = par1Minecraft;
	}

	public void run() {
		while (listening) {
			if(this.mc == null || !Keyboard.isCreated()) {
				listening = false;
				System.out.println("Shutting down Coral Reef GUI Helper Thread.");
				return;
			}
			try {
				if(this.mc.currentScreen instanceof GuiOptions) {
					if(Keyboard.isKeyDown(mod_CoralReefGUI.openKey.keyCode))
						this.mc.displayGuiScreen(new GuiCoralReef());
				}
			} catch (Throwable t) {
				t.printStackTrace();
				listening = false;
			}
			try {
				sleep(50L);
			} catch (InterruptedException ie) {
				;
			}
		}
	}
}
