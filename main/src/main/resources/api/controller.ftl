<#if apipackage??>
package ${apipackage};
<#if pojo.hasIdentifierProperty() && !pojo.isJavaType(pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5))>
import ${pojo.getPackageName()}.${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)};
</#if>
<#if pojo.isPartOfUnionTable(pojo_list)>
<#assign unionType = pojo.getUnionPOJOClass(pojo_list)>
<#assign unionTypeName = unionType.getShortName()>
import api_builder.gen.bean.${unionTypeName};
import api_builder.gen.service.${unionTypeName?cap_first}Service;
<#if pojo.isBiUnionEntity(unionType)>
import api_builder.gen.bean.${pojo.getOtherTypeNameInBiUnion(unionType)};
import api_builder.gen.service.${pojo.getOtherTypeNameInBiUnion(unionType)?cap_first}Service;
</#if>
</#if>
import api_builder.gen.bean.${pojo.getShortName()};
import api_builder.gen.service.${pojo.getShortName()}Service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.util.UriComponentsBuilder;


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
	private ${declarationName}Service ${declarationName?lower_case}Service;
	<#if pojo.isPartOfUnionTable(pojo_list)>
	@Autowired
	private ${unionTypeName?cap_first}Service ${unionTypeName?lower_case}Service;
	<#if pojo.isBiUnionEntity(unionType)>
	@Autowired
	private ${pojo.getOtherTypeNameInBiUnion(unionType)?cap_first}Service ${pojo.getOtherTypeNameInBiUnion(unionType)?lower_case}Service;
	</#if>
	</#if>

	@GetMapping("${declarationName?lower_case}/{id}")
    public ResponseEntity<${declarationName}> getArticleById(@PathVariable("id") <#if pojo.hasIdentifierProperty()>${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)}<#else>int</#if> id) {
		${declarationName} instance = ${declarationName?lower_case}Service.find${declarationName}ById(id);
		return new ResponseEntity<${declarationName}>(instance, HttpStatus.OK);
	}

	@GetMapping("${declarationName?lower_case}/all")
	public ResponseEntity<List<${declarationName}>> getAllArticles() {
		List<${declarationName}> list = ${declarationName?lower_case}Service.findAll();
		return new ResponseEntity<List<${declarationName}>>(list, HttpStatus.OK);
	}
	
	@PostMapping("${declarationName?lower_case}/post")
	public ResponseEntity<Void> add${declarationName}(@RequestBody ${declarationName} instance, UriComponentsBuilder builder) {
        ${declarationName?lower_case}Service.save(instance);
        // return new ResponseEntity<Void>(HttpStatus.CONFLICT); 
        HttpHeaders headers = new HttpHeaders();
        <#if pojo.hasIdentifierProperty()>
        headers.setLocation(builder.path("/${declarationName}/{id}").buildAndExpand(instance.${pojo.getGetterSignature(pojo.getIdentifierProperty())}()).toUri());
        </#if>
             
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@PutMapping("${declarationName?lower_case}/put")
	public ResponseEntity<${declarationName}> update${declarationName}(@RequestBody ${declarationName} instance) {
		${declarationName?lower_case}Service.save(instance);
		return new ResponseEntity<${declarationName}>(instance, HttpStatus.OK);
	}
	
	@DeleteMapping("${declarationName?lower_case}/delete/{id}")
	public ResponseEntity<Void> deleteArticle(@PathVariable("id") <#if pojo.hasIdentifierProperty()>${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)}<#else>int</#if> id) {
		${declarationName?lower_case}Service.delete(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	/**
	 * Si il existe une table d'union sans attribut autres que 2 foreign key dont une qui pointe vers cette table
	 * Alors on peut faire le lien via cette table de liaison simplement via l'URL (PUT)/entity/id/otherEntityToLink
	 * Sinon il faut passer par l'url : (PUT)/entity/id/linkerEntity
	 **/
	<#if pojo.isPartOfUnionTable(pojo_list)>
	<#if !pojo.isBiUnionEntity(unionType)>
	@PostMapping("${declarationName?lower_case}/{id}/${unionTypeName?lower_case}s")
	public ResponseEntity<Void> add${unionTypeName?cap_first}(@PathVariable("id") <#if pojo.hasIdentifierProperty()>${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)}<#else>int</#if> id,@RequestBody ${unionTypeName?cap_first} instance, UriComponentsBuilder builder){
	 	${unionTypeName?lower_case}Service.save(instance);
        HttpHeaders headers = new HttpHeaders();
        <#if pojo.hasIdentifierProperty()>
        headers.setLocation(builder.path("/${declarationName}/{id}").buildAndExpand(instance.${pojo.getGetterSignature(pojo.getIdentifierProperty())}()).toUri());
        </#if>
             
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    
    <#else>
    @PostMapping("${declarationName?lower_case}/{id}/${pojo.getOtherTypeNameInBiUnion(unionType)}s")
	public ResponseEntity<Void> add${pojo.getOtherTypeNameInBiUnion(unionType)}(@PathVariable("id") <#if pojo.hasIdentifierProperty()>${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)}<#else>int</#if> id,@RequestBody ${pojo.getOtherTypeNameInBiUnion(unionType)} instance, UriComponentsBuilder builder){
	 	${pojo.getOtherTypeNameInBiUnion(unionType)?lower_case}Service.save(instance);
        HttpHeaders headers = new HttpHeaders();
        <#if pojo.hasIdentifierProperty()>
        headers.setLocation(builder.path("/${declarationName}/{id}").buildAndExpand(instance.${pojo.getGetterSignature(pojo.getIdentifierProperty())}()).toUri());
        </#if>
             
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    </#if>
	</#if>
}
</#assign>

${pojo.generateImports()}
${classbody}
