package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.PublisherDto;
import com.example.Ecommerce.exception.CreateDataFailException;
import com.example.Ecommerce.exception.DataNotFoundException;
import com.example.Ecommerce.exception.DeleteDataFailException;
import com.example.Ecommerce.exception.UpdateDataFailException;

import java.util.List;

public interface PublisherService {
    List<PublisherDto> getPublisherListToShow();

    Boolean addNewPublisher(PublisherDto publisherDto) throws CreateDataFailException;

    Boolean updatePublisher(PublisherDto publisherDto) throws DataNotFoundException, UpdateDataFailException;

    Boolean deletePublisher(Long id) throws DataNotFoundException, DeleteDataFailException;
}
