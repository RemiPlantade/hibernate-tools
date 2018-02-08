<#if apipackage??>
package ${apipackage};
<#if pojo.hasIdentifierProperty() && !pojo.isJavaType(pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5))>
import ${pojo.getPackageName()}.${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)};
</#if>
import api_builder.bean.${pojo.getShortName()};
import api_builder.service.${pojo.getShortName()}Service;


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
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;
import api_builder.jackson.Views;

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
@RestController
@RequestMapping("api_builder")
public class ${declarationName}Controller {

	@Autowired
	private ${declarationName}Service ${declarationName?lower_case}Serv;


    <#if pojo.hasIdentifierProperty()>
	@JsonView(Views.${declarationName}View.class)
	</#if>
	@GetMapping("${declarationName?lower_case}/{id}")
    public ResponseEntity<${declarationName}> getArticleById(@PathVariable("id") <#if pojo.hasIdentifierProperty()>${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)}<#else>int</#if> id) {
		${declarationName} instance = ${declarationName?lower_case}Serv.get${declarationName}ById(id);
		return new ResponseEntity<${declarationName}>(instance, HttpStatus.OK);
	}
	<#if pojo.hasIdentifierProperty()>
	@JsonView(Views.${declarationName}View.class)
	</#if>
	@GetMapping("${declarationName?lower_case}/all")
	public ResponseEntity<List<${declarationName}>> getAllArticles() {
		List<${declarationName}> list = ${declarationName?lower_case}Serv.getAll();
		return new ResponseEntity<List<${declarationName}>>(list, HttpStatus.OK);
	}
	
	
	<#if pojo.hasIdentifierProperty()>
	@JsonView(Views.${declarationName}View.class)
	</#if>
	@PostMapping("${declarationName?lower_case}/post")
	public ResponseEntity<Void> add${declarationName}(@RequestBody ${declarationName} instance, UriComponentsBuilder builder) {
                boolean flag = ${declarationName?lower_case}Serv.add${declarationName}(instance);
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
	@PutMapping("${declarationName?lower_case}/put")
	public ResponseEntity<${declarationName}> update${declarationName}(@RequestBody ${declarationName} instance) {
		${declarationName?lower_case}Serv.update${declarationName}(instance);
		return new ResponseEntity<${declarationName}>(instance, HttpStatus.OK);
	}
	
	<#if pojo.hasIdentifierProperty()>
	@JsonView(Views.${declarationName}View.class)
	</#if>
	@DeleteMapping("${declarationName?lower_case}/delete/{id}")
	public ResponseEntity<Void> deleteArticle(@RequestBody ${declarationName} instance) {
		${declarationName?lower_case}Serv.delete${declarationName}(instance);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
    
}
</#assign>

${pojo.generateImports()}
${classbody}
