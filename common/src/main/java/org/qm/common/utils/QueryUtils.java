package org.qm.common.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.qm.common.dao.structureDao.GroupDao;
import org.qm.common.dao.structureDao.GroupStatMemberDao;
import org.qm.common.dao.structureDao.WorkshopDao;
import org.qm.common.dao.structureDao.WorkshopGroupDao;
import org.qm.domain.base.*;
import org.qm.domain.system.response.ProfileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class QueryUtils {

    private GroupDao groupDao;
    private WorkshopDao workshopDao;
    private WorkshopGroupDao workshopGroupDao;
    private GroupStatMemberDao groupStatMemberDao;

    @Autowired
    public QueryUtils(GroupDao groupDao, WorkshopDao workshopDao, WorkshopGroupDao workshopGroupDao, GroupStatMemberDao groupStatMemberDao) {
        this.groupDao = groupDao;
        this.workshopDao = workshopDao;
        this.workshopGroupDao = workshopGroupDao;
        this.groupStatMemberDao = groupStatMemberDao;
    }

    //判断当前用户是否有查询id的权力
    public boolean hasPermFind(String id, String targetStructName) {
        List<String> allManagedIds = getAllManaged(targetStructName);
        for (String oneId : allManagedIds)
            if (oneId.equals(id)) return true;
        return false;
    }

    public List<String> getAllManaged(String managedStructName) {
        ProfileResult curUser = getCurUser();
        int curEmployeeId = curUser.getId();
        Set<String> curUserRoles = curUser.getStrRoles();
        List<String> res = new ArrayList<>();
        switch (managedStructName) {
            case "stat":
                if (curUserRoles.contains("dpt")) {
                    //查询所有工位id并返回。
                    List<GroupStatMember> allStat = groupStatMemberDao.findAll();
                    return getAllStatId(allStat);
                }else if (curUserRoles.contains("workshop")) {
                    //查询该经理管理的车间
                    String workshopId = workshopDao.findByEmployeeId(curEmployeeId).getId();
                    //查询车间下所有组
                    List<String> allGroupList = workshopGroupDao.findAllGroupIdByWorkshopId(workshopId);
                    //查询所有组下所有工位
                    return groupStatMemberDao.findAllStatIdByGroupIdIn(allGroupList);
                }else if (curUserRoles.contains("group")) {
                    //查询该组长所管理的组
                    String groupId = groupDao.findByEmployeeId(curEmployeeId).getId();
                    //查询该组下所有工位
                    return groupStatMemberDao.findAllStatIdByGroupId(groupId);
                }

            case "group":
                if (curUserRoles.contains("dpt")) {
                    //查询所有组id并返回。
                    List<Group> allGroup = groupDao.findAll();
                    return getAllId(allGroup);
                } else if (curUserRoles.contains("workshop")) {
                    //查询该经理管理的车间
                    String workshopId = workshopDao.findByEmployeeId(curEmployeeId).getId();
                    //查询车间下所有组
                    return workshopGroupDao.findAllGroupIdByWorkshopId(workshopId);
                } else if (curUserRoles.contains("group")){
                    //查询该组长所管理的组
                    String groupId = groupDao.findByEmployeeId(curEmployeeId).getId();
                    res.add(groupId);
                    return res;
                }

            case "workshop":
                if (curUserRoles.contains("dpt")) {
                    //查询所有车间id并返回。
                    List<Workshop> allWorkShop = workshopDao.findAll();
                    return getAllId(allWorkShop);
                } else if (curUserRoles.contains("workshop")) {
                    //查询该经理管理的车间
                    String workshopId = workshopDao.findByEmployeeId(curEmployeeId).getId();
                    res.add(workshopId);
                    return res;
                }
            default:
                throw new UnauthorizedException("您没有该查询权限");
        }

    }

    private List<String> getAllStatId(List<GroupStatMember> allStat) {
        List<String> res = new ArrayList<>();
        for (GroupStatMember item : allStat) {
            res.add(item.getId());
        }
        return res;
    }

    private List<String> getAllId(List<? extends BaseTableBase> baseTableList) {
        List<String> res = new ArrayList<>();
        for (BaseTableBase base : baseTableList) {
            res.add(base.getId());
        }
        return res;
    }

    public ProfileResult getCurUser() {
        Subject subject = SecurityUtils.getSubject();
        // 构造安全数据并返回
        return (ProfileResult) subject.getPrincipal();
    }

}
