package com.treyhutson.Models;

import java.awt.*;

public class Triangle {
    private Vertex vertex1;
    private Vertex vertex2;
    private Vertex vertex3;
    private Color color;

    public Triangle(Vertex vertex1, Vertex vertex2, Vertex vertex3, Color color) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.vertex3 = vertex3;

        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Vertex getVertex1() {
        return vertex1;
    }

    public Vertex getVertex2() {
        return vertex2;
    }

    public Vertex getVertex3() {
        return vertex3;
    }
}
