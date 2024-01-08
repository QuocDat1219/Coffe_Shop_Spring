package com.inn.cafe.servicesImpl;


import com.inn.cafe.dao.BillDao;
import com.inn.cafe.dao.CategoryDao;
import com.inn.cafe.dao.ProductDao;
import com.inn.cafe.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    BillDao billDao;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("category",categoryDao.count());
            map.put("product",productDao.count());
            map.put("bill",billDao.count());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
