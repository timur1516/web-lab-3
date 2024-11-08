package ru.timur.web3.model;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.timur.web3.db.ArchiveDAO;
import ru.timur.web3.view.ErrorHandlingView;

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
            errorHandlingView.handleError("Возникла непредвиденная ошибка на сервере. Не удалось загрузить данные. :(", e);
        }
    }

    public synchronized void addPoint(PointBean point) {
        try {
            archiveDAO.savePoint(point);
        } catch (Exception e) {
            errorHandlingView.handleError("Возникла непредвиденная ошибка на сервере. Не удалось добавить точку. :(", e);
            return;
        }
        archive.addFirst(point);
    }

    public synchronized PointBean getFirstPoint() {
        return archive.isEmpty() ? null : archive.getFirst();
    }

    public synchronized void removeSessionData(String sessionId) {
        try {
            archiveDAO.removePointsBySession(sessionId);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }
        archive.removeIf(point -> point.getSessionId().equals(sessionId));
    }
}
