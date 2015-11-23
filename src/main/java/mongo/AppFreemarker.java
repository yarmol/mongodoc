package mongo;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class AppFreemarker {
	public static void _main(String[] args) throws IOException {
		Configuration conf = new Configuration();
		conf.setClassForTemplateLoading(AppFreemarker.class, "/");
		Template tpl = conf.getTemplate("main.ftl");
		
		StringWriter writer = new StringWriter();
		Map<String,String> params = new HashMap<>();
		params.put("re", "user");
		try {
			tpl.process(params, writer);
			System.out.println(writer);
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
