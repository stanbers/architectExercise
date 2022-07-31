package com.stanxu.service.impl;

import com.stanxu.mapper.CategoryMapper;
import com.stanxu.mapper.CategoryMapperCustom;
import com.stanxu.pojo.Category;
import com.stanxu.pojo.vo.CategoryVO;
import com.stanxu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> queryRootCategory() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type",1);

        List<Category> categoryList = categoryMapper.selectByExample(example);

        return categoryList;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<CategoryVO> getSubCatList(Integer rootCatId) {

        List<CategoryVO> list = categoryMapperCustom.getSubCategoryList(rootCatId);
        return list;
    }
}
