package ru.timur.web3.model;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;

import java.io.Serializable;
import java.util.LinkedList;

@Getter
@Named
@SessionScoped
public class ArchiveBean implements Serializable {
    private final LinkedList<PointBean> archive = new LinkedList<>();

    public void addPoint(PointBean point) {
        archive.addFirst(point);
    }

    public PointBean getFirstPoint() {
        return archive.getFirst();
    }
}
