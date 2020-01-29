package com.api.sample.restful.api;

import com.api.sample.restful.service.AccountService;
import com.api.sample.restful.vo.AccountVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@RestController
@RequestMapping("/api/v1/accounts")
@Slf4j
@RequiredArgsConstructor
public class AccountAPI {
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountVO>> find(
            @RequestParam(required = false) String email
    ) {
        List<AccountVO> accounts;
        if (email == null) {
            accounts = accountService.findAll();
        } else {
            accounts = accountService.findByEmail(email);
        }
        return ResponseEntity.ok(accounts);
    }

    @PostMapping
    public ResponseEntity<AccountVO> create(@Valid @RequestBody AccountVO account) {
        return ResponseEntity.ok(accountService.save(account));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountVO> findById(@PathVariable Long id) {
        Optional<AccountVO> account = accountService.findById(id);
        if (!account.isPresent()) {
            log.error("Id " + id + " is not existed");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(account.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountVO> update(@PathVariable Long id, @Valid @RequestBody AccountVO account) {
        if (!accountService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(accountService.save(account));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        if (!accountService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }

        accountService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
