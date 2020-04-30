package org.qm.fake_data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.qm.common.dao.dataDao.group.GroupOnlineDao;
import org.qm.common.dao.dataDao.stat.StatAvgDao;
import org.qm.common.dao.dataDao.stat.StatDailyDao;
import org.qm.common.dao.dataDao.workshop.WorkshopAvgDao;
import org.qm.common.dao.dataDao.workshop.WorkshopDailyDao;
import org.qm.common.utils.IdWorker;
import org.qm.common.utils.QueryUtils;
import org.qm.domain.data.stat.DStatAvg;
import org.qm.domain.data.stat.DStatDaily;
import org.qm.domain.data.workshop.DWorkshopAvg;
import org.qm.domain.data.workshop.DWorkshopDaily;
import org.qm.fake_data.other_fake.FakeBaseTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FakeDataTest {

    @Autowired
    Start start;
    @Autowired
    GroupOnlineDao groupOnlineDao;
    @Autowired
    BaseQmCfg baseQmCfg;
    @Autowired
    FakeBaseTable fakeBaseTable;
    @Autowired
    QueryUtils queryUtils;
    @Autowired
    StatAvgDao statAvgDao;
    @Autowired
    WorkshopAvgDao workshopAvgDao;
    @Autowired
    WorkshopDailyDao workshopDailyDao;
    @Autowired
    IdWorker idWorker;
    @Autowired
    StatDailyDao statDailyDao;
    @Test
    public void t() {
        for (int i = 0; i < 10; i++) {
            //查找当前时间点avg表中的数据
            List<DStatAvg> allStatAvg = statAvgDao.findAll();
            //构造daily数据
            List<DStatDaily> res = new ArrayList<>();
            for (DStatAvg raw : allStatAvg) {
                String id = idWorker.nextId() + "";
                String workshopId = raw.getStatId();
                Date date = new Date(); //取时间
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.add(calendar.DATE,i); //把日期往后增加一天,整数  往后推,负数往前移动
                date = calendar.getTime(); //这个时间就是日期往后推一天的结果
                Double quality = raw.getQuality();
                Double workHour = raw.getWorkHour();
                DStatDaily target = new DStatDaily(id, workshopId, quality, workHour, date);
                res.add(target);
            }
            statDailyDao.saveAll(res);
        }
    }

    @Test
    public void foo() {
        List<DWorkshopAvg> all = workshopAvgDao.findAll();
        List<DWorkshopDaily> res = new ArrayList<>();
        for (DWorkshopAvg raw : all) {
            String id = idWorker.nextId() + "";
            String workshopId = raw.getWorkshopId();
            Date date = raw.getTime();
            Double quality = raw.getQuality();
            Double workHour = raw.getWorkHour();
            DWorkshopDaily target = new DWorkshopDaily(id, workshopId, quality, workHour, date);
            res.add(target);
        }
        workshopDailyDao.saveAll(res);
    }

}
