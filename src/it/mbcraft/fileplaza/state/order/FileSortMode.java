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

package it.mbcraft.fileplaza.state.order;

/**
 * The FileSortMode used for sorting the files.
 * 
 * ALPHABETICAL_ASCENDING : order alphabetically, from the first letter to the last
 * ALPHABETICAL_DESCENDING : order alphabetically, from the last letter to the first one
 * DATE_ASCENDING : order by date, from the oldest to the newest
 * DATE_DESCENDING : order by date, from the newest to the oldest
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public enum FileSortMode {
    ALPHABETICAL_ASCENDING, ALPHABETICAL_DESCENDING, DATE_ASCENDING, DATE_DESCENDING;
    
}
