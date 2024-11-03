package ru.timur.web3.db;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;

import jakarta.enterprise.inject.Produces;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@ApplicationScoped
public class HibernateUtil {
    private static final SessionFactory sessionFactory =
            new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();

    @Produces
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @PreDestroy
    public static void closeSessionFactory() {
        sessionFactory.close();
    }
}

