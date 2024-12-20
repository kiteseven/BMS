package org.kiteseven.bms_pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rental implements Serializable {
    private Integer rentalId;
    private Integer bicycleId;
    private Integer userId;
    private LocalDateTime rentTime;
    private LocalDateTime returnTime;
    private double rentalFee;
}
