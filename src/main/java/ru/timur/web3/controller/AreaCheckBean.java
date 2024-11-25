package ru.timur.web3.controller;

import jakarta.enterprise.context.SessionScoped;
import ru.timur.web3.model.PointBean;
import ru.timur.web3.view.InputBean;

import java.io.Serializable;
import java.util.Date;

@SessionScoped
public class AreaCheckBean implements Serializable {
    public PointBean processInput(InputBean input) {
        PointBean pointBean = new PointBean();
        pointBean.setTime(new Date());
        pointBean.setX(input.getX());
        pointBean.setY(input.getY());
        pointBean.setR(input.getR());

        long startTime = System.nanoTime() / 1000;
        pointBean.setHit(isHit(pointBean.getX(), pointBean.getY(), pointBean.getR()));
        long endTime = System.nanoTime() / 1000;
        pointBean.setCalculationTime(endTime - startTime);
        return pointBean;
    }

    public boolean isHit(double x, double y, double r) {
        return x * x + y * y <= r * r && y >= 0 && x >= 0 ||
                y <= 2 * x + r && y >= 0 && x <= 0 ||
                -r <= x && x <= 0 && -r / 2 <= y && y <= 0;
    }
}
