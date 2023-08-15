package io.metersphere.tools.mybatis.validation;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.internal.DefaultCommentGenerator;

import java.util.Arrays;
import java.util.List;

public class SchemaAnnotationGenerator extends DefaultCommentGenerator {
    // 后台直接给值的字段，不需要限制传入
    private List<String> ignoreColumns = Arrays.asList(
            "create_time",
            "update_time",
            "delete_time",
            "create_user",
            "update_user",
            "delete_user",
            "num",
            "enable"
    );

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (!introspectedColumn.isNullable() && !ignoreColumns.contains(introspectedColumn.getActualColumnName())) {
            field.addJavaDocLine("@Schema(description = \"" + introspectedColumn.getRemarks() + "\", requiredMode = Schema.RequiredMode.REQUIRED)");

            String notBlankMessage = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime() + "." + introspectedColumn.getActualColumnName() + ".not_blank";
            if ("id".equalsIgnoreCase(introspectedColumn.getActualColumnName())) {
                field.addJavaDocLine("@NotBlank(message = \"{" + notBlankMessage + "}\", groups = {Updated.class})");
            } else {
                if (introspectedColumn.isStringColumn()) {
                    field.addJavaDocLine("@NotBlank(message = \"{" + notBlankMessage + "}\", groups = {Created.class})");
                } else {
                    field.addJavaDocLine("@NotNull(message = \"{" + notBlankMessage + "}\", groups = {Created.class})");
                }
            }
            if (introspectedColumn.isStringColumn()) {
                String lengthRangeMessage = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime() + "." + introspectedColumn.getActualColumnName() + ".length_range";
                field.addJavaDocLine("@Size(min = 1, max = " + introspectedColumn.getLength() + ", message = \"{" + lengthRangeMessage + "}\", groups = {Created.class, Updated.class})");
            }
        } else {
            field.addJavaDocLine("@Schema(description = \"" + introspectedColumn.getRemarks() + "\")");
        }

    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        super.addClassComment(innerClass, introspectedTable, markAsDoNotDelete);
    }
}
