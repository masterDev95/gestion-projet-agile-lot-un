package com.lotun.gestionprojetagilelotun.classes;

/**
 * Cette classe représente un livre.
 */
public class Livre {
    /** L'id du livre. */
    int id;
    /** Le titre du livre */
    String titre;
    /** L'auteur du livre */
    Auteur auteur;
    /** La présentation du livre */
    String presentation;
    /** La date de parution du livre */
    int parution;
    /** La colonne où le livre est rangé dans la bibliothèque */
    int colonne;
    /** La rangée où le livre est rangé dans la bibliothèque */
    int rangee;
    /** L'URL de l'image de couverture du livre */
    String urlImage;

    /** Booléen sur l'état du livre */
    Boolean etat;

    /**
     * Cette classe représente un livre.
     */
    public Livre() {
        // Empty constructor
    }
    /**
     * Retourne l'id du livre.
     * @return L'id du livre.
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    /**
     * Retourne le titre du livre.
     *
     * @return Le titre du livre.
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Modifie le titre du livre.
     *
     * @param titre Le nouveau titre du livre.
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Retourne l'auteur du livre.
     *
     * @return L'auteur du livre.
     */
    public Auteur getAuteur() {
        return auteur;
    }

    /**
     * Modifie l'auteur du livre.
     *
     * @param auteur Le nouveau auteur du livre.
     */
    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }

    /**
     * Retourne la présentation du livre.
     *
     * @return La présentation du livre.
     */
    public String getPresentation() {
        return presentation;
    }

    /**
     * Modifie la présentation du livre.
     *
     * @param presentation La nouvelle présentation du livre.
     */
    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    /**
     * Retourne la date de parution du livre.
     *
     * @return La date de parution du livre.
     */
    public int getParution() {
        return parution;
    }

    /**
     * Modifie la date de parution du livre.
     *
     * @param parution La date de parution du livre.
     */
    public void setParution(int parution) {
        this.parution = parution;
    }

    /**
     * Retourne la colonne où le livre est rangé dans la bibliothèque.
     *
     * @return La colonne où le livre est rangé dans la bibliothèque.
     */
    public int getColonne() {
        return colonne;
    }

    /**
     * Modifie la colonne où le livre est rangé dans la bibliothèque.
     *
     * @param colonne La nouvelle colonne où le livre est rangé dans la
     *                bibliothèque.
     */
    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    /**
     * Retourne la rangée où le livre est rangé dans la bibliothèque.
     *
     * @return La rangée où le livre est rangé dans la bibliothèque.
     */
    public int getRangee() {
        return rangee;
    }

    /**
     * Modifie la rangée où le livre est rangé dans la bibliothèque.
     *
     * @param rangee La nouvelle rangée où le livre est rangé dans la bibliothèque.
     */
    public void setRangee(int rangee) {
        this.rangee = rangee;
    }

    /**
     * Retourne l'URL de l'image de couverture du livre.
     *
     * @return L'URL de l'image de couverture du livre.
     */
    public String getUrlImage() {
        return urlImage;
    }

    /**
     * Modifie l'URL de l'image de couverture du livre.
     *
     * @param urlImage La nouvelle URL de l'image de couverture du livre.
     */
    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    /**
     * Retourne l'état du livre
     * @return L'état du livre
     */
    public Boolean getEtat() {
        return etat;
    }

    /**
     * Modifie l'état du livre
     * @param etat - Le nouvel état du livre
     */
    public void setEtat(Boolean etat) {
        this.etat = etat;
    }
}
