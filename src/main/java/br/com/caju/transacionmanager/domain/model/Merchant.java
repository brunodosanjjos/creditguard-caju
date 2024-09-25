package br.com.caju.transacionmanager.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_merchant_mcc")
public class Merchant {
    @Id
    @Column(name = "merchant_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "mcc")
    private Mcc mcc;


}
