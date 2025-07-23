package com.example.mailguard.controller;

import com.example.mailguard.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping("/emails")
    public ResponseEntity<Void> sendEmailByCategory(@RequestParam String emailCategory){
        adminService.sendEmailByCategory(emailCategory);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
