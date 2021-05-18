package view;


import javax.swing.*;
import java.awt.*;
import java.util.Date;


class GraphDrawer extends JPanel {
    private final int[] yCoords;
    private final int startX = 50;
    private final int startY = 20;
    private int endX = this.getWidth() - 50;
    private int endY = this.getHeight() - 160;
    private int prevX = startX;
    private int prevY = endY;
    private int unitX;
    private int unitY;
    String name;
    Color lineColor;
    Date time = new Date();


    public GraphDrawer(int[] yCoords, String name, Color lineColor) {
        this.yCoords = yCoords;
        this.name = name;
        this.lineColor = lineColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int topElementY = GetMax(yCoords);
        int hour = time.getHours();
        int numHoritzontalLines = numSections(topElementY);

        endX = this.getHeight() - 50;
        endY = this.getWidth() - 160;
        unitX = (endX - startX) / 23;
        unitY = (endY - startY) / numHoritzontalLines;


        g2d.setFont(new Font("TimesRoman", Font.BOLD, 14));

        //Dibuix de les lineas verticals
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

        //dibuix de les lineas horitzontals
        int count = 0;
        for (int i = endY; i > startY; i -= unitY) {
            g2d.drawLine(startX, i, endX, i);
            g2d.setColor(Color.BLACK);
            g2d.drawString(Integer.toString(topElementY*count/numHoritzontalLines), startX - 45, i + 5);
            g2d.setColor(Color.DARK_GRAY);
            count++;
        }



        boolean firstElement = true;
        g2d.setColor(lineColor);
        g2d.setStroke(new BasicStroke(4));
        prevY = endY - (yCoords[0] * unitY);
        for (int y : yCoords) {
            if (firstElement) {
                firstElement = false;
            } else {
                g2d.drawLine(prevX, prevY, prevX += unitX, prevY = endY - (y * unitY * numHoritzontalLines / topElementY));
            }
        }

        //Drowing the axis of our graph
         g2d.setColor(Color.BLACK);
        g2d.drawString(name, endX / 2 + 50, endY + 105);
        g2d.setColor(lineColor);
        g2d.drawLine(endX / 2 - 50, endY + 100, endX / 2 + 40, endY + 100);


        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(startX, startY, startX, endY);
        g2d.drawLine(startX, endY, endX, endY);
    }

    private int numSections(int x) {
        while (x > 10) {
            x /= 10;
        }
        return x;
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
}