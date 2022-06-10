package com.example.Ecommerce.service.Imp;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.converter.AuthorConverter;
import com.example.Ecommerce.dto.AuthorDto;
import com.example.Ecommerce.exception.*;
import com.example.Ecommerce.model.Author;
import com.example.Ecommerce.repository.AuthorRepository;
import com.example.Ecommerce.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorServiceImpl.class);

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    AuthorConverter authorConverter;

    @Override
    public List<AuthorDto> getAuthorListToShow() {
        List<AuthorDto> listDTO;
        try {
            List<Author> author = authorRepository.getAllAuthors();
            listDTO = authorConverter.toDTOList(author);
        } catch (Exception e) {
            LOGGER.info("Having error when load list author" + e.getMessage());
            throw new DataNotFoundException(ErrorCode.ERR_BOOK_LIST_LOADED_FAIL);
        }
        return listDTO;
    }


    @Override
    public Boolean addNewAuthor(AuthorDto authorDto) throws CreateDataFailException {
        boolean result;
        try {
            Author author = authorConverter.convertAuthorDTOToEntity(authorDto);
            Author tempAuthor = authorRepository.checkExistedAuthor(author.getAuthorName());
            if (tempAuthor != null) {
                LOGGER.info("Author have been existed ");
                throw new DuplicateDataException(ErrorCode.ERR_AUTHOR_EXISTED);
            }
            authorRepository.save(author);
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when create new Author: " + e.getMessage());
            throw new CreateDataFailException(ErrorCode.ERR_CREATE_AUTHOR_FAIL);
        }
        return result;
    }

    @Override
    public Boolean updateAuthor(AuthorDto authorDto) throws DataNotFoundException, UpdateDataFailException {
        boolean result;
        try {
            Author author = authorConverter.convertAuthorDTOToEntity(authorDto);
            Author tempAuthor = authorRepository.checkExistedAuthorId(author.getId());
            if (tempAuthor == null) {
                LOGGER.info("Can't find author ");
                throw new DataNotFoundException(ErrorCode.ERR_AUTHOR_NOT_FOUND);
            }
            authorRepository.save(author);
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when update author: " + e.getMessage());
            throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_AUTHOR_FAIL);
        }
        return result;
    }

    @Override
    public Boolean deleteAuthor(Long id) throws DataNotFoundException, DeleteDataFailException {
        boolean result;
        try {
            Optional<Author> authorOptional = authorRepository.findById(id);
            if (!authorOptional.isPresent()) {
                LOGGER.info("Can't find author ");
                throw new DataNotFoundException(ErrorCode.ERR_AUTHOR_NOT_FOUND);
            }
            authorRepository.deleteAuthorId(id);
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when delete author " + e.getMessage());
            throw new DeleteDataFailException(ErrorCode.ERR_DELETE_AUTHOR_FAIL);
        }
        return result;
    }

}
