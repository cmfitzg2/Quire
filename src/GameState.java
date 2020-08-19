package src;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class GameState extends State
{
	private ScreenOverlay screenOverlay;
	private long startTime;
	private World world1, world2;
	private Rectangle loadZoneWest, loadZoneEast, loadZoneNorth, loadZoneSouth;
	public static boolean playStarted = false;
	static AudioClip overworldMusic;
	static AudioClip overworldMusicLoop;

	public GameState(Handler handler) throws Exception {
		super(handler);
		world1 = new World(handler,"res/worlds/world1.txt");
		world2 = new World(handler,"res/worlds/world2.txt");
		screenOverlay = new ScreenOverlay(handler);
		handler.setWorld(world1);
	}



	@Override
	public void tick() {
		getLoadZones();

		if (firstTime) {
			world1.getEntityManager().addEntity(new YellowStoneRingOverworld(handler, 300, 300, 8, 5));
			world1.tick();
			world2.tick();
			firstTime = false;
		}

		if (handler.getWorldNumber() == 1) {
			world1.tick();

			if (Player.playerRec.intersects(loadZoneWest)) {
				handler.setWorld(world2);
				handler.setWorldNumber(2);
				justTransitioned = true;
				world2.tick();
			}
		}


		else if (handler.getWorld() == world2) {
			world2.tick();

			if (Player.playerRec.intersects(loadZoneEast)) {
				handler.setWorld(world1);
				handler.setWorldNumber(1);
				justTransitioned = true;
				world1.tick();
			}
		}
	}
	private boolean firstTime = true, justTransitioned = false;
	private int alpha = 255;

	@Override
	public void render(Graphics graphics) {

		if (handler.getWorldNumber() == 1) {
			world1.render(graphics);
			if (justTransitioned) {
				handler.getPlayer().setX(20);
				handler.getPlayer().setY(496);
				justTransitioned = false;
			}
			graphics.drawRect((int) -handler.getGameCamera().getxOffset(),(int) (511 - handler.getGameCamera().getyOffset()),10,64);
		}

		else if (handler.getWorldNumber() == 2) {
			world2.render(graphics);
			if (justTransitioned) {
				handler.getPlayer().setX(1130);
				handler.getPlayer().setY(304);
				justTransitioned = false;
			}
			graphics.drawRect((int) (1200 - handler.getGameCamera().getxOffset()),(int) (320 - handler.getGameCamera().getyOffset()),10,64);
		}
		screenOverlay.drawTimeOfDay(graphics, SurvivalStatus.currentTime);
		screenOverlay.drawVision(graphics);
		handler.getPlayer().getInventory().render(graphics);
		screenOverlay.drawTemperature(graphics, SurvivalStatus.currentTemperature);
		DebugTools.outputText("Current volume gain (dB): " + handler.getPlayer().getSurvivalStatus().getVolume(), graphics, 16, handler.getHeight() - 96);
		DebugTools.outputText("Current fear: " + Float.toString(SurvivalStatus.currentFear), graphics, 16, handler.getHeight() - 64);
		DebugTools.outputText("Current (x,y): (" + handler.getPlayer().x + ", " + handler.getPlayer().y + ")", graphics, 16, handler.getHeight() - 32);
		graphics.setColor(Color.BLACK);
	}

	private void getLoadZones() {
		if (handler.getWorldNumber() == 1) {
			loadZoneWest = new Rectangle((int) -handler.getGameCamera().getxOffset(),(int) (511 - handler.getGameCamera().getyOffset()),10,64);
		}
		else if (handler.getWorldNumber() == 2) {
			loadZoneEast = new Rectangle((int) (1200 - handler.getGameCamera().getxOffset()),(int) (320 - handler.getGameCamera().getyOffset()),10,64);
		}

	}

	public void playMusic()
	{
		playStarted = true;
		overworldMusic = Applet.newAudioClip(getClass().getResource("ocean.au"));
		overworldMusicLoop = Applet.newAudioClip(getClass().getResource("ocean.au"));

		overworldMusic.play();
		startTime = System.currentTimeMillis();
	}

	public static void stopMusic()
	{
		playStarted = false;
		overworldMusic.stop();
	}

}
