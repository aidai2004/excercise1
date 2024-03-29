package com.example.demo.entity;

import com.example.demo.entity.enums.Brand;
import com.example.demo.entity.enums.Color;
import com.example.demo.entity.enums.OS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private int currentPrice;
    @Enumerated(EnumType.STRING)
    private Brand brand;

    @Enumerated(EnumType.STRING)
    private Color color;

    private String dateOfIssue;

    @Enumerated(EnumType.STRING)
    private OS os;

    private String ram;
    private String rom;
    private String sim;
    private String cpu;
    private String weight;
    private String guarantee;
    private String image;
    private String displayInch;
    private String appointment;
    private String PDF;
    private String description;

    private Long quantityOfSim;
    @Column(name = "capacity_battery")
    private String capacityBattery;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Transient
    Long categoryId;

    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;


    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;


    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne
    @JoinColumn(name = "order_list_id")
    private OrderList orderList;

    @ManyToOne
    @JoinColumn(name = "chosen_id")
    private Chosen chosen;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @Transient
    private Long discountId;
}