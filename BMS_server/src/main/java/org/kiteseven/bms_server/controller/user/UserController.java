package org.kiteseven.bms_server.controller.user;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.kiteseven.bms_common.result.Result;
import org.kiteseven.bms_common.utils.JwtUtil;
import org.kiteseven.bms_pojo.dto.LoginDTO;
import org.kiteseven.bms_pojo.vo.LoginVO;
import org.kiteseven.bms_pojo.vo.UserVO;
import org.kiteseven.bms_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 用户端获取用户信息
     * @param token
     * @return
     */
    @GetMapping("/getUserData")
    public Result<UserVO> getUserData(@RequestHeader("Authorization") String token){
        log.info("获取用户信息");
        log.info("当前用户token:{}",token);
        // 提取 Bearer 前缀
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            return Result.error("无效的令牌");
        }
        Claims claims = JwtUtil.parseJWT("jwtProperties.getUserSecretKeyString()", token);
        int userId = Integer.parseInt(claims.get("userId").toString());
        log.info("当前用户id:{}", userId);
        return Result.success(userService.getUserData(userId));
    }
}
