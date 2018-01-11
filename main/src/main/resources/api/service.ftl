<#if apipackage??>
package ${apipackage};
<#if pojo.hasIdentifierProperty() && !pojo.isJavaType(pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5))>
import ${pojo.getPackageName()}.${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)};
</#if>
import ${pojo.getPackageName()}.${pojo.getShortName()};
import java.util.List;
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
public interface ${declarationName}Service {
    
    public boolean add${declarationName}(${declarationName} e);
    public void update${declarationName}(${declarationName} e);
	public List<${declarationName}> getAll();
    public ${declarationName} get${declarationName}ById(<#if pojo.hasIdentifierProperty()>${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)}<#else>int</#if> id);
    public List<${declarationName}> get${declarationName}ByAttr(String attrName, String value);
    public void delete${declarationName}(${declarationName} e);
    
}
</#assign>

${pojo.generateImports()}
${classbody}
