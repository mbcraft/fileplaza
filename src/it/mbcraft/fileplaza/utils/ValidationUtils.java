/*
 *    FilePlaza - a tag based file manager
 *    Copyright (C) 2015 - Marco Bagnaresi
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Affero General Public License as published 
 *    by the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Affero General Public License for more details.
 *
 *    You should have received a copy of the GNU Affero General Public License
 *    along with this program.  If not, see 
 *    https://www.gnu.org/licenses/agpl-3.0.html.
 */

package it.mbcraft.fileplaza.utils;

import com.thaiopensource.util.PropertyMap;
import com.thaiopensource.util.PropertyMapBuilder;
import com.thaiopensource.validate.IncorrectSchemaException;
import com.thaiopensource.validate.Schema;
import com.thaiopensource.validate.SchemaReader;
import com.thaiopensource.validate.ValidateProperty;
import com.thaiopensource.validate.ValidationDriver;
import com.thaiopensource.validate.Validator;
import com.thaiopensource.validate.rng.CompactSchemaReader;
import com.thaiopensource.xml.sax.CountingErrorHandler;
import java.io.File;
import java.io.IOException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import org.xml.sax.SAXException;

/**
 * This class contains methods useful for validating xml files against rnc (RelaxNG Compact Syntax Schema Files).
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ValidationUtils {
    
    //the compact schema reader
    private static final SchemaReader compactSchemaReader = CompactSchemaReader.getInstance();
    
    /**
     * Loads an rnc schema file from file. The resulting Schema object can
     * be used multiple times.
     * 
     * @param schemaFile The schema file (.rnc extension)
     * 
     * @return The schema object used for validating xml documents
     * 
     * @throws IOException, IncorrectSchemaException, SAXException is something bad happens.
     */
    public static Schema loadCompactValidationSchema(File schemaFile) throws IOException, IncorrectSchemaException, SAXException {
        
        //we are using Relax NG compact format  

        return compactSchemaReader.createSchema(ValidationDriver.fileInputSource(schemaFile), PropertyMap.EMPTY);
    }
    
    /**
     * Validate an xml schema using a previously loaded rnc schema.
     * 
     * @param schema The schema object used for validation, as a Schema instance
     * @param xmlFile The xml file to validate, as a File instance
     * @return A CountingErrorHandler useful for checking error count.
     * @throws TransformerException If something bad happens.
     */
    public static CountingErrorHandler validate(Schema schema,File xmlFile) throws TransformerException {
        
        CountingErrorHandler ceh = new CountingErrorHandler();
        
        PropertyMapBuilder  builder = new PropertyMapBuilder();  
        builder.put(ValidateProperty.ERROR_HANDLER, ceh);  
              
        //Validator is NOT thread safe  
        Validator validator = schema.createValidator(builder.toPropertyMap());  
              
        Source source = new SAXSource(ValidationDriver.fileInputSource(xmlFile));

        SAXResult result = new SAXResult(validator.getContentHandler());
        TransformerFactory.newInstance().newTransformer().transform(source,result);  
        
        return ceh;
    }
}
