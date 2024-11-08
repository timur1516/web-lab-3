package ru.timur.web3.controller;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import ru.timur.web3.db.ArchiveDAO;
import ru.timur.web3.model.ArchiveBean;
import ru.timur.web3.model.PointBean;
import ru.timur.web3.view.ErrorHandlingView;
import ru.timur.web3.view.InputBean;

import java.io.IOException;
import java.io.Serializable;

@SessionScoped
@Named
public class ControllerBean implements Serializable {
    @Inject
    private InputBean inputBean;
    @Inject
    private ArchiveBean userArchive;
    @Inject
    private AreaCheckBean areaCheckBean;
    @Inject 
    private ArchiveDAO archiveDAO;
    @Inject
    private ErrorHandlingView errorHandlingView;

    public void processRequest() {
        userArchive.addPoint(areaCheckBean.processInput(inputBean));
        PointBean pointBean = userArchive.getFirstPoint();
        try {
            archiveDAO.saveData(pointBean);
        } catch (Exception e) {
            errorHandlingView.handleError("Возникла ошибка сервера. Печалька...", e);
        }
        PrimeFaces.current().executeScript("draw_point(" + pointBean.getX() + ", " + pointBean.getY() + ", " + pointBean.isHit() + ");");
    }
}
