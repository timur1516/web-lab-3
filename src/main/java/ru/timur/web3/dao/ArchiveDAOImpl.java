package ru.timur.web3.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.timur.web3.entity.PointEntity;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class ArchiveDAOImpl implements ArchiveDAO, Serializable {
    @Inject
    private SessionFactory sessionFactory;

    public void savePoint(PointEntity pointEntity) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.persist(pointEntity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new Exception(e);
        }
    }

    public List<PointEntity> loadData() throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<PointEntity> pointEntities;
        try (session) {
            pointEntities = session.createQuery("from PointEntity", PointEntity.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new Exception(e);
        }
        return pointEntities;
    }

    @Override
    public void removePointsBySession(String sessionId) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.createMutationQuery("delete from PointEntity where sessionId = :sessionId")
                    .setParameter("sessionId", sessionId).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new Exception(e);
        }
    }
}
