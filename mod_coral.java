// CoralReef Mod
// Original author: Nandonalt
// Current maintainer: q3hardcore
// Special thanks to: OvermindDL1

package net.minecraft.src;

import java.io.File;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;

public class mod_coral extends BaseMod {

	// Directory for storing settings
	private static File modDir;

	// Coral Reef settings
	private static Settings settings;
	private static Settings blockIDSettings;

	// Settings
	public static boolean enable = true;
	public static boolean spiky = true;
	public static int size = 1;
	public static boolean bubble = true;
	public static boolean grow = false;
	public static boolean ocean = true;
	public static boolean classic = false;
	public static boolean land = false;

	// Coral block IDs
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

	// Sprite indices
	private static int Sprite_coral1 = 0;
	private static int Sprite_coral2 = 1;
	private static int Sprite_coral3 = 2;
	private static int Sprite_coral4 = 3;
	private static int Sprite_coral5 = 4;
	private static int Sprite_coral6 = 5;
	private static int Sprite_coralr1 = 6;
	private static int Sprite_coralr2 = 7;

	// ML Sprite indices
	private static int MLSprite_coral1 = -1;
	private static int MLSprite_coral2 = -1;
	private static int MLSprite_coral3 = -1;
	private static int MLSprite_coral4 = -1;
	private static int MLSprite_coral5 = -1;
	private static int MLSprite_coral6 = -1;
	private static int MLSprite_coralr1 = -1;
	private static int MLSprite_coralr2 = -1;

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

	// Does player have Minecraft Forge installed?
	private static boolean hasForge;

	// Have we already checked for Forge?
	private static boolean forgeChecked = false;

	// Are we using overrrides?
	private static boolean usingOverrides = false;

	// Are blocks instantiated?
	private static boolean blocksInstantiated = false;

	@Override
	public String getName() {
		return "Nandonalt's CoralMod";
	}

	@Override
	public String getVersion() {
		return getMinecraftVersion();
	}

	/**
	 * Gets current Minecraft version
	 */
	public static String getMinecraftVersion() {
		return new CallableMinecraftVersion(null).minecraftVersion();
	}

	@Override
	public void load() {
		// Check if running on client side and load settings
		checkClientSide();
		loadSettings();

		// Preload coral textures for Forge
		if(clientSide && checkHasForge()) {
			MinecraftForgeClient.preloadTexture("/nandonalt/CoralMod/blocks.png");
			MinecraftForgeClient.preloadTexture("/nandonalt/CoralMod/items.png");
		}

		// Make sure 'classic' is set correctly
		if(clientSide && !checkEnoughSlots()) {
			classic = false;
		} else if(!checkHasForge()) {
			classic = true;
		}

		if(clientSide && checkEnoughSlots())
			addOverrides();

		// Toggle terrain.png overrides
		if(clientSide && classic) {
			toggleOverrides(false);
		}

		// Instantiate blocks
		instantiateBlocks();

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

	private static void instantiateBlocks() {
		if(blocksInstantiated)
			return;

		Coral1 = (new BlockCoral(corale1, 1, Sprite_coral1, Sprite_coral2, Sprite_coral3, Sprite_coral4, Sprite_coral5, Sprite_coral6)).setHardness(0.2F).setStepSound(Block.soundStoneFootstep).setBlockName("Coral1");
		Coral2 = (new BlockCoral2(corale2, Sprite_coralr1)).setHardness(0.5F).setStepSound(Block.soundStoneFootstep).setBlockName("Coral2");
		Coral3 = (new BlockCoral2(corale3, Sprite_coralr2)).setHardness(0.5F).setStepSound(Block.soundStoneFootstep).setBlockName("Coral3");
		Coral4 = (new BlockCoral(corale4, 6, Sprite_coral1, Sprite_coral2, Sprite_coral3, Sprite_coral4, Sprite_coral5, Sprite_coral6)).setHardness(0.2F).setStepSound(Block.soundStoneFootstep).setBlockName("Coral4");
		Coral5 = (new BlockCoral(corale5, 6, Sprite_coral1, Sprite_coral2, Sprite_coral3, Sprite_coral4, Sprite_coral5, Sprite_coral6)).setHardness(0.2F).setStepSound(Block.soundStoneFootstep).setLightValue(1.0F).setBlockName("CoralLightt");

		blocksInstantiated = true;
	}

	// Add overrides for ModLoader
	public static void toggleOverrides(boolean reload) {
		if(usingOverrides && !reload) {
			System.out.println("CoralMod: not toggling overrides");
			return;
		}

		if(classic || !checkHasForge()) {
			System.out.println("CoralMod - ModLoader");
			if(MLSprite_coral1 == -1) {
				System.out.println("CoralMod: adding overrides");
				addOverrides();
			}
			Sprite_coral1 = MLSprite_coral1;
			Sprite_coral2 = MLSprite_coral2;
			Sprite_coral3 = MLSprite_coral3;
			Sprite_coral4 = MLSprite_coral4;
			Sprite_coral5 = MLSprite_coral5;
			Sprite_coral6 = MLSprite_coral6;
			Sprite_coralr1 = MLSprite_coralr1;
			Sprite_coralr2 = MLSprite_coralr2;
			usingOverrides = true;
		} else {
			Sprite_coral1 = 0;
			Sprite_coral2 = 1;
			Sprite_coral3 = 2;
			Sprite_coral4 = 3;
			Sprite_coral5 = 4;
			Sprite_coral6 = 5;
			Sprite_coralr1 = 6;
			Sprite_coralr2 = 7;
		}

		if(reload && checkHasForge()) {
			Block.blocksList[corale1] = null;
			Block.blocksList[corale2] = null;
			Block.blocksList[corale3] = null;
			Block.blocksList[corale4] = null;
			Block.blocksList[corale5] = null;
			blocksInstantiated = false;
			instantiateBlocks();
		}
	}

	private static void addOverrides() {
		MLSprite_coral1 = ModLoader.addOverride("/terrain.png", "/nandonalt/CoralMod/block_coral1.png");
		MLSprite_coral2 = ModLoader.addOverride("/terrain.png", "/nandonalt/CoralMod/block_coral2.png");
		MLSprite_coral3 = ModLoader.addOverride("/terrain.png", "/nandonalt/CoralMod/block_coral3.png");
		MLSprite_coral4 = ModLoader.addOverride("/terrain.png", "/nandonalt/CoralMod/block_coral4.png");
		MLSprite_coral5 = ModLoader.addOverride("/terrain.png", "/nandonalt/CoralMod/block_coral5.png");
		MLSprite_coral6 = ModLoader.addOverride("/terrain.png", "/nandonalt/CoralMod/block_coral6.png");
		MLSprite_coralr1 = ModLoader.addOverride("/terrain.png", "/nandonalt/CoralMod/block_reef.png");
		MLSprite_coralr2 = ModLoader.addOverride("/terrain.png", "/nandonalt/CoralMod/block_reef2.png");
	}

	// Adds dye recipes for coral
	private void addRecipes() {
		Item dye = Item.dyePowder;
		ModLoader.addRecipe(new ItemStack(dye, 1, 14), new Object[]{"B", Character.valueOf('B'), new ItemStack(Coral1, 1, 0)});
		ModLoader.addRecipe(new ItemStack(dye, 1, 10), new Object[]{"B", Character.valueOf('B'), new ItemStack(Coral1, 1, 1)});
		ModLoader.addRecipe(new ItemStack(dye, 1, 13), new Object[]{"B", Character.valueOf('B'), new ItemStack(Coral1, 1, 2)});
		ModLoader.addRecipe(new ItemStack(dye, 1, 9), new Object[]{"B", Character.valueOf('B'), new ItemStack(Coral4, 1, 3)});
		ModLoader.addRecipe(new ItemStack(dye, 1, 3), new Object[]{"B", Character.valueOf('B'), new ItemStack(Coral1, 1, 4)});
		ModLoader.addRecipe(new ItemStack(dye, 1, 6), new Object[]{"B", Character.valueOf('B'), new ItemStack(Coral5, 1, 5)});
	}

	/**
	 * Generates coral reef at specified chunk co-ordinates
	 */
	@Override
	public void generateSurface(World world, Random random, int chunkX, int chunkZ) {
		if(!enable || (ocean && !world.getWorldChunkManager().getBiomeGenAt(chunkX, chunkZ).biomeName.equals("Ocean"))) {
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

	/**
	 * Attempts to load settings, defaults are used if failure occures
	 */
	private void loadSettings() {
		boolean createdModDir = false;

		try {
			if(!modDir.exists()) {
				createdModDir = modDir.mkdirs();
			} else {
				createdModDir = true;
			}
		} catch (SecurityException se) {
			System.out.println("Could not create mod directory for CoralMod.");
		}

		if(!createdModDir) {
			System.out.println("Using hard-coded settings.");
			return;
		}

		settings = new Settings(new File(modDir, "settings.txt"));
		enable = settings.getBoolean("coralgen", true);
		spiky = settings.getBoolean("spikyenabled", true);
		bubble = settings.getBoolean("enablebubbles", true);
		grow = settings.getBoolean("enablegrow", false);
		size = settings.getInteger("avgsize", 1);
		ocean = settings.getBoolean("oceanonly", true);
		classic = settings.getBoolean("classic", false);
		land = settings.getBoolean("land", false);

		blockIDSettings = new Settings(new File(modDir, "blockids.txt"));
		corale1 = blockIDSettings.getInteger("Coral1", 178);
		corale2 = blockIDSettings.getInteger("Coral2", 179);
		corale3 = blockIDSettings.getInteger("Coral3", 180);
		corale4 = blockIDSettings.getInteger("Coral4", 177);
		corale5 = blockIDSettings.getInteger("Coral5", 176);

		updateSettings();
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
			settings.set("classic", classic);
			settings.set("land", land);
			settings.save("CoralReef Settings");
			System.out.println("CoralReef settings updated.");
		} else {
			System.out.println("No settings found for CoralMod.");
		}

		if(blockIDSettings != null && setIDs) {
			System.out.println("Setting Coral Block IDs.");
			blockIDSettings.set("Coral1", corale1);
			blockIDSettings.set("Coral2", corale2);
			blockIDSettings.set("Coral3", corale3);
			blockIDSettings.set("Coral4", corale4);
			blockIDSettings.set("Coral5", corale5);
			blockIDSettings.save("CoralReef Block IDs");
			setIDs = false;
		}
	}

	/**
	 * Used to determine if Coral Reef is running on client side
	 */
	public static boolean checkClientSide() {
		if(sideChecked)
			return clientSide;

		try {
			Class<?> guiScreen = GuiScreen.class;
			modDir = new File(Minecraft.getMinecraftDir(), "mods/coralreef/");
			clientSide = true;
		} catch (NoClassDefFoundError ncdfe) {
			setServerSide();
		} catch (Throwable t) { // shouldn't reach this point
			t.printStackTrace();
			setServerSide();
		}

		sideChecked = true;
		return clientSide;
	}

	// Set server side mode for CoralMod
	private static void setServerSide() {
		System.out.println("CoralMod - Server");
		modDir = new File(".", "mods/coralreef/");
		clientSide = false;
	}

	/**
	 * Check if Minecraft Forge is installed
	 */
	public static boolean checkHasForge() {
		if(forgeChecked)
			return hasForge;

		try {
			Class<?> forge = cpw.mods.fml.common.modloader.BaseModProxy.class;
			hasForge = true;
		} catch (NoClassDefFoundError ncdfe) {
			hasForge = false;
		}

		forgeChecked = true;
		return hasForge;
	}

	public static boolean checkSettingsLoaded() {
		return settings != null;
	}

	/**
	 * Checks if at least 8 slots are free (Forge only)
	 */
	public static boolean checkEnoughSlots() {
		if(checkHasForge()) {
			int freeSlots = 8;
			try {
				freeSlots = cpw.mods.fml.client.SpriteHelper.freeSlotCount("/terrain.png");
			} catch (NullPointerException npe) {
				try {
					Class<?> spriteHelperClass = cpw.mods.fml.client.SpriteHelper.class;
					java.lang.reflect.Field spriteInfoField = spriteHelperClass.getDeclaredField("spriteInfo");
					spriteInfoField.setAccessible(true);
					Object spriteInfoObj = spriteInfoField.get(null);
					if(spriteInfoObj instanceof java.util.Map) {
						if(((java.util.Map)spriteInfoObj).size() == 0) {
							java.lang.reflect.Method initMCSpriteMapsMethod = spriteHelperClass.getDeclaredMethod("initMCSpriteMaps");
							initMCSpriteMapsMethod.setAccessible(true);
							initMCSpriteMapsMethod.invoke(null);
						} else {
							System.out.println("If you see this text, report it!");
						}
					}
					freeSlots = cpw.mods.fml.client.SpriteHelper.freeSlotCount("/terrain.png");
					System.out.println("FML has loaded sprite indices.");
				} catch (Exception e) {
					System.out.println("Something has gone really wrong!");
					e.printStackTrace();
				}
			}
			if(freeSlots >= 8) {
				return true;
			} else if(MLSprite_coral1 != -1) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

}
