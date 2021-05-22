package view;


import javax.swing.*;
import java.awt.*;

class GraphDrawer extends JPanel {
    private static final int GRAPH_HIGHT = 450;
    private static final int GRAPH_WIDTH = 700;
    private static final int INITIAL_POINT_X = 50;
    private static final int INITIAL_POINT_Y = 20;

    private final int[] yCoords;
    private final String name;
    private final Color lineColor;


    public GraphDrawer(int[] yCoords, String name, Color lineColor) {
        this.yCoords = yCoords;
        this.name = name;
        this.lineColor = lineColor;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int topElementY = this.maxValue(yCoords);
        int numHoritzontalLines = this.numSections(topElementY);
        int hour = yCoords[yCoords.length-1];
        int endX = GraphDrawer.GRAPH_WIDTH;
        int endY = GraphDrawer.GRAPH_HIGHT;
        int prevX = GraphDrawer.INITIAL_POINT_X;
        int prevY;
        int unitX = (endX - GraphDrawer.INITIAL_POINT_X) / 23;
        int unitY = (endY - GraphDrawer.INITIAL_POINT_Y) / numHoritzontalLines;

        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 14));

        //Dibuix de les lineas verticals
        for (int i = GraphDrawer.INITIAL_POINT_X; i <= endX; i += unitX) {
            g2d.drawLine(i, GraphDrawer.INITIAL_POINT_Y, i, endY);
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
        for (int i = endY; i >= GraphDrawer.INITIAL_POINT_Y; i -= unitY) {
            g2d.drawLine(GraphDrawer.INITIAL_POINT_X, i, endX, i);
            g2d.setColor(Color.BLACK);
            g2d.drawString(Integer.toString(topElementY*count/numHoritzontalLines), GraphDrawer.INITIAL_POINT_X - 45, i + 5);
            g2d.setColor(Color.DARK_GRAY);
            count++;
        }

        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(GraphDrawer.INITIAL_POINT_X, GraphDrawer.INITIAL_POINT_Y, GraphDrawer.INITIAL_POINT_X, endY);
        g2d.drawLine(GraphDrawer.INITIAL_POINT_X, endY, endX, endY);


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
        while (x > 10) x /= 10;
        return x;
    }

    private int maxValue(int[] inputArray) {
        int maxValue = inputArray[0];
        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[i] > maxValue) {
                maxValue = inputArray[i];
            }
        }
        return maxValue;
    }
}