package ru.timur.web3.view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.SlideEndEvent;

import java.io.Serializable;

@Getter
@Setter
@ViewScoped
@Named
public class InputBean implements Serializable {
    private double x = 0;
    private double y = 0;
    private double r = 1;

    public void updateR(SlideEndEvent event) {
        this.r = event.getValue();
    }
}
