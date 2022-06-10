package com.example.Ecommerce.service.Imp;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.converter.UserConverter;
import com.example.Ecommerce.dto.UserProfileDto;
import com.example.Ecommerce.exception.*;
import com.example.Ecommerce.model.ERole;
import com.example.Ecommerce.model.Role;
import com.example.Ecommerce.model.User;
import com.example.Ecommerce.payload.request.LoginRequest;
import com.example.Ecommerce.payload.request.SignUpRequest;
import com.example.Ecommerce.payload.response.JwtResponse;
import com.example.Ecommerce.repository.RoleRepository;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.sercurity.jwt.JwtUtils;
import com.example.Ecommerce.sercurity.service.impl.UserDetailsImpl;
import com.example.Ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.AccountNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserConverter userConverter;

    @Override
    public User getUserDetail(Long userId) throws DataNotFoundException {
        User user;
        try {
            Optional<User> optinalUser = userRepository.findById(userId);
            if (optinalUser.isPresent()) {
                user = optinalUser.get();
            } else {
                LOGGER.info("Can't find account with id: " + userId);
                throw new DataNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
            }
        } catch (Exception e) {
            LOGGER.info("Having error when load account with id: " + e.getMessage());
            throw new DataNotFoundException(ErrorCode.ERR_USER_NOT_FOUND);
        }
        return user;
    }

    @Override
    public JwtResponse authenticateAccount(LoginRequest loginRequest) throws AccountAuthenticationException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            return new JwtResponse(jwt, userDetails.getId() ,userDetails.getUsername(),roles);
        } catch (Exception e) {
            LOGGER.info("Account Authentication Error: " + e.getMessage());
            throw new AccountAuthenticationException(ErrorCode.ERR_ACCOUNT_LOGIN_FAIL);
        }
    }

    @Override
    public Boolean registerAccount(SignUpRequest signUpRequest) throws CreateDataFailException {
        boolean result;
        try {
            boolean existedUsername = userRepository.existsByUserName(signUpRequest.getUsername());
            if (existedUsername) {
                LOGGER.info("Username " + signUpRequest.getUsername() + " is already taken");
                throw new DuplicateDataException(ErrorCode.ERR_USERNAME_ALREADY_TAKEN);
            }
            User theAccount = new User();
            theAccount.setUserName(signUpRequest.getUsername());
            theAccount.setPassword(encoder.encode(signUpRequest.getPassword()));

            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(ERole.CUSTOMER).orElseThrow(() -> {
                LOGGER.info("Role " + ERole.CUSTOMER.name() + " is not found");
                return new DataNotFoundException(ErrorCode.ERR_ROLE_NOT_FOUND);
            });
            roles.add(userRole);
            theAccount.setRoles(roles);
            userRepository.save(theAccount);
            result = true;
        } catch (Exception ex) {
            LOGGER.info("Fail to create new account " + signUpRequest.getUsername());
            throw new CreateDataFailException(ErrorCode.ERR_ACCOUNT_SIGNUP_FAIL);
        }
        return result;
    }

    @Override
    public User getAccountByUsername(String username) throws AccountNotFoundException {
        Optional<User> result = userRepository.findByUserName(username);
        User theAccount;
        if (result.isPresent()) {
            theAccount = result.get();
        } else {
            LOGGER.info("Having error when load account with this username " + username);
            throw new AccountNotFoundException(ErrorCode.ERR_ACCOUNT_NOT_FOUND);
        }
        return theAccount;
    }

    @Override
    public Boolean updateAccount(User theAccount) throws UpdateDataFailException {
        boolean result;
        try {
            userRepository.save(theAccount);
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when update account : " + e.getMessage());
            throw new UpdateDataFailException(ErrorCode.ERR_UPDATE_ACCOUNT_FAIL);
        }
        return result;
    }


    @Override
    public Boolean deleteAccount(Long id) throws DeleteDataFailException {
        boolean result;
        User account;
        try {
            Optional<User> accountOptional = userRepository.findById(id);
            if (accountOptional.isPresent()) {
                account = accountOptional.get();
            } else {
                LOGGER.info("Can't find account with the id: " + id);
                throw new AccountNotFoundException(ErrorCode.ERR_ACCOUNT_NOT_FOUND);
            }
            userRepository.updateAccountStatusToLocked(account.getId());
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when lock account: " + e.getMessage());
            throw new DeleteDataFailException(ErrorCode.ERR_DELETE_ACCOUNT_FAIL);
        }
        return result;
    }

    @Override
    public Boolean activeAccount(Long id) throws UpdateDataFailException {
        boolean result;
        User account;
        try {
            Optional<User> accountOptional = userRepository.findById(id);
            if (accountOptional.isPresent()) {
                account = accountOptional.get();
            } else {
                LOGGER.info("Can't find account with the id: " + id);
                throw new AccountNotFoundException(ErrorCode.ERR_ACCOUNT_NOT_FOUND);
            }
            userRepository.updateAccountStatusToActive(account.getId());
            result = true;
        } catch (Exception e) {
            LOGGER.info("Having error when unlock account : " + e.getMessage());
            throw new UpdateDataFailException(ErrorCode.ERR_DELETE_ACCOUNT_FAIL);
        }
        return result;
    }

    @Override
    public User getForgotAccount(String username) {
        return userRepository.getForForgotPassword(username);
    }

    @Override
    public UserProfileDto getAccountToShow(Long accountId) throws DataNotFoundException {
        UserProfileDto dto;
        try {
            User account;
            Optional<User> optionalAccount = userRepository.findById(accountId);
            if (optionalAccount.isPresent()) {
                account = optionalAccount.get();
            } else {
                LOGGER.info("Can't find account with id: " + accountId);
                throw new DataNotFoundException(ErrorCode.ERR_ACCOUNT_NOT_FOUND);
            }
            dto = userConverter.convertAccountProfileToDto(account);
        } catch (Exception e) {
            LOGGER.info("Having error when load account: " + e.getMessage());
            throw new DataNotFoundException(ErrorCode.ERR_ACCOUNT_NOT_FOUND);
        }
        return dto;
    }

//    @Override
//    public List<UserProfileDto> getListAccountProfilesForAdmin(int pageNo, String valueSort) {
//        List<UserProfileDto> listDTO;
//        try {
//            Pageable pageable = PageRequest.of(pageNo - 1, 5, Sort.by(valueSort).ascending());
//            Page<Account> page = accountRepository.findAll(pageable);
//            listDTO = userConverter.toDTOList(page.getContent());
//        } catch (Exception e) {
//            LOGGER.info("Having error when load list of account: " + e.getMessage());
//            throw new DataNotFoundException(ErrorCode.ERR_ACCOUNT_LIST_LOADED_FAIL);
//        }
//        return listDTO;
//    }

    @Override
    public List<UserProfileDto> getListAccountProfilesForAdmin() {
        List<UserProfileDto> listDTO;
        try {
            List<User> user = userRepository.findAll();
            listDTO = userConverter.toDTOList(user);
        } catch (Exception e) {
            LOGGER.info("Having error when load list of account:" + e.getMessage());
            throw new DataNotFoundException(ErrorCode.ERR_USER_LIST_LOADED_FAIL);
        }
        return listDTO;
    }

}
