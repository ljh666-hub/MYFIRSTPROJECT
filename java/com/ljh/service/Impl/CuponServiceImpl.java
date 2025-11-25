package com.ljh.service.Impl;

import com.ljh.dao.CuponDao;
import com.ljh.pojo.Cupon;
import com.ljh.service.CuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuponServiceImpl implements CuponService {
    @Autowired
    private CuponDao cuponDao;

    @Override
    public boolean insert(Cupon cupon) {
        return cuponDao.insert(cupon)>0;
    }

    @Override
    public List<Cupon> selectByCondition(Cupon cupon) {
        return cuponDao.selectByCondition(cupon);
    }

    @Override
    public List<Cupon> selectNewUser(int userId,int cuponId) {
        return cuponDao.selectNewUser(userId,cuponId);
    }

    @Override
    public boolean insertCuponUser(Cupon cupon) {
        return cuponDao.insertCuponUser(cupon)>0;
    }

    @Override
    public List<Cupon> selectAllCupon() {
        return cuponDao.selectAllCupon();
    }

    @Override
    public int updateCuponAmount(int cuponId) {
        return cuponDao.updateCuponAmount(cuponId);
    }

    @Override
    public boolean updateCuponEndTime(Cupon cupon) {
        return cuponDao.updateCuponEndTime(cupon)>0;
    }

    @Override
    public boolean deleteCupon(int cuponId) {
        return cuponDao.deleteCupon(cuponId)>0;
    }

    @Override
    public List<Cupon> selectByCuponStatus(String cuponStatus) {
        return cuponDao.selectByCuponStatus(cuponStatus);
    }

    @Override
    public List<Cupon> selectUseful(Cupon cupon) {
        return cuponDao.selectUseful(cupon);
    }

    @Override
    public boolean updateCuponUse(String orderId,int userId,int cuponId) {
        return cuponDao.updateCuponUse(orderId,userId,cuponId)>0;
    }


}
