<#if apipackage??>
package ${apipackage};
</#if>

// Generated ${date} by Hibernate Tools ${version}
// Improved by AbouCorp

<#assign classbody>
/**
 * Home object for domain model class Views.java
 * @author AbouCorp
 */
 public class Views {
 	<#list pojo_list as pojo>
		public interface ${pojo.getDeclarationName()}View {}
	</#list>
}
 </#assign>
${classbody}