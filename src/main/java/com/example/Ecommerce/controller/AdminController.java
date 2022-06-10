package com.example.Ecommerce.controller;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.constants.SuccessCode;
import com.example.Ecommerce.dto.*;
import com.example.Ecommerce.exception.CreateDataFailException;
import com.example.Ecommerce.exception.DataNotFoundException;
import com.example.Ecommerce.exception.DeleteDataFailException;
import com.example.Ecommerce.exception.UpdateDataFailException;
import com.example.Ecommerce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController

@RequestMapping("/auth/admin")
public class AdminController {

    @Autowired
    BookService bookService;
    @Autowired
    AuthorService authorService;
    @Autowired
    PublisherService publisherService;
    @Autowired
    CategoryService categoryService;

    @Autowired
    PaymentMethodService paymentMethodService;

    @Autowired
    ShippingFeeService shippingFeeService;

    @GetMapping("/allBook")
    public ResponseEntity<ResponseDTO> getAllBook() {
        ResponseDTO response = new ResponseDTO();
        try {
            List<BookDto> listDTO = bookService.getBookListToShow();
            response.setData(listDTO);
            response.setSuccessCode(SuccessCode.GET_ALL_BOOK_SUCCESS);
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_BOOK_NOT_FOUND);
            throw new DataNotFoundException(ErrorCode.ERR_BOOK_NOT_FOUND);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/book")
    public ResponseEntity<ResponseDTO> createNewBook(@Valid @RequestBody BookDto bookDto) throws CreateDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            boolean result = bookService.addNewBook(bookDto);
            if (result) {
                response.setData(true);
                response.setSuccessCode(SuccessCode.BOOK_CREATE_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_CREATE_BOOK_FAIL);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_CREATE_BOOK_FAIL);
            throw new CreateDataFailException(ErrorCode.ERR_CREATE_BOOK_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/book")
    public ResponseEntity<ResponseDTO> updateBook(@Valid @RequestBody BookDto bookDto) throws UpdateDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            boolean result = bookService.updateBook(bookDto);
            if (result) {
                response.setData(true);
                response.setSuccessCode(SuccessCode.BOOK_UPDATE_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_UPDATE_BOOK_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setErrorCode(ErrorCode.ERR_UPDATE_BOOK_FAIL);
            throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_BOOK_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<ResponseDTO> deleteBook(@PathVariable("bookId") int bookId) throws DeleteDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            Long id = Long.valueOf(String.valueOf(bookId));
            boolean result = bookService.deleteBook(id);
            if (result) {
                response.setData(true);
                response.setSuccessCode(SuccessCode.BOOK_DELETE_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_DELETE_BOOK_FAIL);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_DELETE_BOOK_FAIL);
            throw new DeleteDataFailException(ErrorCode.ERR_DELETE_BOOK_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/allAuthor")
    public ResponseEntity<ResponseDTO> getAllAuthor() {
        ResponseDTO response = new ResponseDTO();
        try {
            List<AuthorDto> listDTO = authorService.getAuthorListToShow();
            response.setData(listDTO);
            response.setSuccessCode(SuccessCode.GET_ALL_BOOK_SUCCESS);
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_BOOK_NOT_FOUND);
            throw new DataNotFoundException(ErrorCode.ERR_BOOK_NOT_FOUND);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/author")
    public ResponseEntity<ResponseDTO> createNewAuthor(@Valid @RequestBody AuthorDto authorDto) throws CreateDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            boolean result = authorService.addNewAuthor(authorDto);
            if (result) {
                response.setData(true);
                response.setSuccessCode(SuccessCode.AUTHOR_CREATE_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_CREATE_AUTHOR_FAIL);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_CREATE_AUTHOR_FAIL);
            throw new CreateDataFailException(ErrorCode.ERR_CREATE_AUTHOR_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/author")
    public ResponseEntity<ResponseDTO> updateAuthor(@Valid @RequestBody AuthorDto authorDto) throws UpdateDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            boolean result = authorService.updateAuthor(authorDto);
            if (result) {
                response.setData(true);
                response.setSuccessCode(SuccessCode.AUTHOR_UPDATE_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_UPDATE_AUTHOR_FAIL);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_UPDATE_AUTHOR_FAIL);
            throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_AUTHOR_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/author/{authorId}")
    public ResponseEntity<ResponseDTO> deleteAuthor(@PathVariable("authorId") int authorId) throws DeleteDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            Long id = Long.valueOf(String.valueOf(authorId));
            boolean result = authorService.deleteAuthor(id);
            if (result) {
                response.setData(true);
                response.setSuccessCode(SuccessCode.AUTHOR_DELETE_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_DELETE_AUTHOR_FAIL);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_DELETE_AUTHOR_FAIL);
            throw new DeleteDataFailException(ErrorCode.ERR_DELETE_AUTHOR_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/allPublisher")
    public ResponseEntity<ResponseDTO> getAllPublisher() {
        ResponseDTO response = new ResponseDTO();
        try {
            List<PublisherDto> listDTO = publisherService.getPublisherListToShow();
            response.setData(listDTO);
            response.setSuccessCode(SuccessCode.GET_ALL_PUBLISHER_SUCCESS);
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_PUBLISHER_NOT_FOUND);
            throw new DataNotFoundException(ErrorCode.ERR_PUBLISHER_NOT_FOUND);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/publisher")
    public ResponseEntity<ResponseDTO> createNewPublisher(@Valid @RequestBody PublisherDto publisherDto) throws CreateDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            boolean result = publisherService.addNewPublisher(publisherDto);
            if (result) {
                response.setData(true);
                response.setSuccessCode(SuccessCode.PUBLISHER_CREATE_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_CREATE_AUTHOR_FAIL);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_CREATE_AUTHOR_FAIL);
            throw new CreateDataFailException(ErrorCode.ERR_CREATE_AUTHOR_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/publisher")
    public ResponseEntity<ResponseDTO> updatePublisher(@Valid @RequestBody PublisherDto publisherDto) throws UpdateDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            boolean result = publisherService.updatePublisher(publisherDto);
            if (result) {
                response.setData(true);
                response.setSuccessCode(SuccessCode.PUBLISHER_UPDATE_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_UPDATE_PUBLISHER_FAIL);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_UPDATE_PUBLISHER_FAIL);
            throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_PUBLISHER_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/publisher/{publisherId}")
    public ResponseEntity<ResponseDTO> deletePublisher(@PathVariable("publisherId") int publisherId) throws DeleteDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            Long id = Long.valueOf(String.valueOf(publisherId));
            boolean result = publisherService.deletePublisher(id);
            if (result) {
                response.setData(true);
                response.setSuccessCode(SuccessCode.AUTHOR_DELETE_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_DELETE_AUTHOR_FAIL);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_DELETE_AUTHOR_FAIL);
            throw new DeleteDataFailException(ErrorCode.ERR_DELETE_AUTHOR_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/allCategory")
    public ResponseEntity<ResponseDTO> getAllCategory() {
        ResponseDTO response = new ResponseDTO();
        try {
            List<CategoryDto> listDTO = categoryService.getCategoryListToShow();
            response.setData(listDTO);
            response.setSuccessCode(SuccessCode.GET_ALL_CATEGORY_SUCCESS);
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_CATEGORY_NOT_FOUND);
            throw new DataNotFoundException(ErrorCode.ERR_CATEGORY_NOT_FOUND);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/category")
    public ResponseEntity<ResponseDTO> createNewCategory(@Valid @RequestBody CategoryDto categoryDto) throws CreateDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            boolean result = categoryService.addNewCategory(categoryDto);
            if (result) {
                response.setData(true);
                response.setSuccessCode(SuccessCode.CATEGORY_CREATE_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_CREATE_CATEGORY_FAIL);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_CREATE_CATEGORY_FAIL);
            throw new CreateDataFailException(ErrorCode.ERR_CREATE_CATEGORY_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/category")
    public ResponseEntity<ResponseDTO> updateCategory(@Valid @RequestBody CategoryDto categoryDto) throws UpdateDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            boolean result = categoryService.updateCategory(categoryDto);
            if (result) {
                response.setData(true);
                response.setSuccessCode(SuccessCode.CATEGORY_UPDATE_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_UPDATE_CATEGORY_FAIL);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_UPDATE_CATEGORY_FAIL);
            throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_CATEGORY_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<ResponseDTO> deleteCategory(@PathVariable("categoryId") int categoryId) throws DeleteDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            Long id = Long.valueOf(String.valueOf(categoryId));
            boolean result = categoryService.deleteCategory(id);
            if (result) {
                response.setData(true);
                response.setSuccessCode(SuccessCode.CATEGORY_DELETE_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_DELETE_CATEGORY_FAIL);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_DELETE_CATEGORY_FAIL);
            throw new DeleteDataFailException(ErrorCode.ERR_DELETE_CATEGORY_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/allPayment")
    public ResponseEntity<ResponseDTO> getAllPayment() {
        ResponseDTO response = new ResponseDTO();
        try {
            List<PaymentMethodDto> listDTO = paymentMethodService.getPayListToShow();
            response.setData(listDTO);
            response.setSuccessCode(SuccessCode.GET_ALL_BOOK_SUCCESS);
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_BOOK_NOT_FOUND);
            throw new DataNotFoundException(ErrorCode.ERR_BOOK_NOT_FOUND);
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/allShipping")
    public ResponseEntity<ResponseDTO> getAllShipping() {
        ResponseDTO response = new ResponseDTO();
        try {
            List<ShippingFeeDto> listDTO = shippingFeeService.getShipListToShow();
            response.setData(listDTO);
            response.setSuccessCode(SuccessCode.GET_ALL_BOOK_SUCCESS);
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_BOOK_NOT_FOUND);
            throw new DataNotFoundException(ErrorCode.ERR_BOOK_NOT_FOUND);
        }
        return ResponseEntity.ok().body(response);
    }
}
