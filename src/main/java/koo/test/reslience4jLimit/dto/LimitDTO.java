package koo.test.reslience4jLimit.dto;

import lombok.Data;

@Data
public class LimitDTO {
    private int limitForPeriod;
    private int limitRefreshPeriod;
//    private String timeoutDuration;
}
