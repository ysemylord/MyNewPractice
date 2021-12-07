## 构造者模式

将复杂对象的构造和表示分离开，使得相同的构造过程可以创建不同的表示。

## Retrofit

### 1. Retrofit提供的注解

+ Get

+ Post

+ Field

+ Query

### 2. Retrofit的思路

Retrofit通过**动态代理**生成Service.class的代理类

在代理类中

1. 解析方法上的注解
2. 生成Call

