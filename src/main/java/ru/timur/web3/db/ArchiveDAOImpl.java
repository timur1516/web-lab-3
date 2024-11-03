package ru.timur.web3.db;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.timur.web3.model.PointBean;

import java.io.Serializable;

@ApplicationScoped
public class ArchiveDAOImpl implements ArchiveDAO, Serializable {
    @Inject
    private SessionFactory sessionFactory;

    public void saveData(PointBean pointBean) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.persist(pointBean);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new Exception(e);
        }
    }
}
