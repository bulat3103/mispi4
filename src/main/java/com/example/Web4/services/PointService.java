package com.example.Web4.services;

import com.example.Web4.entities.Point;
import com.example.Web4.entities.User;
import com.example.Web4.mbeans.AverageClickTime;
import com.example.Web4.mbeans.PointCounter;
import com.example.Web4.repositories.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.List;

@Service
public class PointService {
    private final PointRepository pointRepository;
    private final PointCounter pointCounter;
    private final AverageClickTime averageClickTime;

    @Autowired
    public PointService(PointRepository pointRepository, PointCounter pointCounter, AverageClickTime averageClickTime) {
        this.pointRepository = pointRepository;
        this.pointCounter = pointCounter;
        this.averageClickTime = averageClickTime;
    }

    public void countPoints(Point point) {
        this.pointCounter.addPoint(point);
        this.averageClickTime.addPoint(point);
    }

    public List<Point> findByUser(User user) {
        return pointRepository.findByUser(user);
    }

    public void addPoint(Point point) {
        pointRepository.save(point);
    }

    public long deleteByUser(User user) {
        return pointRepository.deleteByUser(user);
    }
}
