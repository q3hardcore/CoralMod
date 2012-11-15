package net.minecraft.src;

import java.util.Random;

public class WorldGen_Reef2 extends WorldGenerator {

	private int coralID;
	private int numberOfBlocks;

	public WorldGen_Reef2(int numberOfBlocks, int coralID) {
		this.coralID = coralID;
		this.numberOfBlocks = numberOfBlocks;
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		float f = random.nextFloat() * 3.141593F;
		double d1 = (double)((float)(x + 8) + MathHelper.sin(f) * (float)numberOfBlocks / 8.0F);
		double d2 = (double)((float)(x + 8) - MathHelper.sin(f) * (float)numberOfBlocks / 8.0F);
		double d3 = (double)((float)(z + 8) + MathHelper.cos(f) * (float)numberOfBlocks / 8.0F);
		double d4 = (double)((float)(z + 8) - MathHelper.cos(f) * (float)numberOfBlocks / 8.0F);
		double d5 = (double)(y + random.nextInt(3) + 2);
		double d6 = (double)(y + random.nextInt(3) + 2);

		for(int i = 0; i <= numberOfBlocks; i++) {
			double d7 = d1 + (d2 - d1) * (double)i / (double)numberOfBlocks;
			double d8 = d5 + (d6 - d5) * (double)i / (double)numberOfBlocks;
			double d9 = d3 + (d4 - d3) * (double)i / (double)numberOfBlocks;
			double d10 = random.nextDouble() * (double)numberOfBlocks / 16.0D;
			double d11 = (double)(MathHelper.sin((float)i * 3.141593F / (float)numberOfBlocks) + 1.0F) * d10 + 1.0D;
			double d12 = (double)(MathHelper.sin((float)i * 3.141593F / (float)numberOfBlocks) + 1.0F) * d10 + 1.0D;

			for(int j = (int)(d7 - d11 / 2.0D); j <= (int)(d7 + d11 / 2.0D); ++j) {
				for(int k = (int)(d8 - d12 / 2.0D); k <= (int)(d8 + d12 / 2.0D); ++k) {
					for(int m = (int)(d9 - d11 / 2.0D); m <= (int)(d9 + d11 / 2.0D); ++m) {
						double d13 = ((double)j + 0.5D - d7) / (d11 / 2.0D);
						double d14 = ((double)k + 0.5D - d8) / (d12 / 2.0D);
						double d15 = ((double)m + 0.5D - d9) / (d11 / 2.0D);
						if(d13 * d13 + d14 * d14 + d15 * d15 < 1.0D && (world.getBlockId(j, k, m) == mod_coral.Coral2.blockID || world.getBlockId(j, k, m) == mod_coral.Coral2.blockID)
						&& (world.getBlockId(j, k + 1, m) == Block.waterStill.blockID || world.getBlockId(j, k + 1, m) == Block.waterMoving.blockID)
						&& (world.getBlockId(j, k + 2, m) == Block.waterStill.blockID || world.getBlockId(j, k + 2, m) == Block.waterMoving.blockID)
						&& (world.getBlockId(j, k + 3, m) == Block.waterStill.blockID || world.getBlockId(j, k + 3, m) == Block.waterMoving.blockID)
						&& (world.getBlockId(j, k + 4, m) == Block.waterStill.blockID || world.getBlockId(j, k + 4, m) == Block.waterMoving.blockID)) {
							world.setBlockWithNotify(j, k, m, coralID);
							world.setBlockWithNotify(j + 1, k, m, coralID);
							world.setBlockWithNotify(j, k, m + 1, coralID);
							world.setBlockWithNotify(j + 1, k, m + 1, coralID);
							world.setBlockWithNotify(j, k + random.nextInt(2), m, coralID);
							world.setBlockWithNotify(j + 1, k + random.nextInt(2), m, coralID);
							world.setBlockWithNotify(j, k + random.nextInt(2), m + 1, coralID);
							world.setBlockWithNotify(j + 1, k + random.nextInt(2), m + 1, coralID);
						}
					}
				}
			}
		}

		return true;
	}
}
