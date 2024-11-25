package ru.timur.web3.dao;

import ru.timur.web3.entity.PointEntity;

import java.util.List;

public interface ArchiveDAO {
    void savePoint(PointEntity pointEntity) throws Exception;
    List<PointEntity> loadData() throws Exception;
    void removePointsBySession(String sessionId) throws Exception;
}
