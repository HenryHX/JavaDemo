import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-freemarker.xml")
public class TestSpringFreeMarker {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;


	@Test
	public void testGen() throws Exception {
		// 1、从spring容器中获得FreeMarkerConfigurer对象。
		// 2、从FreeMarkerConfigurer对象中获得Configuration对象。
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		// 3、使用Configuration对象获得Template对象。
		Template template = configuration.getTemplate("hello.ftl");
		// 4、创建数据集
		Map dataModel = new HashMap();
		dataModel.put("hello", "1000");
		// 5、创建输出文件的Writer对象。
		Writer out = new FileWriter(new File("D:\\codegene\\ftl\\spring-hello.html"));
		// 6、调用模板对象的process方法，生成文件。
		template.process(dataModel, out);
		// 7、关闭流。
		out.close();
	}

	@Test
	public void testFreemarker() throws Exception {
		// 1、从spring容器中获得FreeMarkerConfigurer对象。
		// 2、从FreeMarkerConfigurer对象中获得Configuration对象。
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		// 3、使用Configuration对象获得Template对象。
		Template template = configuration.getTemplate("student.ftl");
		// 4.创建一个数据集，可以是pojo也可以是map，推荐使用map
		Map data = new HashMap();
		data.put("hello", "hello spring freemarker");
		Student student = new Student(1, "小米", 11, "北京昌平回龙观");
		data.put("student", student);
		List<Student> stuList = new ArrayList();
		stuList.add(new Student(1, "小米", 11, "北京昌平回龙观"));
		stuList.add(new Student(2, "小米2", 12, "北京昌平回龙观"));
		stuList.add(new Student(3, "小米3", 13, "北京昌平回龙观"));
		stuList.add(new Student(4, "小米4", 14, "北京昌平回龙观"));
		stuList.add(new Student(5, "小米5", 15, "北京昌平回龙观"));
		stuList.add(new Student(6, "小米6", 16, "北京昌平回龙观"));
		stuList.add(new Student(7, "小米7", 17, "北京昌平回龙观"));
		data.put("stuList", stuList);
		//日期类型的处理
		data.put("date", new Date());
		data.put("val","123456");
		// 5.创建一个Writer对象，指定输出文件的路径及文件名。
		Writer out = new FileWriter(new File("D:\\codegene\\ftl\\spring-student.html"));
		// 6.使用模板对象的process方法输出文件。
		template.process(data, out);
		// 7.关闭流
		out.close();
	}
}
