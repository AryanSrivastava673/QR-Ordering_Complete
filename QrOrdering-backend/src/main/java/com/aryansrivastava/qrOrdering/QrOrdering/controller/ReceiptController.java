package com.aryansrivastava.qrOrdering.QrOrdering.controller;

import com.aryansrivastava.qrOrdering.QrOrdering.dto.ReceiptDTO;
import com.aryansrivastava.qrOrdering.QrOrdering.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/receipt")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @PostMapping("/{tableNo}/checkout")
    public ResponseEntity<ReceiptDTO> checkout(@PathVariable String tableNo){
        ReceiptDTO receiptDTO= receiptService.createReceipt(tableNo);
        return ResponseEntity.status(HttpStatus.CREATED).body(receiptDTO);



    }

}
