package com.api.sample.restful.service;

import com.api.sample.restful.mapper.AccountMapper;
import com.api.sample.restful.model.Account;
import com.api.sample.restful.repository.AccountRepository;
import com.api.sample.restful.vo.AccountVO;
import com.api.sample.restful.vo.AddressVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final AddressService addressService;

    public List<AccountVO> findAll() {
        return accountRepository.findAll().stream()
                .map(this.accountMapper::toVO)
                .collect(Collectors.toList());
    }

    public Optional<AccountVO> findById(Long id) {
        return accountRepository
                .findById(id)
                .map(this.accountMapper::toVO);
    }


    public List<AccountVO> findByEmail(String email) {
        return accountRepository.findByEmail(email)
                .stream()
                .map(this.accountMapper::toVO)
                .collect(Collectors.toList());
    }

    public AccountVO save(AccountVO accountVO) {
        Account account = accountRepository.save(this.accountMapper.toModel(accountVO));
        List<AddressVO> addressVOs = addressService.saveAll(accountVO.getAddresses(), account);
        return this.accountMapper.toVO(account, addressVOs);
    }

    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }


}
