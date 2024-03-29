package com.example.demo.controller;

import com.example.demo.dto.DiscountRequest;
import com.example.demo.dto.DiscountResponse;
import com.example.demo.dto.ProductResponse;
import com.example.demo.dto.SimpleResponse;
import com.example.demo.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/discount")
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService discountService;

    @GetMapping
    public List<DiscountResponse> discounts(){
        return discountService.getAllDiscounts();
    }

    @PostMapping
    public DiscountResponse save(@RequestBody DiscountRequest discountRequest){
        return discountService.save(discountRequest);
    }

    @PutMapping("{id}")
    public DiscountResponse update(@PathVariable("id") Long id, @RequestBody DiscountRequest discountRequest){
        return discountService.update(id, discountRequest);
    }

    @GetMapping("{id}")
    public DiscountResponse getById(@PathVariable("id") Long id){
        return discountService.getById(id);
    }

    @DeleteMapping("{id}")
    public SimpleResponse delete(@PathVariable("id") Long id){
        return discountService.delete(id);
    }

    @GetMapping("/products/{id}")
    public List<ProductResponse> getProducts(@PathVariable(name = "id", required = false) Long id, @RequestParam(value = "page",required = false)
    int page, @RequestParam(name = "size", required = false) int size){
     return discountService.getProductsByDiscountId(id,page,size);
    }
}
