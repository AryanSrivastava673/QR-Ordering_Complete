package com.aryansrivastava.qrOrdering.QrOrdering.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminLoginRequestDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}

