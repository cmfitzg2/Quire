package src;

import java.awt.Color;
import java.awt.Graphics;

public class ScreenOverlay
{
	private int gameWidth;
	private int gameHeight;
	Handler handler;

	public ScreenOverlay(Handler handler)
	{
		this.handler = handler;
		gameWidth = handler.getGame().getWidth();
		gameHeight = handler.getGame().getHeight();
	}

	public void overlayScreen(Graphics graphics, Color c)
	{
		graphics.setColor(c);
		graphics.fillRect(0, 0, gameWidth, gameHeight);
	}

	public void drawTimeOfDay(Graphics graphics, int currentTime) {
		switch (currentTime) {
			case 0:
				//dawn
				handler.getScreenOverlay().overlayScreen(graphics, new Color(255, 81, 58, 60));
				break;
			case 1:
				//morning
				handler.getScreenOverlay().overlayScreen(graphics, new Color(255, 162, 0, 60));
				break;
			case 2:
				//midday
				break;
			case 3:
				//afternoon
				break;
			case 4:
				//night
				handler.getScreenOverlay().overlayScreen(graphics, new Color(0, 12, 255, 85));
				break;
			case 5:
				//midnight
				handler.getScreenOverlay().overlayScreen(graphics, new Color(0, 2, 45, 175));
				break;
			case 6:
				//twilight
				handler.getScreenOverlay().overlayScreen(graphics, new Color(57, 37, 124, 100));
				break;
		}
	}

	public void drawTemperature(Graphics graphics, int currentTemperature) {
		if (currentTemperature >= 9) {
			graphics.drawImage(Assets.temperatureHot, 0, 0, handler.getWidth(), handler.getHeight(), null);
		} else if (currentTemperature <= 2) {
			graphics.drawImage(Assets.temperatureCold, 0, 0, handler.getWidth(), handler.getHeight(), null);
		}
	}

	public void drawVision(Graphics graphics) {
		if (!handler.getFlags().isVisionLimited()) {
			return;
		}
		int xOffsetCamera = (int) handler.getGameCamera().getxOffsetCamera();
		int yOffsetCamera = (int) handler.getGameCamera().getyOffsetCamera();
		int xOffset = (int) handler.getGameCamera().getxOffset();
		int yOffset = (int) handler.getGameCamera().getyOffset();
		int xDrawFrom = -handler.getWidth();
		int yDrawFrom = -handler.getHeight();
		int xScale = handler.getWidth() * 3;
		int yScale = handler.getHeight() * 3;

		//we need to check the 4 cardinal directions and the diagonals to figure out where to center the vision
		if (xOffsetCamera < 0) {
			xDrawFrom += xOffsetCamera;
		} else if (xOffsetCamera > xOffset) {
			xDrawFrom += xOffsetCamera - xOffset;
		}
		if (yOffsetCamera < 0) {
			yDrawFrom += yOffsetCamera;
		} else if (yOffsetCamera > yOffset) {
			yDrawFrom += yOffsetCamera - yOffset;
		}
		if (handler.getFlags().isVisionLimited()) {
			graphics.drawImage(Assets.tunnelVision[SurvivalStatus.maxDarknessLevel - SurvivalStatus.darknessLevel],
					xDrawFrom - 1, yDrawFrom, xScale, yScale, null);
		}
	}
}
