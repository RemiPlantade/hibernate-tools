${pojo.getPackageDeclaration()}
// Generated ${date} by Hibernate Tools ${version}

import api_builder.jackson.Views;
import com.fasterxml.jackson.annotation.JsonView;
<#assign classbody>
<#include "PojoTypeDeclaration.ftl"/> {

<#if !pojo.isInterface()>
<#include "PojoFields.ftl"/>

<#include "PojoConstructors.ftl"/>
   
<#include "PojoPropertyAccessors.ftl"/>

<#include "PojoToString.ftl"/>

<#include "PojoEqualsHashcode.ftl"/>

<#else>
<#include "PojoInterfacePropertyAccessors.ftl"/>

</#if>
<#include "PojoExtraClassCode.ftl"/>

}
</#assign>

${pojo.generateImports()}
${classbody}

