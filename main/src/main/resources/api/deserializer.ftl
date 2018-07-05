
<#if apipackage??>
package ${apipackage};
</#if>
<#list pojo.getAllEntitiesPropClassName(pojo,pojo_list) as pojoTypeName>
import api_builder.gen.service.${pojoTypeName?cap_first}Service;
import api_builder.gen.bean.${pojoTypeName?cap_first};
</#list>
import api_builder.gen.jackson.Views;
import ${pojo.getPackageName()}.${pojo.getDeclarationName()};

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator; 

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import org.springframework.beans.factory.annotation.Autowired;

// Generated ${date} by Hibernate Tools ${version}
// Improved by AbouCorp
<#assign classbody>
<#assign declarationName = pojo.importType(pojo.getDeclarationName())>/**
 * Home object for domain model class ${declarationName}.
 * @see ${pojo.getQualifiedDeclarationName()}
 * @author Hibernate Tools
 */
public class ${declarationName}Deserializer extends StdDeserializer<${declarationName}>{
<#list pojo.getAllEntitiesPropClassName(pojo,pojo_list) as pojoTypeName>
	@Autowired
	private ${pojoTypeName?cap_first}Service ${pojoTypeName?lower_case}Service; 
</#list>
	<#if pojo.containDateProp()>
	private SimpleDateFormat format = new SimpleDateFormat("<#if dateFormat??>${dateFormat}<#else>dd.MM.YYYY</#if>");
	</#if>
	
	public ${declarationName}Deserializer() {
		this(null);
	}

	public ${declarationName}Deserializer(Class<${declarationName}> t) {
		super(t);
	}
	
	@Override
	public ${declarationName} deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode node = parser.getCodec().readTree(parser);
	    <#list pojo.getAllJavaProp(pojo_list) as prop>
	    	<#assign propTypeName = pojo.getJavaTypeName(prop, jdk5) >
		${propTypeName} ${prop.getName()?lower_case} = null;
		if(node.get("${prop.getName()}") != null) {
			<#if pojo.isJacksonNumberType(propTypeName)>
			${prop.getName()?lower_case} = (${propTypeName}) node.get("${prop.getName()}").numberValue();
			<#elseif pojo.isJacksonStringType(propTypeName)>
			${prop.getName()?lower_case} = (${propTypeName}) node.get("${prop.getName()}").asText();
			<#elseif pojo.isJacksonBooleanType(propTypeName)>
			${prop.getName()?lower_case} = (${propTypeName}) node.get("${prop.getName()}").asBoolean()
			<#elseif pojo.isJacksonBinaryType(propTypeName)>
		    ${prop.getName()?lower_case} = (${propTypeName}) node.get("${prop.getName()}").asText();
			<#elseif pojo.isJacksonArrayType(propTypeName)>  
			// ArrayList
			<#elseif propTypeName == "Date">
			if(!node.get("${prop.getName()?lower_case}").asText().toLowerCase().equals("null")) {
			try {
				${prop.getName()?lower_case} = format.parse(node.get("${prop.getName()?lower_case}").asText());
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
			</#if>
		}  	 
	    </#list>
	    return new ${declarationName}(
		<#list pojo.getAllJavaProp(pojo_list) as prop>
		${prop.getName()?lower_case}<#if prop?has_next>,</#if>
		</#list>
		);	
		
		<#list pojo_list as pojojo>
		// ${pojojo.getShortName()}
		// ${pojojo.isUnionEntity()?string('yes', 'no')}
			<#list pojojo.getLinkerEntities() as p >
			// ${p.getShortName()}
			</#list>
			<#list pojojo.getLinkedEntities() as f >
			// ${f.getShortName()}
			</#list>
		</#list>
		
	}
}
</#assign>
${pojo.generateImports()}
${classbody}
