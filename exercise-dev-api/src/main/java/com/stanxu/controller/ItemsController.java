package com.stanxu.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.stanxu.pojo.Items;
import com.stanxu.pojo.ItemsImg;
import com.stanxu.pojo.ItemsParam;
import com.stanxu.pojo.ItemsSpec;
import com.stanxu.pojo.vo.ItemInfoVO;
import com.stanxu.pojo.vo.ItemsCommentsCountsVO;
import com.stanxu.service.ItemService;
import com.stanxu.utils.JSONResult;
import com.stanxu.utils.PagedGridResult;
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

    @ApiOperation(value = "Query comments level",notes = "Query comments level", httpMethod = "GET")
    @GetMapping("/comments")
    public JSONResult comments(
            @ApiParam(name = "itemId",value = "item id",required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level",value = "level",required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page",value = "page",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",value = "pageSize",required = false)
            @RequestParam Integer pageSize){

        if (StringUtils.isBlank(itemId)){
            return JSONResult.errorMsg(null);
        }

        if (page == null){
            page = 1;
        }

        if (pageSize == null){
            pageSize = BaseController.COMMON_PAGE_SIZE;
        }

        PagedGridResult gridResult = itemService.queryItemsCommentsLevels(itemId,level,page,pageSize);

        return JSONResult.ok(gridResult);
    }

    @ApiOperation(value = "Search items",notes = "Search items", httpMethod = "GET")
    @GetMapping("/search")
    public JSONResult search(
            @ApiParam(name = "keywords",value = "keywords",required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort",value = "sort",required = false)
            @RequestParam String sort,
            @ApiParam(name = "page",value = "page",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",value = "pageSize",required = false)
            @RequestParam Integer pageSize){

        if (StringUtils.isBlank(keywords)){
            return JSONResult.errorMsg(null);
        }

        if (page == null){
            page = 1;
        }

        if (pageSize == null){
            pageSize = BaseController.PAGE_SIZE;
        }

        PagedGridResult gridResult = itemService.searchItems(keywords,sort,page,pageSize);

        return JSONResult.ok(gridResult);
    }

    @ApiOperation(value = "Search items via category id",notes = "Search items via category id", httpMethod = "GET")
    @GetMapping("/catItems")
    public JSONResult catItems(
            @ApiParam(name = "catId",value = "category Id",required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort",value = "sort",required = false)
            @RequestParam String sort,
            @ApiParam(name = "page",value = "page",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",value = "pageSize",required = false)
            @RequestParam Integer pageSize){

        if (catId == null){
            return JSONResult.errorMsg(null);
        }

        if (page == null){
            page = 1;
        }

        if (pageSize == null){
            pageSize = BaseController.PAGE_SIZE;
        }

        PagedGridResult gridResult = itemService.searchItemsByThirdCat(catId,sort,page,pageSize);

        return JSONResult.ok(gridResult);
    }
}
