package com.ljh.service;

import com.ljh.pojo.ShoppingCar;

import java.util.List;

public interface ShoppingCarService {
    int insert(ShoppingCar shoppingCar);

    List<ShoppingCar> selectByUserId(int userId,int nowPage);

    boolean delete(int carId);

    List<ShoppingCar> selectByBrandName(String brandName);

    List<ShoppingCar> findFirst(int userId);
}
