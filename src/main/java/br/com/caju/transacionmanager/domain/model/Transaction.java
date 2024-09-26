package br.com.caju.transacionmanager.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_crgd_transaction")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    private String mcc;
    private String merchant;
    private BigDecimal amount;
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;
    @Column(name = "result_transaction")
    private String transactionResult;


}
