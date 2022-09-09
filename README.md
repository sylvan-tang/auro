# AURO

# 代码提交原则

* 每一个新的主题，新建一个新的 module 进行撰写，module 名 {主项目名}-{大类型}
  * juno：java 程序对应的主项目
  * soteria：scala 程序对应的主项目
  * phoebe：python 程序对应的主项目
  * rhea：rust 程序对应的主项目
  * wiki：各种读书笔记
* demo 的 package：com.sylvan.{主项目名}.{大类型}.{主题名}
* module 包 {主项目名}-{大类型} 目录下放分享稿：{主题名}-bigdata-smallfile.md
* module 包 {主项目名}-{大类型} 目录下新建 picture/{主题名}/ 目录存放 {主题名}-bigdata-smallfile.md 需要用到的图片

### TODO LIST

* JsonUtils 添加测试用例
* 添加 graph cache 的实现接口和压测
* 完善 properties config.md 添加 test case
* 完善 rxjava 的使用说明，添加 test case
* 添加定时复习框架
* RxJava 底层实现原理
* Netty 底层实现原理
* 整理 phoebe 目录

### 系统初始化

* 运行 bin/init.sh
* 运行命令 `docker-compose -f docker/docker-compose.yml up -d`，启动系统环
