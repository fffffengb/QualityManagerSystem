package org.qm.data.onlineData.service;

import org.qm.common.dao.dataDao.group.GroupOnlineDao;
import org.qm.common.utils.QueryUtils;
import org.qm.domain.data.group.DGroupOnline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupOnlineService {

    private GroupOnlineDao groupOnlineDao;
    private QueryUtils queryUtils;

    @Autowired
    public GroupOnlineService(GroupOnlineDao groupOnlineDao, QueryUtils queryUtils) {
        this.groupOnlineDao = groupOnlineDao;
        this.queryUtils = queryUtils;
    }

    public Page<DGroupOnline> findAll(int page, int size) {
        List<String> allManagedIds = queryUtils.getAllManaged("group");
        return groupOnlineDao.findAllByGroupIdIn(allManagedIds, PageRequest.of(page - 1, size));
    }

    public boolean hasPermFind(String groupId) {
        return queryUtils.hasPermFind(groupId, "group");
    }

    public Page<DGroupOnline> findAllByGroupId(String groupId, int page, int size) {
        return groupOnlineDao.findAllByGroupId(groupId, PageRequest.of(page - 1, size));
    }
}
