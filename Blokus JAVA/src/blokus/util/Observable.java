/*
 * Copyright (C) 2018 Meihdi El Amouri <49262@etu.he2b.be>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package blokus.util;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public interface Observable {

    /**
     * Allows to add observers
     *
     * @param o the observer we want to add
     */
    public void registerObserver(Observer o);

    /**
     * Allows you to delete observers.
     *
     * @param o the observer to be deleted
     */
    public void removeObserver(Observer o);

    /**
     * Allows to notify all observers
     */
    public void notifyObservers();
}
