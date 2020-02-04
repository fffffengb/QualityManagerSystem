package org.qm.fake_data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.qm.common.dao.dataDao.group.GroupOnlineDao;
import org.qm.common.dao.dataDao.stat.StatAvgDao;
import org.qm.common.dao.dataDao.workshop.WorkshopAvgDao;
import org.qm.common.dao.dataDao.workshop.WorkshopDailyDao;
import org.qm.common.utils.IdWorker;
import org.qm.common.utils.QueryUtils;
import org.qm.domain.data.workshop.DWorkshopAvg;
import org.qm.domain.data.workshop.DWorkshopDaily;
import org.qm.fake_data.other_fake.FakeBaseTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    @Test
    public void t() {
        start.generateOnlineAndAvg();
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
    @Test
    public void fakeOtherTable() {
        fakeBaseTable.fakeWorkshopGroup(10, 50);
    }

    @Test
    public void seTest() {
        try{
            Integer val = Integer.parseInt("sda");
            System.out.println(val);
        } catch (Exception e) {
            System.out.println("sda");
        }

        Integer val2 = Integer.parseInt("52246");
        System.out.println(val2);
    }

}
