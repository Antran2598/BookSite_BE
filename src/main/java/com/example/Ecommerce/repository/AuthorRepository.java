package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.Author;
import com.example.Ecommerce.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query(value = "SELECT * FROM author",nativeQuery = true)
    List<Author> getAllAuthors();

    @Query(
            value = "SELECT * FROM author where author_name = ?1",
            nativeQuery = true
    )
    Author checkExistedAuthor(String authorName);

    @Query(
            value = "SELECT * FROM author where ID = ?1",
            nativeQuery = true
    )
    Author checkExistedAuthorId(Long id);

    @Modifying
    @Transactional
    @Query(
            value = "delete from author where ID = ?1",
            nativeQuery = true
    )
    int deleteAuthorId(Long id);

}