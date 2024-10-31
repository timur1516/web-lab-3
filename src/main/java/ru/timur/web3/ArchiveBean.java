package ru.timur.web3;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ArchiveBean implements Serializable {
    private final List<PointBean> archive = new ArrayList<>();
    public List<PointBean> getArchive() {
        return archive;
    }
    public void addPoint(PointBean point) {
        archive.add(point);
    }
}
