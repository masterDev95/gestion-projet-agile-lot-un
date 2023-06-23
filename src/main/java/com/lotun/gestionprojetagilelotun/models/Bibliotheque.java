package com.lotun.gestionprojetagilelotun.models;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Classe représentant une bibliothèque contenant une liste de livres.
 */
@XmlRootElement(name = "bibliotheque")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bibliotheque {

    /**
     * Liste des livres de la bibliothèque.
     */
    @XmlElement(name = "livre")
    private List<Livre> livres;

    /**
     * Classe représentant une bibliothèque contenant une liste de livres.
     */
    public Bibliotheque() {
        // Empty constructor
    }

    /**
     * Retourne la liste des livres de la bibliothèque.
     * 
     * @return la liste des livres de la bibliothèque
     */
    public List<Livre> getLivres() {
        return livres;
    }

    /**
     * Modifie la liste des livres de la bibliothèque.
     * 
     * @param livres la nouvelle liste des livres de la bibliothèque
     */
    public void setLivres(List<Livre> livres) {
        this.livres = livres;
    }
}
