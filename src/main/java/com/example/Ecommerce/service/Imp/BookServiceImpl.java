package com.example.Ecommerce.service.Imp;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.converter.BookConverter;
import com.example.Ecommerce.dto.BookDto;
import com.example.Ecommerce.dto.DetailBookDto;
import com.example.Ecommerce.exception.*;
import com.example.Ecommerce.model.Book;
import com.example.Ecommerce.repository.BookRepository;
import com.example.Ecommerce.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookConverter bookConverter;

    @Override
    public List<BookDto> getBookListToShow() {
        List<BookDto> listDTO;
        try {
            List<Book> book = bookRepository.getAllBooks();
            listDTO = bookConverter.toDTOList(book);
        } catch (Exception e) {
            LOGGER.info("Having error when load list book " + e.getMessage());
            throw new DataNotFoundException(ErrorCode.ERR_BOOK_LIST_LOADED_FAIL);
        }
        return listDTO;
    }

    @Override
    public Boolean addNewBook(BookDto bookDto) throws CreateDataFailException {
        boolean result;
        try {
            Book book = bookConverter.convertUpdateCreateBookDTOToEntity(bookDto);
            Book tempBook = bookRepository.checkExistedBook(book.getBookName());
            if (tempBook != null) {
                LOGGER.info("Book have been existed ");
                throw new DuplicateDataException(ErrorCode.ERR_BOOK_EXISTED);
            }
            bookRepository.save(book);
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when create new book: " + e.getMessage());
            throw new CreateDataFailException(ErrorCode.ERR_CREATE_BOOK_FAIL);
        }
        return result;
    }

    @Override
    @Transactional
    public Boolean updateBook(BookDto bookDto) throws DataNotFoundException, UpdateDataFailException {
        boolean result;
        try {
            Book book = bookConverter.convertUpdateCreateBookDTOToEntity(bookDto);
            Book tempBook = bookRepository.checkExistedBookId(book.getId());
            if (tempBook == null) {
                LOGGER.info("Can't find book ");
                throw new DataNotFoundException(ErrorCode.ERR_BOOK_NOT_FOUND);
            }
            bookRepository.save(book);
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when update book: " + e.getMessage());
            throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_BOOK_FAIL);
        }
        return result;
    }

    @Override
    public Boolean deleteBook(Long id) throws DataNotFoundException, DeleteDataFailException {
        boolean result;
        try {
            Optional<Book> bookOptional = bookRepository.findById(id);
            if (!bookOptional.isPresent()) {
                LOGGER.info("Can't find book ");
                throw new DataNotFoundException(ErrorCode.ERR_BOOK_NOT_FOUND);
            }
            bookRepository.deleteBookId(id);
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when delete organization " + e.getMessage());
            throw new DeleteDataFailException(ErrorCode.ERR_DELETE_BOOK_FAIL);
        }
        return result;
    }

    @Override
    public DetailBookDto getBookDetailByID(Long id) throws DataNotFoundException {
        DetailBookDto dto;
        try {
            Book book = bookRepository.checkExistedBookId(id);
            if (book == null) {
                LOGGER.info("Can't find book with the book id: " + id);
                throw new DataNotFoundException(ErrorCode.ERR_BOOK_NOT_FOUND);
            } else {
                dto = bookConverter.convertBookToDTO(book);
            }
        } catch (Exception e) {
            LOGGER.info("Having error when load the book: " + e.getMessage());
            throw new DataNotFoundException(ErrorCode.ERR_BOOK_LOADED_FAIL);
        }
        return dto;
    }

    @Override
    public Book getBookToAdd(Long id) throws DataNotFoundException {
        Book book;
        try {
            book = bookRepository.checkBookToAddToCart(id);
        } catch (Exception e) {
            LOGGER.info("Having error when find product " + e.getMessage());
            throw new DataNotFoundException(ErrorCode.ERR_BOOK_NOT_FOUND);
        }
        return book;
    }

}
