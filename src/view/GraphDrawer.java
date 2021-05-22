package view;


import javax.swing.*;
import java.awt.*;


/**
 * Class used to draw the graphs
 */
class GraphDrawer extends JPanel {

    /**
     * The height of the graph in pixels
     */
    private static final int GRAPH_HIGHT = 450;

    /**
     * The width of the graph in pixels
     */
    private static final int GRAPH_WIDTH = 700;

    /**
     * Value used for creating a margin
     */
    private static final int INITIAL_POINT_X = 50;

    /**
     * Value used for creating a margin
     */
    private static final int INITIAL_POINT_Y = 20;

    /**
     * List fo the values to create the graph from
     */
    private final int[] yCoords;

    /**
     * Starting x position of the graph
     */
    private final int startX = INITIAL_POINT_X;

    /**
     * Starting y position of the graph
     */
    private final int startY = INITIAL_POINT_Y;

    /**
     * Ending x position of the graph
     */
    private int endX;

    /**
     * Ending y position of the graph
     */
    private int endY;

    /**
     * X position of the previous line ending point
     */
    private int prevX;

    /**
     * Y position of the previous line ending point
     */
    private int prevY;

    /**
     * Increment of the x axis for each graph unit
     */
    private int unitX;

    /**
     * Increment of the y value in relationship to the previous one
     */
    private int unitY;

    /**
     * Name of the graph to be displayed
     */
    private String name;

    /**
     * Color of the graph's main line
     */
    private Color lineColor;

    /**
     * Initializes the variables with the values sent
     * @param yCoords Array with the values to display and the current hour at the end
     * @param name Name of the graph to be displayed
     * @param lineColor Color of the graph's main line
     */
    public GraphDrawer(int[] yCoords, String name, Color lineColor) {
        this.yCoords = yCoords;
        this.name = name;
        this.lineColor = lineColor;
    }

    /**
     * Called when the component is drawn on screen, here the graph is drawn
     * @param g Graphics object to be drawn
     */
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

    /**
     * Calculates the number of sections the graph should have
     * @param x Value from which to divide
     * @return Sections the graph will have, between 1 and 10
     */
    private int numSections(int x) {
        while (x > 10) {
            x /= 10;
        }
        return x;
    }

    /**
     * Returns the maximum value from an int array
     * @param inputArray Array of values
     * @return The highest value from the array
     */
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