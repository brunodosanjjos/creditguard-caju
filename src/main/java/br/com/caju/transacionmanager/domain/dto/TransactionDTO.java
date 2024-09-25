package br.com.caju.transacionmanager.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    @NonNull
    @NotBlank
    private String account;
    @NonNull
    private BigDecimal totalAmount;
    @NonNull
    @NotBlank
    private String mcc;
    @NonNull
    @NotBlank
    private String merchant;

}
