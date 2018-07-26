package com.placeholder;

import com.sun.awt.AWTUtilities;
import javafx.scene.input.MouseButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TransparentDemo {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("transparent frame");
        jFrame.setBackground(Color.BLACK);
        jFrame.setForeground(Color.BLACK);
        jFrame.setUndecorated(true);
        jFrame.setOpacity(0.5f);
        jFrame.setSize(200, 200);
        jFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Point point = jFrame.getLocation();
                float alignmentX = jFrame.getAlignmentX();
                float alignmentY = jFrame.getAlignmentY();
                int x1 = jFrame.getX();
                int y1 = jFrame.getY();

                if (e.isControlDown()) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_RIGHT:
                            int x = point.x;
                            int y = point.y;
                            System.out.println(x1 + ", " + y1 + ", " + alignmentX + ", " + alignmentY);
                            System.out.println("old: " + x + ", " + y);
                            int newX = (int) (x + 1);
                            int newY = (int) y;
                            System.out.println("new: " + newX + ", " + newY);
                            jFrame.setLocation(newX, newY);
                            break;
                    }
                }
            }
        });
        jFrame.setVisible(true);

    }
}
