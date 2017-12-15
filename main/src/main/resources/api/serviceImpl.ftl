<#if apipackage??>
package ${apipackage};
import ${pojo.getPackageName()}.${pojo.getShortName()};
import api_builder.gen.api.service.${pojo.getShortName()}Service;
import api_builder.gen.api.dao.impl.${pojo.getShortName()}DaoImpl;
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
public class ${declarationName}ServiceImpl implements ${declarationName}Service {

		private ${declarationName}DaoImpl dao;
    
    	public ${declarationName}ServiceImpl(){
    	
    		dao = new ${declarationName}DaoImpl();
    	}
    	
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
	    public ${declarationName} get${declarationName}ById(Object id){
	    
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
