package org.qm.fake_data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.qm.common.dao.dataDao.group.GroupOnlineDao;
import org.qm.common.dao.dataDao.stat.StatAvgDao;
import org.qm.common.utils.QueryUtils;
import org.qm.fake_data.fake_data.BaseQmCfg;
import org.qm.fake_data.fake_data.Start;
import org.qm.fake_data.fake_data.other_fake.FakeBaseTable;
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
    @Test
    public void t() {
        start.generateOnlineAndAvg();
    }

    @Test
    public void foo() {
        statAvgDao.deleteAll();
    }
    @Test
    public void fakeOtherTable() {
        fakeBaseTable.fakeWorkshopGroup(10, 50);
    }

    @Test
    public void seTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("2", statAvgDao);
        Object o = map.get("233");
        if (o == null) {
            System.out.println("是null");
        }
        System.out.println(o);
    }

}
