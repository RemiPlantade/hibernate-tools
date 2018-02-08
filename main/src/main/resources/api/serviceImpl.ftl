<#if apipackage??>
package ${apipackage};
<#if pojo.hasIdentifierProperty() && !pojo.isJavaType(pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5))>
import ${pojo.getPackageName()}.${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)};
</#if>
import ${pojo.getPackageName()}.${pojo.getShortName()};
import api_builder.service.${pojo.getShortName()}Service;
import api_builder.dao.${pojo.getShortName()}Dao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


<#else>
${pojo.getPackageDeclaration()}
</#if>
// Generated ${date} by Hibernate Tools ${version}
// Improved by AbouCorp

<#assign classbody>
@Service
<#assign declarationName = pojo.importType(pojo.getDeclarationName())>/**
 * Home object for domain model class ${declarationName}.
 * @see ${pojo.getQualifiedDeclarationName()}
 * @author Hibernate Tools
 */
public class ${declarationName}ServiceImpl implements ${declarationName}Service {
		@Autowired
		private ${declarationName}Dao dao;
    	
	    public boolean add${declarationName}(${declarationName} e){
	    	return dao.add${declarationName}(e);
	    }
	    
	    public void update${declarationName}(${declarationName} e){
	    	dao.update${declarationName}(e);
	    }
	    
		public List<${declarationName}> getAll(){
			List<${declarationName}> listInstance =  dao.getAll();
			return listInstance;
		}
		
	    public ${declarationName} get${declarationName}ById(<#if pojo.hasIdentifierProperty()>${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)}<#else>int</#if> id){
	    	${declarationName} instance = dao.get${declarationName}ById(id);
	    	return instance;
	    }
	    
	    public List<${declarationName}> get${declarationName}ByAttr(String attrName, String value){
	    	List<${declarationName}> listInstance = dao.get${declarationName}ByAttr(attrName,value);
	    	return listInstance;
	    }
	    
	    public void delete${declarationName}(${declarationName} e){
	    	dao.delete${declarationName}(e);
	    }
    
}
</#assign>

${pojo.generateImports()}
${classbody}
