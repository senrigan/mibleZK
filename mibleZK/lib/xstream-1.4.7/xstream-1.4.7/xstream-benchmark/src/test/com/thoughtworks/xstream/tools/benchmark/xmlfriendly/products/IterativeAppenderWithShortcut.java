/*
 * Copyright (C) 2008, 2009 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 * 
 * Created on 02. September 2008 by Tatu Saloranta
 */
package com.thoughtworks.xstream.tools.benchmark.xmlfriendly.products;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.tools.benchmark.Product;

import java.io.InputStream;
import java.io.OutputStream;


/**
 * Special handling for conforming strings, iterates otherwise through the incoming string and
 * appends the character.
 * 
 * @author J&ouml;rg Schaible
 * @author Tatu Saloranta
 */
public class IterativeAppenderWithShortcut implements Product {

    private final XStream xstream;

    public IterativeAppenderWithShortcut() {
        this.xstream = new XStream(new XppDriver(new XmlFriendlyReplacer()));
    }

    public void serialize(Object object, OutputStream output) throws Exception {
        xstream.toXML(object, output);
    }

    public Object deserialize(InputStream input) throws Exception {
        return xstream.fromXML(input);
    }

    public String toString() {
        return "Iterative Appender with Shortcut";
    }
    
    public static class XmlFriendlyReplacer extends AbstractXmlFriendlyReplacer {

        public XmlFriendlyReplacer() {
            this("_-", "__", 0);
        }
        
        public XmlFriendlyReplacer(String dollarReplacement, String underscoreReplacement, int bufferIncrement) {
            super(dollarReplacement, underscoreReplacement, bufferIncrement);
        }
        
        public String escapeName(String name) {
            return super.escapeIterativelyAppendingWithShortcut(name);
        }
        
        public String unescapeName(String name) {
            return super.unescapeIterativelyAppendingWithShortcut(name);
        }
    }
}
