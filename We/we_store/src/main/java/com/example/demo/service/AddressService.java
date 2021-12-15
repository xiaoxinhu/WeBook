package com.example.demo.service;


import com.example.demo.model.user.Address;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {
//    int addAddress(Address address);
//    int deleteAddress(int id);
//    int modifyAddress(Address address);
    List<Address> addressList(String account);
//    int setAddressOff(int id);
}
