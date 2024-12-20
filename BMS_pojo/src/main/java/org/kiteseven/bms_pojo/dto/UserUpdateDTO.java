package org.kiteseven.bms_pojo.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private Integer userId;
    private String username;
    private String phoneNumber;
    private Integer creditStatus;//信用状态
    private Integer role;//0为用户,1为管理
}
