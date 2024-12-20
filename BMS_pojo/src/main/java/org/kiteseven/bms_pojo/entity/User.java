package org.kiteseven.bms_pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private Integer userId;
    private String username;
    private String phoneNumber;
    private Integer creditStatus;//信用状态
    private Integer role;//0为用户,1为管理
    private String password;
}
