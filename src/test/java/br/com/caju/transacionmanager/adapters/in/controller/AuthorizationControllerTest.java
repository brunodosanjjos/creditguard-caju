package br.com.caju.transacionmanager.adapters.in.controller;

import br.com.caju.transacionmanager.domain.dto.ResultDTO;
import br.com.caju.transacionmanager.domain.dto.TransactionDTO;
import br.com.caju.transacionmanager.domain.model.TransactionResult;
import br.com.caju.transacionmanager.port.TransactionPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static br.com.caju.transacionmanager.mock.MockData.getTransactiondDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizationControllerTest {

    @Mock
    private TransactionPort transactionPort;
    @InjectMocks
    private AuthorizationController authorizationController;

    @Test
    public void testAuthorizeTransactionSuccess() {
        TransactionDTO transactionDTO = getTransactiondDTO();
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(TransactionResult.APPROVED.getCode());
        when(transactionPort.authorizeTransaction(transactionDTO)).thenReturn(resultDTO);
        ResponseEntity<ResultDTO> response = authorizationController.authorizeTransaction(transactionDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resultDTO, response.getBody());
    }

    @Test
    public void testAuthorizeTransactionDenied() {
        TransactionDTO transactionDTO = getTransactiondDTO();
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(TransactionResult.DENIED.getCode());
        when(transactionPort.authorizeTransaction(transactionDTO)).thenReturn(resultDTO);
        ResponseEntity<ResultDTO> response = authorizationController.authorizeTransaction(transactionDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resultDTO, response.getBody());
    }
    @Test
    public void testAuthorizeTransactionUndertemined() {
        TransactionDTO transactionDTO = getTransactiondDTO();
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(TransactionResult.UNDETERMINED.getCode());
        when(transactionPort.authorizeTransaction(transactionDTO)).thenReturn(resultDTO);
        ResponseEntity<ResultDTO> response = authorizationController.authorizeTransaction(transactionDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resultDTO, response.getBody());
    }
}