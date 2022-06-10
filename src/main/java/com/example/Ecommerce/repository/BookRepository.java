package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "SELECT * FROM book",nativeQuery = true)
    List<Book> getAllBooks();

    @Query(
            value = "SELECT * FROM book where book_name = ?1",
            nativeQuery = true
    )
    Book checkExistedBook(String bookName);

    @Query(
            value = "SELECT * FROM book where ID = ?1",
            nativeQuery = true
    )
    Book checkExistedBookId(Long id);

    @Modifying
    @Transactional
    @Query(
            value = "delete from book where ID = ?1",
            nativeQuery = true
    )
    int deleteBookId(Long id);


    @Query(
            value = "select * from book where ID = ?1 and quantity > 0",
            nativeQuery = true
    )
    Book checkBookToAddToCart(Long id);

}