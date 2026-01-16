package com.treyhutson.Math;

import com.treyhutson.Models.Triangle;
import com.treyhutson.Models.Vertex;

import java.awt.*;

public class Shader {

    public static Color getShade(Color color, Triangle triangle) {
        double shade = Normal.getAngle(triangle);
        double redLinear = Math.pow(color.getRed(), 2.4) * shade;
        double greenLinear = Math.pow(color.getGreen(), 2.4) * shade;
        double blueLinear = Math.pow(color.getBlue(), 2.4) * shade;

        int red = (int) Math.pow(redLinear, 1/2.4);
        int green = (int) Math.pow(greenLinear, 1/2.4);
        int blue = (int) Math.pow(blueLinear, 1/2.4);

        return new Color(red, green, blue);
    }

    private class Normal {

        private Normal() {
        }

        private static double getAngle(Triangle triangle) {
            Vertex v1 = triangle.getVertex1();
            Vertex v2 = triangle.getVertex2();
            Vertex v3 = triangle.getVertex3();

            Vertex ab = new Vertex(v2.getX() - v1.getX(), v2.getY() - v1.getY(), v2.getZ() - v1.getZ());
            Vertex ac = new Vertex(v3.getX() - v1.getX(), v3.getY() - v1.getY(), v3.getZ() - v1.getZ());

            Vertex norm = new Vertex(
                    ab.getY() * ac.getZ() - ab.getZ() * ac.getY(),
                    ab.getZ() * ac.getX() - ab.getX() * ac.getZ(),
                    ab.getX() * ac.getY() - ab.getY() * ac.getX()
            );

            double normalLength = Math.sqrt(norm.getX() * norm.getX() + norm.getY() * norm.getY() + norm.getZ() * norm.getZ());
            norm.setX(norm.getX() / normalLength);
            norm.setY(norm.getY() / normalLength);
            norm.setZ(norm.getZ() / normalLength);

            return Math.abs(norm.getZ());
        }
    }
}
