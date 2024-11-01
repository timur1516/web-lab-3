package ru.timur.web3;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Date;

@RequestScoped
@Named
public class PointBean implements Serializable {
    private double x;
    private double y;
    private double r;
    private boolean hit;
    private Date time;
    private long calculationTime;

    public PointBean() {}

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public long getCalculationTime() {
        return calculationTime;
    }

    public void setCalculationTime(long calculationTime) {
        this.calculationTime = calculationTime;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
