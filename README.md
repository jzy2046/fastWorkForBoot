**Spring_boot快速工作框架(fastWork)** 

JDK版本:

      1.8
版本0.01
数据库环境:

    MYSQL;
库名:
    
    test;

表名:

    user_info_t; 
 
 包含字段:
 
    id  类型:int
    user_name 类型:varchar
    password 类型:varchar
    age 类型:tinyint
    
增加通用mapper查询:

     `可以使用 HQL语句
     Example example=new Example(User.class);
     Example.Criteria criteria = example.createCriteria();
     criteria.andCondition("id is not",null);
     List<User> users = userDao.selectByExample(example);`
增加lombok: `不需要写实体类get set`

增加单元测试以及@Slf4j
