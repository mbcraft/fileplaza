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

package it.mbcraft.fileplaza.service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the service start/stop behaviour
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ServiceManager {
    
    private static List<IService> services = new ArrayList<>();
    
    /**
     * Adds a service to the list of services to be run.
     * 
     * @param service The service to add
     */
    public static void addService(IService service) {
        services.add(service);
    }
    
    /**
     * Starts all the previously added services
     */
    public static void startAllServices() {
        for (IService s : services) {
            if (s.canRun() && !s.isActive())
                s.start();
            else
            {
                throw new IllegalStateException("The service "+s.getName()+" can't be run!!");
            }
        }
    }
    
    /**
     * Stops all the previously started services
     */
    public static void stopAllServices() {
        for (IService s : services) {
            if (s.isActive())
                s.stop();
        }
    }
    
    
}
