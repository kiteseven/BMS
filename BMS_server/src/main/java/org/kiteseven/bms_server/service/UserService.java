package org.kiteseven.bms_server.service;

import org.kiteseven.bms_common.result.PageResult;
import org.kiteseven.bms_pojo.dto.LoginDTO;
import org.kiteseven.bms_pojo.dto.UserDTO;
import org.kiteseven.bms_pojo.dto.UserUpdateDTO;
import org.kiteseven.bms_pojo.vo.LoginVO;
import org.kiteseven.bms_pojo.vo.UserVO;

public interface UserService {
    void addUser(UserDTO userDTO);

    PageResult getAllUsers();
    void deleteUser(Integer id);

    void updateUser(UserUpdateDTO userUpdateDTO);

    LoginVO userLogin(LoginDTO loginDTO);

    UserVO getUserData(Integer userId);
}
