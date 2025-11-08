package com.acme.notify.web;

import com.acme.notify.domain.Notification;
import com.acme.notify.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    public Notification send(@RequestBody Notification n) {
        return service.send(n);
    }

    @GetMapping
    public List<Notification> all() {
        return service.all();
    }

}
