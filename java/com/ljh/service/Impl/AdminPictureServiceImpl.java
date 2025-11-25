package com.ljh.service.Impl;

import com.ljh.dao.AdminPictureDao;
import com.ljh.service.AdminPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminPictureServiceImpl implements AdminPictureService {
    @Autowired
    private AdminPictureDao adminPictureDao;

    @Override
    public boolean insert(String imageUrl) {
        return adminPictureDao.insert(imageUrl)>0;
    }

    @Override
    public boolean deleteAll() {
        return adminPictureDao.deleteAll()>=0;
    }

    @Override
    public List<String> selectAll() {
        return adminPictureDao.selectAll();
    }
}
