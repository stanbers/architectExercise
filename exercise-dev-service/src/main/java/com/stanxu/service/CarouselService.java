package com.stanxu.service;

import com.stanxu.pojo.Carousel;

import java.util.List;

public interface CarouselService {

    public List<Carousel> queryAll(Integer isShow);
}
