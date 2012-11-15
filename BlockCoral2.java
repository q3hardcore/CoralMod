package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Material;

public class BlockCoral2 extends Block {

	public BlockCoral2(int blockID, int type) {
		super(blockID, Material.water);
		blockIndexInTexture = type;
	}
}
