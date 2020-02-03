package org.qm.fake_data.fake_data.other_fake;

import org.qm.common.dao.structureDao.WorkshopGroupDao;
import org.qm.domain.base.WorkshopGroup;
import org.qm.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FakeBaseTable {
    private WorkshopGroupDao workshopGroupDao;
    private IdWorker idWorker;

    @Autowired
    public FakeBaseTable(WorkshopGroupDao workshopGroupDao, IdWorker idWorker) {
        this.workshopGroupDao = workshopGroupDao;
        this.idWorker = idWorker;
    }

    public void fakeWorkshopGroup(int workshopNum, int groupPerWorkshopNum) {
        List<WorkshopGroup> list = new ArrayList<>();
        WorkshopGroup cur;
        int k = 1;
        for (int i = 1; i <= workshopNum; i++) {
            for (int j = 1; j <= groupPerWorkshopNum; j++) {
                cur = new WorkshopGroup(idWorker.nextId() + "", i + "", k++ + "");
                list.add(cur);
            }
        }
        workshopGroupDao.saveAll(list);
    }
}
