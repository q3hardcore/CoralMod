// CoralReef Mod
// Original author: Nandonalt
// Current maintainer: q3hardcore
// Special thanks to: OvermindDL1

package net.minecraft.src;

import java.io.File;
import java.util.Random;
import net.minecraft.client.Minecraft;

public class mod_coral extends BaseMod {

	// Directory for storing settings
	private static File modDir;

	// Coral Reef settings handler
	private static Settings settings;

	// Settings
	public static boolean enable = true;
	public static boolean spiky = true;
	public static int size = 1;
	public static boolean bubble = true;
	public static boolean grow = false;
	public static boolean ocean = true;

	// Coral IDs
	private static int corale1 = 178;
	private static int corale2 = 179;
	private static int corale3 = 180;
	private static int corale4 = 177;
	private static int corale5 = 176;

	// Reef generation size
	private int min1;
	private int min2;
	private int max1;
	private int max2;

	// Sprite indexes
	private static int Sprite_coral1;
	private static int Sprite_coral2;
	private static int Sprite_coral3;
	private static int Sprite_coral4;
	private static int Sprite_coral5;
	private static int Sprite_coral6;
	private static int Sprite_coralr1;
	private static int Sprite_coralr2;

	// Coral blocks
	public static Block Coral1;
	public static Block Coral2;
	public static Block Coral3;
	public static Block Coral4;
	public static Block Coral5;

	// Is running on client side?
	private static boolean clientSide;

	// Have we already checked side?
	private static boolean sideChecked = false;

	// Do ID's need setting?
	private static boolean setIDs = true;

	@Override
	public String getName() {
		return "Nandonalt's CoralMod";
	}

	@Override
	public String getVersion() {
		return "1.4.4";
	}

	@Override
	public void load() {
		checkClientSide();
		loadSettings();

		// Don't use sprites if serverside
		if(clientSide) {
			Sprite_coral1 = ModLoader.addOverride("/terrain.png", "/nandonalt/CoralMod/block_coral1.png");
			Sprite_coral2 = ModLoader.addOverride("/terrain.png", "/nandonalt/CoralMod/block_coral2.png");
			Sprite_coral3 = ModLoader.addOverride("/terrain.png", "/nandonalt/CoralMod/block_coral3.png");
			Sprite_coral4 = ModLoader.addOverride("/terrain.png", "/nandonalt/CoralMod/block_coral4.png");
			Sprite_coral5 = ModLoader.addOverride("/terrain.png", "/nandonalt/CoralMod/block_coral5.png");
			Sprite_coral6 = ModLoader.addOverride("/terrain.png", "/nandonalt/CoralMod/block_coral6.png");
			Sprite_coralr1 = ModLoader.addOverride("/terrain.png", "/nandonalt/CoralMod/block_reef.png");
			Sprite_coralr2 = ModLoader.addOverride("/terrain.png", "/nandonalt/CoralMod/block_reef2.png");
			Coral1 = (new BlockCoral(corale1, 1, Sprite_coral1, Sprite_coral2, Sprite_coral3, Sprite_coral4, Sprite_coral5, Sprite_coral6)).setHardness(0.2F).setStepSound(Block.soundStoneFootstep).setBlockName("Coral1");
			Coral2 = (new BlockCoral2(corale2, Sprite_coralr1)).setHardness(0.5F).setStepSound(Block.soundStoneFootstep).setBlockName("Coral2");
			Coral3 = (new BlockCoral2(corale3, Sprite_coralr2)).setHardness(0.5F).setStepSound(Block.soundStoneFootstep).setBlockName("Coral3");
			Coral4 = (new BlockCoral(corale4, 6, Sprite_coral1, Sprite_coral2, Sprite_coral3, Sprite_coral4, Sprite_coral5, Sprite_coral6)).setHardness(0.2F).setStepSound(Block.soundStoneFootstep).setBlockName("Coral4");
			Coral5 = (new BlockCoral(corale5, 6, Sprite_coral1, Sprite_coral2, Sprite_coral3, Sprite_coral4, Sprite_coral5, Sprite_coral6)).setHardness(0.2F).setStepSound(Block.soundStoneFootstep).setLightValue(1.0F).setBlockName("CoralLightt");
		} else {
			Coral1 = (new BlockCoral(corale1, 1, 0, 1, 2, 3, 4, 5)).setHardness(0.2F).setStepSound(Block.soundStoneFootstep).setBlockName("Coral1");
			Coral2 = (new BlockCoral2(corale2, 0)).setHardness(0.5F).setStepSound(Block.soundStoneFootstep).setBlockName("Coral2");
			Coral3 = (new BlockCoral2(corale3, 1)).setHardness(0.5F).setStepSound(Block.soundStoneFootstep).setBlockName("Coral3");
			Coral4 = (new BlockCoral(corale4, 6, 0, 1, 2, 3, 4, 5)).setHardness(0.2F).setStepSound(Block.soundStoneFootstep).setBlockName("Coral4");
			Coral5 = (new BlockCoral(corale5, 6, 0, 1, 2, 3, 4, 5)).setHardness(0.2F).setStepSound(Block.soundStoneFootstep).setLightValue(1.0F).setBlockName("CoralLightt");
		}

		// Register blocks
		ModLoader.registerBlock(Coral1, ItemCoral.class);
		ModLoader.registerBlock(Coral2);
		ModLoader.registerBlock(Coral3);
		ModLoader.registerBlock(Coral4, ItemCoral.class);
		ModLoader.registerBlock(Coral5, ItemCoral.class);

		// Add block names
		ModLoader.addName(Coral2, "Sea Coral");
		ModLoader.addName(Coral3, "Dry Coral");
		ModLoader.addName(Coral1, "Coral");
		ModLoader.addName(Coral4, "Coral");
		ModLoader.addName(Coral5, "Coral");

		// Add recipes
		addRecipes();
	}

	@Override
	public void generateSurface(World world, Random random, int chunkX, int chunkZ) {
		if(enable) {
			if(ocean && !world.getWorldChunkManager().getBiomeGenAt(chunkX, chunkZ).biomeName.equals("Ocean")) {
				return;
			}

			if(size == 0) {
				min1 = 15;
				min2 = 10;
				max1 = 40;
				max2 = 20;
			} else if(size == 1) {
				min1 = 35;
				min2 = 25;
				max1 = 60;
				max2 = 35;
			} else if(size == 2) {
				min1 = 45;
				min2 = 30;
				max1 = 70;
				max2 = 45;
			}

			for(int i = 0; i < 80; i++) {
				int j = chunkX + random.nextInt(16);
				int k = random.nextInt(128);
				int m = chunkZ + random.nextInt(16);
				int numberReef = random.nextInt(max1 - min1 + 1) + min1;
				new WorldGen_Reef(numberReef, Coral2.blockID, spiky).generate(world, random, j, k, m);
			}

			for(int i = 0; i < 80; i++) {
				int j = chunkX + random.nextInt(16);
				int k = random.nextInt(128);
				int m = chunkZ + random.nextInt(16);
				int numberReef = random.nextInt(max2 - min2 + 1) + min2;
				new WorldGen_Reef2(numberReef, Coral3.blockID).generate(world, random, j, k, m);
			}
		}

	}

	private void addRecipes() {
		Item dye = Item.dyePowder;
		ModLoader.addRecipe(new ItemStack(dye, 1, 14), new Object[]{"B", Character.valueOf('B'), new ItemStack(Coral1, 1, 0)});
		ModLoader.addRecipe(new ItemStack(dye, 1, 10), new Object[]{"B", Character.valueOf('B'), new ItemStack(Coral1, 1, 1)});
		ModLoader.addRecipe(new ItemStack(dye, 1, 13), new Object[]{"B", Character.valueOf('B'), new ItemStack(Coral1, 1, 2)});
		ModLoader.addRecipe(new ItemStack(dye, 1, 9), new Object[]{"B", Character.valueOf('B'), new ItemStack(Coral4, 1, 3)});
		ModLoader.addRecipe(new ItemStack(dye, 1, 3), new Object[]{"B", Character.valueOf('B'), new ItemStack(Coral1, 1, 4)});
		ModLoader.addRecipe(new ItemStack(dye, 1, 6), new Object[]{"B", Character.valueOf('B'), new ItemStack(Coral5, 1, 5)});
	}

	private void loadSettings() {
		try {
			if(!modDir.exists()) {
				modDir.mkdirs();
			}
			settings = new Settings(new File(modDir, "settings.txt"));
			enable = settings.getBoolean("coralgen", true);
			spiky = settings.getBoolean("spikyenabled", true);
			bubble = settings.getBoolean("enablebubbles", true);
			grow = settings.getBoolean("enablegrow", false);
			size = settings.getInteger("avgsize", 1);
			ocean = settings.getBoolean("oceanonly", true);
			corale1 = settings.getInteger("Coral1", 178);
			corale2 = settings.getInteger("Coral2", 179);
			corale3 = settings.getInteger("Coral3", 180);
			corale4 = settings.getInteger("Coral4", 177);
			corale5 = settings.getInteger("Coral5", 176);
			updateSettings();
		} catch (Exception e) {
			System.out.println("Could not load settings.");
		}
	}

	/**
	 * Used to determine if Coral Reef is running on client side
	 */
	public static boolean checkClientSide() {
		if(sideChecked)
			return clientSide;

		try {
			try {
				Class.forName("net.minecraft.src.GuiScreen");
			} catch (ClassNotFoundException cnfe) {
				Class.forName("aue");
			}
			modDir = new File(Minecraft.getMinecraftDir(), "mods/coralreef/");
			clientSide = true;
		} catch (Exception e) {
			modDir = new File(".", "mods/coralreef/");
			clientSide = false;
		}
		sideChecked = true;
		return clientSide;
	}

	/**
	 * Called by Coral Reef GUI to update settings
	 */
	public static void updateSettings() {
		if(settings != null) {
			settings.set("coralgen", enable);
			settings.set("spikyenabled", spiky);
			settings.set("enablebubbles", bubble);
			settings.set("enablegrow", grow);
			settings.set("avgsize", size);
			settings.set("oceanonly", ocean);
			if(setIDs) {
				System.out.println("Setting Coral Block IDs.");
				settings.set("Coral1", corale1);
				settings.set("Coral2", corale2);
				settings.set("Coral3", corale3);
				settings.set("Coral4", corale4);
				settings.set("Coral5", corale5);
				setIDs = false;
			}
			settings.save("CoralReef Settings");
			System.out.println("CoralReef settings updated.");
		} else {
			System.out.println("No settings found.");
		}
	}

}
