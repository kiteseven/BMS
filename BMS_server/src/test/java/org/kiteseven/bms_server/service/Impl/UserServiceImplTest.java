package org.kiteseven.bms_server.service.Impl;

import org.junit.jupiter.api.Test;
import org.kiteseven.bms_common.result.PageResult;
import org.kiteseven.bms_pojo.vo.UserVO;
import org.kiteseven.bms_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Test
    public void testSearchUser() {
        //数据
        String username = "小时";
        PageResult pageResult = userService.searchUser(username);
        UserVO userVO=new UserVO(2, "小时", "13232342", 1, 1);
        assertNotNull(pageResult);
        System.out.println(pageResult);
        assertEquals(1, pageResult.getTotal());
        assertEquals(userVO,pageResult.getRecords().get(0));
    }
}
