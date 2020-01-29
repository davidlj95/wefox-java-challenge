package com.api.sample.restful.mapper;

import com.api.sample.restful.model.Account;
import com.api.sample.restful.model.Address;
import com.api.sample.restful.vo.AccountVO;
import com.api.sample.restful.vo.AddressVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountMapper {

    private final AddressMapper addressMapper;

    public AccountVO toVO(Account account) {
        return toVO(account, addressMapper.toVOs(account.getAddresses()));
    }

    public AccountVO toVO(Account account, List<AddressVO> addressVOs) {
        return AccountVO.builder()
                .created(account.getCreated())
                .updated(account.getUpdated())
                .id(account.getId())
                .name(account.getName())
                .email(account.getEmail())
                .age(account.getAge())
                .addresses(addressVOs)
                .build();
    }

    public Account toModel(AccountVO accountVO) {
        return toModel(accountVO, new ArrayList<>());
    }

    public Account toModel(AccountVO accountVo, List<Address> addresses) {
        return Account.builder()
                .id(accountVo.getId())
                .name(accountVo.getName())
                .email(accountVo.getEmail())
                .age(accountVo.getAge())
                .addresses(addresses)
                .build();
    }
}
