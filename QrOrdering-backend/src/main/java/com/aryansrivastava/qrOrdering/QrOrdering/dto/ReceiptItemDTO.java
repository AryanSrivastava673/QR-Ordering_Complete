package com.aryansrivastava.qrOrdering.QrOrdering.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptItemDTO {
    private long id;
    private String menuItemName;
    private long quantity;
}
