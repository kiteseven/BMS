package org.kiteseven.bms_server.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
    @Select("select rental_id from bms.rentals where bicycle_id=#{bikeId}")
    List<Integer> getRentalIdByBikeId(Integer bikeId);

    @Select("select rental_id from bms.rentals where user_id=#{UserId}")
    List<Integer> getRentalIdByUserId(Integer UserId);

    @Delete("delete from bms.rentals where bicycle_id=#{bikeId}")
    void deleteRentalByBikeId(Integer bikeId);

    @Delete("delete from bms.rentals where user_id=#{UserId}")
    void deleteRentalByUserId(Integer UserId);

    @Delete("delete from bms.rentals where rental_id=#{remtalId}")
    void deleteRental(Integer rentalId);
}
