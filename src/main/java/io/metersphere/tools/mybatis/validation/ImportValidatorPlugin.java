package io.metersphere.tools.mybatis.validation;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

public class ImportValidatorPlugin extends PluginAdapter {

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
        topLevelClass.addImportedType(new FullyQualifiedJavaType("jakarta.validation.constraints.NotBlank"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("jakarta.validation.constraints.Size"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("io.metersphere.validation.groups.Created"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("io.metersphere.validation.groups.Updated"));
    }


}
