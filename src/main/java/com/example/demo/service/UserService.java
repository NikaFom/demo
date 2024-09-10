package com.example.demo.service;

import com.example.demo.model.db.entity.UserEntity;
import com.example.demo.model.db.repository.UserRepository;
import com.example.demo.model.dto.request.UserInfoRequest;
import com.example.demo.model.dto.response.UserInfoResponse;
import com.example.demo.model.enums.UserStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final ObjectMapper mapper;
    private final UserRepository userRepository;

    public UserInfoResponse createUser(UserInfoRequest request) {
        if(!EmailValidator.getInstance().isValid(request.getEmail())) {
            return null;
        }

        UserEntity user = mapper.convertValue(request, UserEntity.class);
        user.setCreatedAt(LocalDateTime.now());
        user.setStatus(UserStatus.CREATED);

        UserEntity savedUser = userRepository.save(user);

        return mapper.convertValue(savedUser, UserInfoResponse.class);
    }

    public UserInfoResponse getUser(Long id) {
        UserEntity user = getUserFromDB(id);
        return mapper.convertValue(user, UserInfoResponse.class);
    }

    public UserEntity getUserFromDB(Long id){
        return userRepository.findById(id).orElse(new UserEntity());
    }

    public UserInfoResponse updateUser(Long id, UserInfoRequest request) {
        if(!EmailValidator.getInstance().isValid(request.getEmail())) {
            return null;
        }

        UserEntity user = getUserFromDB(id);

        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword() == null ? user.getPassword() : request.getPassword());
        user.setFirstName(request.getFirstName() == null ? user.getFirstName() : request.getFirstName());
        user.setLastName(request.getLastName() == null ? user.getLastName() : request.getLastName());
        user.setMiddleName(request.getMiddleName() == null ? user.getMiddleName() : request.getMiddleName());
        user.setAge(request.getAge() == null ? user.getAge() : request.getAge());
        user.setGender(request.getGender() == null ? user.getGender() : request.getGender());

        user.setUpdatedAt(LocalDateTime.now());
        user.setStatus(UserStatus.UPDATED);

        UserEntity savedUser = userRepository.save(user);

        return mapper.convertValue(savedUser, UserInfoResponse.class);
    }

    public void deleteUser(Long id) {
        UserEntity user = getUserFromDB(id);
        user.setUpdatedAt(LocalDateTime.now());
        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);
    }

    public List<UserInfoResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userEntity -> mapper.convertValue(userEntity, UserInfoResponse.class))
                .collect(Collectors.toList());
    }
}
