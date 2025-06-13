package com.aryansrivastava.qrOrdering.QrOrdering.controller;

import com.aryansrivastava.qrOrdering.QrOrdering.dto.MenuItemDTO;
import com.aryansrivastava.qrOrdering.QrOrdering.model.MenuItem;
import com.aryansrivastava.qrOrdering.QrOrdering.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/{table_number}/menu")
@RequiredArgsConstructor
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/available")
    public ResponseEntity<List<MenuItemDTO>> getAvailableItems() {
        List<MenuItemDTO> menuItems = menuService.getAllAvailableItems();
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<MenuItemDTO>> getMenuItemsByCategory(@PathVariable String category){
        List<MenuItemDTO> menuItems= menuService.getAllAvailableItemsByCategory(category);
        return ResponseEntity.ok(menuItems);
    }

//    @PostMapping("/addMenuItem")
//    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem menuItem) {
//        return ResponseEntity.ok(menuService.addMenuItem(menuItem));
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<MenuItem> updateMenuItem(
//            @PathVariable Long id,
//            @RequestBody MenuItem menuItem
//    ) {
//        return ResponseEntity.ok(menuService.updateMenuItem(id, menuItem));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuService.deleteMenuItem(id);
        return ResponseEntity.ok().build();
    }
}