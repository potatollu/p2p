package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.IpLog;
import cn.wolfcode.p2p.base.domain.SystemDictionary;
import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;
import cn.wolfcode.p2p.base.mapper.SystemDictionaryItemMapper;
import cn.wolfcode.p2p.base.mapper.SystemDictionaryMapper;
import cn.wolfcode.p2p.base.query.IpLogQueryObject;
import cn.wolfcode.p2p.base.query.SystemDictionaryQueryObject;
import cn.wolfcode.p2p.base.service.ISystemDictionaryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SystemDictionaryServiceImpl implements ISystemDictionaryService {

    @Autowired
    private SystemDictionaryItemMapper itemMapper;
    @Autowired
    private SystemDictionaryMapper systemDictionaryMapper;

    public List<SystemDictionaryItem> listItemBySn(String sn) {
        return itemMapper.listItemBySn(sn);
    }

    /**
     * 数据字典分页
     * @param qo
     * @return
     */
    public PageInfo queryPage(SystemDictionaryQueryObject qo) {
        //静态方法,底层绑定的ThreadLocal,
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        List<SystemDictionary> list = systemDictionaryMapper.queryPage(qo);
        return new PageInfo(list);
    }

    /**
     * 保存/编辑
     * @param dir
     */
    public void saveOrUpdate(SystemDictionary dir) {
        if (dir.getId() != null){
            systemDictionaryMapper.updateByPrimaryKey(dir);
        }else {
            systemDictionaryMapper.insert(dir);
        }
    }

    /**
     * 数据字典明细分页
     * @param qo
     * @return
     */
    public PageInfo queryItem(SystemDictionaryQueryObject qo) {
        //静态方法,底层绑定的ThreadLocal,
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        List<SystemDictionaryItem> list = itemMapper.queryItem(qo);
        return new PageInfo(list);
    }

    @Override
    public List<SystemDictionary> queryAllDir() {
        return systemDictionaryMapper.selectAll();
    }

    @Override
    public void saveOrUpdateItem(SystemDictionaryItem item) {
        if (item.getId() != null){
            itemMapper.updateByPrimaryKey(item);
        }else {
            itemMapper.insert(item);
        }
    }
}
