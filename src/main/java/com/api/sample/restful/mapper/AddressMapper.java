package com.api.sample.restful.mapper;

import com.api.sample.restful.model.Account;
import com.api.sample.restful.model.Address;
import com.api.sample.restful.vo.AddressVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressMapper {

    public List<AddressVO> toVOs(List<Address> addresses) {
        if (addresses == null) return new ArrayList<>();
        return addresses.stream()
                .map(this::toVO)
                .collect(Collectors.toList());
    }

    private AddressVO toVO(Address address) {
        return AddressVO.builder()
                .id(address.getId())
                .address(address.getAddress())
                .created(address.getCreated())
                .updated(address.getUpdated())
                .build();
    }

    public List<Address> toModel(List<AddressVO> addressVOs, Account account) {
        if (addressVOs == null) return new ArrayList<>();
        return addressVOs.stream()
                .map(address -> toModel(address, account))
                .collect(Collectors.toList());
    }

    private Address toModel(AddressVO addressVO, Account account) {
        return Address.builder()
                .id(addressVO.getId())
                .account(account)
                .address(addressVO.getAddress())
                .created(addressVO.getCreated())
                .updated(addressVO.getUpdated())
                .build();

    }
}
