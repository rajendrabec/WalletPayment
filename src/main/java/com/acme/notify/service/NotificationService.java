package com.acme.notify.service;

import com.acme.notify.domain.Notification;
import com.acme.notify.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    public Notification send(Notification n) {
        n.setSent(Boolean.TRUE);
        return repo.save(n);
    }

    public List<Notification> all() {
        return repo.findAll();
    }
}
