package com.ljh.controller;

import com.ljh.DTO.BrandSelectDTO;
import com.ljh.DTO.SellsTopDTO;
import com.ljh.exception.BusinessException;
import com.ljh.exception.SystemException;
import com.ljh.pojo.Brands;
import com.ljh.pojo.Orders;
import com.ljh.service.BrandService;
import com.ljh.service.CategoryService;
import com.ljh.utils.imageUtil;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brands")
public class BrandController{
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("/findAll")
    @ResponseBody
    public Result findAllBrands() {
        List<Brands> data = brandService.findAllBrands();
        return new Result(data,Status.BRANDFINDALL_SUCCESS,"ok");
    }

    @RequestMapping("/findById")
    @ResponseBody
    public Result findById(int brandId) {
        Brands data = brandService.findBrandById(brandId);
        return new Result(data,Status.BRANDFINDALL_SUCCESS,"ok");
    }

    @RequestMapping("/getImage")
    @ResponseBody
    public String  getImage(@RequestParam("file") MultipartFile file) throws IOException {
        imageUtil imageUtil = new imageUtil();
        if (file.getSize()<50*1024*1024){
            return imageUtil.upload(file);
        }else {
            throw new BusinessException("上传的文件大小不能超过50MB", Status.ORDERUPDATE_ERROR);
        }
    }


    @RequestMapping("/addBrand")
    @ResponseBody
    public Result addBrand(@RequestBody Brands brand) {
        brandService.insertBrand(brand);
        int brandId = brand.getBrandId();

        redisTemplate.delete(redisTemplate.keys("brand*"));
        return new Result(brandId,Status.BRANDADD_SUCCESS,"添加成功");
    }

    @RequestMapping("/deleteBrand")
    @ResponseBody
    @Transactional
    public Result deleteBrand(int brandId) {
        brandService.deleteBrand(brandId);
        categoryService.deleteCategory(brandId);

        redisTemplate.delete(redisTemplate.keys("brand*"));
        return new Result(null,Status.BRANDDELETE_SUCCESS,"删除成功");
    }

    @RequestMapping("/updateBrand")
    @ResponseBody
    public Result updateBrand(@RequestBody Brands brand){
        brandService.updateBrand(brand);
        redisTemplate.delete(redisTemplate.keys("brand*"));
        return new Result(null,Status.BRANDUPDATE_SUCCESS,"修改成功");
    }

    @RequestMapping("/updateBrandRefund")
    @ResponseBody
    public Result updateBrandRefund(int orderAmount,int brandId){
            Brands brand = brandService.findBrandById(brandId);
            int amount = brand.getBrandAmount()-orderAmount;
            int sell = brand.getBrandSell()+orderAmount;
            if(brandService.updateBrandByBrandId(amount,sell,brandId)>0){
                redisTemplate.delete(redisTemplate.keys("brand*"));
                return new Result(null,Status.ORDERADD_SUCCESS,"退款成功");
            }else{
                return new Result(null,Status.ORDERADD_ERROR,"退款失败");
            }
    }

    @RequestMapping("/selectByCondition")
    @ResponseBody
    public List<Brands> selectByCondition(String brandName,String brandType,String priceBegin,String priceEnd,String brandSell,Integer nowPage) {
        String newBrandName = "%" + brandName + "%";
        String key = "brand"+brandName+"*"+brandType+"*"+priceBegin+"*"+priceEnd+"*"+brandSell+"*"+nowPage;
        int brandPriceBegin = 0;
        int brandPriceEnd = 0;
        String rate = "";
        if(brandSell!=null && !brandSell.equals("")){
            rate = brandSell;
        }else {
            rate="desc";
        }
        if (priceBegin == null || priceBegin.equals("")) {
            brandPriceBegin = 0;
        }else{
           brandPriceBegin = Integer.parseInt(priceBegin);
        }
        if (priceEnd == null || priceEnd.equals("")) {
            brandPriceEnd = 99999;
        }else {
            brandPriceEnd = Integer.parseInt(priceEnd);
        }
        List<Brands> brands = (List<Brands>) redisTemplate.opsForValue().get(key);
        if(brands!=null && !brands.isEmpty()) {
            return brands;
        }


        brands = brandService.findBrandsByCondition(newBrandName,brandType,brandPriceBegin,brandPriceEnd,rate,nowPage);
        redisTemplate.opsForValue().set(key,brands);
        return brands;
    }


    @RequestMapping("/brandPage")
    @ResponseBody
    public Result brandPage(Integer nowPage) {
        redisTemplate.delete(redisTemplate.keys("brand*"));
        int count = brandService.countBrands();

        String key = "brand" + nowPage;

        List<Brands> brands = (List<Brands>) redisTemplate.opsForValue().get(key);
        if(brands!=null && !brands.isEmpty()){
            for (Brands brand:brands){
                if(brand.getBrandDetail().length()>8){
                brand.setBrandDetail(brand.getBrandDetail().substring(0, 8)+"...");
                }
            }
            return new Result(brands,count,"ok");
        }


        brands = brandService.brandPage(nowPage);
        for (Brands brand:brands){
            if(brand.getBrandDetail().length()>8){
                brand.setBrandDetail(brand.getBrandDetail().substring(0, 8)+"...");
            }
        }
        redisTemplate.opsForValue().set(key,brands);

        return new Result(brands,count,"ok");
    }

    @RequestMapping("/findByUserName")
    @ResponseBody
    public List<Brands> findByUserName(HttpSession session) {
        String userName = (String)session.getAttribute("userName");
        return brandService.findAllBrandsByUserName(userName);
    }

    @RequestMapping("/SellsTop")
    @ResponseBody
    public SellsTopDTO SellsTop() {
        LocalDate orderTime1 = LocalDate.now().plusDays(1);
        LocalDate orderTime2 = LocalDate.now().plusMonths(-1);
        List<Orders> orders = brandService.SellsTop(orderTime2, orderTime1);
        List<String> nameList = new ArrayList<>();
        List<Integer> sellList = new ArrayList<>();
        if (!orders.isEmpty()) {
            for (Orders order:orders){
                nameList.add(order.getBrandName());
                sellList.add(order.getBrandSell());
            }
            SellsTopDTO sellsTopDTO = new SellsTopDTO();
            sellsTopDTO.setBrandName(nameList);
            sellsTopDTO.setBrandSell(sellList);
            return sellsTopDTO;
        }else {
            return new SellsTopDTO();
        }
    }

    @RequestMapping("/findSelling")
    @ResponseBody
    public Integer findSelling() {
        return brandService.findSelling();
    }

    @RequestMapping("/findAmountByCondition")
    @ResponseBody
    public Integer findAmountByCondition(String brandName, String brandType) {
        return brandService.findAmountByCondition(brandName,brandType);
    }

    @RequestMapping("/findUnSelling")
    @ResponseBody
    public Integer findUnSelling() {
        return brandService.findUnSelling();
    }
}
