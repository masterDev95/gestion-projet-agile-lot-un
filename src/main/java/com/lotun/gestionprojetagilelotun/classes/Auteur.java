package com.lotun.gestionprojetagilelotun.classes;

/**
 * Représente un auteur de livre.
 */
public class Auteur {
    /** Le nom de l'auteur */
    String nom;
    /** Le prénom de l'auteur */
    String prenom;

    /**
     * Constructeur de la classe Auteur.
     *
     * @param nom    Le nom de l'auteur.
     * @param prenom Le prénom de l'auteur.
     */
    public Auteur(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    /**
     * Retourne le nom de l'auteur.
     *
     * @return Le nom de l'auteur.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom de l'auteur.
     *
     * @param nom Le nouveau nom de l'auteur.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne le prénom de l'auteur.
     *
     * @return Le prénom de l'auteur.
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Modifie le prénom de l'auteur.
     *
     * @param prenom Le nouveau prénom de l'auteur.
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}