# 项目主旨 #
记录分享个人知识点

# 系统构建
>
> sbt 版本配置: [project/build.properties](./project/build.properties)
>
> sbt 插件配置: [project/plugins.sbt](./project/plugins.sbt)
> 
> 项目配置: [build.sbt](./build.sbt)

# 代码提交原则
* 每一个新的主题，新建一个新的 module 进行撰写，module 名 auro-{主题名}
* demo 的 package：com.sylvan.auro.{主题名}
* auro-{主题名} 目录下新建 docs 目录，可以存放分享文档，如：why-not-use-custom-tokenizer.md
* auro-{主题名}/docs 目录下新建 pictures 目录存放分享文档需要用到的图片

# 系统初始化与代码格式化
* 运行 bin/auto-hooks.sh
* 运行命令 `docker-compose -f docker/docker-compose.yml up -d`，启动系统环境

### TODO LIST ###
* 按代码提交原则修改代码格式，整体整合到 auro 中
* JsonUtils 添加测试用例
* 添加 graph cache 的实现接口和压测
* 完善 properties config.md 添加 test case
* 完善 rxjava 的使用说明，添加 test case
* 添加定时复习框架
