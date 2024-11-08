package ru.timur.web3.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import ru.timur.web3.model.ArchiveBean;

import java.io.Serializable;

@WebListener
@ApplicationScoped
public class SessionTimeoutListener implements HttpSessionListener, Serializable {
    @Inject
    private ArchiveBean archiveBean;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("Session created: " + event.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        System.out.println("Session finished: " + event.getSession().getId());
        archiveBean.removeSessionData(event.getSession().getId());
    }
}
