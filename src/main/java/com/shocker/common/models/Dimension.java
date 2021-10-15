package com.shocker.common.models;

public class Dimension {
    private int width;
    private int height;

    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void growWidth(int additionalWidth) {
        this.width += additionalWidth;
    }

    public void shrinkWidth(int reducdeWidth) {
        this.width -= reducdeWidth;
    }

    public void growHeight(int additionalHeight) {
        this.height += additionalHeight;
    }

    public void shrinkHeight(int reduceHeight) {
        this.height -= reduceHeight;
    }
}
