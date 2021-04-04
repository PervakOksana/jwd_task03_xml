package by.htp.jwd.service;

import by.htp.jwd.service.impl.ParserXMLImpl;

public class ServiceFactory {

	private static final ServiceFactory instance = new ServiceFactory();

	private final ParserXML parserXMLService = new ParserXMLImpl();

	private ServiceFactory() {
	}

	public ParserXML getParserXML() {
		return parserXMLService;
	}

	public static ServiceFactory getInstance() {
		return instance;
	}

}
