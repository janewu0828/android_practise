AES256Demo
==========

android aes256 demo

java默认最多支持aes128.

如果需要实现aes256则需要在下载第三方的库。

第三方包下载地址：

**java6:**

[http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html](http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html)

**java7:**

[http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html](http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html)


把里面的两个jar包：`local_policy.jar` 和 `US_export_policy.jar` 替换掉原来安装目录`C:\Program Files\Java\jre6\lib\security` 下的两个jar包接可以了


详情请参见博文：
[Android中AES256加密的实现](http://leochin.com/android-aes256/)
