package org.kiteseven.bms_server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.kiteseven.bms_pojo.dto.BicycleDTO;
import org.kiteseven.bms_pojo.dto.BicycleUpdateDTO;
import org.kiteseven.bms_pojo.entity.Bicycles;
import org.kiteseven.bms_pojo.vo.BicyclesVO;

import java.util.List;
@Mapper
public interface BicycleMapper {
 Bicycles getBicycle(Integer id);
 List<BicyclesVO> getBicycles();

 void addBicycles(BicycleDTO bicycleDTO);
 Long getAllBicyclesCount();

 void updateBicycle(BicycleUpdateDTO bicycleUpdateDTO);
 void deleteBicycle(Integer id);

 List<BicyclesVO> getAvailableBike();

 Long getAvailableBikeCounts();

}
