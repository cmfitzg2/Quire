package src;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends Creature
{
	private static boolean down = false, up = false, left = false, right = false;
	private boolean interactedWith = false;
	private ItemManager itemManager;
	private Inventory inventory;
	//Animations	
	private Animation animDown, animUp, animLeft, animRight;
	public static Rectangle playerRec, interactionRectangle;
	private SurvivalStatus survivalStatus;
	//Font
	Font font;

	public Player(Handler handler, float x, float y) throws Exception {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

		bounds.x = 16;
		bounds.y = 32;
		bounds.width = 32;
		bounds.height = 32;

		//Animations
		animDown = new Animation(175, Assets.player_down);
		animLeft = new Animation(100, Assets.player_left);
		animUp = new Animation(175, Assets.player_up);
		animRight = new Animation(100, Assets.player_right);

		//Font
		font = new Font("overlay", Font.ITALIC|Font.BOLD, 16);

		//Items
		itemManager = new ItemManager(handler, this);
		inventory = new Inventory(handler, this);

		//Survival elements
		survivalStatus = new SurvivalStatus(handler);
	}

	@Override
	public void tick()
	{
		currentPlayerRectangle();
		if (!handler.isPlayerFrozen()) {

			//Animations
			animDown.tick();
			animLeft.tick();
			animUp.tick();
			animRight.tick();

			//Movement
			getInput();
			move();
			handler.getGameCamera().centerOnEntity(this);
			checkInteraction();
		}
		itemManager.tick();
		survivalStatus.tick();
		inventory.tick();
	}

	@Override
	public void render(Graphics graphics)
	{
		if (handler.isPlayerFrozen())
		{
			if (up)
				graphics.drawImage(Assets.playerUpNormal, (int) (x - handler.getGameCamera().getxOffset()),  (int) (y-handler.getGameCamera().getyOffset()), width, height, null);
			if (down)
				graphics.drawImage(Assets.playerDownNormal, (int) (x - handler.getGameCamera().getxOffset()),  (int) (y-handler.getGameCamera().getyOffset()), width, height, null);
			if (left)
				graphics.drawImage(Assets.playerLeftNormal, (int) (x - handler.getGameCamera().getxOffset()),  (int) (y-handler.getGameCamera().getyOffset()), width, height, null);
			if (right)
				graphics.drawImage(Assets.playerRightNormal, (int) (x - handler.getGameCamera().getxOffset()),  (int) (y-handler.getGameCamera().getyOffset()), width, height, null);
		}
		else {
			graphics.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),  (int) (y-handler.getGameCamera().getyOffset()), width, height, null);
			/*
			//collison box viewer
		graphics.setColor(Color.RED);
		graphics.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset()),
				bounds.width, bounds.height);
			*/
		}
		survivalStatus.render(graphics);
		itemManager.render(graphics);
	}

	private void currentPlayerRectangle()
	{
		playerRec = new Rectangle((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset()),
				bounds.width, bounds.height);
	}

	private void checkInteraction()
	{
		Rectangle collisionBounds = getCollisionBounds(0,0);
		interactionRectangle = new Rectangle();
		int interactionSize = 20;
		interactionRectangle.width = interactionSize;
		interactionRectangle.height = interactionSize;
		if (up)
		{
			interactionRectangle.x = collisionBounds.x + collisionBounds.width/2 - interactionSize/2;
			interactionRectangle.y = collisionBounds.y - interactionSize;
		}
		else if (down)
		{
			interactionRectangle.x = collisionBounds.x + collisionBounds.width/2 - interactionSize/2;
			interactionRectangle.y = collisionBounds.y + collisionBounds.height;
		}
		else if (left)
		{
			interactionRectangle.x = collisionBounds.x - interactionSize;
			interactionRectangle.y = collisionBounds.y + collisionBounds.height/2 - interactionSize/2;
		}
		else if (right)
		{
			interactionRectangle.x = collisionBounds.x + collisionBounds.width;
			interactionRectangle.y = collisionBounds.y + collisionBounds.height/2 - interactionSize/2;
		}
	}


	@Override
	public void die()
	{
		System.out.println("you lose");
	}

	private void getInput()
	{
		xMove = 0;
		yMove = 0;
		runSpeed = 8.0f;
		speed = 4.0f;

		if (handler.getKeyManager().up) {
			if (!handler.getKeyManager().isStillHoldingUp()) {
				handler.getKeyManager().setStillHoldingUp(true);
			}
			if (handler.getKeyManager().shift)
				yMove = -runSpeed;
			else
				yMove = -speed;
		}
		if (handler.getKeyManager().down) {
			if (!handler.getKeyManager().isStillHoldingDown()) {
				handler.getKeyManager().setStillHoldingDown(true);
			}
			if (handler.getKeyManager().shift)
				yMove = runSpeed;
			else
				yMove = speed;
		}
		if (handler.getKeyManager().left) {
			if (!handler.getKeyManager().isStillHoldingLeft()) {
				handler.getKeyManager().setStillHoldingLeft(true);
			}
			if (handler.getKeyManager().shift)
				xMove = -runSpeed;
			else
				xMove = -speed;
		}
		if (handler.getKeyManager().right) {
			if (!handler.getKeyManager().isStillHoldingRight()) {
				handler.getKeyManager().setStillHoldingRight(true);
			}
			if (handler.getKeyManager().shift)
				xMove = runSpeed;
			else
				xMove = speed;
		}
		if (handler.getKeyManager().c) {
			if (!handler.getKeyManager().isStillHoldingC()) {
				handler.getKeyManager().setStillHoldingC(true);
			}
		}

		if (handler.getKeyManager().z)
		{
			if (!handler.getKeyManager().isStillHoldingZ()) {
				handler.getKeyManager().setStillHoldingZ(true);

				for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
					if (e.equals(this))                    //an entity cannot interact with itself
						continue;
					if (e.getCollisionBounds(0, 0).intersects(interactionRectangle)) {
						if (e.isInteracting())
							return;
						else
							e.interactedWith();                //call interaction function specified by any class extending entity
						return;
					}
				}
			}
		}
	}

	private void autoMove()
	{

	}

	private BufferedImage getCurrentAnimationFrame()
	{
		if (xMove<0)
		{
			left = true; right = false; up = false; down = false;
			return animLeft.getCurrentFrame();
		}
		else if (xMove>0)
		{
			right = true; left = false; up = false; down = false;
			return animRight.getCurrentFrame();
		}
		else if (yMove<0)
		{
			up = true; left = false; right = false; down = false;
			return animUp.getCurrentFrame();
		}
		else if (yMove>0)
		{
			down = true; left = false; up = false; right = false;
			return animDown.getCurrentFrame();
		}
		//not moving
		else if (right)
			return Assets.playerRightNormal;
		else if (up)
			return Assets.playerUpNormal;
		else if (left)
			return Assets.playerLeftNormal;
		else
			return Assets.playerDownNormal;

	}

	public void interactedWith()
	{
		//Player should never be interacted with
		interactedWith = true;
		System.out.println("Interaction with " + this);
		interactedWith = false;
	}

	public boolean isInteracting()
	{
		return interactedWith;
	}
	public static Rectangle getPlayerRec() {
		return playerRec;
	}

	@Override
	public boolean isFirstRender() {
		// TODO Auto-generated method stub
		return false;
	}

	public static String getDirection()
	{
		if (up)
			return "up";
		if (down)
			return "down";
		if (left)
			return "left";
		else return "right";
	}

	public void setDirection(String dir)
	{
		if (dir.equals(up))
		{
			up = true; down = false; left = false; right = false;
		}
		if (dir.equals(down))
		{
			down = true; left = false; right = false; up = false;
		}
		if (dir.equals(left))
		{
			left = true; down = false; up = false; right = false;
		}
		if (dir.equals(right))
		{
			right = true; up = false; down = false; left = false;
		}
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public SurvivalStatus getSurvivalStatus() {
		return survivalStatus;
	}

	public Inventory getInventory() {
		return inventory;
	}
}
