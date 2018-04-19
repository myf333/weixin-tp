package com.myf.weixin.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.myf.weixin.util.CryptUtil;
import com.myf.weixin.util.MatrixToImageWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

/**
 * Created by maoyf0503 on 2018-4-12.
 *
 * @author maoyf0503
 */
@Controller
@RequestMapping(value = "qrCode")
public class QrCodeController {
    private static final String ENCRYPT_KEY = "1wDs#9rf";

    @RequestMapping(value = "getCode",method = RequestMethod.GET)
    public ModelAndView getCode(HttpServletResponse response) throws Exception{
        Long meetingId = 1789654L;
        byte[] encrypt =  CryptUtil.desCbcEncrypt(meetingId.toString().getBytes(),ENCRYPT_KEY.getBytes());
        String content = CryptUtil.byteToHex(encrypt);
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/png");
        BitMatrix bitMatrix = createQRCode(content,300);
        ServletOutputStream outStream = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix,"png",outStream);
        outStream.flush();
        outStream.close();
        response.setStatus(HttpServletResponse.SC_OK);
        return null;
    }

    private BitMatrix createQRCode(String contents, int size) throws WriterException {
        HashMap<EncodeHintType, Object> hints = new HashMap<>(16);
        // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 内容所使用字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //设置二维码边的空度，非负数
        hints.put(EncodeHintType.MARGIN, 1);
        return new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, size, size, hints);
    }

    @RequestMapping(value = "checkCode/{code}",method = RequestMethod.GET)
    @ResponseBody
    public String checkCode(@PathVariable("code")String code){
        byte[] decrypted = CryptUtil.desCbsDecrypt(CryptUtil.hexToBytes(code),ENCRYPT_KEY.getBytes());
        if(decrypted == null){
            return "";
        }
        return new String(decrypted);
    }
}
