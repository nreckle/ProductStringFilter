### 处理设置原生设置字符串

设置源码中，为了试用不同的机器，试用 product 区分各个产品说明，比如 最常见的 product tablet 等，但是在 Android studio 中就会因为字符串而编译不通过！

通过 Java 工程，处理过滤不需要的 product，使用方法，配置 config.java 中的字符串路径以及过滤的product，然后运行工程即可！