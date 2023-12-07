package com.api.evaluacion.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "phone")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPhone;
    private Long number;
    private int citycode;
    private String contrycode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User usuario;
}
