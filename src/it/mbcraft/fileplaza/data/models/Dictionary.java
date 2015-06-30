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

import java.security.InvalidParameterException;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class models a dictionary entry, with all its values.
 * It's uniquely identified by its short title.
 * (which can contain version number, es : 'Dizionario Italiano MBCRAFT 1.0'.
 * The key is md5 of its short title.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class Dictionary {

    /**
     * This class models an entry in the dictionary.
     * It contains a singular and plural term
     */
    public static class DictionaryEntry implements Entry<String,String>,Comparable<DictionaryEntry> {

        private String singular;
        private String plural;
        
        /**
         * Creates a DictionaryEntry.
         * 
         * @param singular The singular form of this entry
         * @param plural The plural form of this entry
         */
        public DictionaryEntry(String singular, String plural) {
            this.singular = singular;
            this.plural = plural;
        }
        
        @Override
        public String getKey() {
            return singular;
        }

        @Override
        public String getValue() {
            return plural;
        }

        @Override
        public String setValue(String value) {
            plural = value;
            return plural;
        }

        @Override
        public int compareTo(DictionaryEntry o) {
            if (o==null) throw new InvalidParameterException("The other comparable parameter can't be null.");
            return singular.compareTo(o.singular);
        }
        
    }
    /**
     * The short title of this dictionary
     */
    private String shortTitle = "";
        
    /**
     * The description of this dictionary
     */
    private String description = "";
    
    /**
     * This field indicates if this dictionary is enabled.
     */
    private boolean enabled = false;
    /**
     * The tuples of this dictionary.
     */   
    private final Set<Entry<String,String>> entries = new TreeSet();

    /**
     * Gets the short title of this Dictionary
     * 
     * @return the shortTitle as a String
     */
    public String getShortTitle() {
        return shortTitle;
    }

    /**
     * Sets the short title for this Dictionary
     * 
     * @param shortTitle the shortTitle for this dictionary as a string
     */
    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
        
    }
    
    /**
     * Checks if this dictionary is enabled
     * 
     * @return true if this dictionary is enabled, false otherwise
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled state of this dictionary
     * 
     * @param enabled sets the enabled state of this dictionary
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    /**
     * Gets the description of this dictionary
     * 
     * @return the description as a string
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description for this dictionary
     * 
     * @param description the description as a string
     */
    public void setDescription(String description) {
        this.description = description;
        
    }

    /**
     * Returns the entries of this dictionary
     * 
     * @return the entries as a set instance
     */
    public Set<Entry<String,String>> getEntries() {
        return entries;
    }

    /**
     * Sets the entries of this dictionary
     * 
     * @param data the entries of this dictionary as a Set instance
     */
    public void setEntries(Set<Entry<String,String>> data) {
        this.entries.clear();
        this.entries.addAll(data);
    }

    @Override
    public boolean equals(Object o) {
        if (o==null) return false;
        if (!(o instanceof Dictionary)) return false;
        Dictionary other = (Dictionary)o;
        return shortTitle.equals(other.shortTitle);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.shortTitle);
        return hash;
    }
    
    /**
     * Try to find an exact match for a word inside this dictionary.
     * Either return a match for both forms (singular and plural), one, or null.
     * @param word The word to match as a string
     * @return An matching Entry or null.
     */
    public Entry<String,String> findEntryFromWord(String word) {
        boolean matchedSingular = false;
        boolean matchedPlural = false;
        Entry<String,String> last = null;
        for (Entry<String,String> entry : entries) {
            if (entry.getKey().equals(word)) matchedSingular = true;
            if (entry.getValue().equals(word)) matchedPlural = true;
            last = entry;
            break;
        }

        if (matchedSingular || matchedPlural)
            return last;
        else
            return null;
    }

}
