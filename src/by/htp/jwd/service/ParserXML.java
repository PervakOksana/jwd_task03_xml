package by.htp.jwd.service;

import by.htp.jwd.bean.Node;
import by.htp.jwd.service.exception.ServiceException;

public interface ParserXML {

	public Node parser(String path) throws ServiceException;

	public Node parserAsBlock(String path) throws ServiceException;
}
