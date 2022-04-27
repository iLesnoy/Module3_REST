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
import com.epam.esm.gifts.model.Order;
import com.epam.esm.gifts.model.User;
import com.epam.esm.gifts.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.gifts.exception.ExceptionCode.*;

@Service
public class UserServiceImpl implements UserService {


    private UserDaoImpl userDao;
    private GiftCertificateValidator validation;

    @Autowired
    public UserServiceImpl(UserDaoImpl userDao, GiftCertificateValidator validation) {
        this.userDao = userDao;
        this.validation = validation;
    }


    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        if (GiftCertificateValidator.isNameValid(userDto.getName())) {
            if (userDao.isNameFree(userDto.getName())) {
                return UserConverter.userToDto(userDao.create(UserConverter.dtoToUser(userDto)));
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
                userDao.update(UserConverter.dtoToUser(userDto));
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
            return UserConverter.userToDto(optionalUser.get());
        }
        throw new SystemException(NON_EXISTENT_ENTITY);
    }

    @Override
    public CustomPage<UserDto> findAll(CustomPageable pageable) {
        long userNumber = userDao.findEntityNumber();
        validation.checkPageableValidation(pageable, userNumber);
        int offset = calculateOffset(pageable);
        List<UserDto> userDtoList = userDao.findAll(offset, pageable.getSize())
                .stream()
                .map(UserConverter::userToDto)
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
            return userDao.findById(id).get();
        } else {
            throw new SystemException(NON_EXISTENT_ENTITY);
        }
    }

    @Override
    public UserDto findByName(String name) {
        if (GiftCertificateValidator.isNameValid(name)) {
            return UserConverter.userToDto(userDao.findByName(name));
        }
        throw new SystemException(USER_INVALID_NAME);
    }

    @Override
    @Transactional
    public CustomPage<ResponseOrderDto> findUserOrderList(Long id, CustomPageable pageable) {
        //если pageable validnoe ->
        Optional<User> optionalUser = userDao.findById(id);
        Long totalElements = userDao.userOrderNumber(optionalUser.get());
        int offset = calculateOffset(pageable);
        List<ResponseOrderDto> userOrder = userDao.finUserOrder(optionalUser.get(), offset, pageable.getSize())
                .stream().map(OrderConverter::orderToDto).toList();
        System.out.println(userOrder);
        return new CustomPage<>(userOrder, pageable, totalElements);
    }
}
