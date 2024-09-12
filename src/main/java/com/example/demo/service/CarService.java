package com.example.demo.service;

import com.example.demo.model.db.entity.CarEntity;
import com.example.demo.model.db.entity.UserEntity;
import com.example.demo.model.db.repository.CarRepository;
import com.example.demo.model.db.repository.UserRepository;
import com.example.demo.model.dto.request.CarInfoRequest;
import com.example.demo.model.dto.request.CarToUserRequest;
import com.example.demo.model.dto.response.CarInfoResponse;
import com.example.demo.model.enums.CarStatus;
import com.example.demo.utils.PaginationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {
    private final UserService userService;
    private final ObjectMapper mapper;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public CarInfoResponse createCar(CarInfoRequest request) {
        CarEntity car = mapper.convertValue(request, CarEntity.class);
        car.setCreatedAt(LocalDateTime.now());
        car.setStatus(CarStatus.CREATED);

        CarEntity savedCar = carRepository.save(car);

        return mapper.convertValue(savedCar, CarInfoResponse.class);
    }

    public CarInfoResponse getCar(Long id) {
        CarEntity car = getCarFromDB(id);
        return mapper.convertValue(car, CarInfoResponse.class);
    }

    public CarEntity getCarFromDB(Long id) {
        return carRepository.findById(id).orElse(new CarEntity());
    }

    public CarInfoResponse updateCar(Long id, CarInfoRequest request) {
        CarEntity car = getCarFromDB(id);

        car.setBrand(request.getBrand());
        car.setModel(request.getModel() == null ? car.getModel() : request.getModel());
        car.setYear(request.getYear() == null ? car.getYear() : request.getYear());
        car.setDoors(request.getDoors() == null ? car.getDoors() : request.getDoors());
        car.setCapacity(request.getCapacity() == null ? car.getCapacity() : request.getCapacity());
        car.setWeight(request.getWeight() == null ? car.getWeight() : request.getWeight());
        car.setSpeed(request.getSpeed() == null ? car.getSpeed() : request.getSpeed());
        car.setColour(request.getColour() == null ? car.getColour() : request.getColour());

        car.setUpdatedAt(LocalDateTime.now());
        car.setStatus(CarStatus.UPDATED);

        CarEntity savedCar = carRepository.save(car);

        return mapper.convertValue(savedCar, CarInfoResponse.class);
    }

    public void deleteCar(Long id) {
        CarEntity car = getCarFromDB(id);
        car.setUpdatedAt(LocalDateTime.now());
        car.setStatus(CarStatus.DELETED);
        carRepository.save(car);
    }

    public Page<CarInfoResponse> getAllCars(Integer page, Integer perPage, String sort, Sort.Direction order, String filter) {
        Pageable pageRequest = PaginationUtil.getPageRequest(page, perPage, sort, order);

        Page<CarEntity> all = carRepository.findAllByStatusNot((java.awt.print.Pageable) pageRequest, CarStatus.DELETED);

        List<CarInfoResponse> content = all.getContent().stream()
                .map(carEntity -> mapper.convertValue(carEntity, CarInfoResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageRequest, all.getTotalElements());
    }

    public void addCarToUser(CarToUserRequest request) {
        CarEntity car = carRepository.findById(request.getCarId()).orElse(null);
        if(car == null) {
            return;
        }

        UserEntity user = userService.getUserFromDB(request.getUserId());
        if(user == null) {
            return;
        }

        user.getCars().add(car);
        userService.updateUserData(user);

        car.setUser(user);
        carRepository.save(car);
    }

    public List<CarInfoResponse> getUserCars(Long userId) {
        UserEntity user = userService.getUserFromDB(userId);
        if(user == null) {
            return null;
        }

        return user.getCars().stream()
                .map(carEntity -> mapper.convertValue(carEntity, CarInfoResponse.class))
                .collect(Collectors.toList());
    }
}
