<#if apipackage??>
package ${apipackage};
<#if pojo.hasIdentifierProperty() && !pojo.isJavaType(pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5))>
import ${pojo.getPackageName()}.${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)};
</#if>
import ${pojo.getPackageName()}.${pojo.getShortName()};
import api_builder.gen.service.${pojo.getShortName()}Service;
import api_builder.gen.dao.${pojo.getShortName()}Dao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


<#else>
${pojo.getPackageDeclaration()}
</#if>
// Generated ${date} by Hibernate Tools ${version}
// Improved by AbouCorp

<#assign classbody>
@Service
@Transactional("tm1")
<#assign declarationName = pojo.importType(pojo.getDeclarationName())>/**
 * Home object for domain model class ${declarationName}.
 * @see ${pojo.getQualifiedDeclarationName()}
 * @author Hibernate Tools
 */
public class ${declarationName}ServiceImpl implements ${declarationName}Service {
		@Autowired
		private ${declarationName}Dao dao;
    	
	    public void save(${declarationName} e){
	    	dao.save(e);
	    }
	    
		public List<${declarationName}> findAll(){
			return (List<${declarationName}>)  dao.findAll();
		}
		
	    public ${declarationName} find${declarationName}ById(<#if pojo.hasIdentifierProperty()>${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)}<#else>int</#if> id){
	    	return dao.findOne(id);
	    }
	    
	    public List<${declarationName}> find${declarationName}ByAttr(String attrName, String value){
	    	return dao.findByAttr(attrName,value);
	    }
	    
	    public void delete(<#if pojo.hasIdentifierProperty()>${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)}<#else>int</#if> e){
	    	dao.delete(e);
	    }
    
}
</#assign>

${pojo.generateImports()}
${classbody}
