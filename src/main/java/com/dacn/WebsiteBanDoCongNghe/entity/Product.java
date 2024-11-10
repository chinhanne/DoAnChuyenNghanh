package com.dacn.WebsiteBanDoCongNghe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    double price;
    double priceSale;
    String description;
    LocalDate dateCreated;
    LocalDate dateUpdated;
    Long quantity;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<Image> images;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<Comment> comments = new ArrayList<>();
    String chip;
    String card;
    String ram;
    String screen;

//    Khởi tạo thời gian tạo sản phẩm
    @PrePersist
    public void prePersist() {
        this.dateCreated = LocalDate.now();
        this.dateUpdated = LocalDate.now();
    }

//    Khởi tạo thời gian update sản phẩm
    @PreUpdate
    public void preUpdate() {
        this.dateUpdated = LocalDate.now();
    }
}
