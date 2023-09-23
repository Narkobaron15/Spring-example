package org.example.entities.product;

import jakarta.persistence.*;
import lombok.*;
import org.example.entities.category.CategoryEntity;

import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tbl_products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 250, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "description", length = 5000, nullable = true)
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImageEntity> productImages;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
}
