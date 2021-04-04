package by.htp.jwd.dao;

import java.io.Closeable;
import java.util.List;

import by.htp.jwd.dao.exception.DAOException;

public interface ReaderXML extends Closeable {

	public String readAll(String path) throws DAOException;

	public List<String> readBlock(String path) throws DAOException;
}
