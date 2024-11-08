package ru.timur.web3.controller;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
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

    public void processRequest() {
        PointBean newPoint = areaCheckBean.processInput(inputBean);
        archiveBean.addPoint(newPoint);
        PrimeFaces.current().executeScript("draw_point(" + newPoint.getX() + ", " + newPoint.getY() + ", " + newPoint.isHit() + ");");
    }
}
