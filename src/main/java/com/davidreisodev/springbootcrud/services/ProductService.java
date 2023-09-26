package com.davidreisodev.springbootcrud.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.davidreisodev.springbootcrud.controllers.ProductController;
import com.davidreisodev.springbootcrud.dtos.ProductRecordDto;
import com.davidreisodev.springbootcrud.models.ProductModel;
import com.davidreisodev.springbootcrud.repositories.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class ProductService {

    private static final String PRODUCT_LIST = "Lista de Produtos";


    @Autowired
    ProductRepository productRepository;

    public Optional<ProductModel> addProduct(ProductRecordDto product){
        var productModel = new ProductModel();
        BeanUtils.copyProperties(product,productModel);
        Optional<ProductModel> productOptional = Optional.of(productRepository.save(productModel));
        productOptional.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel(PRODUCT_LIST));

        return productOptional;
    }

    public List<ProductModel> getAllProducts(){
        List<ProductModel> productList = productRepository.findAll();

        if(!productList.isEmpty()){
            for(ProductModel product:productList){
                UUID id = product.getIdProduct();
                product.add(linkTo(methodOn(ProductController.class).getProductById(id)).withSelfRel());
            }
        }

        return productList;
    }

    public Optional<ProductModel> getProductById(UUID id){        
        Optional<ProductModel> product = productRepository.findById(id);
        
        if(product.isEmpty()){
            return product;
        }
        product.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel(PRODUCT_LIST));
        return product;
    }

    public Optional<ProductModel> updateProductById(UUID id, ProductRecordDto product){
        Optional<ProductModel> productModel = productRepository.findById(id);
        
        if(productModel.isEmpty()){
            return productModel;
        }

        var newProductModel = productModel.get();
        BeanUtils.copyProperties(product, newProductModel);
        Optional<ProductModel> productUpdated = Optional.of(productRepository.save(newProductModel));
        productUpdated.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel(PRODUCT_LIST));

        return productUpdated;
    }

    public Optional<ProductModel> deleteProductById(UUID id){
        Optional<ProductModel> product = productRepository.findById(id);
        
        if(product.isEmpty()){
            return product;
        }
        productRepository.delete(product.get());

        return product;
    }
}
