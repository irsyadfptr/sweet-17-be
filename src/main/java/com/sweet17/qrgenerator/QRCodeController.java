package com.sweet17.qrgenerator;

import com.google.zxing.WriterException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/barcodes")
@RestController
public class QRCodeController {

    private final QrCodeGeneratorService qrCodeGeneratorService;

    public QRCodeController(QrCodeGeneratorService qrCodeGeneratorService) {
        this.qrCodeGeneratorService = qrCodeGeneratorService;
    }

//    @PostMapping(value = "/simple-qrcode", produces = MediaType.IMAGE_PNG_VALUE)
//    public ResponseEntity<BufferedImage> zxingQRCode(@RequestBody String barcode) throws Exception{
//        return successResponse(QrCodeGeneratorService.generateQRCode(barcode));
//    }
//
//    private ResponseEntity<BufferedImage> successResponse(BufferedImage image) {
//        return new ResponseEntity<>(image, HttpStatus.OK);
//    }
//
//    @Bean
//    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
//        return new BufferedImageHttpMessageConverter();
//    }

//    @PostMapping("/text-qrcode")
//    public String createTransaction(@ModelAttribute("request") Transaction request, Model model)
//            throws WriterException, IOException {
//        String qrCodePath = qrCodeGeneratorService.createQR(request);
//        model.addAttribute("code", qrCodePath);
//        return "QRcode";
//    }
//
//    @GetMapping(value = "/read-qrcode")
//    public String verifyQR(@RequestParam("qrImage") String qrImage, Model model) throws Exception {
//        model.addAttribute("content", qrCodeGeneratorService.readQR(qrImage));
//        model.addAttribute("code", qrImage);
//        return "QRcode";
//
//    }

}

