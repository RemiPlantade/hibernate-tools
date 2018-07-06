
<#if apipackage??>
package ${apipackage};
import ${pojo.getPackageName()}.*;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import ${pojo.getPackageName()}.${pojo.getDeclarationName()};
import java.util.Iterator;

</#if>
// Generated ${date} by Hibernate Tools ${version}
// Improved by AbouCorp

<#assign classbody>
<#assign declarationName = pojo.importType(pojo.getDeclarationName())>/**
 * Home object for domain model class ${declarationName}.
 * @see ${pojo.getQualifiedDeclarationName()}
 * @author Hibernate Tools
 */
public class ${declarationName}Serializer extends StdSerializer<${declarationName}>{

	public JsonSerializer<Object> defaultSerializer;
	
	private SimpleDateFormat format = new SimpleDateFormat("<#if dateformat??>${dateformat}<#else>yyyy.MM.dd</#if>");

	public ${declarationName}Serializer() {
		this(null);
	}

	public ${declarationName}Serializer(Class<${declarationName}> t) {
		super(t);
	}

	@Override
	public void serialize(${declarationName} ${declarationName?lower_case}, JsonGenerator jg, SerializerProvider sp) throws IOException {
<#if pojo.hasIdentifierProperty() && !pojo.isComponent()>
		jg.writeStartObject();
	<#foreach prop in pojo.getAllPropertiesIterator()>
		<#assign pojo_prop = c2j.getPOJOClass(prop.getPersistentClass()) prop_type_name = pojo.getJavaTypeName(prop, jdk5)>
		<#if pojo.isPOJOInList(prop_type_name,pojo_list)>
			if(${declarationName?lower_case}.get${prop.getName()?cap_first}() == null){
				jg.writeNullField("${prop.getName()}");
			}else{
				jg.writeNumberField("${prop.getName()}",  ${declarationName?lower_case}.get${prop.getName()?cap_first}().get${pojo.getIdentifierProperty().getName()?cap_first}());
			}
		<#elseif pojo.isJavaCollectionType(prop_type_name)>
			if(${declarationName?lower_case}.get${prop.getName()?cap_first}() == null){
				jg.writeNullField("${prop.getName()}");
			}else{
				jg.writeArrayFieldStart("${prop.getName()}");
				for(Iterator<${pojo.getGenericType(prop_type_name)}> iter = ${declarationName?lower_case}.get${prop.getName()?cap_first}().iterator(); iter.hasNext(); /* NOOP */) {
					${pojo.getGenericType(prop_type_name)} ${pojo.getGenericType(prop_type_name)?lower_case} = iter.next();
					jg.writeObject(${pojo.getGenericType(prop_type_name)?lower_case}.get${pojo.getIdentifierProperty().getName()?cap_first}());
				}
				jg.writeEndArray();
			}
		<#else>
			<#if !c2j.isPrimitive(prop_type_name)>
				if(${declarationName?lower_case}.get${prop.getName()?cap_first}() == null){
					jg.writeNullField("${prop.getName()}");
				}else{
			</#if>
			<#if prop_type_name ='Date'>
					jg.writeStringField("${prop.getName()}",format.format(${declarationName?lower_case}.get${prop.getName()?cap_first}()));
			<#else>
				<#if pojo.isJacksonNumberType(prop_type_name)>jg.writeNumberField("${prop.getName()}",  ${declarationName?lower_case}.get${prop.getName()?cap_first}());</#if>
				<#if pojo.isJacksonStringType(prop_type_name)>jg.writeStringField("${prop.getName()}",  ${declarationName?lower_case}.get${prop.getName()?cap_first}());</#if>
				<#if pojo.isJacksonBooleanType(prop_type_name)>jg.writeBooleanField("${prop.getName()}",  ${declarationName?lower_case}.get${prop.getName()?cap_first}());</#if>
				<#if pojo.isJacksonBinaryType(prop_type_name)>jg.writeBinaryField("${prop.getName()}",  ${declarationName?lower_case}.get${prop.getName()?cap_first}());</#if>
			</#if>
			<#if !c2j.isPrimitive(prop_type_name)>
				}
			</#if>
		</#if>
	</#foreach>
		jg.writeEndObject();
</#if>	
	}
	
	public void setDefaultSerializer(JsonSerializer<Object> serializer) {
		defaultSerializer = serializer;

	}
}
</#assign>
${pojo.generateImports()}
${classbody}
