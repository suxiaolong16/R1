package com.halo.util;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Component
public class ImageUtil {
    /**
     * 截取视频第六帧的图片
     * @param filePath 视频路径
     * @param dir 文件存放的根目录
     * @return 图片的相对路径 例：pic/1.png
     * @throws FrameGrabber.Exception
     */
    public static void videoImage(String mp4Path, String imgpath) throws FrameGrabber.Exception {
       // String pngPath = "";
        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(mp4Path);

        ff.start();
        int ffLength = ff.getLengthInFrames();
        Frame f;
        int i = 0;
        while (i < ffLength) {
            f = ff.grabImage();
            //截取第6帧
            if((i>5) &&  (f.image != null)){
                //生成图片的相对路径 例如：pic/uuid.png
               // pngPath = getPngPath();
                //执行截图并放入指定位置
                doExecuteFrame(f, imgpath);
                break;
            }
            i++;
        }
        ff.stop();


    }

    /**
     * 生成图片的相对路径
     * @return 图片的相对路径 例：pic/1.png
     */
//    private static String getPngPath(){
//        return  getUUID()+".png";
//    }


    /**
     * 生成唯一的uuid
     * @return uuid
     */
//    private static String getUUID(){
//        return UUID.randomUUID().toString().replace("-","");
//    }


    /**
     * 截取缩略图
     * @param f Frame
     * @param targerFilePath:封面图片存放路径
     */
    private static void doExecuteFrame(Frame f, String targerFilePath) {
        String imagemat = "jpg";
        if (null == f || null == f.image) {
            return;
        }
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bi = converter.getBufferedImage(f);
        File output = new File(targerFilePath);
        try {
            ImageIO.write(bi, imagemat, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
