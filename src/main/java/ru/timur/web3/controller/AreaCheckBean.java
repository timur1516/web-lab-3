package ru.timur.web3.controller;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import ru.timur.web3.model.PointBean;
import ru.timur.web3.view.InputBean;

import java.io.Serializable;
import java.util.Date;

@SessionScoped
@Named
public class AreaCheckBean implements Serializable {
    public PointBean processInput(InputBean input) {
        PointBean pointBean = new PointBean();
        pointBean.setTime(new Date());
        pointBean.setX(input.getX());
        pointBean.setY(input.getY());
        pointBean.setR(input.getR());

        long startTime = System.nanoTime() / 1000;
        pointBean.setHit(isHit(pointBean));
        long endTime = System.nanoTime() / 1000;
        pointBean.setCalculationTime(endTime - startTime);
        return pointBean;
    }

    private boolean isHit(PointBean pointBean) {
        double x = pointBean.getX();
        double y = pointBean.getY();
        double r = pointBean.getR();
        double firstAreaA = Math.abs(x) / (r / 7) - 3;
        double firstSecondAreaA = Math.abs(y / (r / 7) + (double) 3 / 7 * Math.sqrt(33));
        double firstSecondAreaB = Math.pow((y / (r / 7)) / 3, 2);
        double firstSecondAreaC = Math.sqrt(Math.abs(firstSecondAreaA) / firstSecondAreaA);
        boolean firstArea = (y / (r / 7)) >= 0 && Math.pow(x / r, 2)
                * Math.sqrt(Math.abs(firstAreaA) / (firstAreaA))
                + firstSecondAreaB
                * firstSecondAreaC
                - 1 <= 0;

        double secondAreaA = Math.abs(x) / (r / 7) - 4;
        boolean secondArea = (y / (r / 7)) < 0 && Math.pow(x / r, 2)
                * Math.sqrt(Math.abs(secondAreaA) / (secondAreaA))
                + firstSecondAreaB
                * firstSecondAreaC
                - 1 <= 0;

        boolean thirdArea = (y / (r / 7)) < 0 && Math.abs((x / (r / 7)) / 2)
                - (3 * Math.sqrt(33) - 7) * Math.pow((x / (r / 7)), 2) / 112
                - 3 + Math.sqrt(1 - Math.pow(Math.abs(Math.abs(x) / (r / 7) - 2) - 1, 2))
                - y / (r / 7) <= 0;

        boolean fourthArea = Math.abs(x) / (r / 7) <= 1 && Math.abs(x) / (r / 7) >= 0.75
                && y / (r / 7) <= 3 && y / (r / 7) >= 0
                && 9 - 8 * Math.abs(x) / (r / 7) >= y / (r / 7);

        boolean fifthArea = y / (r / 7) >= 0
                && Math.abs(x) / (r / 7) <= 0.75 && Math.abs(x) / (r / 7) >= 0.5
                && 3 * Math.abs(x) / (r / 7) + 0.75 >= y / (r / 7);

        boolean sixthArea = x / (r / 7) <= 0.5 && x / (r / 7) >= -0.5
                && y / (r / 7) >= 0
                && y / (r / 7) <= 2.25;

        double seventhAreaA = Math.abs(x) / (r / 7) - 1;
        boolean seventhArea = y / (r / 7) >= 0 && 6 * Math.sqrt(10) / 7
                + (1.5 - 0.5 * Math.abs(x) / (r / 7))
                * Math.sqrt(Math.abs(seventhAreaA) / seventhAreaA)
                - 6 * Math.sqrt(10) / 14
                * Math.sqrt(4 - Math.pow(seventhAreaA, 2)) >= y / (r / 7);

        return firstArea || secondArea || thirdArea
                || fourthArea || fifthArea || sixthArea || seventhArea;
    }
}
