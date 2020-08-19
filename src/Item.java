package src;

import java.awt.*;

public abstract class Item {

    protected KeyManager keyManager;
    protected Handler handler;

    public Item(Handler handler, KeyManager keyManager, int id, String itemTitle) {
        this.keyManager = keyManager;
        this.handler = handler;
        this.id = id;
        this.itemTitle = itemTitle;
    }

    public abstract void tick();

    public abstract void render(Graphics graphics);

    public String itemTitle;

    public abstract void unequipItem();

    public abstract void equipItem();

    public boolean equipped;

    public int id;
}
