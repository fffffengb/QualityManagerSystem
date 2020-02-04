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
