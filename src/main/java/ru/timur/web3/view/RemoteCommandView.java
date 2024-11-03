package ru.timur.web3.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import ru.timur.web3.model.ArchiveBean;
import ru.timur.web3.controller.ControllerBean;

import java.io.IOException;

@Named
@RequestScoped
public class RemoteCommandView {
    @Inject
    private InputBean inputBean;
    @Inject
    private ControllerBean controllerBean;
    @Inject
    private ArchiveBean archiveBean;

    public void execute() throws IOException {
        //TODO Добавить проверку некорректных данных
        double x = Double.parseDouble(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("x"));
        double y = Double.parseDouble(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("y"));
        inputBean.setX(x);
        inputBean.setY(y);
        controllerBean.processRequest();
        PrimeFaces.current().ajax().addCallbackParam("hit", archiveBean.getFirstPoint().isHit());
    }
}
