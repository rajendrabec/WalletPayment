package com.acme.notify.service;

import com.acme.notify.domain.Notification;
import com.acme.notify.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationServiceTest {
    @Test
    public void sendMarksSentTrue() {
        NotificationRepository repo = Mockito.mock(NotificationRepository.class);
        Mockito.when(repo.save(Mockito.any(Notification.class))).thenAnswer(a -> a.getArgument(0));
        NotificationService svc = new NotificationService(repo);
        Notification n = new Notification();
        Notification saved = svc.send(n);
        assertTrue(saved.getSent());
    }
}
