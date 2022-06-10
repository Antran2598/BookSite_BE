package com.example.Ecommerce.converter;

import com.example.Ecommerce.constants.ErrorCode;
import com.example.Ecommerce.dto.RoleDto;
import com.example.Ecommerce.dto.UserDto;
import com.example.Ecommerce.dto.UserProfileDto;
import com.example.Ecommerce.exception.ConvertEntityDTOException;
import com.example.Ecommerce.model.ERole;
import com.example.Ecommerce.model.Role;
import com.example.Ecommerce.model.User;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserConverter.class);

    @Autowired
    private ModelMapper modelMapper;

    public UserDto convertAccountToDto(User user) throws ConvertEntityDTOException {
        try {
            UserDto dto = modelMapper.map(user, UserDto.class);
            Set<String> roles = user.getRoles().stream()
                    .map(role -> role.getName().name())
                    .collect(Collectors.toSet());
            dto.setRoles(roles);
            return dto;
        } catch (Exception e) {
            LOGGER.info("Fail to convert Account to AccountDTO");
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }

    public UserProfileDto convertAccountProfileToDto(User user) throws ConvertEntityDTOException {
        try {
            UserProfileDto dto = modelMapper.map(user, UserProfileDto.class);
            Set<RoleDto> roles = user.getRoles().stream()
                    .map(role -> {
                        if (role.getName().equals(ERole.ADMIN)) {
                            return new RoleDto(role.getId(), "Admin");
                        } else if (role.getName().equals(ERole.CUSTOMER)) {
                            return new RoleDto(role.getId(), "Customer");
                        } else {
                            return new RoleDto(role.getId(), "Manager");
                        }
                    })
                    .collect(Collectors.toSet());
            dto.setRoles(roles);
            return dto;
        } catch (Exception ex) {
            LOGGER.info("Fail to convert Account to AccountProfileDTO");
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }

    public Role convertRoleToEntity(RoleDto dto) throws ConvertEntityDTOException {
        try {
            Role role = new Role();
            role.setId(dto.getId());
            if (dto.getName().equals("Admin")) {
                role.setName(ERole.ADMIN);
            } else if (dto.getName().equals("Customer")) {
                role.setName(ERole.CUSTOMER);
            } else {
                role.setName(ERole.MANAGER);
            }
            return role;
        } catch (Exception ex) {
            LOGGER.info("Fail to convert RoleDTO to Role");
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }
    public User convertAccountProfileToEntity(UserProfileDto dto) throws ConvertEntityDTOException {
        try {
            User user = modelMapper.map(dto, User.class);
            Set<Role> roles = dto.getRoles().stream().map(this::convertRoleToEntity).collect(Collectors.toSet());
            user.setRoles(roles);
            return user;
        } catch (Exception ex) {
            LOGGER.info("Fail to convert UserProfileDTO to User");
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }

    public RoleDto convertRoleToDTO(Role role) throws ConvertEntityDTOException {
        try {
            RoleDto dto = new RoleDto();
            dto.setId(role.getId());
            if (role.getName().equals(ERole.ADMIN)) {
                dto.setName("Admin");
            } else if (role.getName().equals(ERole.CUSTOMER)) {
                dto.setName("Customer");
            } else {
                dto.setName("Manager");
            }
            return dto;
        } catch (Exception ex) {
            LOGGER.info("Fail to convert Role to RoleDTO");
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERTER_DTO_ENTITY_FAIL);
        }
    }

    public UserProfileDto toDTO(User entity) {
        UserProfileDto dto = new UserProfileDto();
        dto.setId(entity.getId());
        dto.setUserName(entity.getUserName());
        dto.setPassword(entity.getPassword());
        Set<Role> setEntityRole = entity.getRoles();
        Set<RoleDto> setDTORole = new HashSet<>();
        for (Role role : setEntityRole) {
            RoleDto dtoRole = convertRoleToDTO(role);
            setDTORole.add(dtoRole);
        }
        dto.setRoles(setDTORole);

        return dto;
    }

    public List<UserProfileDto> toDTOList(List<User> listEntity) {
        List<UserProfileDto> listDTO = listEntity.stream().map(entity -> toDTO(entity)).collect(Collectors.toList());
        return listDTO;
    }

}
