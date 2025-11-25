package com.ljh.service;

import com.ljh.DTO.BrandSelectDTO;
import com.ljh.pojo.Brands;
import com.ljh.pojo.Orders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BrandService {
    List<Brands> findAllBrands();

    boolean insertBrand(Brands brands);

    boolean deleteBrand(int brandId);

    boolean updateBrand(Brands brand);

    boolean updateBrandRefund(int amount,int sell,int brandId);

    Brands findBrandById(int brandId);

    List<Brands> findBrandsByCondition(String brandName,String brandType,int brandPriceBegin,int brandPriceEnd,String rate,int nowPage);

    int countBrands();

    List<Brands>  brandPage(Integer nowPage);

    int updateBrandByBrandId(int amount,int sell,int brandId);

    List<Brands>  findAllBrandsByUserName(String userName);

    List<Orders> SellsTop(LocalDate orderTime1, LocalDate orderTime2);

    Integer findSelling();

    Integer findUnSelling();

    Integer findAmountByCondition(String brandName,String brandType);
}
