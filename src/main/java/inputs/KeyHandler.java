package inputs;

import entities.objects.consumables.ConsumableObject;
import entities.objects.equipables.WeaponObject;
import entities.objects.equipables.WearableObject;
import views.GameScreen;
import views.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {
    private final GameScreen gameScreen;
    private boolean upPressed, downPressed, leftPressed, rightPressed, interactPressed;

    //Debug
    public boolean debugPressed;

    public KeyHandler(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        //TITLE
        if(gameScreen.getGameState() == GameState.TITLESTATE) {
            titleState(keyCode);
        }
        //PLAY
        else if(gameScreen.getGameState() == GameState.PLAYSTATE) {
            playState(keyCode);
        }
        //PAUSE
        else if(gameScreen.getGameState() == GameState.PAUSESTATE) {
            pauseState(keyCode);
        }
        //DIALOGUE
        else if(gameScreen.getGameState() == GameState.DIALOGUESTATE) {
            dialogueState(keyCode);
        }
        //CHARACTER
        else if(gameScreen.getGameState() == GameState.CHARACTERSTATE) {
            characterState(keyCode);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(keyCode == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(keyCode == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(keyCode == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }

    private void titleState(int keyCode) {
        if(gameScreen.getGui().getTitleScreenState() == 0) {
            if (keyCode == KeyEvent.VK_W) {
                gameScreen.getGui().setCommandNumber(gameScreen.getGui().getCommandNumber() - 1);
                if (gameScreen.getGui().getCommandNumber() < 0) {
                    gameScreen.getGui().setCommandNumber(2);
                }
            }
            if (keyCode == KeyEvent.VK_S) {
                gameScreen.getGui().setCommandNumber(gameScreen.getGui().getCommandNumber() + 1);
                if (gameScreen.getGui().getCommandNumber() > 2) {
                    gameScreen.getGui().setCommandNumber(0);
                }
            }
            if (keyCode == KeyEvent.VK_F || keyCode == KeyEvent.VK_ENTER) {
                if (gameScreen.getGui().getCommandNumber() == 0) {
                    gameScreen.getGui().setTitleScreenState(1);
                }
                if (gameScreen.getGui().getCommandNumber() == 1) {
                    //TODO
                }
                if (gameScreen.getGui().getCommandNumber() == 2) {
                    System.exit(0);
                }
            }
        } else if(gameScreen.getGui().getTitleScreenState() == 1) {
            if (keyCode == KeyEvent.VK_W) {
                gameScreen.getGui().setCommandNumber(gameScreen.getGui().getCommandNumber() - 1);
                if (gameScreen.getGui().getCommandNumber() < 0) {
                    gameScreen.getGui().setCommandNumber(3);
                }
            }
            if (keyCode == KeyEvent.VK_S) {
                gameScreen.getGui().setCommandNumber(gameScreen.getGui().getCommandNumber() + 1);
                if (gameScreen.getGui().getCommandNumber() > 3) {
                    gameScreen.getGui().setCommandNumber(0);
                }
            }
            if (keyCode == KeyEvent.VK_F || keyCode == KeyEvent.VK_ENTER) {
                if (gameScreen.getGui().getCommandNumber() == 0) {
                    //TODO: Add warrior
                    gameScreen.setGameState(GameState.PLAYSTATE);
                }
                if (gameScreen.getGui().getCommandNumber() == 1) {
                    //TODO: Add mage
                    gameScreen.setGameState(GameState.PLAYSTATE);
                }
                if (gameScreen.getGui().getCommandNumber() == 2) {
                    //Todo: Add rogue
                    gameScreen.setGameState(GameState.PLAYSTATE);
                }
                if (gameScreen.getGui().getCommandNumber() == 3) {
                    gameScreen.getGui().setCommandNumber(0);
                    gameScreen.getGui().setTitleScreenState(0);
                }
            }
        }
    }

    private void playState(int keyCode) {
        if (keyCode == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (keyCode == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (keyCode == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (keyCode == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            gameScreen.setGameState(GameState.PAUSESTATE);
        }
        if (keyCode == KeyEvent.VK_TAB) {
            gameScreen.setGameState(GameState.CHARACTERSTATE);
        }
        if (keyCode == KeyEvent.VK_F) {
            interactPressed = true;
        }
        if(keyCode == KeyEvent.VK_1) {
            gameScreen.getGui().setHotbarCol(0);
        }
        if(keyCode == KeyEvent.VK_2) {
            gameScreen.getGui().setHotbarCol(1);
        }
        if(keyCode == KeyEvent.VK_3) {
            gameScreen.getGui().setHotbarCol(2);
        }
        if(keyCode == KeyEvent.VK_4) {
            gameScreen.getGui().setHotbarCol(3);
        }
        if(keyCode == KeyEvent.VK_5) {
            gameScreen.getGui().setHotbarCol(4);
        }
        if(keyCode == KeyEvent.VK_6) {
            gameScreen.getGui().setHotbarCol(5);
        }
        if(keyCode == KeyEvent.VK_7) {
            gameScreen.getGui().setHotbarCol(6);
        }
        if(keyCode == KeyEvent.VK_8) {
            gameScreen.getGui().setHotbarCol(7);
        }
        if(keyCode == KeyEvent.VK_9) {
            gameScreen.getGui().setHotbarCol(8);
        }

        //Debug
        if(keyCode == KeyEvent.VK_P) {
            debugPressed = !debugPressed;
        }
        if(keyCode == KeyEvent.VK_O) {
            gameScreen.getTileManager().loadMap("levels/test.txt");
            gameScreen.getAssetLoader().setMonster();
        }
    }

    private void pauseState(int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE) {
            gameScreen.setGameState(GameState.PLAYSTATE);
        }
    }

    private void dialogueState(int keyCode) {
        if(keyCode == KeyEvent.VK_F) {
            gameScreen.setGameState(GameState.PLAYSTATE);
        }
    }

    private void characterState(int keyCode) {
        //On Inventory Screen
        if(gameScreen.getGui().getCharacterMenuState() == 0) {
            if (gameScreen.getGui().getItemMenuState() == 0) {
                if (keyCode == KeyEvent.VK_TAB || keyCode == KeyEvent.VK_ESCAPE) {
                    gameScreen.setGameState(GameState.PLAYSTATE);
                }
                if (keyCode == KeyEvent.VK_Q) {
                    gameScreen.getGui().setTabbing(0);
                    gameScreen.getGui().setTabbed(true);
                    gameScreen.getGui().setCharacterMenuState(1);
                }
                if (keyCode == KeyEvent.VK_E) {
                    gameScreen.getGui().setTabbing(1);
                    gameScreen.getGui().setTabbed(true);
                    gameScreen.getGui().setCharacterMenuState(2);
                }
                if (keyCode == KeyEvent.VK_W) {
                    if (gameScreen.getGui().getSlotRow() != 0) {
                        gameScreen.getGui().setSlotRow(gameScreen.getGui().getSlotRow() - 1);
                    }

                }
                if (keyCode == KeyEvent.VK_A) {
                    if (gameScreen.getGui().getSlotCol() != 0) {
                        gameScreen.getGui().setSlotCol(gameScreen.getGui().getSlotCol() - 1);
                    }
                }
                if (keyCode == KeyEvent.VK_S) {
                    if (gameScreen.getGui().getSlotRow() < gameScreen.getPlayer().getInventory().getUnlockedRows()) {
                        gameScreen.getGui().setSlotRow(gameScreen.getGui().getSlotRow() + 1);
                    }
                }
                if (keyCode == KeyEvent.VK_D) {
                    if (gameScreen.getGui().getSlotCol() < 7) {
                        gameScreen.getGui().setSlotCol(gameScreen.getGui().getSlotCol() + 1);
                    }
                }
                if (keyCode == KeyEvent.VK_F) {
                    if (gameScreen.getGui().getItemIndexOnSlot() < gameScreen.getPlayer().getInventory().getInventoryArray().size()) {
                        gameScreen.getGui().setItemMenuState(1);
                    }
                }
                if(keyCode == KeyEvent.VK_1) {
                    gameScreen.getGui().setHotbarCol(0);
                }
                if(keyCode == KeyEvent.VK_2) {
                    gameScreen.getGui().setHotbarCol(1);
                }
                if(keyCode == KeyEvent.VK_3) {
                    gameScreen.getGui().setHotbarCol(2);
                }
                if(keyCode == KeyEvent.VK_4) {
                    gameScreen.getGui().setHotbarCol(3);
                }
                if(keyCode == KeyEvent.VK_5) {
                    gameScreen.getGui().setHotbarCol(4);
                }
                if(keyCode == KeyEvent.VK_6) {
                    gameScreen.getGui().setHotbarCol(5);
                }
                if(keyCode == KeyEvent.VK_7) {
                    gameScreen.getGui().setHotbarCol(6);
                }
                if(keyCode == KeyEvent.VK_8) {
                    gameScreen.getGui().setHotbarCol(7);
                }
                if(keyCode == KeyEvent.VK_9) {
                    gameScreen.getGui().setHotbarCol(8);
                }
            } else if (gameScreen.getGui().getItemMenuState() == 1) {
                if (keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_TAB) {
                    gameScreen.getGui().setItemMenuState(0);
                }
                if (keyCode == KeyEvent.VK_W) {
                    gameScreen.getGui().setCommandNumber(gameScreen.getGui().getCommandNumber() - 1);
                    if (gameScreen.getGui().getCommandNumber() < 0) {
                        gameScreen.getGui().setCommandNumber(3);
                    }
                }
                if (keyCode == KeyEvent.VK_S) {
                    gameScreen.getGui().setCommandNumber(gameScreen.getGui().getCommandNumber() + 1);
                    if (gameScreen.getGui().getCommandNumber() > 3) {
                        gameScreen.getGui().setCommandNumber(0);
                    }
                }
                if (keyCode == KeyEvent.VK_F) {
                    if (gameScreen.getGui().getCommandNumber() == 0) {
                        if (gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) instanceof WeaponObject) {
                            if(gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) == gameScreen.getPlayer().getInventory().getHotbar()[0] ||
                                    gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) == gameScreen.getPlayer().getInventory().getHotbar()[1] ||
                                    gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) == gameScreen.getPlayer().getInventory().getHotbar()[2] ||
                                    gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) == gameScreen.getPlayer().getInventory().getHotbar()[3] ||
                                    gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) == gameScreen.getPlayer().getInventory().getHotbar()[4] ||
                                    gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) == gameScreen.getPlayer().getInventory().getHotbar()[5] ||
                                    gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) == gameScreen.getPlayer().getInventory().getHotbar()[6] ||
                                    gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) == gameScreen.getPlayer().getInventory().getHotbar()[7] ||
                                    gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) == gameScreen.getPlayer().getInventory().getHotbar()[8]) {
                                gameScreen.getPlayer().getInventory().equipItem(-1);
                                gameScreen.getGui().setItemMenuState(0);
                            } else {
                                gameScreen.getGui().setItemMenuState(2);
                            }
                        } else if (gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) instanceof WearableObject) {
                            gameScreen.getPlayer().getInventory().equipItem(-1);
                            gameScreen.getGui().setItemMenuState(0);
                        } else if (gameScreen.getPlayer().getInventory().getInventoryArray().get(gameScreen.getGui().getItemIndexOnSlot()) instanceof ConsumableObject) {
                            gameScreen.getPlayer().getInventory().equipItem(-1);
                            gameScreen.getGui().setItemMenuState(0);
                        }
                    }
                    if (gameScreen.getGui().getCommandNumber() == 1) {
                        //TODO: Add drop

                    }
                    if (gameScreen.getGui().getCommandNumber() == 2) {
                        //Todo: Add sort
                    }
                    if (gameScreen.getGui().getCommandNumber() == 3) {
                        gameScreen.getGui().setCommandNumber(0);
                        gameScreen.getGui().setItemMenuState(0);
                    }
                }
            } else if (gameScreen.getGui().getItemMenuState() == 2) {
                if (keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_TAB) {
                    gameScreen.getGui().setItemMenuState(1);
                }
                if (keyCode == KeyEvent.VK_W) {
                    gameScreen.getGui().setCommandNumber(gameScreen.getGui().getCommandNumber() - 1);
                    if (gameScreen.getGui().getCommandNumber() < 0) {
                        gameScreen.getGui().setCommandNumber(9);
                    }
                }
                if (keyCode == KeyEvent.VK_S) {
                    gameScreen.getGui().setCommandNumber(gameScreen.getGui().getCommandNumber() + 1);
                    if (gameScreen.getGui().getCommandNumber() > 9) {
                        gameScreen.getGui().setCommandNumber(0);
                    }
                }
                if (keyCode == KeyEvent.VK_F) {
                    if (gameScreen.getGui().getCommandNumber() == 0) {
                        gameScreen.getGui().setCommandNumber(0);
                        gameScreen.getGui().setItemMenuState(0);
                        gameScreen.getPlayer().getInventory().equipItem(0);
                    }
                    if (gameScreen.getGui().getCommandNumber() == 1) {
                        gameScreen.getGui().setCommandNumber(0);
                        gameScreen.getGui().setItemMenuState(0);
                        gameScreen.getPlayer().getInventory().equipItem(1);
                    }
                    if (gameScreen.getGui().getCommandNumber() == 2) {
                        gameScreen.getGui().setCommandNumber(0);
                        gameScreen.getGui().setItemMenuState(0);
                        gameScreen.getPlayer().getInventory().equipItem(2);
                    }
                    if (gameScreen.getGui().getCommandNumber() == 3) {
                        gameScreen.getGui().setCommandNumber(0);
                        gameScreen.getGui().setItemMenuState(0);
                        gameScreen.getPlayer().getInventory().equipItem(3);
                    }
                    if (gameScreen.getGui().getCommandNumber() == 4) {
                        gameScreen.getGui().setCommandNumber(0);
                        gameScreen.getGui().setItemMenuState(0);
                        gameScreen.getPlayer().getInventory().equipItem(4);
                    }
                    if (gameScreen.getGui().getCommandNumber() == 5) {
                        gameScreen.getGui().setCommandNumber(0);
                        gameScreen.getGui().setItemMenuState(0);
                        gameScreen.getPlayer().getInventory().equipItem(5);
                    }
                    if (gameScreen.getGui().getCommandNumber() == 6) {
                        gameScreen.getGui().setCommandNumber(0);
                        gameScreen.getGui().setItemMenuState(0);
                        gameScreen.getPlayer().getInventory().equipItem(6);
                    }
                    if (gameScreen.getGui().getCommandNumber() == 7) {
                        gameScreen.getGui().setCommandNumber(0);
                        gameScreen.getGui().setItemMenuState(0);
                        gameScreen.getPlayer().getInventory().equipItem(7);
                    }
                    if (gameScreen.getGui().getCommandNumber() == 8) {
                        gameScreen.getGui().setCommandNumber(0);
                        gameScreen.getGui().setItemMenuState(0);
                        gameScreen.getPlayer().getInventory().equipItem(8);
                    }
                    if (gameScreen.getGui().getCommandNumber() == 9) {
                        gameScreen.getGui().setCommandNumber(0);
                        gameScreen.getGui().setItemMenuState(1);
                    }
                }
            }
        //On Status Screen
        } else if(gameScreen.getGui().getCharacterMenuState() == 1) {
            if (keyCode == KeyEvent.VK_TAB || keyCode == KeyEvent.VK_ESCAPE) {
                gameScreen.setGameState(GameState.PLAYSTATE);
                gameScreen.getGui().setCharacterMenuState(0);
            }
            if (keyCode == KeyEvent.VK_Q) {
                gameScreen.getGui().setTabbing(0);
                gameScreen.getGui().setTabbed(true);
                gameScreen.getGui().setCharacterMenuState(2);
            }
            if (keyCode == KeyEvent.VK_E) {
                gameScreen.getGui().setTabbing(1);
                gameScreen.getGui().setTabbed(true);
                gameScreen.getGui().setCharacterMenuState(0);
            }
            if(keyCode == KeyEvent.VK_1) {
                gameScreen.getGui().setHotbarCol(0);
            }
            if(keyCode == KeyEvent.VK_2) {
                gameScreen.getGui().setHotbarCol(1);
            }
            if(keyCode == KeyEvent.VK_3) {
                gameScreen.getGui().setHotbarCol(2);
            }
            if(keyCode == KeyEvent.VK_4) {
                gameScreen.getGui().setHotbarCol(3);
            }
            if(keyCode == KeyEvent.VK_5) {
                gameScreen.getGui().setHotbarCol(4);
            }
            if(keyCode == KeyEvent.VK_6) {
                gameScreen.getGui().setHotbarCol(5);
            }
            if(keyCode == KeyEvent.VK_7) {
                gameScreen.getGui().setHotbarCol(6);
            }
            if(keyCode == KeyEvent.VK_8) {
                gameScreen.getGui().setHotbarCol(7);
            }
            if(keyCode == KeyEvent.VK_9) {
                gameScreen.getGui().setHotbarCol(8);
            }
        //On Crafting Screen
        } else if(gameScreen.getGui().getCharacterMenuState() == 2) {
            if (keyCode == KeyEvent.VK_TAB || keyCode == KeyEvent.VK_ESCAPE) {
                gameScreen.setGameState(GameState.PLAYSTATE);
                gameScreen.getGui().setCharacterMenuState(0);
            }
            if (keyCode == KeyEvent.VK_Q) {
                gameScreen.getGui().setTabbing(0);
                gameScreen.getGui().setTabbed(true);
                gameScreen.getGui().setCharacterMenuState(0);
            }
            if (keyCode == KeyEvent.VK_E) {
                gameScreen.getGui().setTabbing(1);
                gameScreen.getGui().setTabbed(true);
                gameScreen.getGui().setCharacterMenuState(1);
            }
        }
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isInteractPressed() {
        return interactPressed;
    }

    public void setInteractPressed(boolean interactPressed) {
        this.interactPressed = interactPressed;
    }
}
