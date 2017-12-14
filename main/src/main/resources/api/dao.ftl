<#if apipackage??>
package ${apipackage};
import ${pojo.getPackageName()}.${pojo.getShortName()};
<#if pojo.getIdentifierProperty().getType().isComponentType()>
import ${pojo.getIdentifierProperty().getType().getName()};
</#if>
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
public interface ${declarationName}Dao {

	public boolean add${declarationName}(${declarationName} e);
	public void update${declarationName}(${declarationName} e);
	public List<${declarationName}> getAll();
	<#if !pojo.isComponent() && pojo.hasIdentifierProperty()>
    public ${declarationName} get${declarationName}ById(${pojo.getIdentifierProperty().getType().getName()} id);
    </#if>
    public List<${declarationName}> get${declarationName}ByAttr(String attrName, String value);
    public void delete${declarationName}(${declarationName} e);
}
</#assign>

${pojo.generateImports()}
${classbody}
