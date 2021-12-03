package org.jeecg.modules.demo.dataCollect.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HtePortDataCollectDetailVO {
    private String id;
    private String collectPointId;
    private String collectPointName;
    private String testIndexId;
    private String testIndexName;
    private double testValue;
    private String detailValue;
}
