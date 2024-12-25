package org.kiteseven.bms_server.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.kiteseven.bms_common.result.PageResult;
import org.kiteseven.bms_common.result.Result;
import org.kiteseven.bms_pojo.dto.UserDTO;
import org.kiteseven.bms_pojo.dto.UserUpdateDTO;
import org.kiteseven.bms_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/userManage")
public class UserManageController {
    @Autowired
    UserService userService;
    /**
     * 管理端增加用户
     * @param userDTO
     * @return
     */
    @PostMapping("/add")
    public Result addUser(@RequestBody UserDTO userDTO){
        log.info("管理端增加用户:{}",userDTO);
        userService.addUser(userDTO);
        return Result.success();
    }
    /**
     * 管理端获取用户
     *
     * @return
     */
    @GetMapping
    public Result<PageResult> getUsers(){
        log.info("管理端获取用户");
        return Result.success(userService.getAllUsers());
    }
    /**
     * 管理端删除用户
     *
     * @return
     */
    @DeleteMapping("/delete/{userId}")
    public Result deleteUser(@PathVariable Integer userId){
        userService.deleteUser(userId);
        return Result.success();
    }
    /**
     * 管理端更新用户信息
     *
     * @return
     */
    @PutMapping("/updateUser")
    public Result updateUser(@RequestBody UserUpdateDTO userUpdateDTO){
        log.info("管理端更新用户信息：{}",userUpdateDTO);
        userService.updateUser(userUpdateDTO);
        return Result.success();
    }

    /**
     * 管理端查询用户
     * @param username
     * @return
     */
    @GetMapping("/search")
    public Result<PageResult> searchUser(@Param("username") String username){
        log.info("查询用户名为：{}的用户",username);
        return Result.success(userService.searchUser(username));
    }
}
