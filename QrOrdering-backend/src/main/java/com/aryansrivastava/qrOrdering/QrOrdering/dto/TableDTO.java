package com.aryansrivastava.qrOrdering.QrOrdering.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TableDTO {
    @NotBlank(message = "tableNumber is required")
    private String tableNumber;

    private String qrCodeUrl;
}
