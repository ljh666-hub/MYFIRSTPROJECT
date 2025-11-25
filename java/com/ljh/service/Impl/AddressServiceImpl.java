package com.ljh.service.Impl;

import com.ljh.dao.AddressDao;
import com.ljh.pojo.Address;
import com.ljh.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressDao addressDao;

    @Override
    public boolean addAddress(Address address) {
        return addressDao.addAddress(address)>0;
    }

    @Override
    public List<Address> findByUserId(Integer userId) {
        return addressDao.findByUserId(userId);
    }

    @Override
    public boolean deleteAddress(Integer addressId) {
        return addressDao.deleteAddress(addressId)>0;
    }


}
