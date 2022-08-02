package com.stanxu.service;

import com.stanxu.pojo.Items;
import com.stanxu.pojo.ItemsImg;
import com.stanxu.pojo.ItemsParam;
import com.stanxu.pojo.ItemsSpec;
import com.stanxu.pojo.vo.CommentsLevelVO;
import com.stanxu.pojo.vo.ItemsCommentsCountsVO;
import com.stanxu.pojo.vo.SearchItemsVO;
import com.stanxu.pojo.vo.ShopcartVO;
import com.stanxu.utils.PagedGridResult;

import java.util.List;

public interface ItemService {

    public Items queryItemById(String itemId);

    public List<ItemsImg> queryItemsImgList(String itemId);

    public List<ItemsSpec> queryItemsSpecList(String itemId);

    public ItemsParam queryItemsParam(String itemId);

    public ItemsCommentsCountsVO queryItemsCommentsCounts(String itemId);

    public PagedGridResult queryItemsCommentsLevels(String itemId, Integer level, Integer page, Integer pageSize);

    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

    public PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize);

    public List<ShopcartVO> queryShopcartItemsBySpecIds(String specIds);
}
