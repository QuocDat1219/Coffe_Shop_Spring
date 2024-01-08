package com.inn.cafe.services;

import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Objects;

public interface DashboardService {
    ResponseEntity<Map<String, Object>> getCount();
}
