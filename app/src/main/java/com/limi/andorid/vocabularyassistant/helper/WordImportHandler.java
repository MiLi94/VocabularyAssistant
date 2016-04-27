package com.limi.andorid.vocabularyassistant.helper;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by limi on 16/4/27.
 */
public class WordImportHandler {

    public static ArrayList<Word> threeKArrayList = new ArrayList<>();

    public static void getDataFromXml(InputStream is) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(is);

            NodeList wordList = doc.getElementsByTagName("item");
            System.out.println("共有" + wordList.getLength() + "个word节点");
            for (int i = 0; i < wordList.getLength(); i++) {

                String wordName = null;
                String trans = "";
                String tags = null;
                String phonetic = null;


                Node aWord = wordList.item(i);
                Element elem = (Element) aWord;

                for (Node node = elem.getFirstChild(); node != null; node = node.getNextSibling()) {
                    //          System.out.println("node.getNodeType() = " + node.getNodeType());
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        String name = node.getNodeName();
                        if (name.equals("word")) {
                            wordName = node.getFirstChild().getNodeValue();
                        } else if (name.equals("trans")) {
                            String s = node.getFirstChild().getNodeValue();
                            String transArray[] = s.split("\n");
                            for (String sss : transArray) {
                                if (sss.charAt(0) >= '0' && sss.charAt(0) <= '9') {
                                    if (!trans.equals("")) {
                                        trans += "\n";
                                    }
                                    trans += sss;
                                }
                            }
                        } else if (name.equals("tags")) {
                            String s = node.getFirstChild().getNodeValue();
                            tags = s.substring(s.indexOf("(") + 1, s.indexOf(")"));
                        } else if (name.equals("phonetic")) {
                            phonetic = node.getFirstChild().getNodeValue();
                        }
                    }
                }
                Word word = new Word(wordName, trans, tags, "GRE threek Words", phonetic);
                threeKArrayList.add(word);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}