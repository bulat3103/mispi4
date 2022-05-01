package com.example.Web4.mbeans;

import com.example.Web4.entities.Point;
import com.example.Web4.entities.User;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@ManagedResource
@Component
public class AverageClickTime implements AverageClickTimeMXBean, Serializable {
    private final Map<String, Integer> userPointsCount = new HashMap<>();
    private final Map<String, Long> userFirstClick = new HashMap<>();
    private final Map<String, Long> userLastClick = new HashMap<>();

    public void addPoint(Point point) {
        if (!userPointsCount.containsKey(point.getUser().getUsername())) {
            userPointsCount.put(point.getUser().getUsername(), 1);
            userFirstClick.put(point.getUser().getUsername(), System.currentTimeMillis());
        } else {
            int countPoints = userPointsCount.remove(point.getUser().getUsername());
            userPointsCount.put(point.getUser().getUsername(), countPoints + 1);
            userLastClick.remove(point.getUser().getUsername());
        }
        userLastClick.put(point.getUser().getUsername(), System.currentTimeMillis());
    }

    @ManagedAttribute
    @Override
    public Map<String, Integer> getUserPointsCount() {
        return this.userPointsCount;
    }

    @ManagedAttribute
    @Override
    public Map<String, Long> getUserFirstClick() {
        return this.userFirstClick;
    }

    @Override
    public Map<String, Long> getUserLastClick() {
        return this.userLastClick;
    }

    @ManagedOperation
    @Override
    public long getAverageTime(String username) {
        if (!userPointsCount.containsKey(username)) return -1;
        return (userLastClick.get(username) - userFirstClick.get(username)) / userPointsCount.get(username);
    }
}
