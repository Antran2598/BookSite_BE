package com.example.Ecommerce.controller;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.constants.SuccessCode;
import com.example.Ecommerce.converter.UserConverter;
import com.example.Ecommerce.dto.ResponseDTO;
import com.example.Ecommerce.dto.UserForgotDto;
import com.example.Ecommerce.dto.UserProfileDto;
import com.example.Ecommerce.exception.DataNotFoundException;
import com.example.Ecommerce.exception.DeleteDataFailException;
import com.example.Ecommerce.exception.UpdateDataFailException;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @GetMapping("/forgotAccount")
    public ResponseEntity<ResponseDTO> getForgotAccount(@RequestBody UserForgotDto accountForgotDTO) {
        ResponseDTO response = new ResponseDTO();
        try {
            User account =
                    userService.getForgotAccount(accountForgotDTO.getUserName());
            if (account != null) {
                UserProfileDto dto = userConverter.convertAccountProfileToDto(account);
                response.setSuccessCode(SuccessCode.ACCOUNT_LOADED_SUCCESS);
                response.setData(dto);
            }
        } catch (Exception ex) {
            response.setErrorCode(ErrorCode.ERR_ACCOUNT_NOT_FOUND);
            throw new DataNotFoundException(ErrorCode.ERR_ACCOUNT_NOT_FOUND);
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/allUser")
    public ResponseEntity<ResponseDTO> getAllUser() {
        ResponseDTO response = new ResponseDTO();
        try {
            List<UserProfileDto> listDTO = userService.getListAccountProfilesForAdmin();
            response.setData(listDTO);
            response.setSuccessCode(SuccessCode.GET_ALL_USER_SUCCESS);
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_ACCOUNT_NOT_FOUND);
            throw new DataNotFoundException(ErrorCode.ERR_ACCOUNT_NOT_FOUND);
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/details/{accountID}")
    public ResponseEntity<ResponseDTO> getAccountDetail(@PathVariable int accountID) {
        ResponseDTO response = new ResponseDTO();
        try {
            Long value = Long.valueOf(String.valueOf(accountID));
            UserProfileDto dto = userService.getAccountToShow(value);
            if (dto != null) {
                response.setData(dto);
                response.setSuccessCode(SuccessCode.ACCOUNT_LOADED_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_ACCOUNT_NOT_FOUND);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_ACCOUNT_LOADED_FAIL);
            throw new DataNotFoundException(ErrorCode.ERR_ACCOUNT_LOADED_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/lockAccount/{accountID}")
    public ResponseEntity<ResponseDTO> lockAccount(@PathVariable("accountID") int accountID) throws DeleteDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            boolean result = userService.deleteAccount(Long.valueOf(String.valueOf(accountID)));
            if (result) {
                response.setData(true);
                response.setSuccessCode(SuccessCode.ACCOUNT_DELETE_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_DELETE_ACCOUNT_FAIL);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_DELETE_ACCOUNT_FAIL);
            throw new DeleteDataFailException(ErrorCode.ERR_DELETE_ACCOUNT_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/unlockAccount/{accountID}")
    public ResponseEntity<ResponseDTO> activeAccount(@PathVariable("accountID") int accountID) throws UpdateDataFailException {
        ResponseDTO response = new ResponseDTO();
        try {
            boolean result = userService.activeAccount(Long.valueOf(String.valueOf(accountID)));
            if (result) {
                response.setData(true);
                response.setSuccessCode(SuccessCode.ACCOUNT_ACTIVE_SUCCESS);
            } else {
                response.setData(false);
                response.setErrorCode(ErrorCode.ERR_UPDATE_ACCOUNT_FAIL);
            }
        } catch (Exception e) {
            response.setErrorCode(ErrorCode.ERR_UPDATE_ACCOUNT_FAIL);
            throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_ACCOUNT_FAIL);
        }
        return ResponseEntity.ok().body(response);
    }
}
