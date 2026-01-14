package com.treyhutson.Math;

public class DepthBuffer {
    private int width;
    private double[] maxDepth;
    public DepthBuffer(int width, int height) {
        this.width = width;
        maxDepth = new double[width * height];
        for (int i = 0; i < width * height; i++) {
            maxDepth[i] = Double.NEGATIVE_INFINITY;
        }
    }

    public boolean checkTopDepth(double depth, int x, int y) {
        int zIndex = y * width + x;
        if (maxDepth[zIndex] < depth) {
            maxDepth[zIndex] = depth;
            return true;
        }
        return false;
    }
}
