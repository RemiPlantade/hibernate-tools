<#if apipackage??>
package ${apipackage};
<#if pojo.hasIdentifierProperty() && !pojo.isJavaType(pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5))>
import ${pojo.getPackageName()}.${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)};
</#if>
import api_builder.gen.api.bean.${pojo.getShortName()};
import api_builder.gen.api.service.impl.${pojo.getShortName()}ServiceImpl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;
import api_builder.gen.jackson.Views;

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
@Controller
@RequestMapping("api_builder")
public class ${declarationName}Controller {

	@Autowired
	private ${declarationName}ServiceImpl ${declarationName}Serv;


    <#if pojo.hasIdentifierProperty()>
	@JsonView(Views.${declarationName}View.class)
	</#if>
	@GetMapping("${declarationName}/{id}")
    public ResponseEntity<${declarationName}> getArticleById(@PathVariable("id") <#if pojo.hasIdentifierProperty()>${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)}<#else>int</#if> id) {
		${declarationName} instance = ${declarationName}Serv.get${declarationName}ById(id);
		return new ResponseEntity<${declarationName}>(instance, HttpStatus.OK);
	}
	<#if pojo.hasIdentifierProperty()>
	@JsonView(Views.${declarationName}View.class)
	</#if>
	@GetMapping("${declarationName}/all")
	public ResponseEntity<List<${declarationName}>> getAllArticles() {
		List<${declarationName}> list = ${declarationName}Serv.getAll();
		return new ResponseEntity<List<${declarationName}>>(list, HttpStatus.OK);
	}
	
	
	<#if pojo.hasIdentifierProperty()>
	@JsonView(Views.${declarationName}View.class)
	</#if>
	@PostMapping("${declarationName}")
	public ResponseEntity<Void> add${declarationName}(@RequestBody ${declarationName} instance, UriComponentsBuilder builder) {
                boolean flag = ${declarationName}Serv.add${declarationName}(instance);
                if (flag == false) {
        	    return new ResponseEntity<Void>(HttpStatus.CONFLICT);
                }
                HttpHeaders headers = new HttpHeaders();
                
               <#if pojo.hasIdentifierProperty()>
               	
               		headers.setLocation(builder.path("/${declarationName}/{id}").buildAndExpand(instance.${pojo.getGetterSignature(pojo.getIdentifierProperty())}()).toUri());
               	
               </#if>
             
                return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	<#if pojo.hasIdentifierProperty()>
	@JsonView(Views.${declarationName}View.class)
	</#if>
	@PutMapping("${declarationName}")
	public ResponseEntity<${declarationName}> update${declarationName}(@RequestBody ${declarationName} instance) {
		${declarationName}Serv.update${declarationName}(instance);
		return new ResponseEntity<${declarationName}>(instance, HttpStatus.OK);
	}
	
	<#if pojo.hasIdentifierProperty()>
	@JsonView(Views.${declarationName}View.class)
	</#if>
	@DeleteMapping("${declarationName}/{id}")
	public ResponseEntity<Void> deleteArticle(@RequestBody ${declarationName} instance) {
		${declarationName}Serv.delete${declarationName}(instance);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
    
}
</#assign>

${pojo.generateImports()}
${classbody}
