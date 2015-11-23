package mongo;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class PageBuilder {

		String path = null;
		Template tmpl = null;
		Configuration conf;
		Map params;
		
		PageBuilder(String path, String tmplName, Map params) {
			
			this.path = path;
			this.params = params;
			conf = new Configuration();
			conf.setClassForTemplateLoading(this.getClass(), path);
			try {
				tmpl = conf.getTemplate(tmplName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
		}
		
		public String process() {
			StringWriter writer = new StringWriter();
			try {
				tmpl.process(params, writer);
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return writer.toString();
		}
		
		
	
}
