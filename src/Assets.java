package src;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Assets {
	
	private static final int width = 32, height = 32;
	public static final int textboxWidth = 228, textboxHeight = 96;
	
	public static BufferedImage dirt, grass, stone, tree, water,
								playerDownNormal, playerUpNormal, playerLeftNormal, playerRightNormal;
	public static BufferedImage temperatureHot, temperatureCold, inventory, inventoryHighlight;
	public static BufferedImage ringOverworld, inventoryRingEquip, inventoryRingUnequip;
	public static BufferedImage textbox, textbox_player;
	public static BufferedImage[] player_down, player_up, player_left, player_right;
	public static BufferedImage[] currentTime;
	public static BufferedImage[] tunnelVision;
	public static BufferedImage[] activeInventoryHeader;
	public static Font philosopher;

	public static void init() {
		try {
		    //create the font to use. Specify the size!
		    philosopher = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/Philosopher.ttf")).deriveFont(72f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    //register the font
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/Philosopher.ttf")));
		} catch (IOException | FontFormatException e) {
		    e.printStackTrace();
		}

		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/res/textures/sheet.png"));
		SpriteSheet playerSheet = new SpriteSheet(ImageLoader.loadImage("/res/textures/newcharactersheet.png"));
		SpriteSheet daynightSheet = new SpriteSheet(ImageLoader.loadImage("/res/textures/daynightHud.png"));
		SpriteSheet inventoryActiveSheet = new SpriteSheet(ImageLoader.loadImage("/res/textures/inventory-active-header.png"));
		
		textbox_player = ImageLoader.loadImage("/res/textures/tb1.png");
		textbox = ImageLoader.loadImage("/res/textures/tb.png");

		temperatureHot = ImageLoader.loadImage("/res/textures/temperature-hot.png");
		temperatureCold = ImageLoader.loadImage("/res/textures/temperature-cold.png");

		ringOverworld = ImageLoader.loadImage("/res/textures/ring-overworld.png");
		inventoryRingEquip = ImageLoader.loadImage("/res/textures/inventory-ring-equip.png");
		inventoryRingUnequip = ImageLoader.loadImage("/res/textures/inventory-ring-unequip.png");

		activeInventoryHeader = new BufferedImage[3];
		activeInventoryHeader[0] = inventoryActiveSheet.crop(0, 0, 268, 70);
		activeInventoryHeader[1] = inventoryActiveSheet.crop(268, 0, 268, 70);
		activeInventoryHeader[2] = inventoryActiveSheet.crop(535, 0, 268, 70);
		inventory = ImageLoader.loadImage("/res/textures/inventory.png");
		inventoryHighlight = ImageLoader.loadImage("/res/textures/inventory-highlight.png");

		currentTime		= new BufferedImage[7];
		currentTime[0] 	= daynightSheet.crop(0, 0, 48, 48);
		currentTime[1] 	= daynightSheet.crop(48, 0, 48, 48);
		currentTime[2] 	= daynightSheet.crop(96, 0, 48, 48);
		currentTime[3] 	= daynightSheet.crop(144, 0, 48, 48);
		currentTime[4] 	= daynightSheet.crop(0, 48, 48, 48);
		currentTime[5] 	= daynightSheet.crop(48, 48, 48, 48);
		currentTime[6] 	= daynightSheet.crop(96, 48, 48, 48);

		tunnelVision = new BufferedImage[46];
		for (int i = 0, j = 20; i < tunnelVision.length; i++, j+=2) {
			tunnelVision[i] = ImageLoader.loadImage("/res/textures/reduced-vision/vision-" + j + "-75.png");
		}

		player_down = new BufferedImage[4]; 		//4 = frame count
		player_up = new BufferedImage[4];
		player_left = new BufferedImage[6];
		player_right = new BufferedImage[6];
		
		player_down[0] = playerSheet.crop(0, 0, width, height);
		player_down[1] = playerSheet.crop(width, 0, width, height);
		player_down[2] = playerSheet.crop(width*2, 0, width, height);
		player_down[3] = playerSheet.crop(width*3, 0, width, height);
		
		player_up[0] = playerSheet.crop(0, height, width, height);
		player_up[1] = playerSheet.crop(width, height, width, height);
		player_up[2] = playerSheet.crop(width*2, height, width, height);
		player_up[3] = playerSheet.crop(width*3, height, width, height);
		
		player_left[0] = playerSheet.crop(0, height*2, width, height);
		player_left[1] = playerSheet.crop(width, height*2, width, height);
		player_left[2] = playerSheet.crop(width*2, height*2, width, height);
		player_left[3] = playerSheet.crop(width*3, height*2, width, height);
		player_left[4] = playerSheet.crop(width*4, height*2, width, height);
		player_left[5] = playerSheet.crop(width*5, height*2, width, height);
		
		player_right[0] = playerSheet.crop(0, height*3, width, height);
		player_right[1] = playerSheet.crop(width, height*3, width, height);
		player_right[2] = playerSheet.crop(width*2, height*3, width, height);
		player_right[3] = playerSheet.crop(width*3, height*3, width, height);
		player_right[4] = playerSheet.crop(width*4, height*3, width, height);
		player_right[5] = playerSheet.crop(width*5, height*3, width, height);
		
		playerDownNormal = player_down[0];
		playerLeftNormal = player_left[5];
		playerRightNormal = player_right[5];
		playerUpNormal = player_up[0];
				
		dirt = sheet.crop(0,0,width,height);
		grass = sheet.crop(width, 0, width, height);
		stone = sheet.crop(width*2, 0, width, height);
		tree = sheet.crop(width*3, 0, width, height);
		water = sheet.crop(0, height*2, width, height);
	}
	public static int getTextboxHeight()
	{
		return textboxHeight;
	}
	
	public static int getTextboxWidth()
	{
		return textboxWidth;
	}
}
