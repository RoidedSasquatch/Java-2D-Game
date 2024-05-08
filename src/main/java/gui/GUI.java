package gui;

import entities.objects.GameObject;
import entities.objects.consumables.ConsumableObject;
import entities.objects.equipables.WeaponObject;
import entities.objects.equipables.WearableObject;
import views.GameScreen;
import views.GameState;
import utils.UtilityMethods;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static utils.Constants.*;

public class GUI {
    private final GameScreen gameScreen;
    private Graphics2D graphics2D;
    private Font silver;
    private BufferedImage statusBar, healthBar, manaBar;
    private List<String> message;
    private List<Integer> messageCounter;
    private String currentDialogue;
    private int commandNumber;
    private int titleScreenState;
    private int slotCol;
    private int slotRow;
    private int hotbarCol;
    private int itemMenuState;
    private int characterMenuState;
    private int tabbing;
    private int tabbingCounter;
    private boolean tabbed;
    private int pauseMenuState;

    public GUI(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        commandNumber = 0;
        titleScreenState = 0;
        message = new ArrayList<>();
        messageCounter = new ArrayList<>();
        slotCol = 0;
        slotRow = 0;
        hotbarCol = 0;
        itemMenuState = 0;
        characterMenuState = 0;
        pauseMenuState = 0;
        tabbing = 0;
        tabbingCounter = 0;
        tabbed = false;

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("hud/font/Silver.ttf");
            silver = Font.createFont(Font.TRUETYPE_FONT, inputStream);

            inputStream = getClass().getClassLoader().getResourceAsStream("hud/statusbar.png");
            statusBar = ImageIO.read(inputStream);
            statusBar = UtilityMethods.scaleImage(statusBar, GAME_TILE_SIZE * 5, (GAME_TILE_SIZE * 2) - 10);

            inputStream = getClass().getClassLoader().getResourceAsStream("hud/healthbar.png");
            healthBar = ImageIO.read(inputStream);
            healthBar = UtilityMethods.scaleImage(healthBar, 137, 14);

            inputStream = getClass().getClassLoader().getResourceAsStream("hud/manabar.png");
            manaBar = ImageIO.read(inputStream);
            manaBar = UtilityMethods.scaleImage(manaBar, 129, 9);
        } catch(FontFormatException | IOException e) {
            System.out.println("Could not load or create font. Error: " + e);
        }
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;

        graphics2D.setFont(silver);
        graphics2D.setColor(Color.WHITE);
        if(gameScreen.getGameState() == GameState.TITLESTATE) {
            drawTitleHUD();
        }

        if(gameScreen.getGameState() == GameState.PLAYSTATE) {
            drawPlayHUD();
            drawMessage();
        }
        if(gameScreen.getGameState() == GameState.PAUSESTATE) {
            drawPauseHUD();
        }
        if(gameScreen.getGameState() == GameState.DIALOGUESTATE) {
            drawPlayHUD();
            drawDialogueHUD();
        }
        if(gameScreen.getGameState() == GameState.CHARACTERSTATE) {
            drawCharacterHUD();
        }
    }

    private void drawTitleHUD() {
        if(titleScreenState == 0) {
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 96f));
            String text = "Game Title In Progress";
            int x = getCenteredX(text);
            int y = GAME_TILE_SIZE * 3;

            //Title Shadow
            graphics2D.setColor(Color.DARK_GRAY);
            graphics2D.drawString(text, x + 5, y + 5);

            //Title
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(text, x, y);

            //Character Image
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawImage(gameScreen.getPlayer().getPlayerSprite().getSprite(0,0, 64, 64,2), gameScreen.screenWidth / 2 -  (2 * GAME_TILE_SIZE) - 20, y - GAME_TILE_SIZE, GAME_TILE_SIZE * 5, GAME_TILE_SIZE * 5, null);

            //Menu
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 48f));
            text = "New Game";
            x = getCenteredX(text);
            y += GAME_TILE_SIZE * 4;
            graphics2D.drawString(text, x, y);
            if (commandNumber == 0) {
                graphics2D.drawString(">", x - GAME_TILE_SIZE, y);
            }

            text = "Load Game";
            x = getCenteredX(text);
            y += GAME_TILE_SIZE;
            graphics2D.drawString(text, x, y);
            if (commandNumber == 1) {
                graphics2D.drawString(">", x - GAME_TILE_SIZE, y);
            }

            text = "Quit Game";
            x = getCenteredX(text);
            y += GAME_TILE_SIZE;
            graphics2D.drawString(text, x, y);
            if (commandNumber == 2) {
                graphics2D.drawString(">", x - GAME_TILE_SIZE, y);
            }
        } else if(titleScreenState == 1) {
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(graphics2D.getFont().deriveFont(52f));

            String text = "Class Selection";
            int x = getCenteredX(text);
            int y = GAME_TILE_SIZE * 3;
            graphics2D.drawString(text, x, y);

            text = "Warrior";
            x = getCenteredX(text);
            y += GAME_TILE_SIZE * 3;
            graphics2D.drawString(text, x, y);
            if(commandNumber == 0) {
                graphics2D.drawString(">", x - GAME_TILE_SIZE, y);
            }

            text = "Mage";
            x = getCenteredX(text);
            y += GAME_TILE_SIZE;
            graphics2D.drawString(text, x, y);
            if(commandNumber == 1) {
                graphics2D.drawString(">", x - GAME_TILE_SIZE, y);
            }

            text = "Rogue";
            x = getCenteredX(text);
            y += GAME_TILE_SIZE;
            graphics2D.drawString(text, x, y);
            if(commandNumber == 2) {
                graphics2D.drawString(">", x - GAME_TILE_SIZE, y);
            }

            text = "Cancel";
            x = getCenteredX(text);
            y += GAME_TILE_SIZE * 2;
            graphics2D.drawString(text, x, y);
            if(commandNumber == 3) {
                graphics2D.drawString(">", x - GAME_TILE_SIZE, y);
            }
        }
    }

    private void drawPlayHUD() {
        int x = 0;
        int y = 0;

        //Health and Mana
        graphics2D.drawImage(statusBar, x, y, null);
        if(!(gameScreen.getPlayer().getCurrentHealth() <= 0)) {
            if(!(gameScreen.getPlayer().getCurrentHealth() > gameScreen.getPlayer().getMaxHealth() + 1)) {
                healthBar = UtilityMethods.scaleImage(healthBar, (int) (137 * gameScreen.getPlayer().getCurrentHealth() / gameScreen.getPlayer().getMaxHealth()), 14);
            }
            graphics2D.drawImage(healthBar, 92, 28, null );
        }
        if(!(gameScreen.getPlayer().getCurrentMana() <= 0)) {
            manaBar = UtilityMethods.scaleImage(manaBar, (int) (129 * gameScreen.getPlayer().getCurrentMana() / gameScreen.getPlayer().getMaxMana()), 9);
            graphics2D.drawImage(manaBar, 80, 48, null );
        }

        //Hotbar
        createHotbar();
    }

    private void drawDialogueHUD() {
        int x = (GAME_TILE_SIZE * 2) + (GAME_TILE_SIZE / 2);
        int y = gameScreen.screenHeight - (GAME_TILE_SIZE * 3) - 5;
        int width = gameScreen.screenWidth - (GAME_TILE_SIZE * 5);
        int height = GAME_TILE_SIZE * 3;

        drawSubWindow(x, y, width, height, 5);

        graphics2D.setFont(graphics2D.getFont().deriveFont(32f));
        x += GAME_TILE_SIZE;
        y += GAME_TILE_SIZE;

        for(String line : currentDialogue.split("\n")) {
            graphics2D.drawString(line, x, y);
            y += 40;
        }
    }

    private void drawCharacterHUD() {
        drawSubWindow(GAME_TILE_SIZE * 2, 15, GAME_TILE_SIZE * 12, GAME_TILE_SIZE, 5);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 30f));
        if(tabbed) {
            if (tabbing == 0) {
                graphics2D.setColor(new Color(240, 190, 90));
            }
            tabbingCounter ++;
            if(tabbingCounter > 60) {
                tabbed = false;
                tabbingCounter = 0;
            }
        }
        graphics2D.drawString("< Q", GAME_TILE_SIZE * 3 - 15, GAME_TILE_SIZE -2);
        graphics2D.setColor(Color.WHITE);
        if(tabbed) {
            if (tabbing == 1) {
                graphics2D.setColor(new Color(240, 190, 90));
            }
            tabbingCounter ++;
            if(tabbingCounter > 60) {
                tabbed = false;
                tabbingCounter = 0;
            }
        }
        graphics2D.drawString("E >", GAME_TILE_SIZE * 13 - 10, GAME_TILE_SIZE -2);
        graphics2D.setColor(Color.WHITE);
        if(characterMenuState == 0) {
            graphics2D.drawString("Inventory", getCenteredX("Status"), GAME_TILE_SIZE - 2);
            drawInventoryHUD();
        } else if(characterMenuState == 1) {
            graphics2D.drawString("Status", getCenteredX("Status"), GAME_TILE_SIZE - 2);
            drawStatusHUD();
        } else if(characterMenuState == 2) {
            graphics2D.drawString("Crafting", getCenteredX("Crafting"), GAME_TILE_SIZE - 2);
            //drawCraftingHUD();
        }
    }

    private void drawPauseHUD() {
        //TODO Finish youtube tut on options skipped for now as now sond or fullscreen yet
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(graphics2D.getFont().deriveFont(32F));
        int frameX = GAME_TILE_SIZE * 4;
        int frameY = GAME_TILE_SIZE;
        int frameWidth = GAME_TILE_SIZE * 8;
        int frameHeight = GAME_TILE_SIZE * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight, 5);

        switch(pauseMenuState) {
            case 0:
                int textX;
                int textY;
                String text = "Pause Menu";
                textX = getCenteredX(text);
                textY = frameY + GAME_TILE_SIZE;
                graphics2D.setFont(graphics2D.getFont().deriveFont(36f));
                graphics2D.drawString(text, textX, textY);


            case 1:
            case 2:
        }
    }

    private void drawStatusHUD() {
        final int frameX = GAME_TILE_SIZE * 2;
        final int frameY = GAME_TILE_SIZE + 15;
        final int frameWidth = GAME_TILE_SIZE * 6;
        final int frameHeight = GAME_TILE_SIZE * 9 + 10;
        int textX = frameX + 20;
        int textY = frameY + GAME_TILE_SIZE;
        final int lineHeight = 32;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight, 5);
        drawSubWindow(frameWidth + (GAME_TILE_SIZE * 2), frameY, frameWidth, frameHeight, 5);

        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(graphics2D.getFont().deriveFont(32f));

        graphics2D.drawString("Level", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Health", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Mana", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Strength", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Attack", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Defense", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("EXP", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("EXP To Level", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Money", textX, textY);
        textY += lineHeight;
        graphics2D.drawString("Equipped", getCenteredX("Equipped"), frameY * 10 + 10);

        //Vals
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + GAME_TILE_SIZE;
        String value;

        value = String.valueOf(gameScreen.getPlayer().getLevel());
        textX = getRightAlignedX(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = gameScreen.getPlayer().getCurrentHealth() + " / " + gameScreen.getPlayer().getMaxHealth();
        textX = getRightAlignedX(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = gameScreen.getPlayer().getCurrentMana() + " / " + gameScreen.getPlayer().getMaxMana();
        textX = getRightAlignedX(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gameScreen.getPlayer().getStrength());
        textX = getRightAlignedX(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gameScreen.getPlayer().getDex());
        textX = getRightAlignedX(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gameScreen.getPlayer().getAttack());
        textX = getRightAlignedX(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gameScreen.getPlayer().getDefense());
        textX = getRightAlignedX(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = gameScreen.getPlayer().getExp() + " / " + gameScreen.getPlayer().getExpToNextLevel();
        textX = getRightAlignedX(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        float expRemaining = gameScreen.getPlayer().getExpToNextLevel() - gameScreen.getPlayer().getExp();
        value = String.valueOf(expRemaining);
        textX = getRightAlignedX(value, tailX);
        graphics2D.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gameScreen.getPlayer().getMoney());
        textX = getRightAlignedX(value, tailX);
        graphics2D.drawString(value, textX, textY);

        if(gameScreen.getPlayer().getEquippedShield() != null) {
            graphics2D.drawImage(((GameObject) gameScreen.getPlayer().getEquippedShield()).getImage(), frameX + GAME_TILE_SIZE * 3 - 5, frameY * 10 + 5, null);
        }
        if(gameScreen.getPlayer().getEquippedHelmet() != null) {
            graphics2D.drawImage(((GameObject) gameScreen.getPlayer().getEquippedHelmet()).getImage(), frameX + (GAME_TILE_SIZE * 4 + 25), frameY * 10 + 5, null);
        }
        if(gameScreen.getPlayer().getEquippedArmor() != null) {
            graphics2D.drawImage(((GameObject) gameScreen.getPlayer().getEquippedArmor()).getImage(), frameX + (GAME_TILE_SIZE * 8 + 25), frameY * 10 + 5, null);
        }
        if(gameScreen.getPlayer().getEquippedTrinket() != null) {
            graphics2D.drawImage(((GameObject) gameScreen.getPlayer().getEquippedTrinket()).getImage(), frameX + (GAME_TILE_SIZE * 10 + 5), frameY * 10 + 5, null);
        }

        //Hotbar
        createHotbar();
    }

    private void drawInventoryHUD() {
        int frameX = GAME_TILE_SIZE * 2;
        int frameY = GAME_TILE_SIZE + 15;
        int frameWidth = GAME_TILE_SIZE * 12;
        int frameHeight = GAME_TILE_SIZE * 6;

        //Frame
        drawSubWindow(frameX, frameY, frameWidth, frameHeight, 5);

        //Slot
        final int slotXStart = frameX + 22;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = GAME_TILE_SIZE + 21;

        //Draw Items
        for(int i = 0 ; i < gameScreen.getPlayer().getInventory().getInventoryArray().size() ; i++) {
            //Draw Equipped Cursor
            if(gameScreen.getPlayer().getInventory().getInventoryArray().get(i) == gameScreen.getPlayer().getEquippedShield() || gameScreen.getPlayer().getInventory().getInventoryArray().get(i) == gameScreen.getPlayer().getEquippedArmor() || gameScreen.getPlayer().getInventory().getInventoryArray().get(i) == gameScreen.getPlayer().getEquippedTrinket()) {
                graphics2D.setColor(new Color(240, 190, 90));
                graphics2D.fillRoundRect(slotX, slotY, GAME_TILE_SIZE, GAME_TILE_SIZE, 10, 10);
            }
            graphics2D.drawImage(((GameObject)gameScreen.getPlayer().getInventory().getInventoryArray().get(i)).getImage(), slotX + 1, slotY + 1, null);
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD,20f));
            for(int j = 0 ; j < gameScreen.getPlayer().getInventory().getHotbar().length ; j++) {
                if (gameScreen.getPlayer().getInventory().getInventoryArray().get(i) == gameScreen.getPlayer().getInventory().getHotbar()[j]) {
                    graphics2D.drawString(String.valueOf(j + 1), slotX + 6, slotY + 16);
                }
            }
            slotX += slotSize;
            if(i == 7 || i == 15 || i == 23) {
                slotX = slotXStart;
                slotY += GAME_TILE_SIZE + 2;
            }
        }

        //Cursor
        int cursorX = slotXStart + (slotSize * slotCol);
        int cursorY = slotYStart + ((GAME_TILE_SIZE + 2) * slotRow);
        graphics2D.setColor(Color.WHITE);
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.drawRoundRect(cursorX, cursorY, GAME_TILE_SIZE, GAME_TILE_SIZE, 10, 10);

        //Item Description
        int dFrameY = frameY + frameHeight;
        int dFrameHeight = GAME_TILE_SIZE * 3 + 5;
        int textX = frameX + 20;
        int textY = dFrameY + 30;
        int itemIndex = getItemIndexOnSlot();
        graphics2D.setFont(graphics2D.getFont().deriveFont(28f));

        if(itemIndex < gameScreen.getPlayer().getInventory().getInventoryArray().size()) {
            drawSubWindow(frameX, dFrameY, frameWidth, dFrameHeight, 5);
            for(String line : ((GameObject)gameScreen.getPlayer().getInventory().getInventoryArray().get(itemIndex)).getDescription().split("\n")) {
                graphics2D.drawString(line, textX, textY);
                textY += 32;
            }
        }

        //Hotbar
        createHotbar();

        //First Item Menu
        final int menuX = cursorX + GAME_TILE_SIZE + 5;
        final int menuWidth = GAME_TILE_SIZE * 2;
        final int menuHeight = GAME_TILE_SIZE * 3 - 10;
        int menuTextX = menuX + 15;
        int menuTextY = cursorY + 28;
        int menuLineHeight = 29;
        if(itemMenuState == 1) {
            drawSubWindow(menuX, cursorY, menuWidth, menuHeight, 2);
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 25f));
            if(commandNumber == 0) {
                graphics2D.setColor(new Color(240, 190, 90));
            }
            if(!(gameScreen.getPlayer().getInventory().getInventoryArray().get(getItemIndexOnSlot()) instanceof ConsumableObject)) {
                    if (gameScreen.getPlayer().getInventory().getInventoryArray().get(getItemIndexOnSlot()) == gameScreen.getPlayer().getEquippedShield() ||
                            gameScreen.getPlayer().getInventory().getInventoryArray().get(getItemIndexOnSlot()) == gameScreen.getPlayer().getEquippedHelmet() ||
                            gameScreen.getPlayer().getInventory().getInventoryArray().get(getItemIndexOnSlot()) == gameScreen.getPlayer().getEquippedArmor() ||
                            gameScreen.getPlayer().getInventory().getInventoryArray().get(getItemIndexOnSlot()) == gameScreen.getPlayer().getEquippedTrinket()||
                            gameScreen.getPlayer().getInventory().getInventoryArray().get(getItemIndexOnSlot()) == gameScreen.getPlayer().getInventory().getHotbar()[0] ||
                            gameScreen.getPlayer().getInventory().getInventoryArray().get(getItemIndexOnSlot()) == gameScreen.getPlayer().getInventory().getHotbar()[1] ||
                            gameScreen.getPlayer().getInventory().getInventoryArray().get(getItemIndexOnSlot()) == gameScreen.getPlayer().getInventory().getHotbar()[2] ||
                            gameScreen.getPlayer().getInventory().getInventoryArray().get(getItemIndexOnSlot()) == gameScreen.getPlayer().getInventory().getHotbar()[3] ||
                            gameScreen.getPlayer().getInventory().getInventoryArray().get(getItemIndexOnSlot()) == gameScreen.getPlayer().getInventory().getHotbar()[4] ||
                            gameScreen.getPlayer().getInventory().getInventoryArray().get(getItemIndexOnSlot()) == gameScreen.getPlayer().getInventory().getHotbar()[5] ||
                            gameScreen.getPlayer().getInventory().getInventoryArray().get(getItemIndexOnSlot()) == gameScreen.getPlayer().getInventory().getHotbar()[6] ||
                            gameScreen.getPlayer().getInventory().getInventoryArray().get(getItemIndexOnSlot()) == gameScreen.getPlayer().getInventory().getHotbar()[7] ||
                            gameScreen.getPlayer().getInventory().getInventoryArray().get(getItemIndexOnSlot()) == gameScreen.getPlayer().getInventory().getHotbar()[8]) {
                        graphics2D.drawString("Unequip", menuTextX, menuTextY);
                    } else {
                        graphics2D.drawString("Equip", menuTextX, menuTextY);
                    }
            } else if(gameScreen.getPlayer().getInventory().getInventoryArray().get(getItemIndexOnSlot()) instanceof ConsumableObject) {
                graphics2D.drawString("Use", menuTextX, menuTextY);
            }
            graphics2D.setColor(Color.WHITE);
            menuTextY += menuLineHeight;
            if(commandNumber == 1) {
                graphics2D.setColor(new Color(240, 190, 90));
            }
            graphics2D.drawString("Drop", menuTextX, menuTextY);
            graphics2D.setColor(Color.WHITE);
            menuTextY += menuLineHeight;
            if(commandNumber == 2) {
                graphics2D.setColor(new Color(240, 190, 90));
            }
            graphics2D.drawString("Swap", menuTextX, menuTextY);
            graphics2D.setColor(Color.WHITE);
            menuTextY += menuLineHeight;
            if(commandNumber == 3) {
                graphics2D.setColor(new Color(240, 190, 90));
            }
            graphics2D.drawString("Cancel", menuTextX, menuTextY);
            graphics2D.setColor(Color.WHITE);
            menuTextY = menuX + 15;
        }

        else if(itemMenuState == 2) {
            drawSubWindow(menuX, cursorY, menuWidth, menuHeight + (4 * GAME_TILE_SIZE) - 18, 2);
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 25f));
            if(commandNumber == 0) {
                graphics2D.setColor(new Color(240, 190, 90));
            }
            graphics2D.drawString("Slot 1", menuTextX, menuTextY);
            graphics2D.setColor(Color.WHITE);
            menuTextY += menuLineHeight;
            if(commandNumber == 1) {
                graphics2D.setColor(new Color(240, 190, 90));
            }
            graphics2D.drawString("Slot 2", menuTextX, menuTextY);
            graphics2D.setColor(Color.WHITE);
            menuTextY += menuLineHeight;
            if(commandNumber == 2) {
                graphics2D.setColor(new Color(240, 190, 90));
            }
            graphics2D.drawString("Slot 3", menuTextX, menuTextY);
            graphics2D.setColor(Color.WHITE);
            menuTextY += menuLineHeight;
            if(commandNumber == 3) {
                graphics2D.setColor(new Color(240, 190, 90));
            }
            graphics2D.drawString("Slot 4", menuTextX, menuTextY);
            graphics2D.setColor(Color.WHITE);
            menuTextY += menuLineHeight;
            if(commandNumber == 4) {
                graphics2D.setColor(new Color(240, 190, 90));
            }
            graphics2D.drawString("Slot 5", menuTextX, menuTextY);
            graphics2D.setColor(Color.WHITE);
            menuTextY += menuLineHeight;
            if(commandNumber == 5) {
                graphics2D.setColor(new Color(240, 190, 90));
            }
            graphics2D.drawString("Slot 6", menuTextX, menuTextY);
            graphics2D.setColor(Color.WHITE);
            menuTextY += menuLineHeight;
            if(commandNumber == 6) {
                graphics2D.setColor(new Color(240, 190, 90));
            }
            graphics2D.drawString("Slot 7", menuTextX, menuTextY);
            graphics2D.setColor(Color.WHITE);
            menuTextY += menuLineHeight;
            if(commandNumber == 7) {
                graphics2D.setColor(new Color(240, 190, 90));
            }
            graphics2D.drawString("Slot 8", menuTextX, menuTextY);
            graphics2D.setColor(Color.WHITE);
            menuTextY += menuLineHeight;
            if(commandNumber == 8) {
                graphics2D.setColor(new Color(240, 190, 90));
            }
            graphics2D.drawString("Slot 9", menuTextX, menuTextY);
            graphics2D.setColor(Color.WHITE);
            menuTextY += menuLineHeight;
            if(commandNumber == 9) {
                graphics2D.setColor(new Color(240, 190, 90));
            }
            graphics2D.drawString("Close", menuTextX, menuTextY);
            graphics2D.setColor(Color.WHITE);
            menuTextY += menuLineHeight;
        }
    }

    private void drawSubWindow(int x, int y, int width, int height, int stroke) {
        Color background = new Color(0, 0 ,0, 210);
        graphics2D.setColor(background);
        graphics2D.fillRoundRect(x, y, width, height, 35, 35);

        background = new Color(255, 255, 255);
        graphics2D.setColor(background);
        graphics2D.setStroke(new BasicStroke(stroke));
        graphics2D.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    private void createHotbar() {
        final int frameHX = GAME_TILE_SIZE * 2;
        final int frameHY = GAME_TILE_SIZE * 11 - 20;
        final int frameHWidth = GAME_TILE_SIZE * 12 + 1;
        final int frameHHeight = GAME_TILE_SIZE + 14;
        final int slotHXStart = frameHX + 7;
        int slotHX = slotHXStart;
        final int slotHY = frameHY + 5;
        final int slotHSize = GAME_TILE_SIZE + 4;

        //Hotbar
        Color background = new Color(0, 0 ,0, 210);
        graphics2D.setColor(background);
        graphics2D.fillRoundRect(frameHX, frameHY, frameHWidth, frameHHeight, 10, 10);

        background = new Color(255, 255, 255);
        graphics2D.setColor(background);
        graphics2D.setStroke(new BasicStroke(1));
        graphics2D.drawRoundRect(frameHX + 1, frameHY + 1, frameHWidth - 1, frameHHeight - 1, 10, 10);
        graphics2D.setStroke(new BasicStroke(1));
        for (int i = 0; i < 9; i++) {
            graphics2D.drawRoundRect(slotHX, slotHY, slotHSize, slotHSize, 10, 10);
            slotHX += 64;
        }
        slotHX = slotHXStart;

        //Cursor
        int cursorHX = slotHXStart + ((slotHSize + 12) * hotbarCol);
        graphics2D.setColor(new Color(240, 190, 90));
        graphics2D.setStroke(new BasicStroke(1));
        graphics2D.drawRoundRect(cursorHX, slotHY, slotHSize, slotHSize, 10, 10);

        //Draw Hotbar Items
        for (int i = 0; i < gameScreen.getPlayer().getInventory().getHotbar().length; i++) {
            if (gameScreen.getPlayer().getInventory().getHotbar()[i] != null) {
                graphics2D.drawImage(((GameObject) gameScreen.getPlayer().getInventory().getHotbar()[i]).getImage(), slotHX + 2, slotHY + 2, null);
            }
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 20f));
            graphics2D.drawString(String.valueOf(i + 1), slotHX + 4, slotHY + 14);
            slotHX += 64;
        }
    }

    private void drawMessage() {
        int messageX = GAME_TILE_SIZE;
        int messageY = GAME_TILE_SIZE * 4;

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 32f));
        for(int i = 0 ; i < message.size() ; i++) {
            if(message.get(i) != null) {
                graphics2D.setColor(Color.BLACK);
                graphics2D.drawString(message.get(i), messageX + 2, messageY + 2);
                graphics2D.setColor(Color.WHITE);
                graphics2D.drawString(message.get(i), messageX, messageY);

                int count = messageCounter.get(i) + 1;
                messageCounter.set(i, count);
                messageY += 50;

                if(messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    private int getCenteredX(String text) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return (gameScreen.screenWidth / 2) - (length / 2);
    }

    private int getRightAlignedX(String text, int tailX) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return tailX - length;
    }

    public int getItemIndexOnSlot() {
        return slotCol + (slotRow * 5);
    }

    public void setCurrentDialogue(String currentDialogue) {
        this.currentDialogue = currentDialogue;
    }

    public int getCommandNumber() {
        return commandNumber;
    }

    public void setCommandNumber(int commandNumber) {
        this.commandNumber = commandNumber;
    }

    public int getTitleScreenState() {
        return titleScreenState;
    }

    public void setTitleScreenState(int titleScreenState) {
        this.titleScreenState = titleScreenState;
    }

    public int getSlotCol() {
        return slotCol;
    }

    public void setSlotCol(int slotCol) {
        this.slotCol = slotCol;
    }

    public int getSlotRow() {
        return slotRow;
    }

    public void setHotbarCol(int hotbarCol) {
        this.hotbarCol = hotbarCol;
    }

    public void setSlotRow(int slotRow) {
        this.slotRow = slotRow;
    }

    public int getHotbarCol() {
        return hotbarCol;
    }

    public int getItemMenuState() {
        return itemMenuState;
    }

    public void setItemMenuState(int itemMenuState) {
        this.itemMenuState = itemMenuState;
    }

    public int getCharacterMenuState() {
        return characterMenuState;
    }

    public void setCharacterMenuState(int characterMenuState) {
        this.characterMenuState = characterMenuState;
    }

    public int getTabbing() {
        return tabbing;
    }

    public void setTabbing(int tabbing) {
        this.tabbing = tabbing;
    }

    public boolean isTabbed() {
        return tabbed;
    }

    public void setTabbed(boolean tabbed) {
        this.tabbed = tabbed;
    }
}
