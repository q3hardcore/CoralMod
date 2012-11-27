package net.minecraft.src;

public class BlockCoral2 extends Block {

	public BlockCoral2(int blockID, int type) {
		super(blockID, type, Material.ground);
		setCreativeTab(CreativeTabs.tabBlock);
	}

	/**
	 * Grabs the current texture file used for this block
	 */
	@Override
	public String getTextureFile() {
		if(mod_coral.classic) {
			return super.getTextureFile();
		} else {
			return "/nandonalt/CoralMod/blocks.png";
		}
	}

}
