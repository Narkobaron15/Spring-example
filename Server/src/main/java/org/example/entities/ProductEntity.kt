package org.example.entities

import jakarta.persistence.*
import lombok.Getter
import lombok.RequiredArgsConstructor
import lombok.Setter
import lombok.ToString

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "tbl_products")
class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null
    private val name: String? = null
    private val price: Double? = null
    private val description: String? = null

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @ToString.Exclude
    private val productImages: List<ProductImageEntity>? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private val category: CategoryEntity? = null
}
