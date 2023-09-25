package com.davidreisodev.springbootcrud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davidreisodev.springbootcrud.repositories.ProductRepository;

@RestController
public class ProductController {
    
    @Autowired
    ProductRepository productRepository;

}
