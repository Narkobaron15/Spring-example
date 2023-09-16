package org.example.repositories

import org.example.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepo : JpaRepository<UserEntity, Long> {
    fun findByUsername(uname: String) : Optional<UserEntity>
}