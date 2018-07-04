
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
import java.text.SimpleDateFormat;
import java.util.Iterator; 

import com.fasterxml.jackson.core.JsonParser;
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
	public ${declarationName} deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

}
</#assign>
${pojo.generateImports()}
${classbody}
