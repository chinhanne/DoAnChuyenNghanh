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
    Double priceSale;
    String description;
    LocalDate dateCreated;
    LocalDate dateUpdated;
    Long quantity;
    String status;
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
    Boolean isDelete = false;
    Boolean isBrandVisible = true;
    Boolean isCategoryVisible = true;

//    Khởi tạo thời gian tạo sản phẩm
    @PrePersist
    public void prePersist() {
        this.dateCreated = LocalDate.now();
        this.dateUpdated = LocalDate.now();
        this.priceSale = 0.0;
        this.isDelete = false;
        this.isBrandVisible = true;
        this.isCategoryVisible = true;
        updateStatusBasedOnQuantity();
    }

//    Khởi tạo thời gian update sản phẩm
    @PreUpdate
    public void preUpdate() {
        this.dateUpdated = LocalDate.now();
        updateStatusBasedOnQuantity();
    }

    public void updateStatusBasedOnQuantity() {
        if (this.quantity != null && this.quantity > 0) {
            this.status = "Còn Hàng";
        } else {
            this.status = "Hết Hàng";
        }
    }
}
