package ru.timur.web3.controller;

import jakarta.enterprise.context.SessionScoped;
import ru.timur.web3.entity.PointEntity;
import ru.timur.web3.view.InputBean;

import java.io.Serializable;
import java.util.Date;

@SessionScoped
public class AreaCheckBean implements Serializable {
    public PointEntity processInput(InputBean input) {
        PointEntity pointEntity = new PointEntity();
        pointEntity.setTime(new Date());
        pointEntity.setX(input.getX());
        pointEntity.setY(input.getY());
        pointEntity.setR(input.getR());

        long startTime = System.nanoTime() / 1000;
        pointEntity.setHit(isHit(pointEntity.getX(), pointEntity.getY(), pointEntity.getR()));
        long endTime = System.nanoTime() / 1000;
        pointEntity.setCalculationTime(endTime - startTime);
        return pointEntity;
    }

    public boolean isHit(double x, double y, double r) {
        return x * x + y * y <= r * r && y >= 0 && x >= 0 ||
                y <= 2 * x + r && y >= 0 && x <= 0 ||
                -r <= x && x <= 0 && -r / 2 <= y && y <= 0;
    }
}
