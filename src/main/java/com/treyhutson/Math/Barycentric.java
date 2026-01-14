package com.treyhutson.Math;

import com.treyhutson.Models.Triangle;
import com.treyhutson.Models.Vertex;

public class Barycentric {
    private double triangleArea;
    private Vertex v1;
    private Vertex v2;
    private Vertex v3;

    public Barycentric(Triangle triangle) {
        v1 = triangle.getVertex1();
        v2 = triangle.getVertex2();
        v3 = triangle.getVertex3();
        triangleArea = (v1.getY() - v3.getY()) * (v2.getX() - v3.getX()) + (v2.getY() - v3.getY()) * (v3.getX() - v1.getX());
    }

    public double getTriangleArea() {
        return triangleArea;
    }

    public boolean isInside(int x, int y) {
        double b1 = ((y - v3.getY()) * (v2.getX() - v3.getX()) + (v2.getY() - v3.getY()) * (v3.getX() - x)) / triangleArea;
        double b2 = ((y - v1.getY()) * (v3.getX() - v1.getX()) + (v3.getY() - v1.getY()) * (v1.getX() - x)) / triangleArea;
        double b3 = ((y - v2.getY()) * (v1.getX() - v2.getX()) + (v1.getY() - v2.getY()) * (v2.getX() - x)) / triangleArea;

         return b1 >= 0 && b2 >= 0 && b3 >= 0;
    }
}
