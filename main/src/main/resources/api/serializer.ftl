
<#if apipackage??>
package ${apipackage};
import ${pojo.getPackageName()}.${pojo.getShortName()};
<#if pojo.hasIdentifierProperty() && !pojo.isJavaType(pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5))>
import ${pojo.getPackageName()}.${pojo.getJavaTypeName(pojo.getIdentifierProperty(), jdk5)};
</#if>
import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import api_builder.jackson.Views;
import ${pojo.getPackageName()}.${pojo.getDeclarationName()};

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

	public ${declarationName}Serializer() {
		this(null);
	}

	public ${declarationName}Serializer(Class<${declarationName}> t) {
		super(t);
	}

	@Override
	public void serialize(${declarationName} ${declarationName?lower_case}, JsonGenerator jg, SerializerProvider sp) throws IOException {
<#if pojo.hasIdentifierProperty()>
		if(sp.getActiveView().equals(Views.${declarationName}View.class)) {
			defaultSerializer.serialize(${declarationName?lower_case}, jg, sp);
		}else {
			jg.writeString("/${declarationName?lower_case}/id/" + ${declarationName?lower_case}.get${pojo.getIdentifierProperty().getName()?cap_first}());
		}
<#else>
		defaultSerializer.serialize(${declarationName?lower_case}, jg, sp);
</#if>

	}
	public void setDefaultSerializer(JsonSerializer<Object> serializer) {
		defaultSerializer = serializer;

	}
}
</#assign>
${pojo.generateImports()}
${classbody}
