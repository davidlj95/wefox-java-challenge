package com.api.sample.restful.repository;

import com.api.sample.restful.model.Account;
import com.api.sample.restful.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByAccount(Account account);

}
