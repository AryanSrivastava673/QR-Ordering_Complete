package com.aryansrivastava.qrOrdering.QrOrdering.service;

import com.aryansrivastava.qrOrdering.QrOrdering.dto.OrderDTO;
import com.aryansrivastava.qrOrdering.QrOrdering.dto.OrderItemDTO;
import com.aryansrivastava.qrOrdering.QrOrdering.model.CartItem;
import com.aryansrivastava.qrOrdering.QrOrdering.model.Order;
import com.aryansrivastava.qrOrdering.QrOrdering.model.OrderItem;
import com.aryansrivastava.qrOrdering.QrOrdering.model.RestTable;
import com.aryansrivastava.qrOrdering.QrOrdering.repository.CartItemRepository;
import com.aryansrivastava.qrOrdering.QrOrdering.repository.OrderItemRepository;
import com.aryansrivastava.qrOrdering.QrOrdering.repository.OrderRepository;
import com.aryansrivastava.qrOrdering.QrOrdering.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private TableService tableService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private TableRepository tableRepository;

    public List<OrderDTO> getAllOrders(){
        List<Order> orders=orderRepository.findAll();
        List<OrderDTO> orderDtos= new ArrayList<OrderDTO>();
        for(Order order:orders){
            if(!order.isDone()) {
                OrderDTO orderDTO = new OrderDTO();
                List<OrderItemDTO> orderItemDtos = new ArrayList<>();
                for (OrderItem orderItem : order.getOrderItems()) {
                    orderItemDtos.add(convertToOrderItemDTO(orderItem));
                }
                orderDTO.setId(order.getId());
                orderDTO.setTableId(order.getTableId());
                orderDTO.setOrderItems(orderItemDtos);
                orderDtos.add(orderDTO);
            }
        }
        return orderDtos;
    }
    public List<OrderDTO> getOrdersForTable(String tableNo){
        List<Order> orders=orderRepository.findByTable(tableNo);
        List<OrderDTO> orderDtos= new ArrayList<OrderDTO>();
        for(Order order:orders){
                OrderDTO orderDTO = new OrderDTO();
                List<OrderItemDTO> orderItemDtos = new ArrayList<>();
                for (OrderItem orderItem : order.getOrderItems()) {
                    orderItemDtos.add(convertToOrderItemDTO(orderItem));
                }
                orderDTO.setId(order.getId());
                orderDTO.setTableId(order.getTableId());
                orderDTO.setOrderItems(orderItemDtos);
                orderDTO.setDone(order.isDone());
                orderDtos.add(orderDTO);
        }
        return orderDtos;
    }

    public OrderItemDTO convertToOrderItemDTO(OrderItem orderItem){
        OrderItemDTO orderItemDTO=new OrderItemDTO();
        orderItemDTO.setId(orderItem.getId());
        orderItemDTO.setMenuItemId(orderItem.getMenuItem().getId());
        orderItemDTO.setMenuItemName(orderItem.getMenuItem().getName());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        return orderItemDTO;
    }

    public boolean placeOrder(String tableNo){
        RestTable table = tableRepository.findById(tableNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Table not found."));
        List<CartItem> cartItems=table.getCartItems();
        List<OrderItem> orderItems=new ArrayList<OrderItem>();
        Order order =new Order();
        while(!cartItems.isEmpty()){
            CartItem cartItem=cartItems.getFirst();
            OrderItem orderItem=new OrderItem();
            orderItem.setMenuItem(cartItem.getMenuItem());
            orderItem.setQuantity(cartItem.getQuantity());

            table.getCartItems().remove(cartItem);
            cartItemRepository.delete(cartItem); // Assuming existence of cartItemRepository

            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
        }
        order.setTableId(tableNo);
        order.setOrderItems(orderItems);
        order.setDone(false);
        orderRepository.save(order);
        return true;
    }

    public boolean orderIsDone(long orderId){
        Order order=orderRepository.findById(orderId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found."));

        order.setDone(true);
        orderRepository.save(order);
        return true;
    }

    public boolean deleteOrdersForTable(String tableNo){

        List<Order> orders=orderRepository.findByTable(tableNo);
        while(!orders.isEmpty()){
            Order order=orders.removeFirst();
            orderRepository.delete(order);
        }
        return true;
    }

}

