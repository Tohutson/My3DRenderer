package com.treyhutson;

import com.treyhutson.Math.Barycentric;
import com.treyhutson.Math.Matrix3;
import com.treyhutson.Models.Triangle;
import com.treyhutson.Models.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DemoViewer {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Demo Viewer");
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        // slider for horizontal rotation
        JSlider headingSlider = new JSlider(0, 360, 180);
        pane.add(headingSlider, BorderLayout.SOUTH);

        // slider for vertical rotation
        JSlider pitchSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);
        pane.add(pitchSlider, BorderLayout.EAST);

        // panel to display render results
        JPanel renderPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // rendering will be here
                ArrayList<Triangle> tris = new ArrayList<>();
                tris.add(new Triangle(new Vertex(100, 100, 100), new Vertex(-100, -100, 100), new Vertex(-100, 100, -100), Color.WHITE));
                tris.add(new Triangle(new Vertex(100, 100, 100), new Vertex(-100, -100, 100), new Vertex(100, -100, -100), Color.RED));
                tris.add(new Triangle(new Vertex(-100, 100, -100), new Vertex(100, -100, -100), new Vertex(100, 100, 100), Color.GREEN));
                tris.add(new Triangle(new Vertex(-100, 100, -100), new Vertex(100, -100, -100), new Vertex(-100, -100, 100), Color.BLUE));

                double heading = Math.toRadians(headingSlider.getValue());
                Matrix3 headingTransform = new Matrix3(new double[]{Math.cos(heading), 0, -Math.sin(heading), 0, 1, 0, Math.sin(heading), 0, Math.cos(heading)});

                double pitch = Math.toRadians(pitchSlider.getValue());
                Matrix3 pitchTransform = new Matrix3(new double[]{1, 0, 0, 0, Math.cos(pitch), Math.sin(pitch), 0, -Math.sin(pitch), Math.cos(pitch)});

                Matrix3 transform = headingTransform.multiply(pitchTransform);

                BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

                for (Triangle t : tris) {
                    Vertex v1 = transform.transform(t.getVertex1());
                    Vertex v2 = transform.transform(t.getVertex2());
                    Vertex v3 = transform.transform(t.getVertex3());

                    // since we are not using Graphics2D anymore,
                    // we have to do translation manually
                    v1.setX(v1.getX() + getWidth() / 2);
                    v1.setY(v1.getY() + getWidth() / 2);
                    v2.setX(v2.getX() + getWidth() / 2);
                    v2.setY(v2.getY() + getWidth() / 2);
                    v3.setX(v3.getX() + getWidth() / 2);
                    v3.setY(v3.getY() + getWidth() / 2);
                    
                    // compute rectangular bounds for triangle
                    int minX = (int) Math.max(0, Math.ceil(Math.min(v1.getX(), Math.min(v2.getX(), v3.getX()))));
                    int maxX = (int) Math.min(img.getWidth() - 1, Math.floor(Math.max(v1.getX(), Math.max(v2.getX(), v3.getX()))));
                    int minY = (int) Math.max(0, Math.ceil(Math.min(v1.getY(), Math.min(v2.getY(), v3.getY()))));
                    int maxY = (int) Math.min(img.getHeight() - 1, Math.floor(Math.max(v1.getY(), Math.max(v2.getY(), v3.getY()))));

                    Triangle screenTri = new Triangle(v1, v2, v3, t.getColor());
                    Barycentric b = new Barycentric(screenTri);

                    for (int y = minY; y <= maxY; y++) {
                        for (int x = minX; x <= maxX; x++) {
                            if (b.isInside(x, y)) {
                                img.setRGB(x, y, t.getColor().getRGB());
                            }
                        }
                    }
                }
                g2.drawImage(img, 0, 0, null);
            }

        };
        pane.add(renderPanel, BorderLayout.CENTER);

        frame.setSize(400, 400);
        frame.setVisible(true);
        headingSlider.addChangeListener(e -> renderPanel.repaint());
        pitchSlider.addChangeListener(e -> renderPanel.repaint());
    }
}