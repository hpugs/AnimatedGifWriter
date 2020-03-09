# Gif 动画生成器
> 这里用到了2位大佬写的编码类 `AnimateGifEncoder`、`GifSequenceWriter`。
> 这两个类并不好找，这里对其进行整理。

[TOC]

* **详细演示请参考具体代码：[使用代码](https://github.com/I-Guitar/AnimatedGifWriter/blob/master/src/test/java/com/hujinwen/WriteTest.java)、[速度测试](https://github.com/I-Guitar/AnimatedGifWriter/blob/master/src/test/java/com/hujinwen/SpeedTest.java)。项目导入后可直接运行**

* **这里做一个总结**：
  * 排除初始化影响，同样生成4帧gif，AnimateGifEncoder比GifSequenceWriter平均快上100~300毫秒。
  * AnimateGifEncoder生成的GIF字节数较大，GifSequenceWriter压缩更多，文件更小。





## 使用演示

* **AnimateGifEncoder**

  > 简单演示，详细演示请参考具体代码：[使用代码](https://github.com/I-Guitar/AnimatedGifWriter/blob/master/src/test/java/com/hujinwen/WriteTest.java)

  ```java
  final BufferedImage img1 = ImageIO.read(new ByteArrayInputStream(pngByte1));
  final BufferedImage img2 = ImageIO.read(new ByteArrayInputStream(pngByte2));
  final BufferedImage img3 = ImageIO.read(new ByteArrayInputStream(pngByte3));
  
  try (
  	final FileOutputStream outputStream = new FileOutputStream(new File("result_02.gif"))
  ) {
      final AnimatedGifEncoder encoder = new AnimatedGifEncoder();
      encoder.setRepeat(0);
      encoder.start(outputStream);
      encoder.addFrame(img1);
      encoder.setDelay(500);
      encoder.addFrame(img2);
      encoder.setDelay(500);
      encoder.addFrame(img3);
      encoder.finish();
      outputStream.flush();
  }
  ```

  

* **GifSequenceWriter**

  > 简单演示，详细演示请参考具体代码：[使用代码](https://github.com/I-Guitar/AnimatedGifWriter/blob/master/src/test/java/com/hujinwen/WriteTest.java)
  
  ```java
  final BufferedImage img1 = ImageIO.read(new ByteArrayInputStream(pngByte1));
  final BufferedImage img2 = ImageIO.read(new ByteArrayInputStream(pngByte2));
  final BufferedImage img3 = ImageIO.read(new ByteArrayInputStream(pngByte3));
  
  try (
      final FileImageOutputStream outputStream = new FileImageOutputStream(new File("result_01.gif"));
      final GifSequenceWriter writer = new GifSequenceWriter(outputStream, img1.getType(), 500, false)
  ) {
      writer.writeToSequence(img1);
      writer.writeToSequence(img2);
      writer.writeToSequence(img3);
  }
  
  ```
  
  

## 速度测试

> 简易测试，详细演示请参考具体代码：[速度测试](https://github.com/I-Guitar/AnimatedGifWriter/blob/master/src/test/java/com/hujinwen/SpeedTest.java)

```java
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
```

