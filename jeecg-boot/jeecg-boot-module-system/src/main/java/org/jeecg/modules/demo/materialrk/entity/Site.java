package org.jeecg.modules.demo.materialrk.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Site {
    private String siteId;
    private String siteName;
    private String siteCode;
}
