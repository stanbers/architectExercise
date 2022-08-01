package com.stanxu.service.impl;

import com.stanxu.enums.CommentsLevel;
import com.stanxu.mapper.*;
import com.stanxu.pojo.*;
import com.stanxu.pojo.vo.ItemsCommentsCountsVO;
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

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

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

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsCommentsCountsVO queryItemsCommentsCounts(String itemId) {
        Integer goodCommentsCount = getCommentsLevelCount(itemId, CommentsLevel.GOOD.type);
        Integer normalCommentsCount = getCommentsLevelCount(itemId,CommentsLevel.NORMAL.type);
        Integer badCommentsCount = getCommentsLevelCount(itemId,CommentsLevel.BAD.type);
        Integer totalCommentsCount = goodCommentsCount + normalCommentsCount + badCommentsCount;

        ItemsCommentsCountsVO countsVO = new ItemsCommentsCountsVO();
        countsVO.setGoodCounts(goodCommentsCount);
        countsVO.setBadCounts(badCommentsCount);
        countsVO.setNormalCounts(normalCommentsCount);
        countsVO.setTotalCounts(totalCommentsCount);

        return countsVO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    Integer getCommentsLevelCount(String itemId, Integer level){
        ItemsComments condition = new ItemsComments();
        condition.setItemId(itemId);
        if (level != null){
            condition.setCommentLevel(level);
        }
        return itemsCommentsMapper.selectCount(condition);
    }
}
