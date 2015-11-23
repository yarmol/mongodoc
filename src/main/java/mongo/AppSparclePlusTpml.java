package mongo;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class AppSparclePlusTpml {
	public static void _main(String[] args) {
		Configuration conf = new Configuration();
		conf.setClassForTemplateLoading(App.class, "/");
		
		Spark.get("/", new Route() {

			@Override
			public Object handle(Request req, Response resp) throws Exception {
				
				Map<String,String> params = new HashMap<>();
				params.put("user", "vitaly");
				params.put("i", "1");
				StringWriter writer = new StringWriter();
				Template tmpl = null;
				try {
					tmpl = conf.getTemplate("main.ftl");
					tmpl.process(params, writer);
				} catch (TemplateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return writer;
				
			}
			
		});
		
		Spark.get("/index", new Route() {

			@Override
			public Object handle(Request req, Response resp) throws Exception {
				

				Map<String,String> params = new HashMap<>();
				params.put("user", "valery");
				params.put("i", "2");
				StringWriter writer = new StringWriter();
				Template tmpl = null;
				try {
					tmpl = conf.getTemplate("main.ftl");
					tmpl.process(params, writer);
				} catch (TemplateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return writer;
				
			}
			
		});
		
		Spark.get("/query/:data", new Route() {

			@Override
			public Object handle(Request req, Response resp) throws Exception {
				

				Map<String,String> params = new HashMap<>();
				params.put("user", "alexander");
				params.put("i", "3");
				StringWriter writer = new StringWriter();
				Template tmpl = null;
				try {
					tmpl = conf.getTemplate("main.ftl");
					tmpl.process(params, writer);
				} catch (TemplateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String result = writer.toString() + "\\n";
				result = result + " " + req.params(":data");
				return result;
				
				
			}
			
		});
		
	}
}
