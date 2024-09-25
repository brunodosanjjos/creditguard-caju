package br.com.caju.transacionmanager.adapters.in.controller;

import br.com.caju.transacionmanager.domain.dto.ResultDTO;
import br.com.caju.transacionmanager.domain.dto.TransactionDTO;
import br.com.caju.transacionmanager.port.TransactionPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("v1/transaction")
@AllArgsConstructor
@Tag(name = "Authorization Transaction", description = "Management transactions APIs")
public class AuthorizationController {

    private final TransactionPort transactionPort;
    @PostMapping
    @Operation(summary = "Authorize transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction Result",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResultDTO.class))),
    })
    public ResponseEntity<ResultDTO> authorizeTransaction(@Valid @RequestBody TransactionDTO transaction) {
        log.info("Authorize transaction: {}", transaction);
        var response = transactionPort.authorizeTransaction(transaction);
        return ResponseEntity.ok(response);
    }
}
