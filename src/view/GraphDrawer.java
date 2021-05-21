package view;


import javax.swing.*;
import java.awt.*;



class GraphDrawer extends JPanel {

    private static final int GRAPH_HIGHT = 450;
    private static final int GRAPH_WIDTH = 700;
    private static final int INITIAL_POINT_X = 50;
    private static final int INITIAL_POINT_Y = 20;

    protected final int[] yCoords;
    protected final int startX = INITIAL_POINT_X;
    protected final int startY = INITIAL_POINT_Y;
    protected int endX;
    protected int endY;
    protected int prevX;
    protected int prevY;
    protected int unitX;
    protected int unitY;
    protected String name;
    protected Color lineColor;


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
        int numHoritzontalLines = numSections(topElementY);
        int hour = yCoords[yCoords.length-1];
        endX = GRAPH_WIDTH;
        endY = GRAPH_HIGHT;
        prevX = startX;
        prevY = endY;
        unitX = (endX - startX) / 23;
        unitY = (endY - startY) / numHoritzontalLines;


        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 14));

        //Dibuix de les lineas verticals
        for (int i = startX; i <= endX; i += unitX) {

            g2d.drawLine(i, startY, i, endY);
            g2d.setColor(Color.BLACK);
            g2d.rotate(-0.78, i, endY + 30);
            g2d.drawString(hour + ":00", i - 32, endY + 30);
            g2d.setColor(Color.GRAY);
            g2d.rotate(0.78, i, endY + 30);
            hour++;
            if (hour > 23) {
                hour = 0;
            }
        }

        //dibuix de les lineas horitzontals
        int count = 0;
        for (int i = endY; i >= startY; i -= unitY) {
            g2d.drawLine(startX, i, endX, i);
            g2d.setColor(Color.BLACK);
            g2d.drawString(Integer.toString(topElementY*count/numHoritzontalLines), startX - 45, i + 5);
            g2d.setColor(Color.DARK_GRAY);
            count++;
        }

        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(startX, startY, startX, endY);
        g2d.drawLine(startX, endY, endX, endY);


        g2d.setColor(lineColor);
        g2d.setStroke(new BasicStroke(4));
        prevY = endY - (yCoords[0] * unitY * numHoritzontalLines / topElementY);
        for (int i = 1; i < yCoords.length - 1; i++) {
            g2d.drawLine(prevX, prevY, prevX += unitX, prevY = endY - (yCoords[i] * unitY * numHoritzontalLines / topElementY));
        }


         g2d.setColor(Color.BLACK);
        g2d.drawString(name, 205 , endY + 105);
        g2d.setColor(lineColor);
        g2d.drawLine( 150, endY + 100, 200, endY + 100);



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