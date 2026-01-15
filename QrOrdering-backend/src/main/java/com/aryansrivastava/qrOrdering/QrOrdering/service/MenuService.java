package com.aryansrivastava.qrOrdering.QrOrdering.service;

import com.aryansrivastava.qrOrdering.QrOrdering.dto.MenuItemDTO;
import com.aryansrivastava.qrOrdering.QrOrdering.exception.CategoryNotFoundException;
import com.aryansrivastava.qrOrdering.QrOrdering.model.Category;
import com.aryansrivastava.qrOrdering.QrOrdering.model.MenuItem;
import com.aryansrivastava.qrOrdering.QrOrdering.repository.CategoryRepository;
import com.aryansrivastava.qrOrdering.QrOrdering.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {


    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<MenuItemDTO> getAllAvailableItems() {
        List<MenuItem> menuItems = menuItemRepository.findByAvailable(true);
        return menuItems.stream().map(this::convertToMenuItemDTO).collect(Collectors.toList());
    }

    public List<MenuItemDTO> getAllAvailableItemsByCategory(String category) {

        Category cat =categoryRepository.findByName(category)
                .orElseThrow(()->new CategoryNotFoundException("No category with name "+category));
        List<MenuItem> menuItems = menuItemRepository.findByAvailableAndCategory(true,cat);
        return menuItems.stream().map(this::convertToMenuItemDTO).collect(Collectors.toList());
    }



    private MenuItemDTO convertToMenuItemDTO(MenuItem menuItem) {
        return new MenuItemDTO.Builder()
                .id(menuItem.getId())
                .name(menuItem.getName())
                .description(menuItem.getDescription())
                .price(menuItem.getPrice())
                .imageUrl(menuItem.getImageUrl())
                .available(menuItem.isAvailable())
                .categoryName(menuItem.getCategory() != null ? menuItem.getCategory().getName() : null)
                .build();
    }


    public MenuItem addMenuItem(MenuItemDTO menuItemDTO) {
        // Map DTO to entity
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemDTO.getName());
        menuItem.setDescription(menuItemDTO.getDescription());
        menuItem.setPrice(menuItemDTO.getPrice());
        menuItem.setImageUrl(menuItemDTO.getImageUrl());
        menuItem.setAvailable(menuItemDTO.isAvailable());

        // Resolve category by name if provided
        if (menuItemDTO.getCategoryName() != null && !menuItemDTO.getCategoryName().isBlank()) {
            Category category = categoryRepository.findByName(menuItemDTO.getCategoryName())
                    .orElseThrow(() -> new CategoryNotFoundException("No category with name " + menuItemDTO.getCategoryName()));
            menuItem.setCategory(category);
        }

        return menuItemRepository.save(menuItem);
    }

    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }

    public MenuItem updateMenuItem(Long id, MenuItem menuItem) {
        MenuItem existing = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        existing.setName(menuItem.getName());
        existing.setDescription(menuItem.getDescription());
        existing.setPrice(menuItem.getPrice());
        existing.setAvailable(menuItem.isAvailable());
        existing.setCategory(menuItem.getCategory());

        return menuItemRepository.save(existing);
    }
}
