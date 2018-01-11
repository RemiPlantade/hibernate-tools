package org.hibernate.tool.hbm2x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import org.eclipse.osgi.internal.loader.ModuleClassLoader.GenerationProtectionDomain;
import org.hibernate.boot.model.IdGeneratorStrategyInterpreter.GeneratorNameDeterminationContext;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.mapping.Component;
import org.hibernate.tool.hbm2x.conf.TestWindow;
import org.hibernate.tool.hbm2x.pojo.ComponentPOJOClass;
import org.hibernate.tool.hbm2x.pojo.POJOClass;


public class GenericExporter extends AbstractExporter {

	static abstract class ModelIterator {		
		abstract void process(GenericExporter ge);
	}

	static Map<String, ModelIterator> modelIterators = new HashMap<String, ModelIterator>();
	static {
		modelIterators.put( "configuration", new ModelIterator() {
			void process(GenericExporter ge) {
				TemplateProducer producer = 
						new TemplateProducer(
								ge.getTemplateHelper(),
								ge.getArtifactCollector());
				System.out.println("============== Config Generation for template : " + ge.templateName);
				producer.produce(
						new HashMap<String, Object>(), 
						ge.getTemplateName(), 
						new File(ge.getOutputDirectory(),ge.filePattern), 
						ge.templateName, 
						"Configuration");				
			}			
		});
		modelIterators.put("entity", new ModelIterator() {		
			void process(GenericExporter ge) {
				Iterator<?> iterator = 
						ge.getCfg2JavaTool().getPOJOIterator(
								ge.getMetadata().getEntityBindings().iterator());
				Map<String, Object> additionalContext = new HashMap<String, Object>();

				List<POJOClass> pojoList = new ArrayList<>();
				while(iterator.hasNext()) {
					pojoList.add((POJOClass) iterator.next());
				}
				additionalContext.put("pojo_list", pojoList);
				iterator = 
						ge.getCfg2JavaTool().getPOJOIterator(
								ge.getMetadata().getEntityBindings().iterator());

				while ( iterator.hasNext() ) {			
					POJOClass element = (POJOClass) iterator.next();
					System.out.println("================= Exporting Entity : " + element.getDeclarationName());
					ge.exportPersistentClass( additionalContext, element );
				}
			}
		});
		modelIterators.put("component", new ModelIterator() {
			void process(GenericExporter ge) {
				System.out.println("============== Component Generation for template : " + ge.templateName);
				Map<String, Component> components = new HashMap<String, Component>();

				Iterator<?> iterator = 
						ge.getCfg2JavaTool().getPOJOIterator(
								ge.getMetadata().getEntityBindings().iterator());
				Map<String, Object> additionalContext = new HashMap<String, Object>();
				while ( iterator.hasNext() ) {					
					POJOClass element = (POJOClass) iterator.next();
					ConfigurationNavigator.collectComponents(components, element);	

				}
				iterator = components.values().iterator();
				while ( iterator.hasNext() ) {			
					Component component = (Component) iterator.next();
					ComponentPOJOClass element = new ComponentPOJOClass(component,ge.getCfg2JavaTool());
					ge.exportComponent( additionalContext, element );	
				}
			}
		});
		modelIterators.put("single", new ModelIterator() {		
			void process(GenericExporter ge) {
				System.out.println("============== Single Generation for template : " + ge.templateName);
				Iterator<?> iterator = 
						ge.getCfg2JavaTool().getPOJOIterator(
								ge.getMetadata().getEntityBindings().iterator());
				Map<String, Object> additionalContext = new HashMap<String, Object>();
				iterator = 
						ge.getCfg2JavaTool().getPOJOIterator(
								ge.getMetadata().getEntityBindings().iterator());
				// Create Singles file
				String javaFilename = getJavaFileNameFromTemplateName(ge.templateName);
				ge.exportSingleClasses(additionalContext, iterator,ge.filePattern);
			}

			private String getJavaFileNameFromTemplateName(String templateName) {
				int idxStart = templateName.lastIndexOf('/');
				idxStart = idxStart == -1 ? 0 : idxStart;
				int idxEnd = templateName.lastIndexOf('_');
				idxEnd = idxEnd == -1 ? templateName.length()-1 : idxEnd;
				String filename = templateName.substring(idxStart+1, idxEnd) + ".java";
				String firstLetter = filename.substring(0, 1);
				return firstLetter.toUpperCase() + filename.substring(1, filename.length());
			}
		});
	}

	private String templateName;
	private String filePattern;
	private String forEach;

	public String getTemplateName() {
		return templateName;
	}



	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}


	public void setForEach(String foreach) {
		this.forEach = foreach;
	}


	protected void doStart() {

		if(filePattern==null) {
			throw new ExporterException("File pattern not set on " + this.getClass());
		}
		if(templateName==null) {
			throw new ExporterException("Template name not set on " + this.getClass());
		}

		List<ModelIterator> exporters = new ArrayList<ModelIterator>();

		if(StringHelper.isEmpty( forEach )) {
			if(filePattern.indexOf("{class-name}")>=0) {				
				exporters.add( modelIterators.get( "entity" ) );
				exporters.add( modelIterators.get( "component") );
			}else if(templateName.indexOf("single")>=0) { 
				exporters.add(modelIterators.get( "single" ));	
			}else {
				exporters.add( modelIterators.get( "configuration" ));	
			}
		} else {
			StringTokenizer tokens = new StringTokenizer(forEach, ",");

			while ( tokens.hasMoreTokens() ) {
				String nextToken = tokens.nextToken();
				ModelIterator modelIterator = modelIterators.get(nextToken);
				if(modelIterator==null) {
					throw new ExporterException("for-each does not support [" + nextToken + "]");
				}
				exporters.add(modelIterator);
			}
		}
		Iterator<ModelIterator> it = exporters.iterator();
		while(it.hasNext()) {
			ModelIterator mit = it.next();
			mit.process( this );
		}
	}

	protected void exportComponent(Map<String, Object> additionalContext, POJOClass element) {
		exportPOJO(additionalContext, element);		
	}

	protected void exportPersistentClass(Map<String, Object> additionalContext, POJOClass element) {
		exportPOJO(additionalContext, element);		
	}


	protected void exportPOJO(Map<String, Object> additionalContext, POJOClass element) {
		TemplateProducer producer = new TemplateProducer(getTemplateHelper(),getArtifactCollector());					
		additionalContext.put("pojo", element);
		additionalContext.put("clazz", element.getDecoratedObject());
		String filename = resolveFilename( element );
		if(filename.endsWith(".java") && filename.indexOf('$')>=0) {
			log.warn("Filename for " + getClassNameForFile( element ) + " contains a $. Innerclass generation is not supported.");
		}
		producer.produce(additionalContext, getTemplateName(), new File(getOutputDirectory(),filename), templateName, element.toString());
	}

	protected void exportSingleClasses(Map<String, Object> additionalContext, Iterator<?> pojoIter, String javaFileName) {
		List<POJOClass> pojoList = new ArrayList<>();
		while(pojoIter.hasNext()) {
			pojoList.add((POJOClass) pojoIter.next());
		}
		additionalContext.put("pojo_list", pojoList);
		TemplateProducer producer = new TemplateProducer(getTemplateHelper(),getArtifactCollector());
		producer.produceOne(additionalContext, getTemplateName(), new File(getOutputDirectory(),javaFileName), templateName,"java",javaFileName + " single generation");
	}

	protected String resolveFilename(POJOClass element) {
		String filename = StringHelper.replace(filePattern, "{class-name}", getClassNameForFile( element )); 
		String packageLocation = StringHelper.replace(getPackageNameForFile( element ),".", "/");
		if(StringHelper.isEmpty(packageLocation)) {
			packageLocation = "."; // done to ensure default package classes doesn't end up in the root of the filesystem when outputdir=""
		}
		filename = StringHelper.replace(filename, "{package-name}", packageLocation);
		return filename;
	}

	protected String getPackageNameForFile(POJOClass element) {
		return element.getPackageName(); 
	}

	protected String getClassNameForFile(POJOClass element) {
		return element.getDeclarationName();
	}

	public void setFilePattern(String filePattern) {
		this.filePattern = filePattern;		
	}

	public String getFilePattern() {
		return filePattern;
	}

	@Override
	public String getId() {
		return ID_GENERIC;
	}

}
