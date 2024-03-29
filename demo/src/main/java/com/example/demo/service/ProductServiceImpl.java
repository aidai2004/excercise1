package com.example.demo.service;

import com.example.demo.dto.ProductRequest;
import com.example.demo.dto.ProductResponse;
import com.example.demo.dto.SimpleResponse;
import com.example.demo.entity.Category;
import com.example.demo.entity.Discount;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.DiscountRepository;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productsList = new ArrayList<>();
        for (Product product : products) {
            productsList.add(mapToResponse(product));
        }
        return productsList;
    }

    @Override
    public ProductResponse save(ProductRequest productRequest) {
        Product product = mapToEntity(productRequest);
        productRepository.save(product);
        return mapToResponse(product);
    }

    @Override
    public ProductResponse savePriceAndQuantity(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).get();
        product.setPrice(productRequest.getPrice());
        product.setDiscountId(productRequest.getDiscountId());
        productRepository.save(product);
        return mapToResponse(product);

    }

    @Override
    public ProductResponse saveDescription(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).get();
        product.setPDF(productRequest.getPDF());
        product.setImage(productRequest.getImage());
        product.setDescription(productRequest.getDescription());
        productRepository.save(product);
        return mapToResponse(product);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).get();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setBrand(productRequest.getBrand());
        product.setColor(productRequest.getColor());
        product.setDateOfIssue(productRequest.getDateOfIssue());
        product.setOs(productRequest.getOs());
        product.setRam(productRequest.getRam());
        product.setRom(productRequest.getRom());
        product.setSim(productRequest.getSim());
        product.setQuantityOfSim(productRequest.getQuantityOfSim());
        product.setCpu(productRequest.getCpu());
        product.setWeight(productRequest.getWeight());
        product.setGuarantee(productRequest.getGuarantee());
        product.setImage(productRequest.getImage());
        product.setDisplayInch(productRequest.getDisplayInch());
        product.setAppointment(productRequest.getAppointment());
        product.setCapacityBattery(productRequest.getCapacityBattery());
        if (productRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(productRequest.getCategoryId()).get();
            product.setCategory(category);
        }
        product.setPDF(productRequest.getPDF());
        product.setDescription(productRequest.getDescription());
        productRepository.saveAndFlush(product);
        return mapToResponse(product);
    }

    @Override
    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id).get();
        return mapToResponse(product);
    }

    @Override
    public SimpleResponse delete(Long id) {
        SimpleResponse productDeleteResponse = new SimpleResponse();
        Boolean exists1 = productRepository.existsById(id);
        Product product = new Product();
        if (exists1) {
            product = productRepository.findById(id).get();
        }
        if (product.getId() == id) {
            productRepository.delete(product);
            productDeleteResponse.setHttpStatus(HttpStatus.OK);
            productDeleteResponse.setMessage("the product with this id: " + product.getId() + " was deleted");
        } else {
            productDeleteResponse.setHttpStatus(HttpStatus.NOT_FOUND);
            productDeleteResponse.setMessage("the product's id is " + product.getId());
        }
        return productDeleteResponse;
    }

    private Product mapToEntity(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setBrand(productRequest.getBrand());
        product.setColor(productRequest.getColor());
        product.setDateOfIssue(productRequest.getDateOfIssue());
        product.setOs(productRequest.getOs());
        product.setRam(productRequest.getRam());
        product.setRom(productRequest.getRom());
        product.setSim(productRequest.getSim());
        product.setQuantityOfSim(productRequest.getQuantityOfSim());
        product.setCpu(productRequest.getCpu());
        product.setWeight(productRequest.getWeight());
        product.setGuarantee(productRequest.getGuarantee());
        product.setDisplayInch(productRequest.getDisplayInch());
        product.setAppointment(productRequest.getAppointment());
        product.setCapacityBattery(productRequest.getCapacityBattery());
        if (productRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(productRequest.getCategoryId()).get();
            product.setCategory(category);
        }
        if(productRequest.getDiscountId() != null){
            Discount discount = discountRepository.findById(productRequest.getDiscountId()).get();
            product.setDiscount(discount);
        }
        return product;
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
        //  productResponse.setDiscountPercent(product.getDiscount().getPercent());
        return productResponse;
    }
}
