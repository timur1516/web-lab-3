package ru.timur.web3.view;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor

@SessionScoped
@Named
public class ErrorHandlingView implements Serializable {
    private String userErrorMessage;

    public void handleError(String userErrorMessage, Exception exception) {
        this.userErrorMessage = userErrorMessage;
        System.err.println(exception.getMessage());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("errorPage.xhtml");
        } catch (IOException ignored) {}
    }
}
