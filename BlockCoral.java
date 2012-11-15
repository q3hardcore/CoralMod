package net.minecraft.src;

import java.util.Random;

public class BlockCoral extends Block {

	public int sprite;
	public int sprite2;
	public int sprite3;
	public int sprite4;
	public int sprite5;
	public int sprite6;
	public int type;

	/* UNUSED
	public int ia;
	public int id;
	*/

	public BlockCoral(int blockID, int type, int sprite1, int sprite2, int sprite3, int sprite4, int sprite5, int sprite6) {
		super(blockID, Material.water);
		blockIndexInTexture = sprite1;
		this.sprite2 = sprite2;
		this.sprite3 = sprite3;
		this.sprite4 = sprite4;
		this.sprite5 = sprite5;
		this.sprite6 = sprite6;
		this.type = type;
		if(type == 1) {
			float f = 0.375F;
			setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
		} else if(type == 6) {
			float f = 0.5F;
			setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
		}

		setTickRandomly(true);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		if(mod_coral.grow) {
			super.updateTick(world, x, y, z, random);
			int metadata = world.getBlockMetadata(x, y, z);
			if(metadata == 1 || metadata == 4) {
				int offset = 1;
				while(world.getBlockId(x, y - offset, z) == blockID)
					offset++;

				int rand = random.nextInt(100);
				if(rand == 0 && (world.getBlockId(x, y + 1, z) == 8 || world.getBlockId(x, y + 1, z) == 9)
				&& (world.getBlockId(x, y + 2, z) == 8 || world.getBlockId(x, y + 2, z) == 9) && offset < 4) {
					world.setBlockAndMetadataWithNotify(x, y + 1, z, blockID, metadata);
				}
			}
		}

	}

	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		if(mod_coral.bubble) {
			Random rand = world.rand;
			double d1 = 0.0625D;

			for(int i = 0; i < 6; i++) {
				double d2 = (double)((float)x + rand.nextFloat());
				double d3 = (double)((float)y + rand.nextFloat());
				double d4 = (double)((float)z + rand.nextFloat());
				if(i == 0 && !world.isBlockOpaqueCube(x, y + 1, z)) {
					d3 = (double)(y + 1) + d1;
				}

				if(i == 1 && !world.isBlockOpaqueCube(x, y - 1, z)) {
					d3 = (double)(y + 0) - d1;
				}

				if(i == 2 && !world.isBlockOpaqueCube(x, y, z + 1)) {
					d4 = (double)(z + 1) + d1;
				}

				if(i == 3 && !world.isBlockOpaqueCube(x, y, z - 1)) {
					d4 = (double)(z + 0) - d1;
				}

				if(i == 4 && !world.isBlockOpaqueCube(x + 1, y, z)) {
					d2 = (double)(x + 1) + d1;
				}

				if(i == 5 && !world.isBlockOpaqueCube(x - 1, y, z)) {
					d2 = (double)(x + 0) - d1;
				}

				if(d2 < (double)x || d2 > (double)(x + 1) || d3 < 0.0D || d3 > (double)(y + 1) || d4 < (double)z || d4 > (double)(z + 1)) {
					world.spawnParticle("bubble", d2, d3, d4, 0.0D, 0.0D, 0.0D);
				}
			}
		}

	}

	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}

	@Override
	public int idDropped(int metadata, Random random, int chance) {
		return blockID;
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int paramInt1, int paramInt2, int paramInt3) {
		int i = world.getBlockId(paramInt1, paramInt2 - 1, paramInt3);
		int i2 = world.getBlockMetadata(paramInt1, paramInt2 - 1, paramInt3);
		int i4 = world.getBlockMetadata(paramInt1, paramInt2 + 1, paramInt3);
		int i3 = world.getBlockMetadata(paramInt1, paramInt2, paramInt3);
		int x = paramInt1;
		int y = paramInt2;
		int z = paramInt3;

		if (i3 == 1) {
			if (((world.getBlockId(x, y - 1, z) == mod_coral.Coral1.blockID) && (world.getBlockMetadata(paramInt1, paramInt2 - 1, paramInt3) == 1))
			|| (world.getBlockId(x, y - 1, z) == mod_coral.Coral2.blockID) || (world.getBlockId(x, y - 1, z) == mod_coral.Coral3.blockID)) {
				return true;
			}

		}

		if ((i3 == 4) && (((world.getBlockId(x, y - 1, z) == mod_coral.Coral1.blockID) && (world.getBlockMetadata(paramInt1, paramInt2 - 1, paramInt3) == 4))
		|| (world.getBlockId(x, y - 1, z) == mod_coral.Coral2.blockID) || (world.getBlockId(x, y - 1, z) == mod_coral.Coral3.blockID))) {
			return true;
		}

		if ((world.getBlockId(x, y + 1, z) == 8) && ((world.getBlockId(x, y - 1, z) == mod_coral.Coral2.blockID) || (world.getBlockId(x, y - 1, z) == mod_coral.Coral3.blockID))) {
			return true;
		}

		return ((world.getBlockId(x, y + 1, z) == 9) && ((world.getBlockId(x, y - 1, z) == mod_coral.Coral2.blockID) || (world.getBlockId(x, y - 1, z) == mod_coral.Coral3.blockID)));
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		if(metadata == 0 || metadata > 5) {
			sprite = blockIndexInTexture;
		}

		if(metadata == 1) {
			sprite = sprite2;
		}

		if(metadata == 2) {
			sprite = sprite3;
		}

		if(metadata == 3) {
			sprite = sprite4;
		}

		if(metadata == 4) {
			sprite = sprite5;
		}

		if(metadata == 5) {
			sprite = sprite6;
		}

		return sprite;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return type;
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return canPlaceBlockAt(world, x, y, z);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockID) {
		if(!canBlockStay(world, x, y, z)) {
			dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockWithNotify(x, y, z, 0);
		}

	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		int metadata = world.getBlockMetadata(x, y, z);
		if(!(entity instanceof EntityWaterMob) && metadata == 4) {
			entity.attackEntityFrom(DamageSource.cactus, 2);
		}

	}
}
