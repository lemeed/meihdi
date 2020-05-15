/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atlg4.ultimate.g49262.pers;

/**
 *
 * @author Meihdi El Amouri
 */
public abstract class EntityDto<T> {

    protected T id;

    public boolean isPersistant() {
        return (id != null);
    }

    @Override
    public boolean equals(Object dto) {
        if (dto == null || dto.getClass() != getClass()
                || ((EntityDto) dto).isPersistant() != isPersistant()) {
            return false;
        }
        return ((EntityDto) dto).getId().equals(getId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    public T getId() {
        return id;
    }

}
