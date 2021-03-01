package net.snake;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import net.snake.game.Menu;

import javax.swing.*;

public class Main {


    /**
     * Main method, launches the menu window
     * @param args Launch arguments
     */
    public static void main(String[] args) {
        Menu.main(args);
    }


    /**
     * Checks the specified location in a grid pane and returns the corresponding node
     * @param root The GridPane we want to check
     * @param x The column index
     * @param y The row index
     * @return Returns the Region node or null
     */
    public static Region getGrid(GridPane root, int x, int y){
        for(Node reg : root.getChildren()){
            if(GridPane.getColumnIndex(reg) == x){
                if(GridPane.getRowIndex(reg) == y){
                    return (Region)reg;
                }
            }
        }

        return null;
    }

    /**
     * Creates a message box and displays it.
     * Freezes the thread its run on until closed.
     * @param infoMessage Message to display
     * @param titleBar Title on the window
     */
    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.WARNING_MESSAGE);
    }
}
