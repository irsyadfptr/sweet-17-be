package com.sweet17.qrgenerator.controller;

import antlr.CodeGenerator;
import com.google.zxing.WriterException;
import com.sweet17.qrgenerator.QrCodeGeneratorService;
import com.sweet17.qrgenerator.dto.LinkDto;
import com.sweet17.qrgenerator.dto.PointDto;
import com.sweet17.qrgenerator.dto.ResponseDto;
import com.sweet17.qrgenerator.dto.TransactionDto;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
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

    @PostMapping(value = "/images-qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> createImage(@ModelAttribute("request") TransactionDto request, Model model) throws Exception{
        TransactionDto responseData = qrCodeGeneratorService.savingData(request);
        String qrCodePath = qrCodeGeneratorService.createQR(responseData);
        model.addAttribute("code", qrCodePath);
        return successResponse(qrCodeGeneratorService.createQRRemote(responseData));
    }

    private ResponseEntity<BufferedImage> successResponse(BufferedImage image) {
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
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

    @PostMapping("/{link}")
    public ResponseEntity<ResponseDto> validationCode(@PathVariable(value = "link") LinkDto link) {
        ResponseDto validation = qrCodeGeneratorService.validation(link);
        return ResponseEntity.created(URI.create("/validate")).body(validation);
    }


}

