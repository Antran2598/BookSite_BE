package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.UserProfileDto;
import com.example.Ecommerce.exception.*;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.payload.request.LoginRequest;
import com.example.Ecommerce.payload.request.SignUpRequest;
import com.example.Ecommerce.payload.response.JwtResponse;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface UserService {
    User getUserDetail(Long userId) throws DataNotFoundException;

    JwtResponse authenticateAccount(LoginRequest loginRequest) throws AccountAuthenticationException;

    Boolean registerAccount(SignUpRequest signUpRequest) throws CreateDataFailException;

    User getAccountByUsername(String username) throws AccountNotFoundException;

    Boolean updateAccount(User theAccount) throws UpdateDataFailException;

    Boolean deleteAccount(Long id) throws DeleteDataFailException;

    Boolean activeAccount(Long id) throws UpdateDataFailException;

    User getForgotAccount(String username);

    UserProfileDto getAccountToShow(Long accountId) throws DataNotFoundException;

    List<UserProfileDto> getListAccountProfilesForAdmin();
}
