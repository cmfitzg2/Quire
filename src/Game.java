package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {

	private Display display;
	private int width, height;
	public String title;

	private Thread thread;
	private boolean running = false;

	private BufferStrategy bufferStrategy;
	private Graphics graphics;

	//Screen fading
	public ScreenOverlay screenOverlay;
	public int alpha = 0;
	private boolean fadeOut = false;
	private boolean fadeIn = true;

	//States
	public State gameState;
	public State menuState;

	//Input
	private KeyManager keyManager;
	private MouseManager mouseManager;

	//Camera
	private GameCamera gameCamera;

	//Object management
	private Handler handler;
	private Flags flags;

	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}

	private void init() throws Exception {
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		Assets.init();

		handler = new Handler(this);
		flags = new Flags();
		handler.setFlags(flags);
		gameCamera = new GameCamera(handler, 0,0);
		screenOverlay = new ScreenOverlay(handler);

		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		State.setState(menuState);
	}

	private void tick() {
		keyManager.tick();
		if (State.getState() != null)
			State.getState().tick();
	}

	private void render() {
		bufferStrategy = display.getCanvas().getBufferStrategy();
		if (bufferStrategy == null)
		{
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		graphics = bufferStrategy.getDrawGraphics();
		//clear screen
		graphics.clearRect(0, 0, width, height);

		//draw
		if (State.getState() != null)
			State.getState().render(graphics);
		else
			System.out.println("no state");
		if (fadeOut) {
			alpha++;
			if (alpha<=255) {
				screenOverlay.overlayScreen(graphics, new Color(0, 0, 0, alpha));
			}
			else {
				screenOverlay.overlayScreen(graphics, new Color(0, 0, 0, 255));
			}
		}

		if (fadeIn) {
			alpha--;
			if (alpha>=0) {
				screenOverlay.overlayScreen(graphics, new Color(0, 0, 0, alpha));
			}
			else {
				screenOverlay.overlayScreen(graphics, new Color(0, 0, 0, 0));
			}
		}

		//done drawing
		bufferStrategy.show();
		graphics.dispose();
	}
	@Override
	public void run()
	{
		try {
			init();
		} catch (Exception e) {
			System.out.println("There was an error during initialization");
			e.printStackTrace();
		}

		int frameCounter = 0;
		int fps = 60;
		double timePerTick = (double) 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();

		while(running)
		{
			frameCounter++;
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			lastTime = now;
			if (delta >= 1)
			{
				tick();
				render();
				delta = 0;
				DebugTools.outputText(Integer.toString(frameCounter), graphics, 16, 82);
				frameCounter = 0;
			}

		}

		stop();

	}

	public KeyManager getKeyManager()
	{
		return keyManager;
	}

	public MouseManager getMouseManager()
	{
		return mouseManager;
	}

	public GameCamera getGameCamera()
	{
		return gameCamera;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public ScreenOverlay getScreenOverlay() {
		return screenOverlay;
	}

	public synchronized void start()
	{
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop()
	{
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setFadeIn(boolean fade) {
		this.fadeIn = fade;
		alpha = 255;
	}

	public boolean isFadeIn() {
		return fadeIn;
	}

	public void setFadeOut(boolean fade){
		this.fadeOut = fade;
		alpha = 0;
	}

	public boolean isFadeOut() {
		return fadeOut;
	}

}
