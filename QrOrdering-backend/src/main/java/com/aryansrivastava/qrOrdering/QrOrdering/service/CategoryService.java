package com.aryansrivastava.qrOrdering.QrOrdering.service;

import com.aryansrivastava.qrOrdering.QrOrdering.dto.CategoryDTO;
import com.aryansrivastava.qrOrdering.QrOrdering.model.Category;
import com.aryansrivastava.qrOrdering.QrOrdering.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private CategoryDTO convertToDto(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }
}
