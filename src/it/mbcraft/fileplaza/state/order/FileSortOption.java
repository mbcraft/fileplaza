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

package it.mbcraft.fileplaza.state.order;

/**
 * File sort options : 
 * MIXED : the files and folders are interleaved.
 * FOLDERS_THEN_FILES : put the sorted folders before the sorted files.
 * FILES_THEN_FOLDERS : put the sorted files before the sorted folders.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public enum FileSortOption {
    MIXED, FOLDERS_THEN_FILES, FILES_THEN_FOLDERS
}
