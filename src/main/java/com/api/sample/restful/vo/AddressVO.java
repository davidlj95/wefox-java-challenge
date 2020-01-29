package com.api.sample.restful.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AddressVO {
    private Long id;
    private String address;
    private Date created;
    private Date updated;
}
