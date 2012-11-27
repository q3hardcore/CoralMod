package net.minecraft.src;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class ItemCoral extends ItemBlock {

	public ItemCoral(int itemID) {
		super(itemID);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getIconFromDamage(int damageValue) {
		return Block.blocksList[shiftedIndex].getBlockTextureFromSideAndMetadata(2, damageValue);
	}

	@Override
	public int getMetadata(int itemDamage) {
		return itemDamage;
	}

	/**
	 * Grabs the current texture file used for this block
	 */
	@Override
	public String getTextureFile() {
		if(mod_coral.classic) {
			return super.getTextureFile();
		} else {
			return "/nandonalt/CoralMod/items.png";
		}
	}

}
