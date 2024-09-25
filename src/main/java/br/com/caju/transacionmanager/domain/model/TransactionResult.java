package br.com.caju.transacionmanager.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionResult {

    APPROVED("00"),
    DENIED("51"),
    UNDETERMINED("07");

    private final String code;

}
