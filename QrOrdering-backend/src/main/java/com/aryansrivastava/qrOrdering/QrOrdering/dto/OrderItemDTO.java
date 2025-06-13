package com.aryansrivastava.qrOrdering.QrOrdering.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Long MenuItemId;
    private String MenuItemName;
    private Integer quantity;
}
