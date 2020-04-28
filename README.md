## 模块图和接口文档在doc文件中

## 数据源

1.表结构在fake_data/src/main/resources/下的em.sql。
直接导入navicat执行即可。
2.数据由fake_data随机生成，运行FakeDataApplication即可。
也可在fake_data包下的Start类中自行设置生成数据的频率。
## 踩坑记录
1.当你在控制台发现报错的地方是一个你已经删除的包，把target删掉即可。

2.如果在A模块启动时找不到声明在B模块中的Bean：
- 检查包的扫描。
- 检查B模块的包路径。
- 没有把B模块的*application包含在包扫描里。

3.制定了数据库之后，@Table(name = "表名")还是说找不到表，点击右侧的数据库，刷新一下。

4.Servlet.service() for servlet [dispatcherServlet] in context with path [] 
threw exception [Filtered request failed.] with root cause
出现这个错误是因为没有网，所以redis连接不到。


5. UnknownSessionException: org.crazycake.shiro.exception.SerializationException: serialize error, object=org.apache.shiro.session.mgt.SimpleSession,id=xxx
这个是因为在UserRealm中返回的安全数据类没有实现序列化接口.

6.在参数前加了自定义的验证器但没有起作用。在类前面加@Validated就可以了。

7.定义抽象Dao时要加上@NoRepositoryBean注解。