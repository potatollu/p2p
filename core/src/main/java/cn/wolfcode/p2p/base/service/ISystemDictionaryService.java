package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.SystemDictionary;
import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;
import cn.wolfcode.p2p.base.query.SystemDictionaryQueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ISystemDictionaryService {
    List<SystemDictionaryItem> listItemBySn(String sn);
    PageInfo queryPage(SystemDictionaryQueryObject qo);

    void saveOrUpdate(SystemDictionary dir);

    PageInfo queryItem(SystemDictionaryQueryObject qo);

    List<SystemDictionary> queryAllDir();

    void saveOrUpdateItem(SystemDictionaryItem item);
}
