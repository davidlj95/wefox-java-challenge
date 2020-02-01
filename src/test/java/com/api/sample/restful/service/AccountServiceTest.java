package com.api.sample.restful.service;

import com.api.sample.restful.mapper.AccountMapper;
import com.api.sample.restful.model.Account;
import com.api.sample.restful.repository.AccountRepository;
import com.api.sample.restful.vo.AccountVO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    private AccountFixtureService accountFixtureService = new AccountFixtureService();

    private AccountVOFixtureService accountVOFixtureService = new AccountVOFixtureService();

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private AddressService addressService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAllUsesRepositoryAndMapsAllResultsToVO() {
        int numberOfAccounts = (new Random()).nextInt(9) + 1;
        List<Account> accounts =
                Stream.generate(accountFixtureService::getFixture)
                        .limit(numberOfAccounts)
                        .collect(Collectors.toList());

        when(accountRepository.findAll()).thenReturn(accounts);
        ArgumentCaptor<Account> accountArguments = ArgumentCaptor.forClass(Account.class);

        List<AccountVO> accountVOs = accountService.findAll();

        assertEquals(accountVOs.size(), numberOfAccounts);
        verify(accountMapper, times(numberOfAccounts)).toVO(accountArguments.capture());
        assertEquals(accounts, accountArguments.getAllValues());
    }

    @Test
    public void findByIdUsesRepositoryAndMapsResultToVO() {
        Account account = accountFixtureService.getFixture();
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        ArgumentCaptor<Long> idArgument = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Account> accountArgument = ArgumentCaptor.forClass(Account.class);

        accountService.findById(account.getId());

        verify(accountRepository, times(1)).findById(idArgument.capture());
        verify(accountMapper, times(1)).toVO(accountArgument.capture());

        assertEquals(account.getId(), idArgument.getValue());
        assertEquals(account, accountArgument.getValue());
    }

    @Test
    public void findByEmail() {
        int numberOfAccounts = (new Random()).nextInt(9) + 1;
        List<Account> accounts =
                Stream.generate(accountFixtureService::getFixture)
                        .limit(numberOfAccounts)
                        .collect(Collectors.toList());

        Account account = accountFixtureService.getFixture();
        String email = account.getEmail();
        when(accountRepository.findByEmail(email)).thenReturn(accounts);
        ArgumentCaptor<String> emailArgument = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Account> accountArgument = ArgumentCaptor.forClass(Account.class);

        accountService.findByEmail(email);

        verify(accountRepository, times(1)).findByEmail(emailArgument.capture());
        verify(accountMapper, times(numberOfAccounts)).toVO(accountArgument.capture());

        assertEquals(email, emailArgument.getValue());
        assertEquals(accounts, accountArgument.getAllValues());
    }

    @Test
    public void save() {
        AccountVO accountVO = accountVOFixtureService.getFixture();
        Account account = accountFixtureService.getFixture();
        ArgumentCaptor<AccountVO> accountVOArgument = ArgumentCaptor.forClass(AccountVO.class);

        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.toModel(accountVO)).thenReturn(account);
        when(accountMapper.toVO(account)).thenReturn(accountVO);

        accountService.save(accountVO);

        verify(accountMapper, times(1)).toModel(
                accountVOArgument.capture()
        );
        verify(addressService, times(1)).saveAll(
                new ArrayList<>(),
                account
        );
        verify(accountMapper, times(1)).toVO(
                account,
                new ArrayList<>()
        );

        assertEquals(accountVO, accountVOArgument.getValue());
    }

    @Test
    public void deleteById() {
        Long idToDelete = (new Random()).nextLong();
        ArgumentCaptor<Long> idArgument = ArgumentCaptor.forClass(Long.class);

        accountService.deleteById(idToDelete);

        verify(accountRepository, times(1))
                .deleteById(idArgument.capture());

        assertEquals(idToDelete, idArgument.getValue());
    }
}
