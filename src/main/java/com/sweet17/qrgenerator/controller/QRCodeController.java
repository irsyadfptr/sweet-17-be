package com.sweet17.qrgenerator.controller;

import com.google.zxing.WriterException;
import com.sweet17.qrgenerator.QrCodeGeneratorService;
import com.sweet17.qrgenerator.dto.TransactionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;

@RequestMapping("/barcodes")
@RestController
public class QRCodeController {

    private final QrCodeGeneratorService qrCodeGeneratorService;

    public QRCodeController(QrCodeGeneratorService qrCodeGeneratorService) {
        this.qrCodeGeneratorService = qrCodeGeneratorService;
    }


    @PostMapping("/text-qrcode")
    public ResponseEntity<TransactionDto> createTransaction(@ModelAttribute("request") TransactionDto request, Model model)
            throws WriterException, IOException {
        TransactionDto responseData = qrCodeGeneratorService.savingData(request);
        String qrCodePath = qrCodeGeneratorService.createQR(responseData);
        model.addAttribute("code", qrCodePath);
        return ResponseEntity.created(URI.create("/create/")).body(responseData);
    }


    @GetMapping(value = "/read-qrcode")
    public String verifyQR(@RequestParam("qrImage") String qrImage, Model model) throws Exception {
        model.addAttribute("content", qrCodeGeneratorService.readQR(qrImage));
        model.addAttribute("code", qrImage);
        return "QRcode";
    }

    @GetMapping( "/{link}")
    public ResponseEntity<TransactionDto> get(@PathVariable(value = "link") String link) {
        TransactionDto transactionDto = qrCodeGeneratorService.get(link);
        return ResponseEntity.created(URI.create("/" + link)).body(transactionDto);
    }

}

