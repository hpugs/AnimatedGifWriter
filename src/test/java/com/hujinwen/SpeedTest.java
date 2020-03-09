package com.hujinwen;

import org.junit.Test;

import java.io.IOException;

public class SpeedTest {

    @Test
    public void test1() throws IOException {
        final WriteTest writeTest = new WriteTest();

        // 防止某些初始化行为对测试结果的影响
        writeTest.animatedGifEncoderTest();
        writeTest.gifSequenceWriterTest();

        // 测试结果 2058ms / 2050.2ms / 2051.7ms
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            writeTest.animatedGifEncoderTest();
        }
        System.out.println((System.currentTimeMillis() - start) / 10.0);

        // 测试结果 2312ms / 2272.9ms / 2149.9ms
        start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            writeTest.gifSequenceWriterTest();
        }
        System.out.println((System.currentTimeMillis() - start) / 10.0);

    }

}