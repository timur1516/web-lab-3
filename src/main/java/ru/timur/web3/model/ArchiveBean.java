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

    //TODO: Добавить более адекватную обработку исключения от redirect
    @PostConstruct
    private void init() throws IOException {
        try {
            archive = new LinkedList<>(archiveDAO.loadData().stream().sorted(Comparator.reverseOrder()).toList());
        } catch (Exception e) {
            archive = new LinkedList<>();
            FacesContext.getCurrentInstance().getExternalContext().redirect("errorPage.xhtml");
            System.out.println(e.getMessage());
        }
    }

    public void addPoint(PointBean point) {
        archive.addFirst(point);
    }

    public PointBean getFirstPoint() {
        return archive.getFirst();
    }
}
