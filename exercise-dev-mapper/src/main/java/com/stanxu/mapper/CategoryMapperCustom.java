package com.stanxu.mapper;

import com.stanxu.my.mapper.MyMapper;
import com.stanxu.pojo.Category;
import com.stanxu.pojo.vo.CategoryVO;

import java.util.List;

public interface CategoryMapperCustom {

    public List<CategoryVO> getSubCategoryList(Integer rootCatId);
}