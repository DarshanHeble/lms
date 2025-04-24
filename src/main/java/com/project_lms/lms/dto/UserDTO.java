package com.project_lms.lms.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String membershipStatus; // ACTIVE, INACTIVE, SUSPENDED
}