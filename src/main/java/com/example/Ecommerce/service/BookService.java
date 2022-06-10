package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.BookDto;
import com.example.Ecommerce.dto.DetailBookDto;
import com.example.Ecommerce.exception.CreateDataFailException;
import com.example.Ecommerce.exception.DataNotFoundException;
import com.example.Ecommerce.exception.DeleteDataFailException;
import com.example.Ecommerce.exception.UpdateDataFailException;
import com.example.Ecommerce.model.Book;

import java.util.List;

public interface BookService {
    List<BookDto> getBookListToShow();

    Boolean addNewBook(BookDto bookDto) throws CreateDataFailException;

    Boolean updateBook(BookDto bookDto) throws DataNotFoundException, UpdateDataFailException;

    Boolean deleteBook(Long id) throws DataNotFoundException, DeleteDataFailException;

    DetailBookDto getBookDetailByID(Long id) throws DataNotFoundException;

    Book getBookToAdd(Long id) throws DataNotFoundException;
}
