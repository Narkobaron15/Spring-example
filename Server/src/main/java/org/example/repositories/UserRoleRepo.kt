package org.example.repositories

import org.example.entities.UserEntity
import org.example.entities.UserRoleEntity
import org.example.entities.UserRolePK
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserRoleRepo : JpaRepository<UserRoleEntity, UserRolePK> {
    fun findByUser(user: UserEntity): List<UserRoleEntity>
}
