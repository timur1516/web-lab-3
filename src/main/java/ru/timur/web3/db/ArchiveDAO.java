package ru.timur.web3.db;

import ru.timur.web3.model.PointBean;

public interface ArchiveDAO {
    void saveData(PointBean pointBean) throws Exception;
}
