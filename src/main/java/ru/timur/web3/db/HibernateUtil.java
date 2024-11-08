package ru.timur.web3.db;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;

import jakarta.enterprise.inject.Produces;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;

@NoArgsConstructor
@ApplicationScoped
public class HibernateUtil implements Serializable {
    private SessionFactory sessionFactory;

    @PostConstruct
    private void init() {
        sessionFactory = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    }

    @Produces
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @PreDestroy
    public void closeSessionFactory() {
        sessionFactory.close();
    }
}

