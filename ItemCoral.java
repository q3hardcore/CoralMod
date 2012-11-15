package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.ItemBlock;

public class ItemCoral extends ItemBlock {

	public ItemCoral(int itemID) {
		super(itemID);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getIconFromDamage(int damageValue) {
		return Block.blocksList[shiftedIndex].getBlockTextureFromSideAndMetadata(2, damageValue);
	}

	@Override
	public int getMetadata(int itemDamage) {
		return itemDamage;
	}
}
