package org.example.repositories

import org.example.entities.category.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepo : JpaRepository<CategoryEntity, Int>
