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
public class PointCounter implements NotificationPublisherAware, PointCounterMXBean, Serializable {
    private NotificationPublisher notificationPublisher;
    private final Map<String, Integer> usersPoints = new HashMap<>();
    private final Map<String, Integer> usersHits = new HashMap<>();
    private int sequenceNumber;

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
            notificationPublisher.sendNotification(new Notification(
                    "Notification!", this, sequenceNumber++,
                    System.currentTimeMillis(), "The number of points is a multiply of 5"
            ));
        }
    }

    @Override
    public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        this.notificationPublisher = notificationPublisher;
    }

    @ManagedAttribute
    @Override
    public Map<String, Integer> getUserPoints() {
        return this.usersPoints;
    }

    @ManagedAttribute
    @Override
    public Map<String, Integer> getUserHits() {
        return this.usersHits;
    }

    @ManagedOperation
    @Override
    public Integer getPoints(String username) {
        return this.usersPoints.get(username);
    }

    @ManagedOperation
    @Override
    public Integer getHits(String username) {
        return this.usersHits.get(username);
    }
}
