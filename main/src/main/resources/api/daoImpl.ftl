<#if apipackage??>
package ${apipackage};
<#if pojo.hasIdentifierProperty() && !pojo.isJavaType(pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5))>
import ${pojo.getPackageName()}.${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)};
</#if>
import api_builder.gen.api.dao.${pojo.getShortName()}Dao;
import api_builder.gen.api.bean.${pojo.getShortName()};
import java.util.List;
import javax.persistence.Query;
import javax.persistence.EntityExistsException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

<#else>
${pojo.getPackageDeclaration()}
</#if>
// Generated ${date} by Hibernate Tools ${version}
// Improved by AbouCorp

<#assign classbody>
@Transactional
@Repository
<#assign declarationName = pojo.importType(pojo.getDeclarationName())>/**
 * Home object for domain model class ${declarationName}.
 * @see ${pojo.getQualifiedDeclarationName()}
 * @author Hibernate Tools
 */
public class ${declarationName}DaoImpl implements ${declarationName}Dao{
    
    @${pojo.importType("javax.persistence.PersistenceContext")} 
    private ${pojo.importType("javax.persistence.EntityManager")} entityManager;
    
    public boolean add${declarationName}(${declarationName} transientInstance) {
       	try {
           entityManager.persist(transientInstance);
           entityManager.getTransaction().commit();
           return true;
        }
        catch (EntityExistsException re) {
            return false;
        }
    } 
    
        public void update${declarationName}(${declarationName} detachedInstance) {
        
        try {
            ${declarationName} result = entityManager.merge(detachedInstance); 
        }
        catch (RuntimeException re) {
            throw re;
        }
    }
    
    
        public void delete${declarationName}(${declarationName} persistentInstance) {
        try {
            entityManager.remove(persistentInstance);
        }
        catch (RuntimeException re) {
            throw re;
        }
    }

        public ${declarationName} get${declarationName}ById(<#if pojo.hasIdentifierProperty()>${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)}<#else>int</#if> id) {
        try {
            ${declarationName} instance = entityManager.find(${pojo.getDeclarationName()}.class, id);
            return instance;
        }
        catch (RuntimeException re) {
            throw re;
        }
    }

    public List<${declarationName}> getAll(){
    
    	try {
            List<${declarationName}> instance = entityManager.createQuery(
            "SELECT * FROM  ${declarationName}").getResultList();
            return instance;
        }
        catch (RuntimeException re) {
            throw re;
        }
    }
    
    public List<${declarationName}> get${declarationName}ByAttr(String attrName, String value){
    	
    	try {
        Query query = entityManager.createQuery("SELECT"+attrName+"FROM ${declarationName} WHERE" + attrName + "= :value");
        query.setParameter("value", value);
        List<${declarationName}> instance = (List<${declarationName}>) query.getResultList();
        return instance;
        }
        catch (RuntimeException re) {
            throw re;
        }
    	
    }
    
}
</#assign>

${pojo.generateImports()}
${classbody}
