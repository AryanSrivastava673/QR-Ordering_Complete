package com.aryansrivastava.qrOrdering.QrOrdering.controller;


import com.aryansrivastava.qrOrdering.QrOrdering.dto.CartItemDTO;
import com.aryansrivastava.qrOrdering.QrOrdering.dto.MenuItemDTO;
import com.aryansrivastava.qrOrdering.QrOrdering.dto.TableDTO;
import com.aryansrivastava.qrOrdering.QrOrdering.model.CartItem;
import com.aryansrivastava.qrOrdering.QrOrdering.model.RestTable;
import com.aryansrivastava.qrOrdering.QrOrdering.service.TableService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
// In TableController.java

@RestController
@RequestMapping("/api")
public class TableController {
    @Autowired
    private TableService tableService;

    @Autowired
    private ModelMapper modelMapper;

    // Existing endpoint to add items to the cart
    @GetMapping("/admin/tables")
    public ResponseEntity<List<String>> getAllTables(){
        List<String> tables=tableService.getAllTablesId();
        return ResponseEntity.ok(tables);
    }

    @PostMapping("/admin/tables")
    public ResponseEntity<RestTable> createTable(@Valid @RequestBody TableDTO tableDTO) {
        RestTable created = tableService.addTable(tableDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/{tableNo}/addToCart")
    public ResponseEntity<?> addToCart(@PathVariable String tableNo, @RequestBody MenuItemDTO item) {
        CartItem cartItem = tableService.addToCart(item, tableNo);
        CartItemDTO cartItemDTO = modelMapper.map(cartItem, CartItemDTO.class); // Assuming ModelMapper bean is defined
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDTO);
    }

    // New endpoint to get cart items
    @GetMapping("/{tableNo}/getCart")
    public ResponseEntity<List<CartItemDTO>> getCart(@PathVariable String tableNo) {
        List<CartItem> cartItems = tableService.getCartItems(tableNo);
        List<CartItemDTO> cartItemsDTO = cartItems.stream()
                .map(cartItem -> modelMapper.map(cartItem, CartItemDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(cartItemsDTO);
    }

    @PostMapping("/{tableNo}/removeFromCart/{menuItemId}")
    public ResponseEntity<?> removeFromCart(@PathVariable String tableNo, @PathVariable Long menuItemId) {
        CartItem cartItem = tableService.removeFromCart(tableNo, menuItemId);
        if (cartItem.getQuantity() > 0) {
            CartItemDTO cartItemDTO = modelMapper.map(cartItem, CartItemDTO.class);
            return ResponseEntity.ok(cartItemDTO);
        } else {
            return ResponseEntity.ok().build(); // Send an empty response indicating item was removed
        }
    }
}