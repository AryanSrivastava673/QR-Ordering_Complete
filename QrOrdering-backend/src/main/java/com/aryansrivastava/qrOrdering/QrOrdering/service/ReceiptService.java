package com.aryansrivastava.qrOrdering.QrOrdering.service;

import com.aryansrivastava.qrOrdering.QrOrdering.dto.ReceiptDTO;
import com.aryansrivastava.qrOrdering.QrOrdering.dto.ReceiptItemDTO;
import com.aryansrivastava.qrOrdering.QrOrdering.model.*;
import com.aryansrivastava.qrOrdering.QrOrdering.repository.OrderRepository;
import com.aryansrivastava.qrOrdering.QrOrdering.repository.ReceiptItemRepository;
import com.aryansrivastava.qrOrdering.QrOrdering.repository.ReceiptRepository;
import com.aryansrivastava.qrOrdering.QrOrdering.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReceiptService {

    @Autowired
    private TableService tableService;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private ReceiptItemRepository receiptItemRepository;

    @Autowired
    private OrderService orderService;

    public ReceiptDTO createReceipt(String tableNo){
        ReceiptDTO receiptDTO=new ReceiptDTO();
        Receipt receipt =new Receipt();
        RestTable table=tableRepository.getById(tableNo);
        List<ReceiptItem> receiptItems = new ArrayList<>();

        List<Order> orders= orderRepository.findByTable(tableNo);
        double itemsTotal=0;
        for(Order order:orders){
            for(OrderItem orderItem:order.getOrderItems()){
                ReceiptItem receiptItem=createReceiptItem(orderItem);
                receiptItemRepository.save(receiptItem);
                itemsTotal=itemsTotal+receiptItem.getMenuItem().getPrice()*receiptItem.getQuantity();
                receiptItems.add(receiptItem);
            }
        }
        double tax=itemsTotal*0.1;
        receipt.setTax(tax);
        receipt.setTableNo(tableNo);
        receipt.setItemsTotal(itemsTotal);
        receipt.setGrandTotal(itemsTotal+tax);
        receipt.setReceiptItems(receiptItems);

        receiptRepository.save(receipt);

        receiptDTO=convertReceiptToReceiptDTO(receipt);

        orderService.deleteOrdersForTable(tableNo);

        return receiptDTO;

    }

    public ReceiptDTO convertReceiptToReceiptDTO(Receipt receipt){
        ReceiptDTO receiptDTO=new ReceiptDTO();
        receiptDTO.setId(receipt.getId());
        receiptDTO.setTax(receipt.getTax());
        receiptDTO.setGrandTotal(receipt.getGrandTotal());
        receiptDTO.setItemsTotal(receipt.getItemsTotal());

        List<ReceiptItemDTO> receiptItemDTOS=new ArrayList<>();

        for(ReceiptItem receiptItem: receipt.getReceiptItems()){
            receiptItemDTOS.add(receiptItemToDTO(receiptItem));
        }
        receiptDTO.setReceiptItemDTOList(receiptItemDTOS);
        return receiptDTO;
    }

    public ReceiptItemDTO receiptItemToDTO(ReceiptItem receiptItem){
        ReceiptItemDTO receiptItemDTO=new ReceiptItemDTO();
        receiptItemDTO.setId(receiptItem.getId());;
        receiptItemDTO.setMenuItemName(receiptItem.getMenuItem().getName());
        receiptItemDTO.setQuantity(receiptItem.getQuantity());
        return receiptItemDTO;
    }

    public ReceiptItem createReceiptItem(OrderItem orderItem){
        ReceiptItem receiptItem=new ReceiptItem();
        receiptItem.setMenuItem(orderItem.getMenuItem());
        receiptItem.setQuantity(orderItem.getQuantity());
        return receiptItem;
    }
}
