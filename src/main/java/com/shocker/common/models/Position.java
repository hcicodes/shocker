package com.shocker.common.models;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void moveLeft(int jump) {
        this.x -= jump;
    }

    public void moveRight(int jump) {
        this.x += jump;
    }

    public void moveUp(int jump) {
        this.y += jump;
    }

    public void moveDown(int jump) {
        this.y -= jump;
    }
}
