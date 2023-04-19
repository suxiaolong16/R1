package com.halo;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql:///ruiji", "root", "315400sxl")
                .globalConfig(builder -> {
                    builder.author("halo") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            //.fileOverride() // 覆盖已生成文件
                            .outputDir("C:\\Users\\86155\\IdeaProjects\\R1\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.halo") // 设置父包名
                            .moduleName("customer") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "C:\\Users\\86155\\IdeaProjects\\R1\\src\\main\\resources\\mapper\\customer")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("r_text") // 设置需要生成的表名
                            .addTablePrefix("r_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
