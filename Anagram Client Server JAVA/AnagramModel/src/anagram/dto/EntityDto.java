package anagram.dto;

/**
 * Classe abstraite que chacun des dto doit étendre
 * @param <T>
 */
public abstract class EntityDto<T> {

    /**
     * Clé primaire de l'objet persisté.
     * Le type peut dépendre d'une classe à l'autre
     */
    protected T id;

    /**
     * retourne le fait que le dto est pourvu ou non d'une valeur de clé primaire
     * @return 
     */
    public  boolean isPersistant(){
        return (id!=null);
    }

    /**
     * Retourne la valeur de la clé de l'objet.
     * Cette valeur sera à null pour les objets non encore persistés
     * @return
     */
    public T getId(){
        return id;
    }

    @Override
    public boolean equals(Object dto){
        if ( dto == null || dto.getClass() != getClass()
                         || ((EntityDto)dto).isPersistant() != isPersistant()) {
            return false;
        }
        return ((EntityDto)dto).getId().equals(getId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
}
