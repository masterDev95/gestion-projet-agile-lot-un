package com.lotun.gestionprojetagilelotun.classes;

public class Role {
    /** Le nom de l'auteur */
    int id;
    /** Le prénom de l'auteur */
    String nomRole;


    /**
     * Représente un auteur de livre.
     */
    public Role() {
    }

    /**
     * Retourne le nom du role.
     *
     * @return Le nom du role.
     */
    public String getRole() {
        return nomRole;
    }

    /**
     * Modifie le nom du role.
     *
     * @param nomRole Le nouveau nom du role.
     */
    public void setRole(String nomRole) {
        this.nomRole = nomRole;
    }

    /**
     * Retourne l'id du role.
     *
     * @return l'id du role.
     */
    public int getId() {
        return id;
    }

    /**
     * Modifie l'id role'.
     *
     * @param id L'id du role.
     */
    public void setId(int id) {
        this.id = id;
    }
}
