package org.example.entities

import jakarta.persistence.*
import lombok.RequiredArgsConstructor

@RequiredArgsConstructor
@Entity
@Table(name = "tbl_products")
class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    lateinit var name: String

    var price: Double? = null

    lateinit var description: String

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    lateinit var productImages: List<ProductImageEntity>

    @ManyToOne(fetch = FetchType.LAZY)
    lateinit var category: CategoryEntity
}
