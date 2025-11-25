package com.ljh.service;

import com.ljh.pojo.Cupon;

import java.util.List;

public interface CuponService {
    boolean insert(Cupon cupon);

    List<Cupon> selectByCondition(Cupon cupon);

    List<Cupon> selectNewUser(int userId,int cuponId);

    boolean insertCuponUser(Cupon cupon);

    List<Cupon> selectAllCupon();

    int updateCuponAmount(int cuponId);

    boolean updateCuponEndTime(Cupon cupon);

    boolean deleteCupon(int cuponId);

    List<Cupon> selectByCuponStatus(String cuponStatus);

    List<Cupon> selectUseful(Cupon cupon);

    boolean updateCuponUse(String orderId,int userId,int cuponId);
}
