package com.aryansrivastava.qrOrdering.QrOrdering.service;

import com.aryansrivastava.qrOrdering.QrOrdering.dto.CartItemDTO;
import com.aryansrivastava.qrOrdering.QrOrdering.dto.MenuItemDTO;
import com.aryansrivastava.qrOrdering.QrOrdering.model.CartItem;
import com.aryansrivastava.qrOrdering.QrOrdering.model.MenuItem;
import com.aryansrivastava.qrOrdering.QrOrdering.model.RestTable;
import com.aryansrivastava.qrOrdering.QrOrdering.repository.CartItemRepository;
import com.aryansrivastava.qrOrdering.QrOrdering.repository.MenuItemRepository;
import com.aryansrivastava.qrOrdering.QrOrdering.repository.TableRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TableService {

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    public List<String> getAllTablesId(){
        List<RestTable> tables=tableRepository.findAll();
        List<String> ids=new ArrayList<>();
        for(RestTable table:tables){
            ids.add(table.getTableNumber());
        }
        return ids;
    }

    public List<CartItem> getCartItems(String tableNo) {
        RestTable table = tableRepository.findById(tableNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Table not found."));
        return new ArrayList<>(table.getCartItems());
    }


    @Transactional
    public CartItem addToCart(MenuItemDTO item, String tableNo){
        Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(item.getId());
        if (!optionalMenuItem.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu item not found.");
        }
        MenuItem menuItem = optionalMenuItem.get();

        RestTable currTable = tableRepository.findById(tableNo).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Table not found."));

        CartItem newCartItem=new CartItem();
        boolean found =false;
        for(CartItem cartItem: currTable.getCartItems()){
            if(cartItem.getMenuItem().getId().equals(menuItem.getId())){
                cartItem.setQuantity(cartItem.getQuantity()+1);
                cartItem.setPrice(cartItem.getPrice()+ menuItem.getPrice());
                return cartItemRepository.save(cartItem);
            }
        }

            newCartItem.setMenuItem(menuItem);
            newCartItem.setQuantity(1);
            newCartItem.setPrice(menuItem.getPrice());
            currTable.getCartItems().add(newCartItem);
//            tableRepository.save(currTable);
        return cartItemRepository.save(newCartItem);
    }


    public CartItem removeFromCart(String tableNo, Long menuItemId) {
        RestTable table = tableRepository.findById(tableNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Table not found."));
        CartItem cartItem = table.getCartItems().stream()
                .filter(item -> item.getMenuItem().getId().equals(menuItemId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found in cart."));
        Double itemPrice=menuItemRepository.findById(cartItem.getMenuItem().getId()).get().getPrice();
        cartItem.setQuantity(cartItem.getQuantity()-1);
        cartItem.setPrice(cartItem.getPrice()-itemPrice);
        if (cartItem.getQuantity() <= 0) {
            table.getCartItems().remove(cartItem);
            cartItemRepository.delete(cartItem); // Assuming existence of cartItemRepository
        } else {
            cartItemRepository.save(cartItem); // Update cart item in repository
        }

        return cartItem;
    }

}

/*
        {
         "id": 5,
         "name": "Croissant",
         "description": "Buttery and flaky French pastry",
         "price": 2.0,
         "imageUrl": "images/croissant.jpg",
         "available": true,
         "categoryName": "Pastries"
         }


 */

