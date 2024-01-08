package com.inn.cafe.rest;

import com.inn.cafe.POJO.Bill;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/api/bill")
public interface BillRest {

    @PostMapping(path = "/generate-report")
    ResponseEntity<String> generateReport(@RequestBody() Map<String,Object> requestmap);

    @GetMapping(path = "/")
    ResponseEntity<List<Bill>> getBills();

    @PostMapping(path = "/get-Pdf")
    ResponseEntity<byte[]> getPdf(@RequestBody() Map<String,Object> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteBill(@PathVariable Integer id);


}
