package com.aryansrivastava.qrOrdering.QrOrdering.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptDTO {
    private long id;
    private List<ReceiptItemDTO> receiptItemDTOList;
    private double itemsTotal;
    private double tax;
    private double grandTotal;
}
