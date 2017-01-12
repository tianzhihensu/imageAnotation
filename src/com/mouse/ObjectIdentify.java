package com.mouse;

import com.utils.FileOperation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Created by SSS on 2017/1/7.
 */
public class ObjectIdentify extends JFrame {

    private JFrame frame = new JFrame("标记图片");

    private JPanel imagePanel;

    private ImageIcon background;

    // 矩形的左上角和width/height
    private Integer topLeftX = null;
    private Integer topLeftY = null;
    private Integer downRightX = null;
    private Integer downRightY = null;
    private Integer width = null;
    private Integer height = null;

    // 圆的半径
    private Integer ovalWidth = 8;
    private Integer ovalHeight = 8;

    // flag
    private Integer clickCount = 0;

    // image的索引
    private Integer index = 0;

    private String dirStr = null;
    private String []filesArr = null;

    private String resultPath = null;

    // 结果存放
    private List<List<Integer>> imgResult = new ArrayList<>();  // 整个img的结果

    // Constructor
    public ObjectIdentify() {

    }


    public void initPane(){
        // 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
        imagePanel = (JPanel) frame.getContentPane();
        imagePanel.setOpaque(false);
        // 内容窗格默认的布局管理器为BorderLayout
        imagePanel.setLayout(new FlowLayout());
        // 设置画笔

        // add next button and reverse button
        JButton nextBtn = new JButton("Next One");
        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 先把上次的结果保存
                String fileName = filesArr[index];
                FileOperation.writeResult(getResultPath(), imgResult, fileName);
                // 保存当前标注之后的image
                savePic(getDirStr() + "annoted_" + fileName);
                // 保存之后，清空结果
                imgResult.clear();
                index ++;
                if(index == filesArr.length) {
                    System.exit(0);
                }
                setFinalFrame();
            }
        });
        imagePanel.add(nextBtn, new Integer(Integer.MAX_VALUE));
        JButton reverseBtn = new JButton("reverse");
        reverseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("reverse");
                clickCount --;
                repaint();

            }
        });
        imagePanel.add(reverseBtn, new Integer(Integer.MAX_VALUE));

        imagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                clickCount ++;

                int i = e.getButton();
                if (i  == MouseEvent.BUTTON1) {  // 单击的是鼠标左键，也只有左键有效
                    int curX = e.getX();
                    int curY = e.getY();

                    // 点击奇/偶数次，对应不同的操作
                    if(clickCount % 2 == 0) {
                        downRightX = curX;
                        downRightY = curY;
                        // 计算该矩形的width、height
                        width = downRightX - topLeftX;
                        height = downRightY - topLeftY;

                        // 保存当前rectangle的结果
                        List<Integer> rowResult = new ArrayList<Integer>();
                        rowResult.add(topLeftX);
                        rowResult.add(topLeftY);
                        rowResult.add(width);
                        rowResult.add(height);
                        // 有撤销操作的情况下
                        if (imgResult.size() == (clickCount/2)) {
                            imgResult.set((clickCount/2) - 1, rowResult);
                        } else {
                            imgResult.add(rowResult);
                        }

                        // show current click information
                        System.out.println(filesArr[index] + "的第 " + clickCount/2 + " 个object, 相关信息：（x = " + topLeftX + ", y = " + topLeftY + ", width = " + width + ", height = " + height + ")");
                    } else {
                        topLeftX = curX;
                        topLeftY = curY;
                    }
                    Graphics g = imagePanel.getGraphics();
                    g.setColor(Color.RED);
                    paint(g);
                }
            }
        });

        this.setFinalFrame();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 画圆
        g.fillOval(this.topLeftX - this.ovalWidth/2, this.topLeftY - this.ovalHeight/2, this.ovalWidth, this.ovalHeight);

        // 如果是点击了偶数次，那么就应该画一个矩形
        if ((this.clickCount % 2) == 0) {
            g.fillOval(this.downRightX - this.ovalWidth/2, this.downRightY - this.ovalHeight/2, this.ovalWidth, this.ovalHeight);
            g.drawRect(this.topLeftX, this.topLeftY, this.width, this.height);
        }

    }

    /**
     * 设置背景的图片
     * @param imgPath
     * @return
     */
    public JLabel setBgImage(String imgPath) {
        background = new ImageIcon(imgPath);// 背景图片
        JLabel label = new JLabel(background);// 把背景图片显示在一个标签里面
        // 把标签的大小位置设置为图片刚好填充整个面板
        label.setBounds(0, 0, background.getIconWidth(),
                background.getIconHeight());

        return label;
    }

    /**
     * 根据背景，重新设置Frame
     */
    public void setFinalFrame() {
        System.out.println("Current path: " + getDirStr());
        String imgPath = this.getDirStr() + filesArr[this.index];
        JLabel label = this.setBgImage(imgPath);

        frame.getLayeredPane().setLayout(null);
        // 把背景图片添加到分层窗格的最底层作为背景
        frame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE + this.index));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(imgPath);
        frame.setSize(background.getIconWidth(), background.getIconHeight());
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void savePic(String path) {
        BufferedImage myImage = null;
        try {
            myImage = new Robot().createScreenCapture(
                    new Rectangle(frame.getX(), frame.getY(), frame.getWidth(), frame.getHeight()));
            ImageIO.write(myImage, "jpg", new File(path));
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDirStr() {
        return dirStr;
    }

    public void setDirStr(String dirStr) {
        this.dirStr = dirStr;
    }

    public String[] getFilesArr() {
        return filesArr;
    }

    public void setFilesArr(String[] filesArr) {
        this.filesArr = filesArr;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }
}
