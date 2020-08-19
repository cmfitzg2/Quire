package src;

import java.awt.*;

public class Tree extends StaticEntity {

    public Tree(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        bounds.x = 30;
        bounds.y = (int) (height/1.5f);
        bounds.width = width/2;
        bounds.height = (int) (height - height/1.5f);
    }

    @Override
    public boolean isFirstRender() {
        return false;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics)
    {
        graphics.drawImage(Assets.tree, (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void die()
    {
        handler.getWorld().getEntityManager().removeEntity(this);
    }

    @Override
    public void interactedWith() {
        isInteracting = true;
        isInteracting = false;
    }

    @Override
    public boolean isInteracting() {
        return isInteracting;
    }
}
