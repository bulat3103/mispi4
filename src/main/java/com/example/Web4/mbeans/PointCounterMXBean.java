package com.example.Web4.mbeans;

import java.util.Map;

public interface PointCounterMXBean {
    Map<String, Integer> getUserPoints();
    Map<String, Integer> getUserHits();
    Integer getPoints(String username);
    Integer getHits(String username);
}
