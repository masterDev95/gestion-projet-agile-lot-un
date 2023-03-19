package com.lotun.gestionprojetagilelotun.classes;

/**
 * Cette classe représente un livre.
 */
public class Livre {
    /** Le titre du livre */
    String titre;
    /** L'auteur du livre */
    Auteur auteur;
    /** La présentation du livre */
    String presentation;
    /** La date de parution du livre */
    int parution;
    /** La colonne où le livre est rangé dans la bibliothèque */
    short colonne;
    /** La rangée où le livre est rangé dans la bibliothèque */
    short rangee;
    /** L'URL de l'image de couverture du livre */
    String urlImage;
    
    public Livre() {
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
     * @param parution La nouvelle date de parution du livre.
     */
    public void setParution(int parution) {
        this.parution = parution;
    }

    /**
     * Retourne la colonne où le livre est rangé dans la bibliothèque.
     *
     * @return La colonne où le livre est rangé dans la bibliothèque.
     */
    public short getColonne() {
        return colonne;
    }

    /**
     * Modifie la colonne où le livre est rangé dans la bibliothèque.
     *
     * @param colonne La nouvelle colonne où le livre est rangé dans la
     *                bibliothèque.
     */
    public void setColonne(short colonne) {
        this.colonne = colonne;
    }

    /**
     * Retourne la rangée où le livre est rangé dans la bibliothèque.
     *
     * @return La rangée où le livre est rangé dans la bibliothèque.
     */
    public short getRangee() {
        return rangee;
    }

    /**
     * Modifie la rangée où le livre est rangé dans la bibliothèque.
     *
     * @param rangee La nouvelle rangée où le livre est rangé dans la bibliothèque.
     */
    public void setRangee(short rangee) {
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
}
