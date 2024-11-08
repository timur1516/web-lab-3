package ru.timur.web3.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import ru.timur.web3.controller.AreaCheckBean;
import ru.timur.web3.db.ArchiveDAO;
import ru.timur.web3.model.ArchiveBean;
import ru.timur.web3.controller.ControllerBean;
import ru.timur.web3.model.PointBean;

import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class RemoteCommandView implements Serializable {
    @Inject
    private ArchiveBean userArchive;
    @Inject
    private ControllerBean controllerBean;
    @Inject
    private InputBean inputBean;
    @Inject ErrorHandlingView errorHandlingView;

    public void execute() {
        double x, y;
        try {
            x = Double.parseDouble(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("x"));
            y = Double.parseDouble(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("y"));
        } catch (NumberFormatException e) {
            errorHandlingView.handleError("Возникла ошибка сервера. Печалька...", e);
            return;
        }
        inputBean.setX(x);
        inputBean.setY(y);
        controllerBean.processRequest();

        PrimeFaces.current().ajax().addCallbackParam("hit", userArchive.getFirstPoint().isHit());
    }
}
