package src;

import java.awt.*;
import java.util.ArrayList;

public class ItemManager {

    private Handler handler;
    private Player player;
    private ArrayList<Item> items;
    private Item item;

    public ItemManager(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
        items = new ArrayList<>();
    }

    public void tick() {
        for (Item item : items) {
            item.tick();
        }
    }

    public void render(Graphics graphics) {
        for (Item item : items) {
            item.render(graphics);
        }
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Item getItemById(int id) {
        for (Item item : items) {
            if (item.id == id) {
                return item;
            }
        }
        return null;
    }

    public Item getItemByTitle(String title) {
        for (Item item : items) {
            if (item.itemTitle.equals(title)) {
                return item;
            }
        }
        return null;
    }
}
