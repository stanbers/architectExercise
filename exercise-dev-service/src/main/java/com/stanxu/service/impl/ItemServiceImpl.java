package com.stanxu.service.impl;

import com.stanxu.mapper.ItemsImgMapper;
import com.stanxu.mapper.ItemsMapper;
import com.stanxu.mapper.ItemsParamMapper;
import com.stanxu.mapper.ItemsSpecMapper;
import com.stanxu.pojo.Items;
import com.stanxu.pojo.ItemsImg;
import com.stanxu.pojo.ItemsParam;
import com.stanxu.pojo.ItemsSpec;
import com.stanxu.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsParamMapper itemsParamMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ItemsImg> queryItemsImgList(String itemId) {
        Example itemsExample = new Example(ItemsImg.class);
        Example.Criteria criteria = itemsExample.createCriteria();
        criteria.andEqualTo("itemId", itemId);

        return itemsImgMapper.selectByExample(itemsExample);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ItemsSpec> queryItemsSpecList(String itemId) {
        Example itemsSpecExample = new Example(ItemsSpec.class);
        Example.Criteria criteria = itemsSpecExample.createCriteria();
        criteria.andEqualTo("itemId", itemId);

        return itemsSpecMapper.selectByExample(itemsSpecExample);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ItemsParam queryItemsParam(String itemId) {
        Example itemsParamExample = new Example(ItemsParam.class);
        Example.Criteria criteria = itemsParamExample.createCriteria();
        criteria.andEqualTo("itemId", itemId);

        return itemsParamMapper.selectOneByExample(itemsParamExample);
    }
}
