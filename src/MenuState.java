package src;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class MenuState extends State
{
	AudioClip soundFile;
	private int alpha = 255;
	FontMetrics fm;
	private int fontHeight;
	private int fontWidth;
	private boolean firstTime = true, fadeIn = false, fadeOut = false, fadeFinished = false;

	public MenuState(final Handler handler)
	{
		super(handler);
		soundFile = Applet.newAudioClip(getClass().getResource("/res/music/piano.au"));
		soundFile.loop();
	}

	@Override
	public void tick() 
	{
		if (handler.getKeyManager().esc)
		{
			soundFile.stop();
			State.setState(handler.getGame().gameState);
		}
		if (firstTime)
		{
			fadeIn = true;
			firstTime = false;
		}
		if (fadeFinished)
		{
			soundFile.stop();
			State.setState(handler.getGame().gameState);
		}
	}

	@Override
	public void render(Graphics graphics)
	{
		graphics.setColor(Color.WHITE);
		graphics.setFont(Assets.philosopher);
		fm = graphics.getFontMetrics();
		graphics.drawString("Quire", handler.getWidth()/2 - fm.stringWidth("Quire")/2, handler.getHeight()/2 + fm.getAscent()/4);
		if (fadeIn)
		{
			if (alpha>0) {
				alpha--;
				if (alpha>=0)
					handler.getScreenOverlay().overlayScreen(graphics, new Color(0, 0, 0, alpha));
			}
			else{
				fadeOut = true;
				fadeIn = false;
			}
		}
		if (fadeOut) {
			if (alpha<255)
			{
				alpha++;
				if (alpha<=255)
					handler.getScreenOverlay().overlayScreen(graphics, new Color(0, 0, 0, alpha));
			}
			else
			{
				fadeIn = false;
				fadeFinished = true;
				handler.getScreenOverlay().overlayScreen(graphics, new Color(0, 0, 0, 255));
			}
		}
	}
	
}
