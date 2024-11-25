package ru.timur.web3.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import ru.timur.web3.entity.ArchiveEntity;

import java.io.Serializable;

@WebListener
@ApplicationScoped
public class SessionTimeoutListener implements HttpSessionListener, Serializable {
    @Inject
    private ArchiveEntity archiveEntity;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("Session created: " + event.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        System.out.println("Session finished: " + event.getSession().getId());
        archiveEntity.removeSessionData(event.getSession().getId());
    }
}
