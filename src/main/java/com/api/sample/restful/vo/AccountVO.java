package com.api.sample.restful.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class AccountVO {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private List<AddressVO> addresses;
    private Date created;
    private Date updated;
}
