package com.api.sample.restful.service;

import com.api.sample.restful.mapper.AddressMapper;
import com.api.sample.restful.model.Account;
import com.api.sample.restful.model.Address;
import com.api.sample.restful.repository.AddressRepository;
import com.api.sample.restful.vo.AddressVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    public List<AddressVO> saveAll(
            List<AddressVO> addressVOs, Account account
    ) {
        if (addressVOs == null) return new ArrayList<>();
        List<Address> newAddresses = addressMapper.toModel(addressVOs, account);
        List<Address> updatedAddresses = addressRepository.saveAll(newAddresses);
        removeNonUpdatedAddresses(new HashSet<>(updatedAddresses), account);
        return addressMapper.toVOs(updatedAddresses);
    }

    private void removeNonUpdatedAddresses(
            Set<Address> updatedAddresses,
            Account account
    ) {
        List<Address> addressesToRemove = new ArrayList<>();
        List<Address> previousAddresses = addressRepository.findByAccount(account);
        for (Address previousAddress : previousAddresses) {
            if (!updatedAddresses.contains(previousAddress)) {
                addressesToRemove.add(previousAddress);
            }
        }
        addressRepository.deleteAll(addressesToRemove);
    }
}
