package org.example.repositories

import org.example.entities.product.ProductImageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductImageRepo : JpaRepository<ProductImageEntity, Long>