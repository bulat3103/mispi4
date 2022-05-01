package com.example.Web4.mbeans;

import java.util.Map;

public interface AverageClickTimeMXBean {
    Map<String, Integer> getUserPointsCount();
    Map<String, Long> getUserFirstClick();
    Map<String, Long> getUserLastClick();
    long getAverageTime(String username);
}
