package com.example.demo.service;

import com.example.demo.dto.DiscountRequest;
import com.example.demo.dto.DiscountResponse;
import com.example.demo.dto.ProductResponse;
import com.example.demo.dto.SimpleResponse;
import com.example.demo.entity.Discount;
import com.example.demo.entity.Product;
import com.example.demo.repository.DiscountRepository;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;
    @Override
    public List<DiscountResponse> getAllDiscounts() {
        List<Discount>discounts = discountRepository.findAll();
        List<DiscountResponse>responses = new ArrayList<>();
        for(Discount discount : discounts){
            responses.add(mapToResponse(discount));
        }
        return responses;
    }

    @Override
    public DiscountResponse save(DiscountRequest discountRequest) {
        Discount discount = mapToEntity(discountRequest);
        discountRepository.save(discount);
        return mapToResponse(discount);
    }

    @Override
    public DiscountResponse update(Long id, DiscountRequest discountRequest) {
        Discount discount = new Discount();
        discount.setPercent(discountRequest.getPercent());
        discount.setDateOfStart(discountRequest.getDateOfStart());
        discount.setDateOfFinish(discountRequest.getDateOfFinish());
        discountRepository.save(discount);
        return mapToResponse(discount);
    }

    @Override
    public DiscountResponse getById(Long id) {
        Discount discount = discountRepository.findById(id).get();
        return mapToResponse(discount);
    }

    @Override
    public SimpleResponse delete(Long id) {
        SimpleResponse discountDeleteResponse = new SimpleResponse();
        Boolean exists1 = discountRepository.existsById(id);
        Discount discount = new Discount();
        if(LocalDate.now().isAfter(discount.getDateOfFinish())){
            if (exists1) {
                discount = discountRepository.findById(id).get();
            }
            if (discount.getId() == id) {
                discountRepository.delete(discount);
                discountDeleteResponse.setHttpStatus(HttpStatus.OK);
                discountDeleteResponse.setMessage("the discount with this id: " + discount.getId() + " was deleted");
            } else {
                discountDeleteResponse.setHttpStatus(HttpStatus.NOT_FOUND);
                discountDeleteResponse.setMessage("the discount's id is " + discount.getId());
            }
        }
        return discountDeleteResponse;
    }

    @Override
    public List<ProductResponse> getProductsByDiscountId(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        List<Product>products = discountRepository.getProductsByDiscountId(id,pageable);
        List<ProductResponse>productResponses = new ArrayList<>();
        for(Product product : products){
            productResponses.add(mapToResponse(product));
        }
        return productResponses;
    }


    //discount logics
    private Discount mapToEntity(DiscountRequest request){
        Discount discount = new Discount();
        discount.setPercent(request.getPercent());
        discount.setDateOfStart(request.getDateOfStart());
        discount.setDateOfFinish(request.getDateOfFinish());
        return discount;
    }

    private DiscountResponse mapToResponse(Discount discount){
        DiscountResponse discountResponse = new DiscountResponse();
        discountResponse.setId(discount.getId());
        discountResponse.setPercent(discount.getPercent());
        discountResponse.setDateOfStart(discount.getDateOfStart());
        discountResponse.setDateOfFinish(discount.getDateOfFinish());
        return discountResponse;
    }
    private ProductResponse mapToResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setBrand(product.getBrand());
        productResponse.setColor(product.getColor());
        productResponse.setDateOfIssue(product.getDateOfIssue());
        productResponse.setOs(product.getOs());
        productResponse.setRam(product.getRam());
        productResponse.setRom(product.getRom());
        productResponse.setSim(product.getSim());
        productResponse.setQuantityOfSim(product.getQuantityOfSim());
        productResponse.setCpu(product.getCpu());
        productResponse.setWeight(product.getWeight());
        productResponse.setGuarantee(product.getGuarantee());
        productResponse.setImage(product.getImage());
        productResponse.setDisplayInch(product.getDisplayInch());
        productResponse.setAppointment(product.getAppointment());
        productResponse.setCapacityBattery(product.getCapacityBattery());
        productResponse.setDescription(product.getDescription());
        productResponse.setPDF(product.getPDF());
        productResponse.setQuantityOfProducts(productRepository.Quantity(product.getBrand(),
                product.getColor(), product.getRam(),
                product.getQuantityOfSim(), product.getPrice()));
        productResponse.setCurrentPrice(product.getPrice()- product.getPrice()*(product.getDiscount().getPercent()*100));
        return productResponse;
    }
}
