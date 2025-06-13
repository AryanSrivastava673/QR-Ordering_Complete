package com.aryansrivastava.qrOrdering.QrOrdering.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MenuItemDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private boolean available;
    private String categoryName;

    // Getters and Setters
    // Assumes Lombok for brevity
    @Getter
    @Setter
    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private Double price;
        private String imageUrl;
        private boolean available;
        private String categoryName;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder available(boolean available) {
            this.available = available;
            return this;
        }

        public Builder categoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public MenuItemDTO build() {
            MenuItemDTO menuItemDTO = new MenuItemDTO();
            menuItemDTO.id = this.id;
            menuItemDTO.name = this.name;
            menuItemDTO.description = this.description;
            menuItemDTO.price = this.price;
            menuItemDTO.imageUrl = this.imageUrl;
            menuItemDTO.available = this.available;
            menuItemDTO.categoryName = this.categoryName;
            return menuItemDTO;
        }
    }
}
