package com.sweet17.qrgenerator;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Random;

public class UtilService {
    public static Date generateExpiredDate(Date date) throws Exception {
        Date dateNow = new Date();
        Date dateExpired = new Date(dateNow.getTime() + (24 * 3600 * 1000));
        return dateExpired;
    }

    public static class RandomString {
        static String getAlphaNumeric(int n){
            String AlphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                    "0123456789" + "abcdefghijklmnopqrstuvwxyz";

            StringBuilder sb = new StringBuilder(n);

            for (int i = 0; i < n; i++){
                int index = (int) (AlphaNumeric.length() * Math.random());
                sb.append(AlphaNumeric.charAt(index));
            }
            return sb.toString();
        }
    }


}
