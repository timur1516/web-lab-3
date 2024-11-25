package ru.timur.web3.view;

import com.google.gson.Gson;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import ru.timur.web3.dto.PointDTO;
import ru.timur.web3.controller.AreaCheckBean;
import ru.timur.web3.entity.ArchiveEntity;
import ru.timur.web3.controller.ControllerBean;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class RemoteCommandView implements Serializable {
    @Inject
    private ArchiveEntity userArchive;
    @Inject
    private ControllerBean controllerBean;
    @Inject
    private InputBean inputBean;
    @Inject
    ErrorHandlingView errorHandlingView;
    @Inject
    private AreaCheckBean areaCheckBean;

    public void execute() {
        double x, y;
        try {
            x = Double.parseDouble(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("x"));
            y = Double.parseDouble(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("y"));
        } catch (NumberFormatException e) {
            errorHandlingView.handleError("При передаче данных возникла ошибка. :(", e);
            return;
        }
        inputBean.setX(x);
        inputBean.setY(y);
        controllerBean.processRequest(false);
    }

    public void recalculatePoints() {
        double r = Double.parseDouble(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("r"));
        List<PointDTO> result = userArchive
                .getArchive()
                .stream()
                .map(pointBean -> PointDTO
                        .builder()
                        .x(pointBean.getX())
                        .y(pointBean.getY())
                        .hit(areaCheckBean.isHit(pointBean.getX(), pointBean.getY(), r))
                        .build()).toList();
        Gson gson = new Gson();
        String json = gson.toJson(result);
        PrimeFaces.current().ajax().addCallbackParam("result", json);
    }
}
