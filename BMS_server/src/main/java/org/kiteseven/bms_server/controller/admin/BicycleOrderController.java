package org.kiteseven.bms_server.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.kiteseven.bms_common.result.PageResult;
import org.kiteseven.bms_common.result.Result;
import org.kiteseven.bms_server.service.BicyclesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/orders")
public class BicycleOrderController {
    @Autowired
    BicyclesService bicyclesService;
    /**
     * 管理端获取所有订单
     *
     * @return
     */
    @GetMapping("/getRentalOrder")
    public Result<PageResult> getOrders(){
        log.info("管理端获取所有订单");
        return Result.success(bicyclesService.getOrders());
    }
    /**
     * 管理端修改订单状态为完成
     *
     * @return
     */
    @PutMapping("/complete/{rentalId}")
    public Result completeOrder(@PathVariable Integer rentalId){
        log.info("完成订单id为：{}的订单",rentalId);
        bicyclesService.completeOrder(rentalId);
        return Result.success();
    }
    /**
     * 管理端修改订单状态为取消
     *
     * @return
     */
    @PutMapping("/cancel/{rentalId}")
    public Result cancelOrder(@PathVariable Integer rentalId){
        log.info("取消订单id为：{}的订单",rentalId);
        bicyclesService.cancelOrder(rentalId);
        return Result.success();
    }
}
