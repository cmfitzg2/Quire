package src;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;

public class EntityManager
{

	private Handler handler;
	private Player player;
	private ArrayList<Entity> entities;
	private Comparator<Entity> renderSorter = (a, b) -> {
		if (a.getY() + a.getHeight() < b.getY() + b.getHeight())
			return -1;
		return 1;
	};
	private Entity e;

	public EntityManager(Handler handler, Player player)
	{
		this.handler = handler;
		this.player = player;
		entities = new ArrayList<>();
		addEntity(player);
	}

	public void tick()
	{
		for(int i=0; i<entities.size(); i++)
		{
			e = entities.get(i);
			e.tick();
			if (!e.isActive())
				entities.remove(e);
		}
		entities.sort(renderSorter);
	}

	public void render(Graphics graphics)
	{
		for(Entity e : entities)
		{
			if (e.isFirstRender())
				e.render(graphics);
		}

		for(Entity e : entities)
		{
			if (!e.isFirstRender())
				e.render(graphics);
		}
	}

	public void addEntity(Entity e)
	{
		entities.add(e);
	}

	public void removeEntity(Entity e)
	{
		entities.remove(e);
	}

	//GETTERS SETTERS


	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}
}
