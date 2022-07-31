package com.stanxu.service;

import com.stanxu.pojo.Category;
import com.stanxu.pojo.vo.CategoryVO;

import java.util.List;

public interface CategoryService {

    public List<Category> queryRootCategory();

    public List<CategoryVO> getSubCatList(Integer rootCatId);
}
