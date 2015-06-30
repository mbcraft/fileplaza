/*
 *    FilePlaza - a tag based file manager
 *    Copyright (C) 2015 - Marco Bagnaresi
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.mbcraft.fileplaza.data.models;

import it.mbcraft.fileplaza.data.dao.meta.DictionaryDAO;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * This class models a tag attached to something. 
 * A tag is formed by :
 * - a key, as a string
 * - a value that can be any object (should be serializable to a string)
 * - a type, useful for parsing and writing the value
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class Tag {

    /**
     * Creates a tag
     * 
     * @param key The key of this tag, as a string
     * @param value The value of this tag, as an object
     * @param type The type of this tag, as a TagType value
     */
    public Tag(String key, Object value, TagType type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }
    /**
     * The tag type
     */
    private TagType type;
    /**
     * The key used to differentiate among other tags
     */
    private String key;
    /**
     * The value of this tag
     */
    private Object value;

    /**
     * Gets the type of this tag, as a TagType value
     * 
     * @return the type
     */
    public TagType getType() {
        return type;
    }

    /**
     * Sets the type of this tag
     * 
     * @param tagType the type of this tag, as a TagType value
     */
    public void setType(TagType tagType) {
        this.type = tagType;
    }

    /**
     * Returns the key of this tag
     * 
     * @return the key of this tag as a string
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key of this tag
     * 
     * @param key the key to set as a string
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Returns the value as a row object
     *
     * @return The value as an object
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the value of this tag and its type.
     *
     * @param value The value of this tag
     * @param tagType The type of this tag
     */
    public void setValue(Object value, TagType tagType) {
        this.value = value;
        this.type = tagType;
    }

    /**
     * Gets the value of this tag as a Date
     * 
     * @return the value as date
     */
    public Date getValueAsDate() {
        return (Date) getValue();
    }

    /**
     * Sets the value of this tag as a Date
     * 
     * @param value the value to set as date
     */
    public void setValueAsDate(Date value) {
        setValue(value, TagType.DATE);
    }

    /**
     * Gets the value of this tag as a string
     * 
     * @return the value as string
     */
    public String getValueAsString() {
        return (String) getValue().toString();
    }

    /**
     * Sets the value of this tag as a string
     * 
     * @param value the value to set as string
     */
    public void setValueAsString(String value) {
        setValue(value, TagType.STRING);
    }

    /**
     * Gets the value of this tag as a number
     * 
     * @return the value as int
     */
    public int getValueAsNumber() {
        return (Integer) getValue();
    }

    /**
     * Sets the value of this tag as a number
     * 
     * @param value the value to set as a number
     */
    public void setValueAsNumber(int value) {
        setValue(value, TagType.NUMBER);
    }

    /**
     * Returns the value of this tag as a label string
     *
     * @return The value of a label as a string
     */
    public String getValueAsLabel() {
        return (String) getValue();
    }

    /**
     * Sets the value of this tag as a label string.
     *
     * @param value The string value of this tag as a label string.
     */
    public void setValueAsLabel(String value) {
        setValue(value, TagType.LABEL);
    }

    /**
     * Gets the tag value as a dictionary string
     * 
     * TO FIX : maybe add a Dictionary parameter to use for verification
     * 
     *
     * @return The string value, as a dictionary value
     */
    public String getValueAsDictionary() {
        return (String) getValue();
    }

    /**
     * Sets the value as a dictionary string
     * 
     * TO FIX : maybe add a Dictionary parameter to use for verification
     *
     * @param dict The value used for this tag, as a DICTIONARY tag
     */
    public void setValueAsDictionary(String dict) {
        setValue(dict, TagType.DICTIONARY);
    }

    /**
     * Checks if this tag is plural of the parameter tag.
     * Useful only for DICTIONARY tags.
     * 
     * @param otherTag the tag to compare to
     * @return true if this tag is plural of the other tag, false otherwise
     */
    public boolean isPluralOf(Tag otherTag) {
        DictionaryDAO dao = DictionaryDAO.getInstance();
        Entry<String,String> match = dao.findEntryFromEnabledDictionaries(otherTag.getValueAsString());
        if (match==null)
            return false;
        if (otherTag.getValueAsString().equals(match.getKey()))
            return true;
        return false;            
    }

    /**
     * The TagType identifies the type of the Tag allowing
     * improved parse, serialization and identification of data.
     * Current supported types :
     * - DATE
     * - STRING
     * - DICTIONARY (differs from string because the value must be present in a dictionary)
     * - NUMBER (integer value)
     * - LABEL (differs from string because the value must be present in a label set)
     * 
     * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
     */
    public static enum TagType {

        DATE, STRING, DICTIONARY, NUMBER, LABEL

    }
    
    @Override
    public boolean equals(Object o) {
        if (o==null) return false;
        if (!(o instanceof Tag)) return false;
        Tag other = (Tag)o;
        return key.equals(other.key) && type.equals(other.type) && value.equals(other.value);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.type);
        hash = 83 * hash + Objects.hashCode(this.key);
        hash = 83 * hash + Objects.hashCode(this.value);
        return hash;
    }
}
