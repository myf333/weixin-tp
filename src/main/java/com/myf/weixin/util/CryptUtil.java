package com.myf.weixin.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Created by maoyf0503 on 2018-4-12.
 *
 * @author maoyf0503
 */
public class CryptUtil {
    public static String encrypt(String data,String algorithm){
        try
        {
            MessageDigest crypt = MessageDigest.getInstance(algorithm);
            crypt.reset();
            crypt.update(data.getBytes("UTF-8"));
            return byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
           return "";
        }
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
