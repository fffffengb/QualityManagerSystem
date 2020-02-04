package org.qm.fake_data.main_data_fake;

import org.apache.commons.beanutils.BeanUtils;
import org.qm.common.utils.IdWorker;
import org.qm.domain.data.DataTableBase;
import org.qm.domain.data.stat.DStatDaily;
import org.qm.domain.data.stat.DStatOnline;
import org.qm.domain.data.workshop.DWorkshopAvg;
import org.qm.domain.data.workshop.DWorkshopDaily;
import org.qm.fake_data.BaseQmCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.*;

@Component
public class FakeDataUtils {

    private BaseQmCfg baseQmCfg;
    private IdWorker idWorker;
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    @Autowired
    public FakeDataUtils(BaseQmCfg baseQmCfg, IdWorker idWorker) {
        this.baseQmCfg = baseQmCfg;
        this.idWorker = idWorker;
    }

    public <T> List<T> getNextLevelOnlineData(List<? extends DataTableBase> lists, int beforeScale,
                                              int afterScale, String name, Class<T> clazz) {
        return getBeans(aggregateData(lists, beforeScale, afterScale, name), clazz);
    }

    public <T> List<T> getAvg(List<? extends DataTableBase> allOnlineData, String name, int scale, Class<T> clazz) {
        List<Map<String, Object>> avgMap = getAvgBeanMap(allOnlineData, name, scale);
        return getBeans(avgMap, clazz);
    }

    //生产随机工位数据
    //[{"stat_id": 1, "quality": 23,
    // ]x
    public List<DStatOnline> generateRandomStatData() {
        String id;
        int statId;
        double quality, work_hour;
        Date time = new Date();

        List<DStatOnline> lists = new ArrayList<>();
        for (int i = 0; i < baseQmCfg.stat_num; i++) {
            id = idWorker.nextId() + "";
            statId = i + 1;
            quality = Double.parseDouble(decimalFormat.format(Math.random() * 100));
            work_hour = Double.parseDouble(decimalFormat.format(Math.random() * 100));
            DStatOnline dStatOnline = new DStatOnline(id, statId + "", quality, work_hour, time);
            lists.add(dStatOnline);
        }
        return lists;
    }

    private List<Map<String, Object>> aggregateData(List<? extends DataTableBase> lists, int beforeScale,
                                                    int afterScale, String name){
         //根据当前的数据规模和数据，计算结果数据规模的平均数
        int pre = beforeScale/afterScale;
        if (pre == 0) throw new RuntimeException("规模数据除数为0");
        List<Map<String, Object>> res = new ArrayList<>();
        Date time = lists.get(0).getTime();
        for (int i = 0, j = 1; i < beforeScale; i += pre, j++) {
            Map<String, Object> map = new HashMap<>();
            Double qualitySum = 0d;
            Double work_hourSum = 0d;
            for (int k = i; k < pre + i; k++) {
                qualitySum += lists.get(k).getQuality();
                work_hourSum += lists.get(k).getWorkHour();
            }
            Double quality = Double.parseDouble(decimalFormat.format(qualitySum/pre));
            Double work_hour = Double.parseDouble(decimalFormat.format(work_hourSum/pre));
            map.put("id", idWorker.nextId());
            map.put(name, j);
            if (j == afterScale) j = 0;
            map.put("quality", quality);
            map.put("workHour", work_hour);
            map.put("time", time);
            res.add(map);
        }
        return res;
    }

    private <T> List<T> getBeans(List<Map<String, Object>> rawData, Class<T> clazz) {
        T t;
        List<T> res = new ArrayList<>();
        for (Map<String, Object> map : rawData) {
            try {
                t = clazz.newInstance();
                BeanUtils.populate(t, map);
            } catch (Exception e) {
                throw new RuntimeException("封装Bean出现问题");
            }
            res.add(t);
        }
        return res;
    }

    private List<Map<String, Object>> getAvgBeanMap(List<? extends DataTableBase> allOnlineData, String name, int scale) {
        List<Map<Integer, Double>> AllQualityAndWorkHourAvg = getAllQualityAndWorkHourAvg(allOnlineData, scale);
        Map<Integer, Double> allQualityAvg = AllQualityAndWorkHourAvg.get(0);
        Map<Integer, Double> allWorkHourAvg = AllQualityAndWorkHourAvg.get(1);
        List<Map<String, Object>> res = new ArrayList<>();
        for (Integer key : allQualityAvg.keySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", idWorker.nextId());
            map.put(name, key);
            map.put("quality", allQualityAvg.get(key));
            map.put("workHour", allWorkHourAvg.get(key));
            map.put("time", new Date());
            res.add(map);
        }
        return res;
    }

    private List<Map<Integer, Double>> getAllQualityAndWorkHourAvg(List<? extends DataTableBase> allOnlineData, int scale) {
        Map<Integer, List<Double>> qualityMap = getAllQualityAndWorkHour(allOnlineData, scale).get(0);
        Map<Integer, List<Double>> workHourMap = getAllQualityAndWorkHour(allOnlineData, scale).get(1);
        Map<Integer, Double> qualityAvgMap = new HashMap<>();
        Map<Integer, Double> workHourAvgMap = new HashMap<>();
        List<Map<Integer, Double>> res = new ArrayList<>();
        computeAvg(qualityMap, qualityAvgMap);
        computeAvg(workHourMap, workHourAvgMap);
        res.add(qualityAvgMap);
        res.add(workHourAvgMap);
        return res;
    }

    private void computeAvg(Map<Integer, List<Double>> dataMap, Map<Integer, Double> avgMap) {
        for (Integer key : dataMap.keySet()){
            Double sum = 0d;
            List<Double> lise = dataMap.get(key);
            for (Double val : lise) {
                sum += val;
            }
            avgMap.put(key, Double.parseDouble(decimalFormat.format(sum/ dataMap.get(key).size())));
        }
    }

    private List<Map<Integer, List<Double>>> getAllQualityAndWorkHour(List<? extends DataTableBase> allOnlineData, int scale) {
        Map<Integer, List<Double>> qualityMap = new HashMap<>();
        Map<Integer, List<Double>> workHourMap = new HashMap<>();
        List<Map<Integer, List<Double>>> res = new ArrayList<>();

        for (int i = 0; i < scale; i++) {
            qualityMap.put(i + 1, new ArrayList<>());
            workHourMap.put(i + 1, new ArrayList<>());
        }
        int i = 1;
        for (DataTableBase dataTableBase : allOnlineData) {
            List<Double> qualityArray = qualityMap.get(i);
            List<Double> workHourArray = workHourMap.get(i);
            qualityArray.add(dataTableBase.getQuality());
            workHourArray.add(dataTableBase.getWorkHour());
            if (++i > scale) i = 1;
        }
        res.add(qualityMap);
        res.add(workHourMap);
        return res;
    }

    public Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE , -1);
        return calendar.getTime();
    }


}
