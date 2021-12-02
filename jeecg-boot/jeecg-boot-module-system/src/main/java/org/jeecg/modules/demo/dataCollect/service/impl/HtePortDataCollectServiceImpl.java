package org.jeecg.modules.demo.dataCollect.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.calculate.PolandCalculator;
import org.jeecg.modules.demo.algorithm.entity.HtePortDataCollectAlgorithm;
import org.jeecg.modules.demo.algorithm.mapper.HtePortDataCollectAlgorithmMapper;
import org.jeecg.modules.demo.dataCollect.entity.HtePortDataCollect;
import org.jeecg.modules.demo.dataCollect.entity.HtePortDataCollectDetail;
import org.jeecg.modules.demo.dataCollect.mapper.HtePortDataCollectDetailMapper;
import org.jeecg.modules.demo.dataCollect.mapper.HtePortDataCollectMapper;
import org.jeecg.modules.demo.dataCollect.service.IHtePortDataCollectService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Collection;
import java.util.Map;

/**
 * @Description: 质量数据采集概览表
 * @Author: jeecg-boot
 * @Date: 2021-12-02
 * @Version: V1.0
 */
@Service
public class HtePortDataCollectServiceImpl extends ServiceImpl<HtePortDataCollectMapper, HtePortDataCollect> implements IHtePortDataCollectService {

    @Autowired
    private HtePortDataCollectMapper htePortDataCollectMapper;
    @Autowired
    private HtePortDataCollectDetailMapper htePortDataCollectDetailMapper;
    @Autowired
    private HtePortDataCollectAlgorithmMapper htePortDataCollectAlgorithmMapper;

    @Override
    @Transactional
    public void saveMain(HtePortDataCollect htePortDataCollect, List<HtePortDataCollectDetail> htePortDataCollectDetailList) {
        htePortDataCollectMapper.insert(htePortDataCollect);
        if (htePortDataCollectDetailList != null && htePortDataCollectDetailList.size() > 0) {
            for (HtePortDataCollectDetail entity : htePortDataCollectDetailList) {
                //外键设置
                entity.setParentId(htePortDataCollect.getId());
                //计算逆波兰表达式
                HtePortDataCollectAlgorithm algorithm = htePortDataCollectAlgorithmMapper.selectOne(new QueryWrapper<HtePortDataCollectAlgorithm>().eq("test_index_id", entity.getTestIndexId()));
                String exp = algorithm.getPolandExp();
                String detailValue = entity.getDetailValue();
                String res = swapExpValue(exp, detailValue);
                double v = PolandCalculator.calculate(res);
                entity.setTestValue(v);
                htePortDataCollectDetailMapper.insert(entity);
            }
        }
    }

    /**
     * 替换波兰表达式的值
     *
     * @param exp
     * @param detailValue
     * @return
     */
    private String swapExpValue(String exp, String detailValue) {
        String[] detail = detailValue.split(";");
        Map<String, String> map = new HashMap<>();
        for (String d : detail) {
            map.put(d.split(":")[0], d.split(":")[1]);
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            exp = exp.replace(entry.getKey(), entry.getValue());
        }
        return exp;
    }

    @Override
    @Transactional
    public void updateMain(HtePortDataCollect htePortDataCollect, List<HtePortDataCollectDetail> htePortDataCollectDetailList) {
        htePortDataCollectMapper.updateById(htePortDataCollect);

        //1.先删除子表数据
        htePortDataCollectDetailMapper.deleteByMainId(htePortDataCollect.getId());

        //2.子表数据重新插入
        if (htePortDataCollectDetailList != null && htePortDataCollectDetailList.size() > 0) {
            for (HtePortDataCollectDetail entity : htePortDataCollectDetailList) {
                //外键设置
                entity.setParentId(htePortDataCollect.getId());
                htePortDataCollectDetailMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional
    public void delMain(String id) {
        htePortDataCollectDetailMapper.deleteByMainId(id);
        htePortDataCollectMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void delBatchMain(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            htePortDataCollectDetailMapper.deleteByMainId(id.toString());
            htePortDataCollectMapper.deleteById(id);
        }
    }

}
