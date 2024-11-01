package ru.timur.web3;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.LinkedList;

@Named
@SessionScoped
public class ArchiveBean implements Serializable {
    private final LinkedList<PointBean> archive = new LinkedList<>();

    public LinkedList<PointBean> getArchive() {
        return archive;
    }

    public void addPoint(PointBean point) {
        archive.addFirst(point);
    }

    public PointBean getFirstPoint(){
        return archive.getFirst();
    }
}
