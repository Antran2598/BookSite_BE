package com.example.Ecommerce.converter;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.dto.BookDto;
import com.example.Ecommerce.dto.DetailBookDto;
import com.example.Ecommerce.exception.ConvertEntityDTOException;
import com.example.Ecommerce.exception.DataNotFoundException;
import com.example.Ecommerce.model.Author;
import com.example.Ecommerce.model.Book;
import com.example.Ecommerce.model.Category;
import com.example.Ecommerce.model.Publisher;
import com.example.Ecommerce.repository.AuthorRepository;
import com.example.Ecommerce.repository.CategoryRepository;
import com.example.Ecommerce.repository.PublisherRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookConverter.class);
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public DetailBookDto convertBookToDTO(Book book) {
        try {
            DetailBookDto detailBookDto = modelMapper.map(book, DetailBookDto.class);
            return detailBookDto;
        } catch (Exception ex) {
            LOGGER.info("Fail to convert Book to BookDto Reason: " + ex.getMessage());
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }

//    public Book convertBookDTOToEntity(BookDto dtoBook) {
//        try {
//            Book book = modelMapper.map(dtoBook, Book.class);
//            return book;
//        } catch (Exception e) {
//            LOGGER.info("Fail to convert BookDTO to Book");
//            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
//        }
//    }

    public Book convertUpdateCreateBookDTOToEntity(BookDto dtoBook) {
        try {
            Author author;
            Publisher publisher;
            Category category;
            Book book = modelMapper.map(dtoBook, Book.class);
            Optional<Author> authorOptional = authorRepository.findById(dtoBook.getAuthor());
            if (authorOptional.isPresent()) {
                author = authorOptional.get();
            } else {
                LOGGER.info("Can't find author of book");
                throw new DataNotFoundException(ErrorCode.ERR_AUTHOR_NOT_FOUND);
            }
            Optional<Publisher> publisherOptional = publisherRepository.findById(dtoBook.getPublisher());
            if (publisherOptional.isPresent()) {
                publisher = publisherOptional.get();
            } else {
                LOGGER.info("Can't find publisher of book");
                throw new DataNotFoundException(ErrorCode.ERR_AUTHOR_NOT_FOUND);
            }
            Optional<Category> categoryOptional = categoryRepository.findById(dtoBook.getCategory());
            if (categoryOptional.isPresent()) {
                category = categoryOptional.get();
            } else {
                LOGGER.info("Can't find category of book");
                throw new DataNotFoundException(ErrorCode.ERR_AUTHOR_NOT_FOUND);
            }
            book.setAuthor(author);
            book.setPublisher(publisher);
            book.setCategory(category);
            return book;
        } catch (Exception e) {
            LOGGER.info("Fail to convert BookDTO to Book");
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }

    public BookDto toDTO(Book entity) {
        BookDto dto = new BookDto();
        dto.setId(entity.getId());
        dto.setBookName(entity.getBookName());
        if (entity.getAuthor() == null) {
            dto.setAuthor(null);
        } else {
            dto.setAuthor(entity.getAuthor().getId());
        }

        if (entity.getPublisher() == null) {
            dto.setPublisher(null);
        } else {
            dto.setPublisher(entity.getPublisher().getId());
        }

        if (entity.getCategory() == null) {
            dto.setCategory(null);
        } else {
            dto.setCategory(entity.getCategory().getId());
        }
        dto.setSalePrice(entity.getSalePrice());
        dto.setOriginalPrice(entity.getOriginalPrice());
        dto.setDescription(entity.getDescription());
        dto.setQuantity(entity.getQuantity());
        dto.setPicture(entity.getPicture());
        return dto;
    }

    public List<BookDto> toDTOList(List<Book> entityList) {
        List<BookDto> dtoList = entityList.stream().map(entity -> toDTO(entity)).collect(Collectors.toList());
        return dtoList;
    }
}
