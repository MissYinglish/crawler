# crawler
simple steps to learn a crawler procession

## class
- Spider 本打算存放爬虫类的公共方法，诸如：爬取给定链接的正文内容 SendGet ，给定模板和目标模式的匹配 RegexString ，从文本中获取链接并以线性表的形式返回 GetList。在这里，GetAllRecommendTopic 方法放在 zhiHu 本类中其实会更好。因为推荐内容是知乎推荐页的个性内容，应该放在类中自行设计。
- FileReaderWriter 把内容写入本地磁盘
- zhiHu 关于知乎类的具体、个性化操作
