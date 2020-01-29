package com.api.sample.restful.model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ACCOUNT")
@Data
@Builder
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name__c")
    @NotNull
    private String name;

    @Column(name = "email__c")
    private String email;

    @Column(name = "age__c")
    @PositiveOrZero
    private Integer age;

    @OneToMany(
            mappedBy = "account", fetch = FetchType.EAGER, orphanRemoval = true
    )
    private List<Address> addresses;

    @CreationTimestamp
    @Column(name = "created__c", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @UpdateTimestamp
    @Column(name = "updated__c")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
}
