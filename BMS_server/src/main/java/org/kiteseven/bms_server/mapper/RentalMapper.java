package org.kiteseven.bms_server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.kiteseven.bms_pojo.dto.BikeRentDTO;
import org.kiteseven.bms_pojo.vo.RentalVO;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface RentalMapper {
    void RentalBike(BikeRentDTO bikeRentDTO);
    List<RentalVO> getRentals();
    Long getRentalsCount();

    void completeOrder(Integer rentalId,LocalDateTime returnTime);
    void cancelOrder(Integer rentalId,LocalDateTime returnTime);

    RentalVO getRentalData(Integer rentalId);
}
