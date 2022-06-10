package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.ERole;
import com.example.Ecommerce.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

    @Modifying
    @Transactional
    @Query(
            value = "insert into user_role(user_id, role_id) values( ?1 , ?2 )",
            nativeQuery = true
    )
    int addNewAccountRole(Long accountID, Long roleID);

    @Modifying
    @Transactional
    @Query(
            value = "delete from user_role where user_id = ?1 and role_id = ?2",
            nativeQuery = true
    )
    int deleteAccountRole(Long accountID, Long roleID);
}