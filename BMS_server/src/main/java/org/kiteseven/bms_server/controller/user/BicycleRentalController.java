package org.kiteseven.bms_server.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.kiteseven.bms_common.result.PageResult;
import org.kiteseven.bms_common.result.Result;
import org.kiteseven.bms_pojo.dto.BikeRentDTO;
import org.kiteseven.bms_server.service.BicyclesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/bicycles")
public class BicycleRentalController {
    @Autowired
    BicyclesService bicyclesService;

    /**
     * 用户端获取可租借的单车
     *
     * @return
     */
    @GetMapping("/available")
    public Result<PageResult> getAvailableBike(){
        log.info("用户端获取可租借的单车");
        return Result.success(bicyclesService.getAvailableBike());
    }
    /**
     * 用户端租借单车
     *
     * @return
     */
    @PostMapping("/rent/{bicycleId}")
    public Result rentBike(@RequestBody BikeRentDTO bikeRentDTO){
        log.info("id为：{}的用户租用id为{}的单车",bikeRentDTO.getUserId(),bikeRentDTO.getBicycleId());
        bicyclesService.rentBike(bikeRentDTO);
        return Result.success();
    }
}
