package by.htp.jwd.dao.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import by.htp.jwd.dao.ReaderXML;
import by.htp.jwd.dao.exception.DAOException;

public class ReaderXMLImpl implements ReaderXML {

	BufferedReader reader = null;

	@Override
	public String readAll(String path) throws DAOException {
		String allLine = "";
		String line;
		try {
			reader = new BufferedReader(new FileReader(path));
			while ((line = reader.readLine()) != null) {
				allLine = allLine + line;
			}
		} catch (FileNotFoundException e) {
			throw new DAOException("File is not found");

		} catch (IOException e) {
			throw new DAOException("File is not found");
		}
		return allLine;
	}

	@Override
	public List<String> readBlock(String path) throws DAOException {
		String line = "";
		String allLine = "";
		List<String> lines = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader(path));
			while ((line = reader.readLine()) != null) {
				allLine = allLine + line;
				if (Pattern.compile("<\\/(\\w*)>").matcher(allLine).find()) {
					lines.add(allLine);
					line = "";
					allLine = "";
				}
			}
		} catch (FileNotFoundException e) {
			throw new DAOException("File is not found");

		} catch (IOException e) {
			throw new DAOException("File is not found");
		}
		return lines;
	}

	@Override
	public void close() {
		try {
			if (reader != null) {
				reader.close();
			}
		} catch (IOException e) {
			System.out.println("File not cloused");
		}
	}

}
