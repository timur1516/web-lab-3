package ru.timur.web3;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

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

    public void processRequest() {
        archive.addPoint(areaCheckBean.processInput(inputBean));
        PointBean pointBean = archive.getFirstPoint();
        PrimeFaces.current().executeScript("draw_point(" + pointBean.getX() + ", " + pointBean.getY() + ", " + pointBean.isHit() + ");");
    }
}
