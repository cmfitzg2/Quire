package src;

public class Handler
{
	private Game game;
	private World world;
	private boolean playerFrozen = false, isInMenu = false;
	private int worldNumber = 1;
	private Flags flags;

	public Handler(Game game)
	{
		this.game = game;
	}

	public KeyManager getKeyManager()
	{
		return game.getKeyManager();
	}

	public MouseManager getMouseManager()
	{
		return game.getMouseManager();
	}

	public GameCamera getGameCamera()
	{
		return game.getGameCamera();
	}

	public int getWidth()
	{
		return game.getWidth();
	}

	public int getHeight()
	{
		return game.getHeight();
	}

	public Game getGame() {
		return game;
	}

	public boolean isPlayerFrozen()
	{
		return playerFrozen;
	}

	public void setPlayerFrozen(boolean playerFrozen)
	{
		this.playerFrozen = playerFrozen;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public int getWorldNumber()
	{
		return worldNumber;
	}

	public void setWorldNumber(int worldNumber)
	{
		this.worldNumber = worldNumber;
	}

	public ScreenOverlay getScreenOverlay() {
		return game.getScreenOverlay();
	}

	public Player getPlayer() {
		return world.getEntityManager().getPlayer();
	}

	public Flags getFlags() {
		return flags;
	}

	public void setFlags(Flags flags) {
		this.flags = flags;
	}

	public Inventory getInventory() {
		return getPlayer().getInventory();
	}

	public ItemManager getItemManager() {
		return getPlayer().getItemManager();
	}
}
