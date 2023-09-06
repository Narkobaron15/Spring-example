package org.example.entities

import jakarta.persistence.*

@Entity
@Table(name = "product_image")
class ProductImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    lateinit var filename: String

    var priority: Int = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    lateinit var product: ProductEntity
}
