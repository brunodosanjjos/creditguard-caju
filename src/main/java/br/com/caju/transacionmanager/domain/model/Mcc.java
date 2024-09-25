package br.com.caju.transacionmanager.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_mcc")
public class Mcc {

    @Id
    @Column(name = "code")
    private String code;
    @ManyToOne
    @JoinColumn(name = "type_id")// muitos tipos ex food, podem estar relacionados a varios mccs
    private ClassificationMcc classification;

    private String description;


    @OneToMany(mappedBy = "mcc", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Merchant> merchants;

}
