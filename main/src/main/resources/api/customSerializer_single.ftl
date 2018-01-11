<#if apipackage??>
package ${apipackage};
</#if>

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
<#list pojo_list as pojo>
import ${apipackage}.serializer.${pojo.getDeclarationName()}Serializer;
import ${pojo.getPackageName()}.${pojo.getDeclarationName()};
</#list>

// Generated ${date} by Hibernate Tools ${version}
// Improved by AbouCorp

<#assign classbody>
public class CustomBeanSerializerModifier extends BeanSerializerModifier {
	@Override
	public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
		<#list pojo_list as pojo>
		<#if !pojo.isComponent()>
		if (beanDesc.getBeanClass() == ${pojo.getDeclarationName()}.class) {
			${pojo.getDeclarationName()}Serializer ser = new ${pojo.getDeclarationName()?cap_first}Serializer();
			ser.setDefaultSerializer((JsonSerializer<Object>)serializer);
			return ser;
		}
		</#if>
		</#list>
		return serializer;
	}
}
 </#assign>
${classbody}