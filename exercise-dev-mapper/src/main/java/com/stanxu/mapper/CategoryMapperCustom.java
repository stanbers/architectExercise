package com.stanxu.mapper;

import com.stanxu.my.mapper.MyMapper;
import com.stanxu.pojo.Category;
import com.stanxu.pojo.vo.CategoryVO;
import com.stanxu.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom {

    public List<CategoryVO> getSubCategoryList(Integer rootCatId);

    public List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap") Map<String,Object> map);
}