package ru.timur.web3.controller;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import ru.timur.web3.db.ArchiveDAO;
import ru.timur.web3.db.ArchiveDAOImpl;
import ru.timur.web3.model.ArchiveBean;
import ru.timur.web3.model.PointBean;
import ru.timur.web3.view.InputBean;

import java.io.IOException;
import java.io.Serializable;

@SessionScoped
@Named
public class ControllerBean implements Serializable {
    @Inject
    private InputBean inputBean;
    @Inject
    private ArchiveBean archive;
    @Inject
    private AreaCheckBean areaCheckBean;
    @Inject
    private ArchiveDAO archiveDAO;

    public void processRequest() throws IOException {
        archive.addPoint(areaCheckBean.processInput(inputBean));
        PointBean pointBean = archive.getFirstPoint();
        try {
            archiveDAO.saveData(pointBean);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("errorPage.xhtml");
        }
        PrimeFaces.current().executeScript("draw_point(" + pointBean.getX() + ", " + pointBean.getY() + ", " + pointBean.isHit() + ");");
    }
}
