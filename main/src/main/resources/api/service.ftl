<#if apipackage??>
package ${apipackage};
import ${pojo.getPackageName()}.${pojo.getShortName()};
import java.util.List;
<#if pojo.getIdentifierProperty().getType().isComponentType()>
import ${pojo.getIdentifierProperty().getType().getName()};
</#if>
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
    public ${declarationName} get${declarationName}ById(int id);
    public List<${declarationName}> get${declarationName}ByAttr(String attrName, String value);
    public void delete${declarationName}(${declarationName} e);
    
}
</#assign>

${pojo.generateImports()}
${classbody}
