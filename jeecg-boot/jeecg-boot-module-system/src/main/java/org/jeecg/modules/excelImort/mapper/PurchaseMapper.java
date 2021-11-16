package org.jeecg.modules.excelImort.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.excelImort.entity.PurchaseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
public interface PurchaseMapper {
    void myInsert(PurchaseEntity purchaseEntity);
}
