# 利用 scheduler 进行任务处理时，如何触发 buffer 中的任务

### 背景
在项目开发的过程中，通过 [PublishSubject] 构建了一个 PubSub 类，用来实现将任务收集起来进行批量处理的操作
