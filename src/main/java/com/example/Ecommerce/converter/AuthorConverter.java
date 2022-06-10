package com.example.Ecommerce.converter;


import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.dto.AuthorDto;
import com.example.Ecommerce.exception.ConvertEntityDTOException;
import com.example.Ecommerce.model.Author;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorConverter.class);
    @Autowired
    private ModelMapper modelMapper;

    public AuthorDto convertAuthorToDTO(Author author) {
        try {
            AuthorDto authorDto = modelMapper.map(author, AuthorDto.class);
            return authorDto;
        } catch (Exception e) {
            LOGGER.info("Fail to convert Author to AuthorDTO");
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }

    public Author convertAuthorDTOToEntity(AuthorDto dtoAuthor) {
        try {
            Author author = modelMapper.map(dtoAuthor, Author.class);
            return author;
        } catch (Exception e) {
            LOGGER.info("Fail to convert AuthorDTO to Book");
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }

    public AuthorDto toDTO(Author entity) {
        AuthorDto dto = new AuthorDto();
        dto.setId(entity.getId());
        dto.setAuthorName(entity.getAuthorName());
        return dto;
    }

    public List<AuthorDto> toDTOList(List<Author> entityList) {
        List<AuthorDto> dtoList = entityList.stream().map(entity -> toDTO(entity)).collect(Collectors.toList());
        return dtoList;
    }
}
