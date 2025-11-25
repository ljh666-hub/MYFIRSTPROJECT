package com.ljh.service.Impl;

import com.ljh.dao.ShoppingCarDao;
import com.ljh.pojo.Brands;
import com.ljh.pojo.ShoppingCar;
import com.ljh.service.BrandService;
import com.ljh.service.CategoryService;
import com.ljh.service.ShoppingCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCarServiceImpl implements ShoppingCarService {
    @Autowired
    private ShoppingCarDao shoppingCarDao;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public int insert(ShoppingCar shoppingCar) {
        return shoppingCarDao.insert(shoppingCar);
    }

    @Override
    public List<ShoppingCar> selectByUserId(int userId,int nowPage) {
        List<ShoppingCar> shoppingCars = shoppingCarDao.selectByUserId(userId,nowPage);
        for (ShoppingCar shoppingCar : shoppingCars) {
            Brands brand = brandService.findBrandById(shoppingCar.getBrandId());
            shoppingCar.setBrandName(brand.getBrandName());
            shoppingCar.setImageUrl(brand.getImageUrl());
        }
        return shoppingCars;
    }

    @Override
    public boolean delete(int carId) {
        return shoppingCarDao.delete(carId)>0;
    }

    @Override
    public List<ShoppingCar> selectByBrandName(String brandName) {
        return shoppingCarDao.selectByBrandName(brandName);
    }

    @Override
    public List<ShoppingCar> findFirst(int userId) {
        return shoppingCarDao.findFirst(userId);
    }
}
