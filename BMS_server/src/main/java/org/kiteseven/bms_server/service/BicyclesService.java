package org.kiteseven.bms_server.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.kiteseven.bms_common.result.PageResult;
import org.kiteseven.bms_pojo.dto.BicycleDTO;
import org.kiteseven.bms_pojo.dto.BicycleUpdateDTO;
import org.kiteseven.bms_pojo.dto.BikeRentDTO;
import org.springframework.stereotype.Service;


public interface BicyclesService {
    void addBicycles(BicycleDTO bicycleDTO);
    PageResult getAllBicycles();

    void updateBicycle(BicycleUpdateDTO bicycleUpdateDTO);
    void deleteBicycle(Integer id);

    PageResult getAvailableBike();

    void rentBike(BikeRentDTO bikeRentDTO);

    PageResult getOrders();

    void completeOrder(Integer rentalId);

    void cancelOrder(Integer rentalId);

    PageResult searchBike(String model,String location, Integer status);

    void exportBicycleData(HttpServletResponse response);
}
