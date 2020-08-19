package src;

import java.awt.*;

public class YellowStoneRingOverworld extends StaticEntity {

    public YellowStoneRingOverworld(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, 32, 32);
        bounds.x = 10;
        bounds.y = 8;
        bounds.width = width;
        bounds.height = height;
    }

    @Override
    public boolean isFirstRender() {
        return false;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(Assets.ringOverworld, (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y-handler.getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void die() {
        handler.getWorld().getEntityManager().removeEntity(this);
    }

    @Override
    public void interactedWith() {
        YellowStoneRingItem ring = new YellowStoneRingItem(handler, handler.getKeyManager());
        handler.getPlayer().getItemManager().addItem(ring);
        handler.getFlags().setVisionLimited(true);
        handler.getInventory().addItem(ring.itemTitle, Inventory.ART_ITEM);
        SurvivalStatus.currentTime = 4;
        SurvivalStatus.heartbeatTimer = System.nanoTime() - SurvivalStatus.heartbeatThreshold;
        die();
    }

    @Override
    public boolean isInteracting() {
        return false;
    }
}
