package com.api.sample.restful.model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "ADDRESS")
@Data
@Builder
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn()
    private Account account;

    @Column(name = "address__c")
    @NotNull
    private String address;

    @CreationTimestamp
    @Column(name = "created__c", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @UpdateTimestamp
    @Column(name = "updated__c")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
}
