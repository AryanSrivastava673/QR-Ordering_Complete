package com.aryansrivastava.qrOrdering.QrOrdering.repository;

import com.aryansrivastava.qrOrdering.QrOrdering.model.Category;
import com.aryansrivastava.qrOrdering.QrOrdering.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByCategoryId(Long categoryId);

    @Query("SELECT mi FROM MenuItem mi WHERE mi.available = :available")
    List<MenuItem> findByAvailable(boolean available);

    @Query("SELECT mi FROM MenuItem mi WHERE mi.available = :available and mi.category=:category")
    List<MenuItem> findByAvailableAndCategory(boolean available, Category category);
}