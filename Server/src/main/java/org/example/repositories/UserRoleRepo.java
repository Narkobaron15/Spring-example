package org.example.repositories;

import org.example.entities.user.UserEntity;
import org.example.entities.user.UserRoleEntity;
import org.example.entities.user.UserRolePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRoleEntity, UserRolePK> {
    List<UserRoleEntity> findByUser(UserEntity User);
}
