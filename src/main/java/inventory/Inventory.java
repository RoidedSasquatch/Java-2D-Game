package inventory;

import entities.Entity;
import entities.objects.GameObject;
import entities.objects.consumables.ConsumableObject;
import entities.objects.equipables.WeaponObject;
import views.GameScreen;

import java.util.ArrayList;
import java.util.List;

import static entities.objects.ObjectTypes.*;

public class Inventory {
    private final GameScreen gameScreen;
    private final List<Entity> inventory;
    private final Entity[] hotbar;
    private int inventorySize;
    private int unlockedRows;

    public Inventory(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        inventory = new ArrayList<>();
        hotbar = new Entity[9];
        inventorySize = 24;
        unlockedRows = 2;
    }

    public void addItemToInventory(int objectKey) {
        String text = "";
        if (inventory.size() < inventorySize) {
            inventory.add(gameScreen.getGameObjects().get(objectKey));
            text = "Picked up " + gameScreen.getGameObjects().get(objectKey).getName();
            gameScreen.getGameObjects().remove(objectKey);

        } else {
            text = "Inventory full";
        }
        gameScreen.getGui().addMessage(text);
    }

    public void equipItem(int slot) {
        int itemIndex = gameScreen.getGui().getItemIndexOnSlot();
        if(itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);
            if(selectedItem instanceof WeaponObject) {
                if(gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) == hotbar[0] ) {
                    hotbar[0] = null;
                } else if(gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) == hotbar[1] ) {
                    hotbar[1] = null;
                } else if(gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) == hotbar[2] ) {
                    hotbar[2] = null;
                } else if(gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) == hotbar[3] ) {
                    hotbar[3] = null;
                } else if(gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) == hotbar[4] ) {
                    hotbar[4] = null;
                } else if(gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) == hotbar[5] ) {
                    hotbar[5] = null;
                } else if(gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) == hotbar[6] ) {
                    hotbar[6] = null;
                } else if(gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) == hotbar[7] ) {
                    hotbar[7] = null;
                } else if(gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) == hotbar[8] ) {
                    hotbar[8] = null;
                } else {
                    hotbar[slot] = (selectedItem);
                }
            }
            if(((GameObject)selectedItem).getType() == SHIELD) {
                if(gameScreen.getPlayer().getEquippedShield() == selectedItem) {
                    gameScreen.getPlayer().setEquippedShield(null);
                } else {
                    gameScreen.getPlayer().setEquippedShield(selectedItem);
                }
                gameScreen.getPlayer().setDefense(gameScreen.getPlayer().calculateDefense());
            }
            if(((GameObject)selectedItem).getType() == HELMET) {
                if(gameScreen.getPlayer().getEquippedHelmet() == selectedItem) {
                    gameScreen.getPlayer().setEquippedHelmet(null);
                } else {
                    gameScreen.getPlayer().setEquippedHelmet(selectedItem);
                }
                gameScreen.getPlayer().setDefense(gameScreen.getPlayer().calculateDefense());
            }
            if(((GameObject)selectedItem).getType() == ARMOR) {
                if(gameScreen.getPlayer().getEquippedArmor() == selectedItem) {
                    gameScreen.getPlayer().setEquippedArmor(null);
                } else {
                    gameScreen.getPlayer().setEquippedArmor(selectedItem);
                }
                gameScreen.getPlayer().setDefense(gameScreen.getPlayer().calculateDefense());
            }
            if(((GameObject)selectedItem).getType() == JEWELERY) {
                if(gameScreen.getPlayer().getEquippedTrinket() == selectedItem) {
                    gameScreen.getPlayer().setEquippedTrinket(null);
                } else {
                    gameScreen.getPlayer().setEquippedTrinket(selectedItem);
                }
            }
            if(selectedItem instanceof ConsumableObject) {
                ((ConsumableObject)selectedItem).Consume(gameScreen.getPlayer());
                inventory.remove(itemIndex);
            }
        }
    }

    public List<Entity> getInventoryArray() {
        return inventory;
    }

    public Entity[] getHotbar() {
        return hotbar;
    }

    public int getInventorySize() {
        return inventorySize;
    }

    public int getUnlockedRows() {
        return unlockedRows;
    }
}
