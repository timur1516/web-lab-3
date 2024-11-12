package ru.timur.web3.controller;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;
import ru.timur.web3.db.ArchiveDAO;
import ru.timur.web3.model.ArchiveBean;
import ru.timur.web3.model.PointBean;
import ru.timur.web3.view.ErrorHandlingView;
import ru.timur.web3.view.InputBean;

import java.io.Serializable;

@SessionScoped
@Named
public class ControllerBean implements Serializable {
    @Inject
    private InputBean inputBean;
    @Inject
    private ArchiveBean archiveBean;
    @Inject
    private AreaCheckBean areaCheckBean;

    public void processRequest(boolean doAnimation) {
        PointBean newPoint = areaCheckBean.processInput(inputBean);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        newPoint.setSessionId(session == null ? "undefined" : session.getId());
        archiveBean.addPoint(newPoint);
        PrimeFaces.current().executeScript("display_point(" + newPoint.getX() + ", " + newPoint.getY() + ", " + newPoint.isHit() + ", " + doAnimation + ");");
    }
}
