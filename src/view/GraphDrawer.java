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
     * Font used when printing the graph
     */
    private static final Font font = new Font("TimesRoman", Font.PLAIN, 14);

    /**
     * List fo the values to create the graph from
     */
    private final int[] yCoords;

    /**
     * Starting x position of the graph
     */
    private final String name;

    /**
     * Starting y position of the graph
     */
    private final Color lineColor;


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
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int topElementY = this.maxValue(yCoords);
        if (topElementY == 0) topElementY = 1;
        int numHoritzontalLines = this.numSections(topElementY);
        if (numHoritzontalLines == 0) numHoritzontalLines = 1;

        int hour = yCoords[yCoords.length-1];
        int endX = GraphDrawer.GRAPH_WIDTH;
        int endY = GraphDrawer.GRAPH_HIGHT;
        int prevX = GraphDrawer.INITIAL_POINT_X;
        int prevY;
        int unitX = (endX - GraphDrawer.INITIAL_POINT_X) / 23;
        int unitY = (endY - GraphDrawer.INITIAL_POINT_Y) / numHoritzontalLines;

        g2d.setFont(GraphDrawer.font);

        //Dibuix de les lineas verticals
        int drawingHour = hour+1;
        for (int i = GraphDrawer.INITIAL_POINT_X; i <= endX; i += unitX) {
            g2d.drawLine(i, GraphDrawer.INITIAL_POINT_Y, i, endY);
            g2d.setColor(Color.BLACK);
            g2d.rotate(-0.78, i, endY + 30);
            g2d.drawString(drawingHour + ":00", i - 32, endY + 30);
            g2d.setColor(Color.GRAY);
            g2d.rotate(0.78, i, endY + 30);
            drawingHour++;
            if (drawingHour > 23) {
                drawingHour = 0;
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

    /**
     * Calculates the number of sections the graph should have
     * @param x Value from which to divide
     * @return Sections the graph will have, between 1 and 10
     */
    private int numSections(int x) {
        while (x > 10) x /= 10;
        return x;
    }

    /**
     * Returns the maximum value from an int array
     * @param inputArray Array of values
     * @return The highest value from the array
     */
    private int maxValue(int[] inputArray) {
        int maxValue = inputArray[0];
        for (int i = 1; i < inputArray.length - 1; i++) {
            if (inputArray[i] > maxValue) {
                maxValue = inputArray[i];
            }
        }
        return maxValue;
    }
}