package com.stanxu.controller;

import com.stanxu.pojo.Items;
import com.stanxu.pojo.ItemsImg;
import com.stanxu.pojo.ItemsParam;
import com.stanxu.pojo.ItemsSpec;
import com.stanxu.pojo.vo.ItemInfoVO;
import com.stanxu.pojo.vo.ItemsCommentsCountsVO;
import com.stanxu.service.ItemService;
import com.stanxu.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("items")
@Api(value = "Items API", tags = "Items API")
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "Query item details",notes = "Query item details", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public JSONResult info(
            @ApiParam(name = "itemId",value = "item id",required = true)
            @PathVariable String itemId){

        if (StringUtils.isBlank(itemId)){
            return JSONResult.errorMsg(null);
        }

        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgList = itemService.queryItemsImgList(itemId);
        List<ItemsSpec> itemsSpecsList = itemService.queryItemsSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemsParam(itemId);

        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemParams(itemsParam);
        itemInfoVO.setItemImgList(itemsImgList);
        itemInfoVO.setItemSpecList(itemsSpecsList);

        return JSONResult.ok(itemInfoVO);
    }

    @ApiOperation(value = "Query comments count",notes = "Query comments count", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public JSONResult commentLevel(
            @ApiParam(name = "itemId",value = "item id",required = true)
            @RequestParam String itemId){

        if (StringUtils.isBlank(itemId)){
            return JSONResult.errorMsg(null);
        }

        ItemsCommentsCountsVO countsVO = itemService.queryItemsCommentsCounts(itemId);

        return JSONResult.ok(countsVO);
    }
}
