package org.jeecg.modules.demo.algorithm.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HtePortDataCollectAlgorithmVO {
    private String id;
    private String testIndexId;
    private String polandExp;
    private List<String> optional;
}
