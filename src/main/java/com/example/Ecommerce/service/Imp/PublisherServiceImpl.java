package com.example.Ecommerce.service.Imp;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.converter.PublisherConverter;
import com.example.Ecommerce.dto.PublisherDto;
import com.example.Ecommerce.exception.*;
import com.example.Ecommerce.model.Publisher;
import com.example.Ecommerce.repository.PublisherRepository;
import com.example.Ecommerce.service.PublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherServiceImpl implements PublisherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherServiceImpl.class);

    @Autowired
    PublisherRepository publisherRepository;
    @Autowired
    PublisherConverter publisherConverter;

    @Override
    public List<PublisherDto> getPublisherListToShow() {
        List<PublisherDto> listDTO;
        try {
            List<Publisher> publisher = publisherRepository.getAllPublishers();
            listDTO = publisherConverter.toDTOList(publisher);
        } catch (Exception e) {
            LOGGER.info("Having error when load list publisher " + e.getMessage());
            throw new DataNotFoundException(ErrorCode.ERR_PUBLISHER_LIST_LOADED_FAIL);
        }
        return listDTO;
    }

    @Override
    public Boolean addNewPublisher(PublisherDto publisherDto) throws CreateDataFailException {
        boolean result;
        try {
            Publisher publisher = publisherConverter.convertPublisherDTOToEntity(publisherDto);
            Publisher tempPublisher = publisherRepository.checkExistedPublisher(publisher.getPubName());
            if (tempPublisher != null) {
                LOGGER.info("Publisher have been existed ");
                throw new DuplicateDataException(ErrorCode.ERR_PUBLISHER_EXISTED);
            }
            publisherRepository.save(publisher);
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when create new publisher: " + e.getMessage());
            throw new CreateDataFailException(ErrorCode.ERR_CREATE_PUBLISHER_FAIL);
        }
        return result;
    }

    @Override
    public Boolean updatePublisher(PublisherDto publisherDto) throws DataNotFoundException, UpdateDataFailException {
        boolean result;
        try {
            Publisher publisher = publisherConverter.convertPublisherDTOToEntity(publisherDto);
            Publisher tempPublisher = publisherRepository.checkExistedPublisherId(publisher.getId());
            if (tempPublisher == null) {
                LOGGER.info("Can't find publisher ");
                throw new DataNotFoundException(ErrorCode.ERR_PUBLISHER_NOT_FOUND);
            }
            publisherRepository.save(publisher);
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when update book: " + e.getMessage());
            throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_PUBLISHER_FAIL);
        }
        return result;
    }

    @Override
    public Boolean deletePublisher(Long id) throws DataNotFoundException, DeleteDataFailException {
        boolean result;
        try {
            Optional<Publisher> publisherOptional = publisherRepository.findById(id);
            if (!publisherOptional.isPresent()) {
                LOGGER.info("Can't find publisher ");
                throw new DataNotFoundException(ErrorCode.ERR_PUBLISHER_NOT_FOUND);
            }
            publisherRepository.deletePublisherId(id);
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when delete publisher " + e.getMessage());
            throw new DeleteDataFailException(ErrorCode.ERR_DELETE_PUBLISHER_FAIL);
        }
        return result;
    }
}
