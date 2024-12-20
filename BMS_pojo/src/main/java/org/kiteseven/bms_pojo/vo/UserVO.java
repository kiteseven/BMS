package org.kiteseven.bms_pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO {
    private Integer userId;
    private String username;
    private String phoneNumber;
    private Integer creditStatus;//信用状态
    private Integer role;//0为用户,1为管理
}
