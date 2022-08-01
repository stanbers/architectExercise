package com.stanxu.service;

import com.stanxu.pojo.Items;
import com.stanxu.pojo.ItemsImg;
import com.stanxu.pojo.ItemsParam;
import com.stanxu.pojo.ItemsSpec;

import java.util.List;

public interface ItemService {

    public Items queryItemById(String itemId);

    public List<ItemsImg> queryItemsImgList(String itemId);

    public List<ItemsSpec> queryItemsSpecList(String itemId);

    public ItemsParam queryItemsParam(String itemId);
}
