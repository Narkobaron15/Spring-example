package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_product_images")
public class ProductImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private int priority;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
