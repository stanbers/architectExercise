package com.stanxu.pojo.vo;

import com.stanxu.pojo.Items;
import com.stanxu.pojo.ItemsImg;
import com.stanxu.pojo.ItemsParam;
import com.stanxu.pojo.ItemsSpec;

import java.util.List;

public class ItemInfoVO {

    private Items items;

    private List<ItemsImg> itemsImgList;

    private List<ItemsSpec> itemsSpecList;

    private ItemsParam itemsParam;


    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public List<ItemsImg> getItemsImgList() {
        return itemsImgList;
    }

    public void setItemsImgList(List<ItemsImg> itemsImgList) {
        this.itemsImgList = itemsImgList;
    }

    public List<ItemsSpec> getItemsSpecList() {
        return itemsSpecList;
    }

    public void setItemsSpecList(List<ItemsSpec> itemsSpecList) {
        this.itemsSpecList = itemsSpecList;
    }

    public ItemsParam getItemsParam() {
        return itemsParam;
    }

    public void setItemsParam(ItemsParam itemsParam) {
        this.itemsParam = itemsParam;
    }
}
