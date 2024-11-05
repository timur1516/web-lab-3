package ru.timur.web3.db;

import ru.timur.web3.model.PointBean;

import java.util.List;

public interface ArchiveDAO {
    void saveData(PointBean pointBean) throws Exception;
    List<PointBean> loadData() throws Exception;
}
