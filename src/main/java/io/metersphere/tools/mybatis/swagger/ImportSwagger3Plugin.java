package io.metersphere.tools.mybatis.swagger;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

public class ImportSwagger3Plugin extends PluginAdapter {
    private FullyQualifiedJavaType serializable = new FullyQualifiedJavaType("io.swagger.v3.oas.annotations.media.Schema");

    public boolean validate(List<String> list) {
        return true;
    }

    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.makeApiModel(topLevelClass, introspectedTable);
        return true;
    }

    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.makeApiModel(topLevelClass, introspectedTable);
        return true;
    }

    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.makeApiModel(topLevelClass, introspectedTable);
        return true;
    }

    private void makeApiModel(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType(this.serializable);
    }


}
