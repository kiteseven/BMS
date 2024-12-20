package org.kiteseven.bms_server.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.kiteseven.bms_common.exception.BaseException;
import org.kiteseven.bms_common.result.PageResult;
import org.kiteseven.bms_pojo.dto.BicycleDTO;
import org.kiteseven.bms_pojo.dto.BicycleUpdateDTO;
import org.kiteseven.bms_pojo.dto.BikeRentDTO;
import org.kiteseven.bms_pojo.entity.Bicycles;
import org.kiteseven.bms_pojo.entity.User;
import org.kiteseven.bms_pojo.vo.BicyclesVO;
import org.kiteseven.bms_pojo.vo.RentalVO;
import org.kiteseven.bms_pojo.vo.UserVO;
import org.kiteseven.bms_server.mapper.BicycleMapper;
import org.kiteseven.bms_server.mapper.RentalMapper;
import org.kiteseven.bms_server.mapper.UserMapper;
import org.kiteseven.bms_server.service.BicyclesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class BicyclesServiceImpl implements BicyclesService {
    @Autowired
    BicycleMapper bicycleMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RentalMapper rentalMapper;
    @Override
    public void addBicycles(BicycleDTO bicycleDTO) {
        bicycleMapper.addBicycles(bicycleDTO);
    }

    @Override
    public PageResult getAllBicycles() {
        List<BicyclesVO> list=bicycleMapper.getBicycles();
        Long total =bicycleMapper.getAllBicyclesCount();
        return new PageResult(total,list);
    }

    @Override
    @Transactional
    public void updateBicycle(BicycleUpdateDTO bicycleUpdateDTO) {
        Bicycles bicycles=bicycleMapper.getBicycle(bicycleUpdateDTO.getBicycleId());
        log.info(":{}",bicycles);
        if(bicycles.getStatus()==2){
            throw new BaseException("无法修改！该车租借中！");
        }
        bicycleMapper.updateBicycle(bicycleUpdateDTO);
    }

    @Override
    public void deleteBicycle(Integer id) {
        Bicycles bicycles=bicycleMapper.getBicycle(id);
        if(bicycles==null){
            throw new BaseException("该单车不存在");
        }
        //这里还需要添加一个判断单车是否租出，租出不能删除
        bicycleMapper.deleteBicycle(id);
    }

    @Override
    public PageResult getAvailableBike() {
        List<BicyclesVO> list=bicycleMapper.getAvailableBike();
        Long total=bicycleMapper.getAllBicyclesCount();
        return new PageResult(total,list);
    }

    @Override
    @Transactional
    public void rentBike(BikeRentDTO bikeRentDTO) {
        LocalDateTime time=LocalDateTime.now();
        bikeRentDTO.setRentTime(time);
        Bicycles bicycles=bicycleMapper.getBicycle(bikeRentDTO.getBicycleId());
        if(bicycles==null){
            throw new BaseException("该单车不存在！");
        }
        UserVO user=userMapper.getUserData(bikeRentDTO.getUserId());
        if(user.getCreditStatus()==0){
            throw new BaseException("该用户为失信用户，无法租借单车");
        }
        if(bicycles.getStatus()!=1){
            throw new BaseException("该单车暂时无法租借");
        }
        bikeRentDTO.setRentalFee(bicycles.getRentalFree());
        bicycles.setStatus(2);
        BicycleUpdateDTO bicycleDTO =new BicycleUpdateDTO();
        BeanUtils.copyProperties(bicycles,bicycleDTO);
        rentalMapper.RentalBike(bikeRentDTO);
        bicycleMapper.updateBicycle(bicycleDTO);
    }

    @Override
    public PageResult getOrders() {
        List<RentalVO> list=rentalMapper.getRentals();
        Long total=rentalMapper.getRentalsCount();
        return new PageResult(total,list);
    }

    @Override
    @Transactional
    public void completeOrder(Integer rentalId) {
        LocalDateTime returnTime =LocalDateTime.now();
        rentalMapper.completeOrder(rentalId,returnTime);
        RentalVO rentalVO=rentalMapper.getRentalData(rentalId);
        BicycleUpdateDTO bicycles=new BicycleUpdateDTO();
        bicycles.setBicycleId(rentalVO.getBicycleId());
        bicycles.setStatus(1);
        bicycleMapper.updateBicycle(bicycles);
    }

    @Override
    @Transactional
    public void cancelOrder(Integer rentalId) {
         LocalDateTime returnTime =LocalDateTime.now();
         rentalMapper.cancelOrder(rentalId,returnTime);
         RentalVO rentalVO=rentalMapper.getRentalData(rentalId);
         BicycleUpdateDTO bicycles=new BicycleUpdateDTO();
         bicycles.setBicycleId(rentalVO.getBicycleId());
         bicycles.setStatus(1);
         bicycleMapper.updateBicycle(bicycles);
    }
}
