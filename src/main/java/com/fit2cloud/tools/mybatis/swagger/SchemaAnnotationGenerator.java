package com.fit2cloud.tools.mybatis.swagger;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.internal.DefaultCommentGenerator;

public class SchemaAnnotationGenerator extends DefaultCommentGenerator {

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (!introspectedColumn.isNullable() && !("create_time".equalsIgnoreCase(introspectedColumn.getActualColumnName())
                || "delete_time".equalsIgnoreCase(introspectedColumn.getActualColumnName())
                || "update_time".equalsIgnoreCase(introspectedColumn.getActualColumnName()))) {
            field.addJavaDocLine("@Schema(title = \"" + introspectedColumn.getRemarks() + "\", requiredMode = Schema.RequiredMode.REQUIRED)");

            String notBlankMessage = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime() + "." + introspectedColumn.getActualColumnName() + ".not_blank";
            if ("id".equalsIgnoreCase(introspectedColumn.getActualColumnName())) {
                field.addJavaDocLine("@NotBlank(message = \"{" + notBlankMessage + "}\", groups = {Created.class, Updated.class})");
            } else {
                field.addJavaDocLine("@NotBlank(message = \"{" + notBlankMessage + "}\", groups = {Updated.class})");
            }

            String lengthRangeMessage = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime() + "." + introspectedColumn.getActualColumnName() + ".length_range";
            field.addJavaDocLine("@Size(min = 1, max = " + introspectedColumn.getLength() + ", message = \"{" + lengthRangeMessage + "}\", groups = {Created.class, Updated.class})");
        } else {
            field.addJavaDocLine("@Schema(title = \"" + introspectedColumn.getRemarks() + "\")");
        }

    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        super.addClassComment(innerClass, introspectedTable, markAsDoNotDelete);
    }
}
