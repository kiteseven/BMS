package org.kiteseven.bms_server.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.kiteseven.bms_common.result.PageResult;
import org.kiteseven.bms_pojo.dto.BicycleDTO;
import org.kiteseven.bms_pojo.dto.BicycleUpdateDTO;
import org.kiteseven.bms_server.service.BicyclesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.kiteseven.bms_common.result.Result;

@Slf4j
@RestController
@RequestMapping("/bicyclesManage")//单车管理Controller
public class BicycleManageController {
    @Autowired
    BicyclesService bicyclesService;

    /**
     * 管理端添加新单车
     * @param bicycleDTO
     * @return
     */
    @PostMapping("/add")
    public Result addBicycle(@RequestBody BicycleDTO bicycleDTO){
        log.info("添加新单车：{}",bicycleDTO);
        bicyclesService.addBicycles(bicycleDTO);
        return Result.success();
    }

    /**
     * 管理端获取所有单车数据
     * @return
     */
    @GetMapping
    public Result<PageResult> getBicycle(){
        log.info("获取所有的单车数据");
        return Result.success(bicyclesService.getAllBicycles());
    }

    /**
     * 管理端更新单车数据
     * @param bicycleUpdateDTO
     * @return
     */
    @PutMapping("/update/{bicycleId}")
    public Result updateBicycle(@RequestBody BicycleUpdateDTO bicycleUpdateDTO){
        log.info("更新单车数据：{}",bicycleUpdateDTO);
        bicyclesService.updateBicycle(bicycleUpdateDTO);
        return Result.success();
    }

    /**
     * 管理端删除单车
     * @param bicycleId
     * @return
     */
    @DeleteMapping("/delete/{bicycleId}")
    public Result deleteBicycle(@PathVariable Integer bicycleId){
        log.info("删除id为：{}的单车",bicycleId);
        bicyclesService.deleteBicycle(bicycleId);
        return Result.success();
    }

    /**
     *
     * 管理端查询单车
     * @param model
     * @param location
     * @param status
     * @return
     */
    @GetMapping("/search")
    public Result<PageResult> searchBike(@Param("model") String model, @Param("location") String location, @Param("status") Integer status){
        return Result.success(bicyclesService.searchBike(model, location, status));
    }
}
