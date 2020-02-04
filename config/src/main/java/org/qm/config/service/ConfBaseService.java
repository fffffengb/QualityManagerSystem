package org.qm.config.service;

import org.qm.common.dao.configDao.ConfBaseDao;
import org.qm.common.exception.NoSuchIdException;
import org.qm.common.utils.IdWorker;
import org.qm.domain.conf.ConfBaseInquire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfBaseService {
    private ConfBaseDao confBaseDao;
    private IdWorker idWorker;

    @Autowired
    public ConfBaseService(ConfBaseDao confBaseDao, IdWorker idWorker) {
        this.confBaseDao = confBaseDao;
        this.idWorker = idWorker;
    }

    public List<ConfBaseInquire> findAll() {
        return confBaseDao.findAll();
    }

    public ConfBaseInquire findById(String id) {
        if (id == null || !confBaseDao.findById(id).isPresent()) throw new NoSuchIdException("没有这个设置：" + id);
        return confBaseDao.findById(id).get();
    }

    public ConfBaseInquire deleteById(String id) {
        ConfBaseInquire target = findById(id);
        confBaseDao.delete(target);
        return target;
    }

    public ConfBaseInquire update(ConfBaseInquire old) {
        ConfBaseInquire target = findById(old.getId());
        if (old.getName() != null) target.setName(old.getName());
        if (old.getDescription() != null) target.setDescription(old.getDescription());
        confBaseDao.save(target);
        return target;
    }

    public ConfBaseInquire add(ConfBaseInquire confBase) {
        confBase.setId(idWorker.nextId() + "");
        confBaseDao.save(confBase);
        return confBase;
    }
}
