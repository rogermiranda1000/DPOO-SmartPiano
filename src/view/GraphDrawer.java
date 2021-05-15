package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Date;


class GraphDrawer extends JPanel implements ComponentListener {
    private final int[] yCoords;
    private final int startX = 50;
    private int endX = 1000;
    private int endY = 562;
    private int prevX = startX;
    private int prevY = endY;
    private int unitX = (endX - startX) / 23;
    Date time = new Date();


    public GraphDrawer(int[] yCoords, String name, Color lineColor) {
        this.setSize(1000, 1000);
        endX = this.getWidth() - 50;
        endY = this.getHeight() - 160;
        this.yCoords = yCoords;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int topElementY = GetMax(yCoords);
        int hour = time.getHours();
        int startY = 35;
        int unitY = (endY - startY) / topElementY;


        g2d.setFont(new Font("TimesRoman", Font.BOLD, 14));
        for (int i = startX; i <= endX; i += unitX) {
            g2d.drawLine(i, startY, i, endY);
            g2d.setColor(Color.BLACK);
            g2d.rotate(-0.78, i, endY + 30);
            g2d.drawString(Integer.toString(hour) + 'h', i - 7, endY + 30);
            g2d.setColor(Color.GRAY);
            g2d.rotate(0.78, i, endY + 30);
            hour++;
            if (hour > 23) {
                hour = 0;
            }
        }


        for (int i = endY; i > startY; i -= unitY) {
            g2d.drawLine(startX, i, endX, i);
            g2d.setColor(Color.BLACK);
            if ((i - startY) / unitY == topElementY / 2) {
                if (topElementY % 2 == 0) {
                    g2d.drawString(Integer.toString(topElementY / 2), startX - 20, i + 5);
                } else {
                    g2d.drawString(Integer.toString(topElementY / 2 + 1), startX - 20, i + 5);
                }
                g2d.setColor(Color.DARK_GRAY);
            }
        }


        boolean firstElement = true;
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(4));
        prevY = endY - (yCoords[0] * unitY);
        for (int y : yCoords) {
            if (firstElement) {
                firstElement = false;
            } else {
                g2d.drawLine(prevX, prevY, prevX += unitX, prevY = endY - (y * unitY));
            }
        }

        //Drowing the axis of our graph
        g2d.setColor(Color.BLACK);
        g2d.drawString(Integer.toString(topElementY), startX - 20, startY + 5);
        g2d.drawString("0", startX - 20, endY + 5);


        g2d.drawString("Graph TITLE", endX / 2 + 50, endY + 105);
        g2d.setColor(Color.RED);
        g2d.drawLine(endX / 2 - 50, endY + 100, endX / 2 + 40, endY + 100);


        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(startX, startY, startX, endY);
        g2d.drawLine(startX, endY, endX, endY);
        this.componentResized(new ComponentEvent(this, ComponentEvent.COMPONENT_RESIZED));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(endX + 100, endY + 100);
    }

    private int GetMax(int[] inputArray) {
        int maxValue = inputArray[0];
        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[i] > maxValue) {
                maxValue = inputArray[i];
            }
        }
        return maxValue;
    }

    public void componentResized(ComponentEvent e) {
        endX = e.getComponent().getWidth() - 50;
        endY = e.getComponent().getHeight() - 160;
        unitX = (endX - startX) / 23;
        prevX = startX;
        prevY = endY;

    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

}