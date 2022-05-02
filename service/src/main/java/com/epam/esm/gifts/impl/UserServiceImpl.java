package com.epam.esm.gifts.impl;

import com.epam.esm.gifts.UserService;
import com.epam.esm.gifts.converter.OrderConverter;
import com.epam.esm.gifts.converter.UserConverter;
import com.epam.esm.gifts.dao.impl.UserDaoImpl;
import com.epam.esm.gifts.dto.CustomPage;
import com.epam.esm.gifts.dto.CustomPageable;
import com.epam.esm.gifts.dto.ResponseOrderDto;
import com.epam.esm.gifts.dto.UserDto;
import com.epam.esm.gifts.exception.SystemException;
import com.epam.esm.gifts.model.User;
import com.epam.esm.gifts.validator.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.gifts.exception.ExceptionCode.*;

@Service
public class UserServiceImpl implements UserService {


    private UserDaoImpl userDao;
    private EntityValidator validation;
    private UserConverter userConverter;
    private OrderConverter orderConverter;

    @Autowired
    public UserServiceImpl(UserDaoImpl userDao, EntityValidator validation,
                           UserConverter userConverter, OrderConverter orderConverter) {
        this.userDao = userDao;
        this.validation = validation;
        this.userConverter = userConverter;
        this.orderConverter = orderConverter;
    }

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        if (validation.isNameValid(userDto.getName())) {
            if (userDao.isNameFree(userDto.getName())) {
                return userConverter.userToDto(userDao.create(userConverter.dtoToUser(userDto)));
            }
            throw new SystemException(DUPLICATE_NAME);
        }
        throw new SystemException(USER_INVALID_NAME);
    }


    @Override
    @Transactional
    public UserDto update(Long id, UserDto userDto) {
        Optional<User> optionalUser = userDao.findById(id);
        if (optionalUser.isPresent()) {
            validation.checkUserValidation(userDto);
            if (userDao.isNameFree(userDto.getName())) {
                userDao.update(userConverter.dtoToUser(userDto));
                return userDto;
            }
            throw new SystemException(DUPLICATE_NAME);
        }
        throw new SystemException(NON_EXISTENT_ENTITY);
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> optionalUser = userDao.findById(id);
        if (optionalUser.isPresent()) {
            return userConverter.userToDto(optionalUser.get());
        }
        throw new SystemException(NON_EXISTENT_ENTITY);
    }

    @Override
    public CustomPage<UserDto> findAll(CustomPageable pageable) {
        long userNumber = userDao.findEntityNumber();
        validation.checkPageableValidation(pageable, userNumber);
        long totalTagNumber = userDao.findEntityNumber();
        if (!validation.isPageExists(pageable, totalTagNumber)) {
            throw new SystemException(NON_EXISTENT_PAGE);
        }
        int offset = calculateOffset(pageable);
        List<UserDto> userDtoList = userDao.findAll(offset, pageable.getSize())
                .stream()
                .map(userConverter::userToDto)
                .toList();
        return new CustomPage<>(userDtoList, pageable, userNumber);
    }

    @Override
    public void delete(Long id) {
        Optional<User> optionalUser = userDao.findById(id);
        if (optionalUser.isPresent()) {
            userDao.delete(optionalUser.get());
        } else {
            throw new SystemException(NON_EXISTENT_ENTITY);
        }
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> optionalUser = userDao.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new SystemException(NON_EXISTENT_ENTITY);
        }
    }

    @Override
    public UserDto findByName(String name) {
        if (validation.isNameValid(name)) {
            return userConverter.userToDto(userDao.findByName(name));
        }
        throw new SystemException(USER_INVALID_NAME);
    }

    @Override
    @Transactional
    public CustomPage<ResponseOrderDto> findUserOrderList(Long id, CustomPageable pageable) {
        if(!validation.isPageDataValid(pageable)){
            throw new SystemException(INVALID_DATA_OF_PAGE);
        }
        Optional<User> optionalUser = userDao.findById(id);
        Long totalElements = userDao.userOrderNumber(optionalUser.get());
        if(!validation.isPageExists(pageable,totalElements)){
            throw new SystemException(NON_EXISTENT_PAGE);
        }
        int offset = calculateOffset(pageable);
        List<ResponseOrderDto> userOrder = userDao.finUserOrder(optionalUser.get(), offset, pageable.getSize())
                .stream().map(orderConverter::orderToDto).toList();
        return new CustomPage<>(userOrder, pageable, totalElements);
    }
}
