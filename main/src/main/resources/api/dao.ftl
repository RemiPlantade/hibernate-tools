
<#if apipackage??>
package ${apipackage};
import ${pojo.getPackageName()}.${pojo.getShortName()};
<#if pojo.hasIdentifierProperty() && !pojo.isJavaType(pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5))>
import ${pojo.getPackageName()}.${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)};
</#if>
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
<#else>
${pojo.getPackageDeclaration()}
</#if>
// Generated ${date} by Hibernate Tools ${version}
// Improved by AbouCorp

<#assign classbody>
@Transactional("tm2")
@Repository
<#assign declarationName = pojo.importType(pojo.getDeclarationName())>/**
 * Home object for domain model class ${declarationName}.
 * @see ${pojo.getQualifiedDeclarationName()}
 * @author Hibernate Tools
 */
public interface ${declarationName}Dao extends CrudRepository<${declarationName}, <#if pojo.hasIdentifierProperty()><#if pojo.isJavaPrimitiveType(pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5))>${pojo.getComplexJavaType(pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5))}<#else>${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)}</#if><#else>Integer</#if>>{
	@Query("SELECT p FROM ${declarationName} p WHERE :attrName = :value")
	public List<${declarationName}> findByAttr(@Param("attrName") String attrName,@Param("value") String value);
}
</#assign>
${pojo.generateImports()}
${classbody}
