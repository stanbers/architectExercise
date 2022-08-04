package com.stanxu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stanxu.enums.CommentsLevel;
import com.stanxu.enums.YesOrNo;
import com.stanxu.mapper.*;
import com.stanxu.pojo.*;
import com.stanxu.pojo.vo.CommentsLevelVO;
import com.stanxu.pojo.vo.ItemsCommentsCountsVO;
import com.stanxu.pojo.vo.SearchItemsVO;
import com.stanxu.pojo.vo.ShopcartVO;
import com.stanxu.service.ItemService;
import com.stanxu.utils.DesensitizationUtil;
import com.stanxu.utils.JSONResult;
import com.stanxu.utils.PagedGridResult;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

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

    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

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

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryItemsCommentsLevels(String itemId, Integer level, Integer page, Integer pageSize) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("itemId", itemId);
        map.put("level", level);

        PageHelper.startPage(page, pageSize);

        List<CommentsLevelVO> list = itemsMapperCustom.queryCommentsLevel(map);

        for (CommentsLevelVO levelVO: list) {
            levelVO.setNickname(DesensitizationUtil.commonDisplay(levelVO.getNickname()));
        }

        return setPageGrid(list, page);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    private PagedGridResult setPageGrid(List<?> list, Integer page){
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());

        return grid;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("keywords", keywords);
        map.put("sort", sort);

        PageHelper.startPage(page, pageSize);

        List<SearchItemsVO> list = itemsMapperCustom.searchItems(map);

        return setPageGrid(list, page);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("catId", catId);
        map.put("sort", sort);

        PageHelper.startPage(page, pageSize);

        List<SearchItemsVO> list = itemsMapperCustom.searchItemsByThirdCat(map);

        return setPageGrid(list, page);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ShopcartVO> queryShopcartItemsBySpecIds(String specIds) {

        String [] ids = specIds.split(",");
        List<String> list = new ArrayList<String>();
        Collections.addAll(list,ids);

        List<ShopcartVO> shopcartVOList = itemsMapperCustom.queryShopcartItemsBySpecIds(list);

        return shopcartVOList;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsSpec queryItemSpecBySpecId(String specId) {

        ItemsSpec itemsSpec = new ItemsSpec();
        itemsSpec.setId(specId);

        return itemsSpecMapper.selectOne(itemsSpec);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String queryItemsMainImgByItemId(String itemId) {

        ItemsImg itemsImg = new ItemsImg();
        itemsImg.setItemId(itemId);
        itemsImg.setIsMain(YesOrNo.YES.type);
        ItemsImg img = itemsImgMapper.selectOne(itemsImg);
        return img == null ? img.getUrl() : "";
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void decreaseInventoryAfterOrderPlaced(int pendingCounts, String specId) {
        int result = itemsMapperCustom.decreaseInventoryAfterOrderPlaced(pendingCounts, specId);
        if (result != 1){
            throw new RuntimeException("item inventory is not enough ! place order failed !");
        }
    }
}
