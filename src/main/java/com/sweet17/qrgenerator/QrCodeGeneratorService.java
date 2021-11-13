package com.sweet17.qrgenerator;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@RequiredArgsConstructor
public class QrCodeGeneratorService {

    private ResourceLoader resourceLoader;
    private Transaction transaction;

    public static BufferedImage generateQRCode(String urlText) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(urlText, BarcodeFormat.QR_CODE, 200, 200);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

//    public String readQRCode(String qrcodeImage) throws Exception {
//        BufferedImage bufferedImage = ImageIO.read(new File(qrcodeImage));
//        LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
//        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
//        Result result = new MultiFormatReader().decode(binaryBitmap);
//        return result.getText();
//
//    }

    // Read file from local folder
    public String readQR(String qrImage) throws Exception {
        final Resource fileResource = resourceLoader.getResource("classpath:static/" + qrImage);
        File QRfile = fileResource.getFile();
        BufferedImage bufferedImg = ImageIO.read(QRfile);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImg);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result = new MultiFormatReader().decode(bitmap);
        System.out.println("Barcode Format: " + result.getBarcodeFormat());
        System.out.println("Content: " + result.getText());
        return result.getText();
    }

    //Create QR to local
    public String createQR(Transaction request) throws WriterException, IOException {
        String qcodePath = "src/main/resources/static/images/" + request.getFileName() + "-QRCode.png";
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(request.getTransactionId() + "\n" + request.getCreatedDate() + "\n"
                + request.getExpiredDate() + "\n" + request.getLink(), BarcodeFormat.QR_CODE, 350, 350);
        Path path = FileSystems.getDefault().getPath(qcodePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        return "/images/" + request.getFileName() + "-QRCode.png";
    }
}


