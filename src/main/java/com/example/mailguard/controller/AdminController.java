package com.example.mailguard.controller;

import com.example.mailguard.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @PutMapping("/category/{category-id}")
    public ResponseEntity<Void> updateEmailCategoryStatus(
            @PathVariable("category-id") UUID categoryId,
            @RequestParam Boolean activate
    )
    {
        adminService.changeCategoryStatus(activate, categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
