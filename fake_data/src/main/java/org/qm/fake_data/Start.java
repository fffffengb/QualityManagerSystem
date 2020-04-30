package org.qm.fake_data;

import org.qm.common.dao.dataDao.group.GroupAvgDao;
import org.qm.common.dao.dataDao.group.GroupDailyDao;
import org.qm.common.dao.dataDao.group.GroupOnlineDao;
import org.qm.common.dao.dataDao.stat.StatAvgDao;
import org.qm.common.dao.dataDao.stat.StatDailyDao;
import org.qm.common.dao.dataDao.stat.StatOnlineDao;
import org.qm.common.dao.dataDao.workshop.WorkshopAvgDao;
import org.qm.common.dao.dataDao.workshop.WorkshopDailyDao;
import org.qm.common.dao.dataDao.workshop.WorkshopOnlineDao;
import org.qm.common.utils.IdWorker;
import org.qm.domain.data.group.DGroupAvg;
import org.qm.domain.data.group.DGroupDaily;
import org.qm.domain.data.group.DGroupOnline;
import org.qm.domain.data.stat.DStatAvg;
import org.qm.domain.data.stat.DStatDaily;
import org.qm.domain.data.stat.DStatOnline;
import org.qm.domain.data.workshop.DWorkshopAvg;
import org.qm.domain.data.workshop.DWorkshopDaily;
import org.qm.domain.data.workshop.DWorkshopOnline;
import org.qm.fake_data.main_data_fake.FakeDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    private StatDailyDao statDailyDao;
    private GroupDailyDao groupDailyDao;
    private WorkshopDailyDao workshopDailyDao;
    private FakeDataUtils fakeDataUtils;
    private BaseQmCfg baseQmCfg;
    private IdWorker idWorker;

    @Autowired
    public Start(StatOnlineDao statOnlineDao, GroupOnlineDao groupOnlineDao, WorkshopOnlineDao workshopOnlineDao,
                 StatAvgDao statAvgDao, GroupAvgDao groupAvgDao, WorkshopAvgDao workshopAvgDao,
                 StatDailyDao statDailyDao, GroupDailyDao groupDailyDao, WorkshopDailyDao workshopDailyDao,
                 FakeDataUtils fakeDataUtils, BaseQmCfg baseQmCfg, IdWorker idWorker) {
        this.statOnlineDao = statOnlineDao;
        this.groupOnlineDao = groupOnlineDao;
        this.workshopOnlineDao = workshopOnlineDao;
        this.statAvgDao = statAvgDao;
        this.groupAvgDao = groupAvgDao;
        this.workshopAvgDao = workshopAvgDao;
        this.statDailyDao = statDailyDao;
        this.groupDailyDao = groupDailyDao;
        this.workshopDailyDao = workshopDailyDao;
        this.fakeDataUtils = fakeDataUtils;
        this.baseQmCfg = baseQmCfg;
        this.idWorker = idWorker;
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
        //计算大组平均数据
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

    }

    //每天23：55插入daily数据
    @Scheduled(cron = "0/10 * * * * ?")
    public void generateDaily() {
        generateStatDaily();
        generateGroupDaily();
        generateWorkshopDaily();
        System.out.println("每日数据输出完毕");
    }

    private void generateStatDaily() {
        //查找当前时间点avg表中的数据
        List<DStatAvg> allStatAvg = statAvgDao.findAll();
        //构造daily数据
        List<DStatDaily> res = new ArrayList<>();
        for (DStatAvg raw : allStatAvg) {
            String id = idWorker.nextId() + "";
            String workshopId = raw.getStatId();
            Date date = raw.getTime();
            Double quality = raw.getQuality();
            Double workHour = raw.getWorkHour();
            DStatDaily target = new DStatDaily(id, workshopId, quality, workHour, date);
            res.add(target);
        }
        statDailyDao.saveAll(res);
    }
    private void generateGroupDaily() {
        //查找当前时间点avg表中的数据
        List<DGroupAvg> allGroupAvg = groupAvgDao.findAll();
        //构造daily数据
        List<DGroupDaily> res = new ArrayList<>();
        for (DGroupAvg raw : allGroupAvg) {
            String id = idWorker.nextId() + "";
            String workshopId = raw.getGroupId();
            Date date = raw.getTime();
            Double quality = raw.getQuality();
            Double workHour = raw.getWorkHour();
            DGroupDaily target = new DGroupDaily(id, workshopId, quality, workHour, date);
            res.add(target);
        }
        groupDailyDao.saveAll(res);
    }
    private void generateWorkshopDaily() {
        //查找当前时间点avg表中的数据
        List<DWorkshopAvg> allWorkShopAvg = workshopAvgDao.findAll();
        //构造daily数据
        List<DWorkshopDaily> res = new ArrayList<>();
        for (DWorkshopAvg raw : allWorkShopAvg) {
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
