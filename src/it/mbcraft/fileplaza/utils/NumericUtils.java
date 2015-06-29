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

package it.mbcraft.fileplaza.utils;

/**
 * This class contains methods used for operations on numbers.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class NumericUtils {
    
    /**
     * Returns the max values in a series of double values provided as parameters.
     * 
     * @param values The values used for the comparison.
     * 
     * @return The max value found inside the values provided.
     */
    public static double getMaxValue(Double ...values) {
        double max = Double.NEGATIVE_INFINITY;
        for (double v : values) {
            if (v>max)
                max = v;
        }

        return max;
        
    }
}
