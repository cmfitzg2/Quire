package src;

import java.awt.*;

public class YellowStoneRingItem extends Item {
    private int darknessLevel = SurvivalStatus.maxDarknessLevel;
    private int chargeLevel = 0;
    private boolean isCharging = false, isInMenu = false;
    private long chargeTimer, dischargeTimer;
    private long chargeThreshold = 100000000L, dischargeThreshold = 1200000000L;     //nanoseconds
    private int menuX;

    public YellowStoneRingItem(Handler handler, KeyManager keyManager) {
        super(handler, keyManager, 0, GeneralConstants.YELLOW_RING_NAME);
    }

    @Override
    public void tick() {
        SurvivalStatus.darknessLevel = darknessLevel;
        if (keyManager.x) {
            charge();
        } else {
            isCharging = false;
        }
        if (isCharging) {
            charge();
        } else {
            if (System.nanoTime() - dischargeTimer >= dischargeThreshold && darknessLevel < SurvivalStatus.maxDarknessLevel) {
                discharge();
            }
        }
    }

    @Override
    public void render(Graphics graphics) {
        /*
        graphics.drawImage(Assets.inventoryRingEquip, 200, 200, null);
        graphics.drawImage(Assets.inventoryRingUnequip, 300, 300, null);
        */
        if (isInMenu) {
            if (menuX == 0) {
                graphics.drawImage(Assets.inventoryRingEquip, handler.getWidth() / 2 - 38, handler.getHeight() - 20, null);
            }
            if (keyManager.enter && !keyManager.isStillholdingEnter()) {
                keyManager.setStillholdingEnter(true);
                if (menuX == 0) {
                    unequipItem();
                } else {

                }
            }
        }
    }

    @Override
    public void equipItem() {

    }

    @Override
    public void unequipItem() {
        chargeLevel = 0;
        darknessLevel = 18;
        handler.getFlags().setVisionLimited(false);
    }

    private void charge() {
        if (!isCharging) {
            chargeTimer = System.nanoTime();
        }
        if (chargeLevel == SurvivalStatus.maxDarknessLevel || keyManager.up || keyManager.left || keyManager.down || keyManager.right) {
            isCharging = false;
        } else {
            isCharging = true;
            if (System.nanoTime() - chargeTimer >= chargeThreshold) {
                chargeLevel++;
                darknessLevel--;
                chargeTimer = System.nanoTime();
                dischargeTimer = System.nanoTime();
            }
        }
    }

    private void discharge() {
        chargeLevel--;
        darknessLevel++;
        dischargeTimer = System.nanoTime();
    }

    public int getDarknessLevel() {
        return darknessLevel;
    }

    public void setDarknessLevel(int darknessLevel) {
        this.darknessLevel = darknessLevel;
    }

    public int getId() {
        return id;
    }
}
