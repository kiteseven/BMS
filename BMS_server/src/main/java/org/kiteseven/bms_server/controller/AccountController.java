package org.kiteseven.bms_server.controller;

import lombok.extern.slf4j.Slf4j;
import org.kiteseven.bms_common.result.Result;
import org.kiteseven.bms_pojo.dto.LoginDTO;
import org.kiteseven.bms_pojo.vo.LoginVO;
import org.kiteseven.bms_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping()
public class AccountController {
    @Autowired
    UserService userService;

    /**
     * 登录接口
     * @param loginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO){
        log.info("用户登录：{}",loginDTO);
        return Result.success(userService.userLogin(loginDTO));
    }
}
