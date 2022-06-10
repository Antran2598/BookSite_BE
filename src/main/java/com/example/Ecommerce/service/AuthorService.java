package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.AuthorDto;
import com.example.Ecommerce.exception.CreateDataFailException;
import com.example.Ecommerce.exception.DataNotFoundException;
import com.example.Ecommerce.exception.DeleteDataFailException;
import com.example.Ecommerce.exception.UpdateDataFailException;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAuthorListToShow();

    Boolean addNewAuthor(AuthorDto authorDto) throws CreateDataFailException;

    Boolean updateAuthor(AuthorDto authorDto) throws DataNotFoundException, UpdateDataFailException;

    Boolean deleteAuthor(Long id) throws DataNotFoundException, DeleteDataFailException;
}
