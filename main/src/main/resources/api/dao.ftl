<#if apipackage??>
package ${apipackage};
<#else>
${pojo.getPackageDeclaration()}
</#if>
// Generated ${date} by Hibernate Tools ${version}
// Improved by AbouCorp

<#assign classbody>
<#assign declarationName = pojo.importType(pojo.getDeclarationName())>/**
 * Home object for domain model class ${declarationName}.
 * @see ${pojo.getQualifiedDeclarationName()}
 * @author Hibernate Tools
 */
public interface ${declarationName}Dao {
    
}
</#assign>

${pojo.generateImports()}
${classbody}
