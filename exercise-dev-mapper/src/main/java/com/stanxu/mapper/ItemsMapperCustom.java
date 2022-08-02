package com.stanxu.mapper;

import com.stanxu.my.mapper.MyMapper;
import com.stanxu.pojo.Items;
import com.stanxu.pojo.vo.CommentsLevelVO;
import com.stanxu.pojo.vo.SearchItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {

    public List<CommentsLevelVO> queryCommentsLevel(@Param("paramsMap")Map<String, Object> map);

    public List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map);

    public List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> map);
}