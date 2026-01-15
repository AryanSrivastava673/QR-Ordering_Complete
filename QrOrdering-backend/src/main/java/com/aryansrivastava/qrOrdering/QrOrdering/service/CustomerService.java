package com.aryansrivastava.qrOrdering.QrOrdering.service;

import com.aryansrivastava.qrOrdering.QrOrdering.dto.CustomerRequestDTO;
import com.aryansrivastava.qrOrdering.QrOrdering.dto.CustomerResponseDTO;
import com.aryansrivastava.qrOrdering.QrOrdering.exception.DuplicateResourceException;
import com.aryansrivastava.qrOrdering.QrOrdering.model.Customer;
import com.aryansrivastava.qrOrdering.QrOrdering.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CustomerResponseDTO createCustomer(CustomerRequestDTO request) {
        // Check duplicate
        if (customerRepository.existsByMobile(request.getMobile())) {
            throw new DuplicateResourceException("Customer with this mobile already exists");
        }

        Customer customer = modelMapper.map(request, Customer.class);
        Customer saved = customerRepository.save(customer);
        CustomerResponseDTO response = modelMapper.map(saved, CustomerResponseDTO.class);
        return response;
    }
}

