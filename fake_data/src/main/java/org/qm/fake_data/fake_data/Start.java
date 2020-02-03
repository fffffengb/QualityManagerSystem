package org.qm.fake_data.fake_data;

import org.qm.common.dao.dataDao.group.GroupAvgDao;
import org.qm.common.dao.dataDao.group.GroupOnlineDao;
import org.qm.common.dao.dataDao.stat.StatAvgDao;
import org.qm.common.dao.dataDao.stat.StatOnlineDao;
import org.qm.common.dao.dataDao.workshop.WorkshopAvgDao;
import org.qm.common.dao.dataDao.workshop.WorkshopOnlineDao;
import org.qm.domain.data.group.DGroupAvg;
import org.qm.domain.data.group.DGroupOnline;
import org.qm.domain.data.stat.DStatAvg;
import org.qm.domain.data.stat.DStatOnline;
import org.qm.domain.data.workshop.DWorkshopAvg;
import org.qm.domain.data.workshop.DWorkshopOnline;
import org.qm.fake_data.fake_data.main_data_fake.FakeDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class Start {

    private StatOnlineDao statOnlineDao;
    private GroupOnlineDao groupOnlineDao;
    private WorkshopOnlineDao workshopOnlineDao;
    private StatAvgDao statAvgDao;
    private GroupAvgDao groupAvgDao;
    private WorkshopAvgDao workshopAvgDao;
    private FakeDataUtils fakeDataUtils;
    private BaseQmCfg baseQmCfg;

    @Autowired
    public Start(StatOnlineDao statOnlineDao, GroupOnlineDao groupOnlineDao, WorkshopOnlineDao workshopOnlineDao, StatAvgDao statAvgDao, GroupAvgDao groupAvgDao, WorkshopAvgDao workshopAvgDao, FakeDataUtils fakeDataUtils, BaseQmCfg baseQmCfg) {
        this.statOnlineDao = statOnlineDao;
        this.groupOnlineDao = groupOnlineDao;
        this.workshopOnlineDao = workshopOnlineDao;
        this.statAvgDao = statAvgDao;
        this.groupAvgDao = groupAvgDao;
        this.workshopAvgDao = workshopAvgDao;
        this.fakeDataUtils = fakeDataUtils;
        this.baseQmCfg = baseQmCfg;
    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void generateOnlineAndAvg() {

        System.out.println("开始输出数据...");
        Date yesterday = fakeDataUtils.getYesterday();
        //生产随机的工位online数据。
        List<DStatOnline> statOnlineList = fakeDataUtils.generateRandomStatData();
        statOnlineDao.saveAll(statOnlineList);
        //计算得出最近24小时avg数据
        List<DStatAvg> statAvgList = fakeDataUtils.getAvg(statOnlineDao.findByTimeAfter(yesterday), "statId",
                baseQmCfg.stat_num, DStatAvg.class);
        statAvgDao.deleteAll();
        statAvgDao.saveAll(statAvgList);


        //根据工位online计算大组online数据。
        List<DGroupOnline> groupOnlineList =
                fakeDataUtils.getNextLevelOnlineData(statOnlineList, baseQmCfg.stat_num,
                        baseQmCfg.group_num, "groupId", DGroupOnline.class);
        groupOnlineDao.saveAll(groupOnlineList);
        //计算。。。
        List<DGroupAvg> groupAvgList = fakeDataUtils.getAvg(groupOnlineDao.findByTimeAfter(yesterday), "groupId",
                baseQmCfg.group_num, DGroupAvg.class);
        groupAvgDao.deleteAll();
        groupAvgDao.saveAll(groupAvgList);


        //根据大组online计算车间online数据。
        List<DWorkshopOnline> workshopOnlineList =
                fakeDataUtils.getNextLevelOnlineData(groupOnlineList, baseQmCfg.group_num,
                        baseQmCfg.workshop_num, "workshopId", DWorkshopOnline.class);
        workshopOnlineDao.saveAll(workshopOnlineList);
        List<DWorkshopAvg> workshopAvgList = fakeDataUtils.getAvg(workshopOnlineDao.findByTimeAfter(yesterday), "workshopId",
                baseQmCfg.workshop_num, DWorkshopAvg.class);
        workshopAvgDao.deleteAll();
        workshopAvgDao.saveAll(workshopAvgList);
        //根据车间online计算生产部online数据。
    }
    //每天23：55插入daily数据
}
