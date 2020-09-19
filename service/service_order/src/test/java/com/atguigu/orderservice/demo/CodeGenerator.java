package com.atguigu.orderservice.demo;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @since 2018/12/13
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CodeGenerator {

    @Test
    public void run() {

        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir("E:\\study\\Internet_Java\\atguigu\\project\\guli_parent\\service\\service_order" + "/src/main/java");

        gc.setAuthor("zhengWu");
        gc.setOpen(false); //生成后是否打开资源管理器
        gc.setFileOverride(false); //重新生成时文件是否覆盖

        //UserServie
        gc.setServiceName("%sService");    //去掉Service接口的首字母I

        gc.setIdType(IdType.ID_WORKER_STR); //主键策略
        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
        gc.setSwagger2(true);//开启Swagger2模式

        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/guli?serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("");
        dsc.setPassword("");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.atguigu");
        //包  com.atguigu.eduservice
        pc.setModuleName("orderservice"); //模块名
        //包  com.atguigu.eduservice.controller
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        //如果有多张表可以用逗号隔开
        strategy.setInclude("t_order", "t_pay_log");

        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀
        //去掉表前缀t
//        strategy.setTablePrefix("t" + "_");

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作

        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符

        mpg.setStrategy(strategy);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        String mapperXmlPath = "/templates/mapper.xml.vm";
        String mapperJavaPath = "/templates/mapper.java.vm";
        List<FileOutConfig> focList = new ArrayList<>();
        //Mapper的Xml文件路径配置
        focList.add(new FileOutConfig(mapperXmlPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义xml 文件名和生成路径
                return projectPath + "/src/main/resources/mappers/" +
                        tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
    /*    //Mapper的Java路径配置
        focList.add(new FileOutConfig(mapperJavaPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义Dao类文件名和生成路径
                return projectPath + "/src/main/java/com/atguigu/eduservice/mapper/"
                        + tableInfo.getEntityName() + "Dao" + StringPool.DOT_JAVA;
            }
        });*/


        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        // 6、执行
        mpg.execute();
    }
}
