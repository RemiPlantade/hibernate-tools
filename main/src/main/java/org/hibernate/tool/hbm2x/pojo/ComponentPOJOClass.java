package org.hibernate.tool.hbm2x.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.hibernate.mapping.Component;
import org.hibernate.mapping.ForeignKey;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Table;
import org.hibernate.tool.hbm2x.Cfg2JavaTool;

public class ComponentPOJOClass extends BasicPOJOClass {

	private Component clazz;
	private boolean isUnionEntity;
	private ArrayList<EntityPOJOClass> linkedEntities = new ArrayList<>();
	private List<EntityPOJOClass> linkerEntities = new ArrayList<>();

	public ComponentPOJOClass(Component component, Cfg2JavaTool cfg) {
		super(component, cfg);
		this.clazz = component;
		init();
	}

	protected String getMappedClassName() {
		return clazz.getComponentClassName();
	}

	public String getExtends() {
		String extendz = "";

		if ( isInterface() ) {
			if ( clazz.getMetaAttribute( EXTENDS ) != null ) {
				if ( !"".equals( extendz ) ) {
					extendz += ",";
				}
				extendz += getMetaAsString( EXTENDS, "," );
			}
		}
		else if ( clazz.getMetaAttribute( EXTENDS ) != null ) {
			extendz = getMetaAsString( EXTENDS, "," );
		}

		return "".equals( extendz ) ? null : extendz;
	}

	public String getImplements() {
		List<String> interfaces = new ArrayList<String>();

		//	implement proxy, but NOT if the proxy is the class it self!
		if ( !isInterface() ) {
			if ( clazz.getMetaAttribute( IMPLEMENTS ) != null ) {
				for (Object value : clazz.getMetaAttribute(IMPLEMENTS).getValues()) {
					interfaces.add((String)value);
				}
			}
			interfaces.add( Serializable.class.getName() ); // TODO: is this "nice" ? shouldn't it be a user choice ?
		}
		else {
			// interfaces can't implement suff
		}


		if ( interfaces.size() > 0 ) {
			StringBuffer sbuf = new StringBuffer();
			for ( Iterator<String> iter = interfaces.iterator(); iter.hasNext() ; ) {
				//sbuf.append(JavaTool.shortenType(iter.next().toString(), pc.getImports() ) );
				sbuf.append( iter.next() );
				if ( iter.hasNext() ) sbuf.append( "," );
			}
			return sbuf.toString();
		}
		else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Iterator<Property> getAllPropertiesIterator() {
		return clazz.getPropertyIterator();
	}

	public boolean isComponent() {
		return true;
	}

	public boolean hasIdentifierProperty() {
		return false;
	}

	public boolean needsAnnTableUniqueConstraints() {
		return false;
	}

	public String generateBasicAnnotation(Property property) {
		return "";
	}

	public String generateAnnIdGenerator() {
		return "";
	}

	public String generateAnnTableUniqueConstraint() {
		return "";
	}

	public Object getDecoratedObject() {
		return clazz;
	}

	public boolean isSubclass() {
		return false;
	}

	public List<Property> getPropertiesForFullConstructor() {
		List<Property> res = new ArrayList<Property>();

		Iterator<Property> iter = getAllPropertiesIterator();
		while(iter.hasNext()) {
			res.add(iter.next());
		}
		return res;
	}

	public List<Property> getPropertyClosureForFullConstructor() {
		return getPropertiesForFullConstructor();
	}

	public List<Property> getPropertyClosureForSuperclassFullConstructor() {
		return Collections.emptyList();
	}

	public List<Property> getPropertiesForMinimalConstructor() {
		List<Property> res = new ArrayList<Property>();
		Iterator<Property> iter = getAllPropertiesIterator();
		while(iter.hasNext()) {
			Property prop = (Property)iter.next();
			if(isRequiredInConstructor(prop)) {
				res.add(prop);
			}			
		}
		return res;
	}

	public List<Property> getPropertyClosureForMinimalConstructor() {
		return getPropertiesForMinimalConstructor();
	}

	public List<Property> getPropertyClosureForSuperclassMinimalConstructor() {
		return Collections.emptyList();
	}

	/* 
	 * @see org.hibernate.tool.hbm2x.pojo.POJOClass#getSuperClass()
	 */
	public POJOClass getSuperClass() {
		return null;
	}

	public String toString() {
		return "Component: " + (clazz==null?"<none>":clazz.getComponentClassName());
	}

	public Property getIdentifierProperty(){
		return null;
	}

	public boolean hasVersionProperty() {
		return false;
	}

	/*
	 * @see org.hibernate.tool.hbm2x.pojo.POJOClass#getVersionProperty()
	 */
	public Property getVersionProperty()
	{
		return null;
	}

	@Override
	public String getParentPackage(String childPackage) {
		// TODO Auto-generated method stub

		return childPackage.substring(0,childPackage.lastIndexOf('.'));
	}

	@Override
	public boolean isJavaType(String shortTypeName) {
		return JAVA_COMPLEX_TYPES.contains(shortTypeName) || JAVA_PRIMITIVE_TYPES.contains(shortTypeName);
	}

	@Override
	public boolean isJavaCollectionType(String shortTypeName) {
		// TODO Auto-generated method stub
		return shortTypeName.startsWith("Set")
				|| shortTypeName.startsWith("ArrayList");
	}

	@Override
	public boolean isJavaMapType(String shortTypeName) {
		// TODO Auto-generated method stub
		return shortTypeName.startsWith("Map")
				|| shortTypeName.startsWith("SortedMap");
	}

	@Override
	public String getGenericType(String genericTypeName) {
		int idxStart = genericTypeName.indexOf('<');
		int idxEnd = genericTypeName.indexOf('>');
		if(idxStart == -1 || idxEnd == -1) {
			return "Object";
		}
		return genericTypeName.substring(idxStart+1,idxEnd);
	}

	@Override
	public boolean isJacksonNumberType(String shortTypeName) {
		// TODO Auto-generated method stub
		return JACKSON_NUMERIC_TYPES.contains(shortTypeName)  || JAVA_COMPLEX_TYPES.contains(shortTypeName);
	}

	@Override
	public boolean isJacksonStringType(String shortTypeName) {
		// TODO Auto-generated method stub
		return shortTypeName.equals("char") 
				|| shortTypeName.equals("String")
				|| shortTypeName.equals("SerializableString");
	}

	@Override
	public boolean isJacksonBooleanType(String shortTypeName) {
		// TODO Auto-generated method stub
		return shortTypeName.equals("boolean");
	}

	@Override
	public boolean isJacksonBinaryType(String shortTypeName) {
		// TODO Auto-generated method stub
		return shortTypeName.equals("byte[]") 
				||  shortTypeName.equals("InputStream");
	}

	@Override
	public boolean isJacksonArrayType(String shortTypeName) {
		// TODO Auto-generated method stub
		return shortTypeName.equals("int[]") 
				|| shortTypeName.equals("double[]") 
				|| shortTypeName.equals("long[]") 
				|| shortTypeName.equals("float[]") 
				|| shortTypeName.equals("short[]")
				|| shortTypeName.equals("short[]");
	}

	@Override
	public boolean isPOJOType(Property prop) {
		return c2j.getPOJOClass(prop.getPersistentClass()).getPackageDeclaration().equals(getPackageDeclaration());
	}

	@Override
	public boolean isPOJOInList(String typeName, List<POJOClass> pojos) {
		boolean present = false;
		for (POJOClass pojoClass : pojos) {
			if(typeName.equals(pojoClass.getDeclarationName())) {
				present = true;
			}
		}
		return present;
	}

	@Override
	public boolean isJavaPrimitiveType(String shortTypeName) {
		return JAVA_PRIMITIVE_TYPES.contains(shortTypeName);
	}

	@Override
	public String getComplexJavaType(String primitiveType) {
		// TODO Auto-generated method stub
		return JAVA_MAPPED_TYPES.get(primitiveType);
	}

	@Override
	public boolean isPartOfUnionTable(List<POJOClass> pojos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public POJOClass getUnionPOJOClass(List<POJOClass> pojos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBiUnionEntity(POJOClass clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getOtherTypeNameInBiUnion(POJOClass clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getJavaType(POJOClass pojoClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllEntitiesPropClassName(POJOClass clazz,List<POJOClass> pojos) {
		ArrayList<String> pojoClasses = new ArrayList<>();
		for (Iterator<Property> iter = clazz.getAllPropertiesIterator(); iter.hasNext();) {
			Property prop = iter.next();			
			String pojoTypeName = getJavaTypeName(prop, true);
			for (POJOClass pojo : pojos) {
				if(!pojo.isComponent() && pojo.getDeclarationName().equals(pojoTypeName)){
					pojoClasses.add(pojoTypeName);
				}
			}
		}
		return pojoClasses;
	}
	
	@Override
	public boolean containDateProp() {
		for (Iterator<Property> iter = getAllPropertiesIterator(); iter.hasNext();) {
			Property prop = iter.next();
			String pojoTypeName = getJavaTypeName(prop, true);
			if(pojoTypeName.equals("Date")) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public POJOClass getPOJOClassFromName(String name, List<POJOClass> pojos) {
		for (POJOClass pojoClass : pojos) {
			if(pojoClass.getDeclarationName().equals(name)) {
				return pojoClass;
			}
		}
		return null;
	}
	
	@Override
	public List<Property> getAllJavaProp(List<POJOClass> pojos) {
		ArrayList<Property> props = new ArrayList<>();
		for(Iterator<Property> iter = getAllPropertiesIterator();iter.hasNext();) {
			Property prop = iter.next();
			String pojoTypeName = c2j.getJavaTypeName(prop, true);
			POJOClass pojoProp = getPOJOClassFromName(pojoTypeName,pojos);
			if(pojoProp == null || !pojoProp.isUnionEntity()) {
				props.add(prop);
			}
		}
		return props;
	}
	public String test() {
		return getShortName() +  " isAbstractUnionTable " + this.clazz.getTable().isAbstractUnionTable();
	}
	@Override
	public boolean isUnionEntity() {
		return isUnionEntity;
	}
	@Override
	public void setUnionEntity(boolean isUnionEntity) {
		this.isUnionEntity = isUnionEntity;
	}
	@Override
	public ArrayList<EntityPOJOClass> getLinkedEntities() {
		return linkedEntities;
	}
	@Override
	public void setLinkedEntities(ArrayList<EntityPOJOClass> linkedEntities) {
		this.linkedEntities = linkedEntities;
	}
	@Override
	public List<EntityPOJOClass> getLinkerEntities() {
		return linkerEntities;
	}
	@Override
	public void setLinkerEntities(List<EntityPOJOClass> linkerEntities) {
		this.linkerEntities = linkerEntities;
	}
}
