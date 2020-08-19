package src;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private Handler handler;
    public static String KEY_ITEM = "key", ART_ITEM = "art", YEN_ITEM = "yen";
    private boolean isOpen, ignoreInputs;
    private List<String> keyItems, arts, yen;
    private int xIndex, yIndex, xSpace = 268, ySpace = 64, xStart = 64, yStart = 112, inventoryHeaderY = 96;
    private KeyManager keyManager;
    private Font inventoryFont;
    private ItemManager itemManager;

    public Inventory(Handler handler, Player player) {
        this.handler = handler;
        keyManager = handler.getKeyManager();
        itemManager = player.getItemManager();
        keyItems = new ArrayList<>();
        arts = new ArrayList<>();
        yen = new ArrayList<>();
        keyItems.add("aaaa");
        arts.add("sdfgsdfg");
        yen.add("sdfghrth");
        keyItems.add("aafsbaa");
        arts.add("sdfgsddsasfg");
        yen.add("sdfghrtbth");
        keyItems.add("aaawerta");
        arts.add("sdfgsxzdsadfg");
        yen.add("sdfghrmfghnth");
        keyItems.add("aaareta");
        arts.add("sdfggggdsdfg");
        yen.add("sdfghgggggggggrth");
        keyItems.add("aaasASa");
        arts.add("sdfgsdDFSHGfg");
        yen.add("sdfghrtEDFGh");

        inventoryFont = Assets.philosopher.deriveFont(Assets.philosopher.getSize() * 0.5f);
    }

    public void tick() {
        if (keyManager.q && !keyManager.isStillHoldingQ()) {
            keyManager.setStillHoldingQ(true);
            isOpen = !isOpen;
        }
        if (isOpen) {
            if (!handler.isPlayerFrozen()) {
                handler.setPlayerFrozen(true);
            }
            if (!ignoreInputs) {
                moveFocus();
            }
            if (keyManager.enter && ! keyManager.isStillholdingEnter()) {
                keyManager.setStillholdingEnter(true);
                String selectedItem = getItemNameAtIndex();
                if (null != selectedItem) {
                    selectItem(selectedItem);
                }
            }
        } else {
            if (handler.isPlayerFrozen()) {
                handler.setPlayerFrozen(false);
            }
        }
    }

    public void render(Graphics graphics) {
        if (isOpen) {
            if (Assets.inventory != null) {
                graphics.drawImage(Assets.inventory, 48, 48, null);
            }
            if (!keyItems.isEmpty() || !arts.isEmpty() || !yen.isEmpty()) {
                graphics.drawImage(Assets.inventoryHighlight, 58 + (xSpace * xIndex), 128 + (ySpace * yIndex + 1), null);
            }
            graphics.drawImage(Assets.activeInventoryHeader[xIndex], 48 + (xSpace * xIndex), 48, null);
            int i = 0;
            graphics.setFont(inventoryFont);
            drawInventoryHeaders(graphics);
            graphics.setColor(Color.BLACK);
            for (String item : keyItems) {
                i++;
                graphics.drawString(item, xStart, yStart + (i * ySpace));
            }

            i = 0;
            for (String item : arts) {
                i++;
                graphics.drawString(item, xStart + xSpace, yStart + (i * ySpace));
            }

            i = 0;
            for (String item: yen) {
                i++;
                graphics.drawString(item, xStart + xSpace * 2, yStart + (i * ySpace));
            }
        }
    }

    public boolean addItem(String itemName, String itemType) {
        if (itemType.equals(KEY_ITEM)) {
            keyItems.add(itemName);
            return true;
        } else if (itemType.equals(ART_ITEM)) {
            arts.add(itemName);
            return true;
        } else if (itemType.equals(YEN_ITEM)) {
            yen.add(itemName);
            return true;
        }
        return false;
    }

    private void drawInventoryHeaders(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.drawString("Key Items", xStart + 32, inventoryHeaderY);
        graphics.drawString("Arts", xStart + xSpace + 64, inventoryHeaderY);
        graphics.drawString("Yen", xStart + xSpace * 2 + 64, inventoryHeaderY);
    }

    public String getItemNameAtIndex() {
        if (xIndex == 0) {
            if (!keyItems.isEmpty()) {
                return keyItems.get(yIndex);
            }
        } else if (xIndex == 1) {
            if (!arts.isEmpty()) {
                return arts.get(yIndex);
            }
        } else {
            if (!yen.isEmpty()) {
                return yen.get(yIndex);
            }
        }
        return null;
    }

    public void selectItem(String item) {
        if (item.equals(GeneralConstants.YELLOW_RING_NAME)) {

        }
    }

    private void moveFocus() {
        if (keyItems.isEmpty() && arts.isEmpty() && yen.isEmpty()) {
            return;
        }
        if (keyManager.left && !keyManager.isStillHoldingLeft()) {
            keyManager.setStillHoldingLeft(true);
            switch (xIndex) {
                case 0:
                    if (!yen.isEmpty()) {
                        if (yIndex >= yen.size()) {
                            yIndex = yen.size() - 1;
                        }
                        xIndex = 2;
                    } else if (!arts.isEmpty()) {
                        if (yIndex >= arts.size()) {
                            yIndex = arts.size() - 1;
                        }
                        xIndex = 1;
                    }
                    break;
                case 1:
                    if (!keyItems.isEmpty()) {
                        if (yIndex >= keyItems.size()) {
                            yIndex = keyItems.size() - 1;
                        }
                        xIndex = 0;
                    } else if (!yen.isEmpty()) {
                        if (yIndex >= yen.size()) {
                            yIndex = yen.size() - 1;
                        }
                        xIndex = 2;
                    }
                    break;
                case 2:
                    if (!arts.isEmpty()) {
                        if (yIndex >= arts.size()) {
                            yIndex = arts.size() - 1;
                        }
                        xIndex = 1;
                    } else if (!keyItems.isEmpty()) {
                        if (yIndex >= keyItems.size()) {
                            yIndex = keyItems.size() - 1;
                        }
                        xIndex = 0;
                    }
                    break;
            }
        } else if (keyManager.right && !keyManager.isStillHoldingRight()) {
            keyManager.setStillHoldingRight(true);
            switch (xIndex) {
                case 0:
                    if (!arts.isEmpty()) {
                        if (yIndex >= arts.size()) {
                            yIndex = arts.size() - 1;
                        }
                        xIndex = 1;
                    } else if (!yen.isEmpty()) {
                        if (yIndex >= yen.size()) {
                            yIndex = yen.size() - 1;
                        }
                        xIndex = 2;
                    }
                    break;
                case 1:
                    if (!yen.isEmpty()) {
                        if (yIndex >= yen.size()) {
                            yIndex = yen.size() - 1;
                        }
                        xIndex = 2;
                    } else if (!keyItems.isEmpty()) {
                        if (yIndex >= keyItems.size()) {
                            yIndex = keyItems.size() - 1;
                        }
                        xIndex = 0;
                    }
                    break;
                case 2:
                    if (!keyItems.isEmpty()) {
                        if (yIndex >= keyItems.size()) {
                            yIndex = keyItems.size() - 1;
                        }
                        xIndex = 0;
                    } else if (!arts.isEmpty()) {
                        if (yIndex >= arts.size()) {
                            yIndex = arts.size() - 1;
                        }
                        xIndex = 1;
                    }
                    break;
            }
        } else if (keyManager.up && ! keyManager.isStillHoldingUp()) {
            keyManager.setStillHoldingUp(true);
            if (yIndex > 0) {
                yIndex--;
            } else {
                switch (xIndex) {
                    case 0:
                        yIndex = keyItems.size() - 1;
                        break;
                    case 1:
                        yIndex = arts.size() - 1;
                        break;
                    case 2:
                        yIndex = yen.size() - 1;
                        break;
                }
            }
        } else if (keyManager.down && !keyManager.isStillHoldingDown()) {
            keyManager.setStillHoldingDown(true);
            switch (xIndex) {
                case 0:
                    if (yIndex < keyItems.size() - 1) {
                        yIndex++;
                    } else {
                        yIndex = 0;
                    }
                    break;
                case 1:
                    if (yIndex < arts.size() - 1) {
                        yIndex++;
                    } else {
                        yIndex = 0;
                    }
                    break;
                case 2:
                    if (yIndex < yen.size() - 1) {
                        yIndex++;
                    } else {
                        yIndex = 0;
                    }
                    break;
            }
        }
        if (xIndex == 0 && keyItems.isEmpty()) {
            if (!arts.isEmpty()) {
                xIndex = 1;
            } else {
                xIndex = 2;
            }
        } else if (xIndex == 1 && arts.isEmpty()) {
            if (!keyItems.isEmpty()) {
                xIndex = 0;
            } else {
                xIndex = 2;
            }
        } else if (xIndex == 2 && yen.isEmpty()) {
            if (!keyItems.isEmpty()) {
                xIndex = 0;
            } else {
                xIndex = 1;
            }
        }
    }
}
