package org.hackystat.sensorbase.db.postgres;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * The {@link DefaultHandler} implementation that parses the optional property
 * key-value pairs. The getKeyValMap() method returns a mapping of property keys
 * to values when an xml document is parsed.
 * @author Austen Ito
 * 
 */
public class SensorPropertiesHandler extends DefaultHandler {
  /** True if the element values should be read. */
  private boolean readChars = false;
  /** The current key that is parsed. */
  private String currentKey = "";
  /** True if the key is being read, false if not. */
  private boolean readingKey = false;
  /** The mapping of property keys to values. */
  private final Map<String, String> keyValMap = new HashMap<String, String>();

  /**
   * Determines if the current element is the "Key" tag or "Value" tag. If the
   * element is either or those, the values of the elements are read.
   * @param uri the uri of the element.
   * @param localName the local name of the element.
   * @param qName the tag name.
   * @param attributes the element attributes.
   * @throws SAXException thrown if the xml document can't be parsed.
   */
  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {
    if ("Key".equals(qName)) {
      this.readingKey = true;
      this.readChars = true;
    }
    else if ("Value".equals(qName)) {
      this.readingKey = false;
      this.readChars = true;
    }
  }

  /**
   * Reads the characters of the parsed string at the specified start and end
   * positions.
   * @param ch the parsed xml string.
   * @param start the start index to read from the string.
   * @param length the amount of characters to read from the string.
   * @throws SAXException thrown if the xml document can't be parsed.
   */
  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    if (this.readChars) {
      StringBuffer valueBuffer = new StringBuffer();
      for (int i = start; i < start + length; i++) {
        valueBuffer.append(ch[i]);
      }
      if (this.readingKey) {
        this.currentKey = valueBuffer.toString();
      }
      else {
        this.keyValMap.put(this.currentKey, valueBuffer.toString());
      }
    }
    this.readChars = false;
  }

  /**
   * Returns the populated property key-value mapping.
   * @return the property mapping.
   */
  public Map<String, String> getKeyValMap() {
    return this.keyValMap;
  }
}
