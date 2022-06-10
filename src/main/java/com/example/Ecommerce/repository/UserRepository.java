package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String username);

    Optional<User> findByUserNameAndPassword(String username, String password);

    Boolean existsByUserName(String username);

    @Modifying
    @Transactional
    @Query(
            value = "Update user set status = 2 where ID = ?1",
            nativeQuery = true
    )
    int updateAccountStatusToActive(Long id);

    @Modifying
    @Transactional
    @Query(
            value = "Update user set status = 3 where ID = ?1",
            nativeQuery = true
    )
    int updateAccountStatusToLocked(Long id);

    @Query(
            value = "Select * from user where username = ?1",
            nativeQuery = true
    )
    User getForForgotPassword(String username);


}