package by.htp.jwd.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.htp.jwd.bean.Attribute;
import by.htp.jwd.bean.Node;
import by.htp.jwd.dao.DAOFactory;
import by.htp.jwd.dao.ReaderXML;
import by.htp.jwd.dao.exception.DAOException;
import by.htp.jwd.service.ParserXML;
import by.htp.jwd.service.exception.ServiceException;

public class ParserXMLImpl implements ParserXML {

	DAOFactory factory = DAOFactory.getInstance();
	ReaderXML reader = factory.getReaderXML();

	private static Map<String, Node> nodes = new HashMap<String, Node>();

	Node node = new Node();

	@Override
	public Node parser(String path) throws ServiceException {
		String line;
		try {
			line = reader.readAll(path);
			nodes = parserHelper(line);

			for (Map.Entry entry : nodes.entrySet()) {
				node = (Node) entry.getValue();
			}

		} catch (DAOException e) {
			throw new ServiceException(e);
		} finally {

			try {
				reader.close();
			} catch (IOException e) {
				System.out.println("File is not closed");
			}
		}

		return node;
	}

	@Override
	public Node parserAsBlock(String path) throws ServiceException {

		List<String> lines = new ArrayList<String>();

		try {
			lines = reader.readBlock(path);
			for (String line : lines) {
				nodes = parserHelper(line);
			}

			for (Map.Entry entry : nodes.entrySet()) {
				node = (Node) entry.getValue();
			}

		} catch (DAOException e) {
			throw new ServiceException(e);
		} finally {

			try {
				reader.close();
			} catch (IOException e) {
				System.out.println("File is not closed");
			}
		}

		return node;
	}

	private Map<String, Node> parserHelper(String line) {

		String[] correctLine = line.split("<");

		for (int i = 1; correctLine.length > i; i++) {
			correctLine[i] = "<" + correctLine[i];

			boolean matcher = Pattern.compile("<\\?(\\w*)").matcher(correctLine[i]).find();
			boolean matcherStart = Pattern.compile("<(\\w*)").matcher(correctLine[i]).find();
			boolean matcherEnd = Pattern.compile("<\\/(\\w*)").matcher(correctLine[i]).find();

			if (matcherStart & !matcher & !matcherEnd) {

				nodes = parserStart(correctLine[i]);
			}

			if (matcherEnd) {
				nodes = parserEnd(nodes);
			}
		}
		return nodes;

	}

	private Map<String, Node> parserStart(String line) {

		String nameNode = "";
		Node node = null;
		Pattern patternu = Pattern.compile("\\<.*?\\>");
		Matcher m = patternu.matcher(line);
		while (m.find()) {
			nameNode = (String) (m.group().subSequence(1, m.group().length() - 1));

		}

		if (!"".equals(nameNode)) {

			String[] names = nameNode.split(" ");
			String[] contents = line.trim().split(">");
			node = new Node();
			node.setName(nameNode);

			if (names.length > 1) {
				Attribute attr = new Attribute();
				attr.setName(names[1].split("=")[0]);
				attr.setContent(names[1].split("=")[1].split("\"")[1]);
				nameNode = names[0];
				node.setName(nameNode);
				node.setAttrs(attr);
			}

			if (contents.length > 1) {
				node.setContent(contents[1]);
			}

		}
		nodes.put(nameNode, node);

		return nodes;

	}

	private Map<String, Node> parserEnd(Map<String, Node> nodes) {

		Node nodeRemove = new Node();
		Node node = new Node();

		List<String> keys = new ArrayList<>(nodes.keySet());
		if (keys.size() > 2) {
			nodeRemove = nodes.get(keys.get(1));
			nodes.remove(keys.get(1));
			node = nodes.get(keys.get(2));
			node.setChilds(nodeRemove);
		}
		if (keys.size() > 1 && !(keys.size() > 2)) {
			nodeRemove = nodes.get(keys.get(1));
			nodes.remove(keys.get(1));
			node = nodes.get(keys.get(0));
			node.setChilds(nodeRemove);
		}

		return nodes;
	}

}
