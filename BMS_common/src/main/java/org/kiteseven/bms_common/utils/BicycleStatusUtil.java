package org.kiteseven.bms_common.utils;

public  class BicycleStatusUtil {
    public static String getStatusText(Integer status) {
        switch (status) {
            case 1:
                return "空闲";
            case 2:
                return "租借中";
            case 3:
                return "维修中";
            case 4:
                return "不可用";
            default:
                return "未知状态";
        }
    }
}
