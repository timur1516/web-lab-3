package ru.timur.web3.model;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.timur.web3.db.ArchiveDAO;
import ru.timur.web3.view.ErrorHandlingView;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@Getter
@Named
@NoArgsConstructor
@ApplicationScoped
public class ArchiveBean implements Serializable {
    private LinkedList<PointBean> archive;
    @Inject
    private ArchiveDAO archiveDAO;
    @Inject
    private ErrorHandlingView errorHandlingView;

    @PostConstruct
    private void init() {
        try {
            archive = new LinkedList<>(archiveDAO.loadData().stream().sorted(Comparator.reverseOrder()).toList());
        } catch (Exception e) {
            archive = new LinkedList<>();
            errorHandlingView.handleError("Возникла ошибка сервера. Печалька...", e);
        }
    }

    public void addPoint(PointBean point) {
        archive.addFirst(point);
    }

    public PointBean getFirstPoint() {
        return archive.getFirst();
    }
}
