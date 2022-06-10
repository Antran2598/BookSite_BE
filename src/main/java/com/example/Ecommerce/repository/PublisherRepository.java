package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.Author;
import com.example.Ecommerce.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    @Query(value = "SELECT * FROM publisher",nativeQuery = true)
    List<Publisher> getAllPublishers();

    @Query(
            value = "SELECT * FROM publisher where pub_name = ?1",
            nativeQuery = true
    )
    Publisher checkExistedPublisher(String pubName);

    @Query(
            value = "SELECT * FROM publisher where ID = ?1",
            nativeQuery = true
    )
    Publisher checkExistedPublisherId(Long id);

    @Modifying
    @Transactional
    @Query(
            value = "delete from publisher where ID = ?1",
            nativeQuery = true
    )
    int deletePublisherId(Long id);
}