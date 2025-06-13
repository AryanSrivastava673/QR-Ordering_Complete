package com.aryansrivastava.qrOrdering.QrOrdering.controller;

import com.aryansrivastava.qrOrdering.QrOrdering.dto.OrderDTO;
import com.aryansrivastava.qrOrdering.QrOrdering.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/{tableNo}/placeOrder")
    public ResponseEntity<?> placeOrder(@PathVariable String tableNo){
        boolean success = orderService.placeOrder(tableNo);
        if (success) {
            return ResponseEntity.ok().build(); // Return 200 OK if successful
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 if failed
        }
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<OrderDTO>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/getOrders/{tableNo}")
    public ResponseEntity<List<OrderDTO>> getOrdersForTable(@PathVariable String tableNo){
        return ResponseEntity.ok(orderService.getOrdersForTable(tableNo));
    }

    @PostMapping("/{orderId}/isDone")
    public ResponseEntity<?> isDone(@PathVariable long orderId){
        boolean success = orderService.orderIsDone(orderId);
        if (success) {
            return ResponseEntity.ok().build(); // Return 200 OK if successful
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 if failed
        }
    }

    @DeleteMapping("/{tableNo}")
    public ResponseEntity<?> deleteOrdersForTable(@PathVariable String tableNo){
        boolean success = orderService.deleteOrdersForTable(tableNo);
        if (success) {
            return ResponseEntity.ok().build(); // Return 200 OK if successful
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 if failed
        }
    }

}
