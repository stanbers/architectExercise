package com.stanxu.service.impl;

import com.stanxu.mapper.CategoryMapper;
import com.stanxu.pojo.Category;
import com.stanxu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> queryRootCategory() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type",1);

        List<Category> categoryList = categoryMapper.selectByExample(example);

        return categoryList;
    }
}
