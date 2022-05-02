package com.example.Web4.mbeans;

import com.example.Web4.entities.Point;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Component;

import javax.management.Notification;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@ManagedResource
@Component
public class PointCounter implements NotificationPublisherAware, Serializable {
    private NotificationPublisher notificationPublisher;
    private final Map<String, Integer> usersPoints = new HashMap<>();
    private final Map<String, Integer> usersHits = new HashMap<>();
    private final Map<String, Integer> usersSeq = new HashMap<>();

    public void addPoint(Point point) {
        String username = point.getUser().getUsername();
        int points = usersPoints.containsKey(username) ? usersPoints.get(point.getUser().getUsername()) : 0;
        usersPoints.put(username, points + 1);
        if (point.isResult()) {
            int hits = 0;
            if (usersHits.containsKey(username)) hits = usersHits.remove(username);
            usersHits.put(username, hits + 1);
        }
        if ((points + 1) % 5 == 0) {
            int seq = 0;
            if (usersSeq.containsKey(username)) seq = usersSeq.remove(username);
            notificationPublisher.sendNotification(new Notification(
                    "Notification!", this, seq,
                    System.currentTimeMillis(), "The number of points is a multiply of 5"
            ));
            usersSeq.put(username, seq + 1);
        }
    }

    @Override
    public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        this.notificationPublisher = notificationPublisher;
    }

    @ManagedAttribute
    public Map<String, Integer> getUserPoints() {
        return this.usersPoints;
    }

    @ManagedAttribute
    public Map<String, Integer> getUserHits() {
        return this.usersHits;
    }

    @ManagedOperation
    public Integer getPoints(String username) {
        return this.usersPoints.get(username);
    }

    @ManagedOperation
    public Integer getHits(String username) {
        return this.usersHits.get(username);
    }
}
