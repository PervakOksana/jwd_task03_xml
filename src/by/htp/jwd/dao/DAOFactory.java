package by.htp.jwd.dao;

import by.htp.jwd.dao.impl.ReaderXMLImpl;

public class DAOFactory {

	private static final DAOFactory instance = new DAOFactory();

	private final ReaderXML readerXMLDAO = new ReaderXMLImpl();

	private DAOFactory() {
	}

	public ReaderXML getReaderXML() {
		return readerXMLDAO;
	}

	public static DAOFactory getInstance() {
		return instance;
	}

}
