package com.hujinwen;

import com.hujinwen.core.AnimatedGifEncoder.AnimatedGifEncoder;
import com.hujinwen.core.GifSequenceWriter.GifSequenceWriter;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WriteTest {

    /**
     * 方式一 演示
     */
    @Test
    public void animatedGifEncoderTest() throws IOException {
        final List<byte[]> pngBytes = getPngList();
        final List<BufferedImage> imgList = new ArrayList<>();

        for (byte[] pngByte : pngBytes) {
            imgList.add(ImageIO.read(new ByteArrayInputStream(pngByte)));
        }


        try (
                final FileImageOutputStream outputStream = new FileImageOutputStream(new File("result_01.gif"));
                final GifSequenceWriter writer = new GifSequenceWriter(outputStream, imgList.get(0).getType(), 500, false)
        ) {
            for (BufferedImage img : imgList) {
                writer.writeToSequence(img);
            }
        }
    }

    /**
     * 方式二 演示
     */
    @Test
    public void gifSequenceWriterTest() throws IOException {
        final List<byte[]> pngBytes = getPngList();
        final List<BufferedImage> imgList = new ArrayList<>();

        for (byte[] pngByte : pngBytes) {
            imgList.add(ImageIO.read(new ByteArrayInputStream(pngByte)));
        }

        try (
                final FileOutputStream outputStream = new FileOutputStream(new File("result_02.gif"))
        ) {
            final AnimatedGifEncoder encoder = new AnimatedGifEncoder();
            encoder.setRepeat(0);
            encoder.start(outputStream);
            for (BufferedImage img : imgList) {
                encoder.addFrame(img);
                encoder.setDelay(500);
            }
            encoder.finish();
            outputStream.flush();
        }
    }

    /**
     * 获取测试图片列表
     */
    private List<byte[]> getPngList() throws IOException {
        final List<byte[]> records = new ArrayList<>();

        final String resourcePath = this.getClass().getResource("/").getPath();
        final File[] pngFiles = new File(resourcePath + "/static").listFiles();

        if (pngFiles == null) {
            throw new RuntimeException("The test png could not be found!");
        }

        for (File pngFile : pngFiles) {
            if (!pngFile.getName().endsWith("png")) {
                continue;
            }
            try (
                    final FileInputStream inputStream = new FileInputStream(pngFile);
                    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
            ) {
                final byte[] buffer = new byte[1024];
                int read;
                while ((read = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, read);
                }
                outputStream.flush();
                records.add(outputStream.toByteArray());
            }
        }

        return records;
    }

}