package org.example.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity @Table(name = "tbl_categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", length = 200, nullable = false, unique = true)
    private String name;
    @Column(name = "image_path", length = 400, nullable = false)
    private String image;
    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    // one-to-many relationship for products
    @OneToMany
    @ToString.Exclude
    @JoinColumn(name = "category_id")
    private List<ProductEntity> productEntities;
}
