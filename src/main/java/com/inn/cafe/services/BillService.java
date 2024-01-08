package com.inn.cafe.services;

import com.inn.cafe.POJO.Bill;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BillService {
    ResponseEntity<String> generateReport(Map<String,Object> requestmap);

    ResponseEntity<List<Bill>> getBills();

    ResponseEntity<byte[]> getPdf(Map<String,Object> requestMap);

    ResponseEntity<String> deleteBill(Integer id);
}
