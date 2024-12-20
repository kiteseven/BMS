package org.kiteseven.bms_server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.kiteseven.bms_pojo.dto.UserDTO;
import org.kiteseven.bms_pojo.dto.UserUpdateDTO;
import org.kiteseven.bms_pojo.entity.User;
import org.kiteseven.bms_pojo.vo.UserVO;

import java.util.List;
@Mapper
public interface UserMapper {
    void addUser(UserDTO userDTO);

    List<UserVO> getAllUser();

    Long getAllUserCount();

    void deleteUser(Integer id);

    void updateUser(UserUpdateDTO userUpdateDTO);

    User getUserByUsername(String username);

    UserVO getUserData(Integer userid);

}
