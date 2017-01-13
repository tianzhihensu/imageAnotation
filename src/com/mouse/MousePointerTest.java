package com.mouse;

import javax.swing.*;
import java.awt.Point;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.MouseMotionAdapter;

/**
 * Created by SSS on 2017/1/12.
 */
public class MousePointerTest extends JFrame {

    JTextField jTextField1 = new JTextField();
    JTextField jTextField2 = new JTextField();
    JLabel jLabel1 = new JLabel("X:");
    JLabel jLabel2 = new JLabel("Y:");
    public MousePointerTest() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new FlowLayout());
        this.getContentPane().add(jLabel1);
        this.getContentPane().add(jTextField1);
        this.getContentPane().add(jLabel2);
        this.getContentPane().add(jTextField2);
        jTextField1.setPreferredSize(new Dimension(30, 20));
        jTextField2.setPreferredSize(new Dimension(30, 20));
        jLabel1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 14));
        jLabel2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 14));
        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent mEvent) {

            }
        });
    }

    public static void main(String[] Args) {
        MousePointerTest mpt = new MousePointerTest();
        mpt.setSize(500, 400);
        mpt.setVisible(true);
    }
}
