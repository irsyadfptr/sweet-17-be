package com.sweet17.qrgenerator;

import com.google.zxing.WriterException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
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

    @PostMapping(value = "/simple-qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> zxingQRCode(@RequestBody String barcode) throws Exception{
        return successResponse(QrCodeGeneratorService.generateQRCode(barcode));
    }

    private ResponseEntity<BufferedImage> successResponse(BufferedImage image) {
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }

    @PostMapping("/text-qrcode")
    public ResponseEntity<TransactionDto> createTransaction(@ModelAttribute("request") TransactionDto request, Model model)
            throws WriterException, IOException {
        TransactionDto responseData = qrCodeGeneratorService.savingData(request);
        String qrCodePath = qrCodeGeneratorService.createQR(responseData);
        model.addAttribute("code", qrCodePath);
        return ResponseEntity.created(URI.create("/create/")).body(responseData);
    }



    @PostMapping( "/create")
    public ResponseEntity<TransactionDto> create(TransactionDto transactionDto) {
        TransactionDto responseData = qrCodeGeneratorService.savingData(transactionDto);
        return ResponseEntity.created(URI.create("/create/")).body(responseData);
    }

//    @PostMapping("/create")
//    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
//    public ResponseEntity<StudentDto> create(@RequestBody StudentDto studentDto) {
//        StudentDto responseData = studentService.create(studentDto);
//        return ResponseEntity.created(URI.create("/create/")).body(responseData);
//    }

    @GetMapping(value = "/read-qrcode")
    public String verifyQR(@RequestParam("qrImage") String qrImage, Model model) throws Exception {
        model.addAttribute("content", qrCodeGeneratorService.readQR(qrImage));
        model.addAttribute("code", qrImage);
        return "QRcode";

    }

}

