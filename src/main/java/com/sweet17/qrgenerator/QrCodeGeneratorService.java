package com.sweet17.qrgenerator;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sweet17.qrgenerator.dto.TransactionDto;
import com.sweet17.qrgenerator.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QrCodeGeneratorService {

    private final ResourceLoader resourceLoader;
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;

    public static BufferedImage generateQRCode(String urlText) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(urlText, BarcodeFormat.QR_CODE, 200, 200);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public String readQRCode(String qrcodeImage) throws Exception {
        BufferedImage bufferedImage = ImageIO.read(new File(qrcodeImage));
        LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
        Result result = new MultiFormatReader().decode(binaryBitmap);
        return result.getText();

    }

//     Read file from local folder
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

    public TransactionDto savingData(TransactionDto transactionDto) {
//        Transaction transaction = transactionMapper.toTransactionEntity(transactionDto);
//        transactionRepository.save(transaction);
//        return transactionMapper.toTransactionDto(transaction);
        Date dateNow = new Date();
        Date dateExpired = new Date(dateNow.getTime() + (24 * 3600 * 1000));
        Transaction transaction = transactionMapper.toTransactionEntity(transactionDto);


        transaction.setLink(UtilService.RandomString.getAlphaNumeric(10));
        transaction.setCreatedDate(dateNow);
        transaction.setExpiredDate(dateExpired);

        Optional<Transaction> optionalTransaction = transactionRepository.findByLink(transactionDto.getLink());
        if (optionalTransaction.isPresent()) {
            throw new RuntimeException("Link already exists");
        }
        Transaction savingTransaction = transactionRepository.save(transaction);

        return transactionMapper.toTransactionDto(savingTransaction);
    }

    public TransactionDto get(String link) {
        Transaction transaction = transactionRepository.findTransactionByLink(link)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Link"));
        return transactionMapper.toTransactionDto(transaction);
    }

//    Create QR to local
    public String createQR(TransactionDto transactionDto) throws WriterException, IOException {
        Transaction transaction = transactionMapper.toTransactionEntity(transactionDto);
        String qcodePath = "src/main/resources/static/images/" + transaction.getLink() + "-QRCode.png";
//        String randomLink = UtilService.RandomString.getAlphaNumeric(10);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(
                "https://sweet-17.herokuapp.com/game/" + transaction.getLink(),
                BarcodeFormat.QR_CODE, 350, 350);
        Path path = FileSystems.getDefault().getPath(qcodePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        return "/images/" + transaction.getLink() + "-QRCode.png";
    }


}


