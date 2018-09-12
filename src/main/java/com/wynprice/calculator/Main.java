package com.wynprice.calculator;

import com.wynprice.calculator.types.Expression;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Map;

public class Main {


    public static void main(String[] args) {
        // Create Chart
        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", " ", new double[]{0}, new double[]{0});

        // Show it
        SwingWrapper<XYChart> wrapper = new SwingWrapper<>(chart);
        JFrame frame = wrapper.displayChart();

        XChartPanel<XYChart> panel = wrapper.getXChartPanel();

        JTextField field = new JTextField();
        panel.add(field);
        JButton button = new JButton("Run");
        panel.add(button);


        JTextField minX = new JTextField();
        panel.add(minX);
        JLabel minXLable = new JLabel("Min X: ");
        panel.add(minXLable);

        JTextField maxX = new JTextField();
        panel.add(maxX);
        JLabel maxXLable = new JLabel("Max X: ");
        panel.add(maxXLable);

        JTextField step = new JTextField();
        panel.add(step);
        JLabel stepLabel = new JLabel("Step: ");
        panel.add(stepLabel);

        frame.pack();

        Runnable resize = () -> {
            field.setBounds(frame.getWidth() - 166, frame.getHeight() / 2, 146, 25);
            button.setBounds(frame.getWidth() - 166, frame.getHeight() / 2 + 35, 146, 25);


            minXLable.setBounds(frame.getWidth() - 130, 10, 70, 25);
            minX.setBounds(frame.getWidth() - 90, 10, 70, 25);

            maxXLable.setBounds(frame.getWidth() - 130, 40, 70, 25);
            maxX.setBounds(frame.getWidth() - 90, 40, 70, 25);

            stepLabel.setBounds(frame.getWidth() - 130, 70, 70, 25);
            step.setBounds(frame.getWidth() - 90, 70, 70, 25);
        };
        resize.run();


        button.addActionListener(e -> {
            runExpressionAndGraph(chart, field.getText(), Double.parseDouble(minX.getText()), Double.parseDouble(maxX.getText()), Double.parseDouble(step.getText()));
            panel.repaint();
        });

        frame.addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                resize.run();
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
        });
    }

    public static void runExpressionAndGraph(XYChart chart, String input, double minX, double maxX, double step) {
        InputReader reader = new InputReader(input);

        int size = (int) Math.floor((maxX - minX) / step);

        double[] xData = new double[size];
        double[] yData = new double[size];

        try {
            Expression exp = new Expression(reader);
            int pos = 0;
            for(double x = minX; x < maxX; x+= step) {
                if(pos >= size) {
                    break;
                }
                HashMap<String, Double> consMap = new HashMap<>();
                consMap.put("x", x);
                try {
                    xData[pos] = x;
                    yData[pos] = exp.getValue(consMap);
                } catch (MathExecuteException e) {
                    e.printStackTrace();
                }
                pos++;
            }


        } catch (MathParseException e) {
            String out = reader.getFrom(e.getStartPos());
            System.out.println("UNABLE TO COMPILE CALCULATION");
            System.out.println("Reason: " + e.getMessage());
            System.out.println(out + "<-----HERE");
        }

        chart.getSeriesMap().clear();
        chart.addSeries("y = " + "y = " + input.replace("$x", "x"), xData, yData);
    }
}
