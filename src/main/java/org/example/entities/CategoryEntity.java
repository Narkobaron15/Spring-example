package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data @Entity @Table(name = "tbl_categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", length = 200, nullable = false)
    private String name;
    @Column(name = "image_path", length = 400, nullable = false)
    private String imageURL;
    @Column(name = "description", length = 1000 /*, nullable = true*/)
    private String description;

    /* one-to-many relationship for products
    @OneToMany
    private List<ProductEntity> productEntities;
    */
}
