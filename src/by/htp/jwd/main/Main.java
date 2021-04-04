package by.htp.jwd.main;

import by.htp.jwd.bean.Node;
import by.htp.jwd.service.ParserXML;
import by.htp.jwd.service.ServiceFactory;
import by.htp.jwd.service.exception.ServiceException;

public class Main {

	public static void main(String[] args) {

		ServiceFactory factory = ServiceFactory.getInstance();
		ParserXML parser = factory.getParserXML();

		String path = "D://IT//EPAM//20210312//food.xml";

		Node node;

		try {
			node = parser.parser(path);
			node = parser.parserAsBlock(path);

			System.out.println(node.toString());
		} catch (ServiceException e) {
			System.out.println(e);
		}

	}

}
