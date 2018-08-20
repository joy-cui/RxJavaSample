RxJava 2 demo
================

### 项目介绍

RxJava 2 和 Retrofit 结合使用的几个最常见使用方式举例。

1. **基本使用**

  实现最基本的网络请求和结果处理。

2. **转换(map)**

  把返回的数据转换成更方便处理的格式再交给 Observer。

3. **非一次性 token**

  对于非一次性的 token （即可重复使用的 token），在获取 token 后将它保存起来反复使用，并通过 retryWhen() 实现 token 失效时的自动重新获取，将 token 获取的流程彻底透明化，简化开发流程。


### apk 下载
[RxJavaSample.apk](https://github.com/rengwuxian/RxJavaSample/RxJavaSample.0.apk)