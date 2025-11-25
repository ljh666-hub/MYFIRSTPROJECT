package com.ljh.service.Impl;

import com.ljh.DTO.BrandSelectDTO;
import com.ljh.controller.Status;
import com.ljh.dao.BrandDao;
import com.ljh.exception.BusinessException;
import com.ljh.exception.SystemException;
import com.ljh.pojo.Brands;
import com.ljh.pojo.Orders;
import com.ljh.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandDao brandDao;

    @Override
    public List<Brands> findAllBrands() {
        return brandDao.findAllBrands();
    }

    @Override
    public boolean insertBrand(Brands brand) {

        if(brand.getBrandName()==null|"".equals(brand.getBrandName())){
            throw new BusinessException("商品名不能为空", Status.BUSINESS_ERROR);
        }else if(brand.getBrandAmount()<=0){
            throw new BusinessException("请输入正确的商品数量", Status.BUSINESS_ERROR);
        }else if(brand.getBrandPrice()<=0) {
            throw new BusinessException("请输入正确的商品价格", Status.BUSINESS_ERROR);
        }else if(brand.getBrandType()==null|"".equals(brand.getBrandType())) {
            throw new BusinessException("商品类型不能为空", Status.BUSINESS_ERROR);
        }else if(brand.getBrandDetail()==null|"".equals(brand.getBrandDetail())) {
            throw new BusinessException("商品详情不能为空", Status.BUSINESS_ERROR);
        }else if(brand.getImageUrl()==null|"".equals(brand.getImageUrl())) {
                throw new BusinessException("请上传一张商品图片", Status.BUSINESS_ERROR);
        }else{
            Integer i = brandDao.insertBrand(brand);
            return i>0;
        }
    }

    @Override
    public boolean deleteBrand(int brandId) {
        Integer i = brandDao.deleteBrand(brandId);
        if(i>0){
            return true;
        }else {
            throw new SystemException("商品下架失败",Status.SYSTEM_ERROR);
        }

    }

    @Override
    public boolean updateBrand(Brands brand) {
        Integer i = brandDao.updateBrand(brand);
        if(brand.getBrandName()==null|"".equals(brand.getBrandName())){
            throw new BusinessException("商品名不能为空", Status.BUSINESS_ERROR);
        }else if(brand.getBrandAmount()<=0){
            throw new BusinessException("请输入正确的商品数量", Status.BUSINESS_ERROR);
        }else if(brand.getBrandPrice()<=0) {
            throw new BusinessException("请输入正确的商品价格", Status.BUSINESS_ERROR);
        }else if(brand.getBrandType()==null|"".equals(brand.getBrandType())) {
            throw new BusinessException("商品类型不能为空", Status.BUSINESS_ERROR);
        }else if(brand.getBrandDetail()==null|"".equals(brand.getBrandDetail())) {
            throw new BusinessException("商品详情不能为空", Status.BUSINESS_ERROR);
        }else{
            return i>0;
        }
    }

    @Override
    public boolean updateBrandRefund(int amount,int sell,int brandId) {
        return brandDao.updateBrandRefund(amount,sell,brandId)>0;
    }

    @Override
    public Brands findBrandById(int brandId) {
        return brandDao.findBrandById(brandId);
    }

    @Override
    public List<Brands> findBrandsByCondition(String brandName, String brandType,int brandPriceBegin,int brandPriceEnd,String rate,int nowPage) {
        return brandDao.findBrandsByCondition(brandName,brandType,brandPriceBegin,brandPriceEnd,rate,nowPage);
    }

    @Override
    public int countBrands() {
        return brandDao.countBrands();
    }

    @Override
    public List<Brands> brandPage(Integer nowPage) {
        return brandDao.brandPage(nowPage);
    }

    @Override
    public int updateBrandByBrandId(int amount,int sell,int brandId) {
        return brandDao.updateBrandByBrandId(amount,sell,brandId);
    }

    @Override
    public List<Brands> findAllBrandsByUserName(String userName) {
        return brandDao.findAllBrandsByUserName(userName);
    }

    @Override
    public List<Orders> SellsTop(LocalDate orderTime1, LocalDate orderTime2) {
        return brandDao.SellsTop(orderTime1, orderTime2);
    }

    @Override
    public Integer findSelling() {
        return brandDao.findSelling();
    }

    @Override
    public Integer findUnSelling() {
        return brandDao.findUnSelling();
    }

    @Override
    public Integer findAmountByCondition(String brandName, String brandType) {
        return brandDao.findAmountByCondition(brandName,brandType);
    }


}
