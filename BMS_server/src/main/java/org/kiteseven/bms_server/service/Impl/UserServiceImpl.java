package org.kiteseven.bms_server.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.kiteseven.bms_common.exception.BaseException;
import org.kiteseven.bms_common.result.PageResult;
import org.kiteseven.bms_common.utils.JwtUtil;
import org.kiteseven.bms_pojo.dto.LoginDTO;
import org.kiteseven.bms_pojo.dto.UserDTO;
import org.kiteseven.bms_pojo.dto.UserUpdateDTO;
import org.kiteseven.bms_pojo.entity.User;
import org.kiteseven.bms_pojo.vo.LoginVO;
import org.kiteseven.bms_pojo.vo.UserVO;
import org.kiteseven.bms_server.mapper.UserMapper;
import org.kiteseven.bms_server.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public void addUser(UserDTO userDTO) {
        userMapper.addUser(userDTO);
    }

    @Override
    public PageResult getAllUsers() {
        List<UserVO> list=userMapper.getAllUser();
        Long total=userMapper.getAllUserCount();
        return new PageResult(total,list);
    }

    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);
    }

    @Override
    public void updateUser(UserUpdateDTO userUpdateDTO) {
        userMapper.updateUser(userUpdateDTO);
    }

    @Override
    public LoginVO userLogin(LoginDTO loginDTO) {
        User user = userMapper.getUserByUsername(loginDTO.getUsername());
        if (user==null){
            throw new BaseException("用户不存在");
        }
        if(!Objects.equals(loginDTO.getPassword(), user.getPassword())){
            throw new BaseException("密码错误");
        }
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",user.getUserId());
        String token = JwtUtil.createJWT("jwtProperties.getUserSecretKeyString()",claims);
        LoginVO loginVO= LoginVO.builder()
                .userId(user.getUserId())
                .creditStatus(user.getCreditStatus())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .token(token)
                .username(user.getUsername())
                .build();
        log.info("当前用户登录成功：{}",loginVO);
        return loginVO;
    }

    @Override
    public UserVO getUserData(Integer userId) {
        return  userMapper.getUserData(userId);
    }
}
