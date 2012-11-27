package net.minecraft.src;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCoralReef extends GuiScreen
{
	private static final String[] DESCRIPTIONS = new String[] {"CoralReef Gen.", "Spiky Coral", "Bubbles", "Growing", "Average Size", "Ocean Only", "ModLoader mode", "Place on land"};
	private static final String[] STATES = new String[] {"OFF", "ON"};
	private static final String[] SIZES = new String[] {"Small", "Normal", "Big"};

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		int j = 0;
		for(int i = 0; i < DESCRIPTIONS.length; i++) {
			controlList.add(new GuiSmallButton(i, width / 2 - 155 + j % 2 * 160, height / 6 + 24 * (j >> 1), getButtonDesc(i)));
			j++;
		}

		if(inGame()) {
			controlList.add(new GuiSmallButton(j, width / 2 - 155 + 8 % 2 * 160 + 80, height / 6 + 24 * (8 >> 1), "Back to Game"));
		} else {
			controlList.add(new GuiButton(j, width / 2 - 100, height / 6 + 24 * (8 >> 1), "Done"));
		}
	}

	private String getButtonDesc(int btnIndex) {
		String btnDescription = DESCRIPTIONS[btnIndex];

		switch (btnIndex) {
			case 0:
				return btnDescription + ": " + getState(mod_coral.enable);
			case 1:
				return btnDescription + ": " + getState(mod_coral.spiky);
			case 2:
				return btnDescription + ": " + getState(mod_coral.bubble);
			case 3:
				return btnDescription + ": " + getState(mod_coral.grow);
			case 4:
				return btnDescription + ": " + getSize(mod_coral.size);
			case 5:
				return btnDescription + ": " + getState(mod_coral.ocean);
			case 6:
				return btnDescription + ": " + getState(mod_coral.classic);
			case 7:
				return btnDescription + ": " + getState(mod_coral.land);
			default:
				return "Unknown";
		}
	}

	private String getSize(int btnState) {
		return SIZES[btnState];
	}

	private String getState(boolean btnState) {
		if(btnState) {
			return STATES[1];
		} else {
			return STATES[0];
		}
	}

	/**
	 * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
	 */
	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		switch (par1GuiButton.id)
		{
			case 0:
				mod_coral.enable ^= true;
				break;
			case 1:
				mod_coral.spiky ^= true;
				break;
			case 2:
				mod_coral.bubble ^= true;
				break;
			case 3:
				mod_coral.grow ^= true;
				break;
			case 4:
				mod_coral.size = (mod_coral.size + 1) % 3;
				break;
			case 5:
				mod_coral.ocean ^= true;
				break;
			case 6:
				if(!mod_coral.checkHasForge() || !mod_coral.checkEnoughSlots()) {
					return;
				}

				mod_coral.classic ^= true;
				mod_coral.toggleOverrides(true);
				break;
			case 7:
				mod_coral.land ^= true;
				break;
			case 8:
				if(inGame()) {
					mc.displayGuiScreen((GuiScreen)null);
					mc.setIngameFocus();
				} else {
					this.mc.displayGuiScreen(new GuiOptions(null, this.mc.gameSettings));
				}
				break;
			default:
				System.out.println("Nothing changed.");
				break;
		}

		if(par1GuiButton.id < DESCRIPTIONS.length) {
			par1GuiButton.displayString = getButtonDesc(par1GuiButton.id);
			mod_coral.updateSettings();
		} else {
			System.out.println("Settings closed.");
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		String status = mod_coral.checkHasForge()?"(Forge)":"(ModLoader)";

		if(!mod_coral.checkSettingsLoaded()) {
			status = "(temporary settings)";
		} else if(!inGame()) {
			status = "(Options)";
		} else if(!mc.isSingleplayer()) {
			status = "(disabled)";
		}

		drawCenteredString(fontRenderer, "CoralReef Mod " + status, width / 2, 20, 16777215);

		super.drawScreen(par1, par2, par3);
	}

	private boolean inGame() {
		return mc.theWorld != null;
	}

}
