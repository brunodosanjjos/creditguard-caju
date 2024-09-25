package br.com.caju.transacionmanager.port;

import br.com.caju.transacionmanager.domain.dto.ResultDTO;
import br.com.caju.transacionmanager.domain.dto.TransactionDTO;

public interface TransactionPort {

    ResultDTO authorizeTransaction(TransactionDTO transaction);


}
