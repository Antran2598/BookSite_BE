package com.example.Ecommerce.converter;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.dto.PublisherDto;
import com.example.Ecommerce.exception.ConvertEntityDTOException;
import com.example.Ecommerce.model.Publisher;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PublisherConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherConverter.class);

    @Autowired
    private ModelMapper modelMapper;

    public PublisherDto convertPublisherToDTO(Publisher publisher) {
        try {
            PublisherDto publisherDto = modelMapper.map(publisher, PublisherDto.class);
            return publisherDto;
        } catch (Exception e) {
            LOGGER.info("Fail to convert Publisher to PublisherDTO");
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }

    public Publisher convertPublisherDTOToEntity(PublisherDto dtoPublisher) {
        try {
            Publisher publisher = modelMapper.map(dtoPublisher, Publisher.class);
            return publisher;
        } catch (Exception e) {
            LOGGER.info("Fail to convert PublisherDTO to publisher");
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }

    public PublisherDto toDTO(Publisher entity) {
        PublisherDto dto = new PublisherDto();
        dto.setId(entity.getId());
        dto.setPubName(entity.getPubName());
        return dto;
    }

    public List<PublisherDto> toDTOList(List<Publisher> entityList) {
        List<PublisherDto> dtoList = entityList.stream().map(entity -> toDTO(entity)).collect(Collectors.toList());
        return dtoList;
    }
}
