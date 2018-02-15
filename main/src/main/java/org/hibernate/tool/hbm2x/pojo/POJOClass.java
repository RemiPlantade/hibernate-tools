package org.hibernate.tool.hbm2x.pojo;

import java.util.Iterator;
import java.util.List;

import org.hibernate.mapping.Property;

/**
 * Wrapper class over PersistentClass used in hbm2java and hbm2doc tool
 * @author max
 * @author <a href="mailto:abhayani@jboss.org">Amit Bhayani</a>
 *
 */
public interface POJOClass extends ImportContext {

	/** 
	 * Returns "package packagename;" where packagename is either the declared packagename,
	 * or the one provide via meta attribute "generated-class".
	 * 
	 * Returns "// default package" if no package declarition available.
	 *  
	 */
	public String getPackageDeclaration();
	
	public String getClassModifiers();

	public String getQualifiedDeclarationName();
	
	/**
	 * Returns the javadoc associated with the class.
	 * 
	 * @param fallback the default text if nothing else is found
	 * @param indent how many spaces should be added
	 * @return
	 */
	public String getClassJavaDoc(String fallback, int indent);
	
	/**
	 * 
	 * @return declaration type "interface" or "class"
	 */
	public String getDeclarationType();
	
	/**
	 * @return unqualified classname for this class (can be changed by meta attribute "generated-class")
	 */
	public String getDeclarationName();
	
	public String getImplementsDeclaration();
	public String getImplements();
	
	public String getParentPackage(String childPackage);
	
	public String getExtendsDeclaration();
	public String getExtends();
	
	public String generateEquals(String thisName, String otherName, boolean useGenerics);
	
	public boolean isComponent();
	
	public String getExtraClassCode();
		
	public boolean needsEqualsHashCode();
	
	public boolean hasIdentifierProperty();
	
	public String generateAnnColumnAnnotation(Property property);
	public String generateAnnIdGenerator();
	public String generateAnnTableUniqueConstraint();
	public String generateBasicAnnotation(Property property);
	public Iterator<Property> getAllPropertiesIterator();

	public String getPackageName();
	public String getShortName();

	public Iterator<Property> getToStringPropertiesIterator();
	public Iterator<Property> getEqualsHashCodePropertiesIterator();
	
	public boolean needsToString();
	
	public String getFieldJavaDoc(Property property, int indent);
	public String getFieldDescription(Property property);

	public Object getDecoratedObject();

	public boolean isInterface();
	
	public boolean isSubclass();

	public List<Property> getPropertiesForFullConstructor();
	public List<Property> getPropertyClosureForFullConstructor();
	public List<Property> getPropertyClosureForSuperclassFullConstructor();
	
	public boolean needsMinimalConstructor();
	public boolean needsFullConstructor();
	public List<Property> getPropertiesForMinimalConstructor();
	public List<Property> getPropertyClosureForMinimalConstructor();
	public List<Property> getPropertyClosureForSuperclassMinimalConstructor();
	
	public POJOClass getSuperClass();
	
	public String getJavaTypeName(Property p, boolean useGenerics);
	public String getFieldInitialization(Property p, boolean useGenerics);
	
	public Property getIdentifierProperty();
	
	public boolean hasVersionProperty();
	public Property getVersionProperty();
	
	/** Aboucorp add
	 * Compare typename with List to determine if it's a java type
	 */
	public boolean isJavaType(String shortTypeName);
	
	/** Aboucorp add
	 * Compare typename with java types to determine if it's a Binary 
	 */
	boolean isPOJOType(Property prop);
	
	/** Aboucorp add
	 * Compare typename with Collection jab=va types to determine if it's a Subclass of collection
	 */
	public boolean isJavaCollectionType(String shortTypeName);
	
	/** Aboucorp add
	 * Compare typename with java types to determine if it's a Number 
	 */
	public boolean isJacksonNumberType(String shortTypeName);
	
	/** Aboucorp add
	 * Compare typename with java types to determine if it's a String 
	 */
	public boolean isJacksonStringType(String shortTypeName);
	
	/** Aboucorp add
	 * Compare typename with java types to determine if it's a Boolean 
	 */
	public boolean isJacksonBooleanType(String shortTypeName);
	
	/** Aboucorp add
	 * Compare typename with java types to determine if it's an Array 
	 */
	public boolean isJacksonArrayType(String shortTypeName);

	/** Aboucorp add
	 * Compare typename with java types to determine if it's a Binary 
	 */
	public boolean isJacksonBinaryType(String shortTypeName);
	
	public boolean isPOJOInList(String typeName, List<POJOClass> pojos);

	public String getGenericType(String shortTypeName);

	boolean isJavaMapType(String shortTypeName);
		
	
}
