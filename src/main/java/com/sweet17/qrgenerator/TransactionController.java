package com.sweet17.qrgenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final QrCodeGeneratorService qrCodeGeneratorService;

    @PostMapping( "/create")
    public ResponseEntity<TransactionDto> create(TransactionDto transactionDto) {
        TransactionDto responseData = qrCodeGeneratorService.savingData(transactionDto);
        return ResponseEntity.created(URI.create("/create/")).body(responseData);
    }
    @GetMapping( "/{link}")
    public ResponseEntity<TransactionDto> get(@PathVariable(value = "link") String link) {
        TransactionDto transactionDto = qrCodeGeneratorService.get(link);
        return ResponseEntity.created(URI.create("/" + link)).body(transactionDto);
    }
}
