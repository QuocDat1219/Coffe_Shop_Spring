package com.inn.cafe.services;

import com.inn.cafe.wrapper.UserWrapper;
import org.springframework.boot.context.metrics.buffering.StartupTimeline;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {

    //Đăng ký
    ResponseEntity<String> signUp(Map<String,String> requestMap);
    //Đăng nhập
    ResponseEntity<String> login(Map<String,String> requestMap);
    //Get all user
    ResponseEntity<List<UserWrapper>> getAllUser();
    //Approve user
    ResponseEntity<String> updateUser(Map<String,String> requestMap);
    //Check token
    ResponseEntity<String> checkToken();

    ResponseEntity<String> changePassword(Map<String,String> requestMap);

    ResponseEntity<String> forgotPassword(Map<String, String> requestMap);
}
