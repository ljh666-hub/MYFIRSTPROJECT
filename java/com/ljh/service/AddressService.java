package com.ljh.service;

import com.ljh.pojo.Address;

import java.util.List;

public interface AddressService {
    boolean addAddress(Address address);

    List<Address> findByUserId(Integer userId);

    boolean deleteAddress(Integer addressId);
}
