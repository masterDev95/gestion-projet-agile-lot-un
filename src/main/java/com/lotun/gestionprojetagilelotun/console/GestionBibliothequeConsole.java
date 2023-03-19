package com.lotun.gestionprojetagilelotun.console;

import java.util.List;
import java.util.Scanner;

import com.lotun.gestionprojetagilelotun.classes.Auteur;
import com.lotun.gestionprojetagilelotun.classes.Bibliotheque;
import com.lotun.gestionprojetagilelotun.classes.Livre;
import com.lotun.gestionprojetagilelotun.dao.BibliothequeDAO;

/**
 * Classe permettant la gestion de la bibliothèque en console.
 */
public class GestionBibliothequeConsole {
    /**
     * Point d'entrée du programme.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        // Récupérer la bibliothèque depuis le fichier XML
        Bibliotheque bibliotheque = BibliothequeDAO.getBibliotheque();

        if (bibliotheque == null) {
            System.out.println("Impossible de charger la bibliothèque.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean quitter = false;

        while (!quitter) {
            System.out.println("Que souhaitez-vous faire ?");
            System.out.println("1. Afficher la liste des livres");
            System.out.println("2. Ajouter un livre");
            System.out.println("3. Modifier un livre");
            System.out.println("4. Supprimer un livre");
            System.out.println("5. Quitter");

            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    afficherListeLivres(bibliotheque);
                    break;
                case "2":
                    ajouterLivre(bibliotheque, scanner);
                    break;
                case "3":
                    modifierLivre(bibliotheque, scanner);
                    break;
                case "4":
                    supprimerLivre(bibliotheque, scanner);
                    break;
                case "5":
                    quitter = true;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }

        // Mettre à jour le fichier XML de la bibliothèque avant de quitter
        BibliothequeDAO.updateBibliotheque(bibliotheque);
    }

    /**
     * Affiche la liste des livres de la bibliothèque.
     *
     * @param bibliotheque La bibliothèque.
     */
    private static void afficherListeLivres(Bibliotheque bibliotheque) {
        System.out.println("Liste des livres :");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-30s | %-20s | %-10s | %-10s | %-80s\n", "Index", "Titre", "Auteur", "Parution", "Colonne", "URL de l'image de couverture");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < bibliotheque.getLivres().size(); i++) {
            Livre livre = bibliotheque.getLivres().get(i);
            System.out.printf("%-5d | %-30s | %-20s | %-10s | %-10s | %-80s\n", i, livre.getTitre(), livre.getAuteur().getNom(), livre.getParution(), livre.getColonne(), livre.getUrlImage());
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Ajoute un livre à la bibliothèque.
     *
     * @param bibliotheque La bibliothèque.
     * @param scanner      Le scanner pour lire les entrées de l'utilisateur.
     */
    private static void ajouterLivre(Bibliotheque bibliotheque, Scanner scanner) {
        System.out.println("Ajout d'un livre :");
        System.out.print("Titre : ");
        String titre = scanner.nextLine();
        System.out.print("Nom de l'auteur : ");
        String nomAuteur = scanner.nextLine();
        System.out.print("Prénom de l'auteur : ");
        String prenomAuteur = scanner.nextLine();
        System.out.print("Année de publication : ");
        int anneePublication = Integer.parseInt(scanner.nextLine());

        Livre livre = new Livre();
        livre.setTitre(titre);
        Auteur auteur = new Auteur();
        auteur.setNom(nomAuteur);
        auteur.setPrenom(prenomAuteur);
        livre.setAuteur(auteur);
        livre.setParution(anneePublication);

        bibliotheque.getLivres().add(livre);

        System.out.println("Livre ajouté : " + livre.getTitre());
    }

    /**
     * Modifie un livre de la bibliothèque.
     *
     * @param bibliotheque La bibliothèque.
     * @param scanner      Le scanner pour lire les entrées de l'utilisateur.
     */
    private static void modifierLivre(Bibliotheque bibliotheque, Scanner scanner) {
        System.out.println("Modification d'un livre :");
        System.out.print("Index du livre à modifier : ");
        int index = Integer.parseInt(scanner.nextLine());

        List<Livre> livres = bibliotheque.getLivres();

        if (index < 0 || index >= livres.size()) {
            System.out.println("Index invalide.");
            return;
        }

        Livre livre = livres.get(index);

        System.out.println("Livre trouvé : " + livre.getTitre());
        System.out.print("Nouveau titre (ou laisser vide pour ne pas changer) : ");
        String nouveauTitre = scanner.nextLine();

        if (!nouveauTitre.isEmpty()) {
            livre.setTitre(nouveauTitre);
        }

        System.out.print("Nom de l'auteur (ou laisser vide pour ne pas changer) : ");
        String nomAuteur = scanner.nextLine();
        
        if (!nomAuteur.isEmpty()) {
            System.out.print("Prénom de l'auteur : ");
            String prenomAuteur = scanner.nextLine();

            Auteur nouvelAuteur = new Auteur();
            nouvelAuteur.setNom(nomAuteur);
            nouvelAuteur.setPrenom(prenomAuteur);
            
            livre.setAuteur(nouvelAuteur);
        }

        System.out.print("Nouvelle année de publication (ou laisser vide pour ne pas changer) : ");
        String nouvelleAnneePublication = scanner.nextLine();

        if (!nouvelleAnneePublication.isEmpty()) {
            livre.setParution(Integer.parseInt(nouvelleAnneePublication));
        }

        System.out.println("Livre modifié : " + livre.getTitre());
    }

    /**
     * Supprime un livre de la bibliothèque.
     *
     * @param bibliotheque La bibliothèque.
     * @param scanner      Le scanner pour lire les entrées de l'utilisateur.
     */
    private static void supprimerLivre(Bibliotheque bibliotheque, Scanner scanner) {
        System.out.println("Suppression d'un livre :");
        System.out.print("Index du livre à supprimer : ");
        int index = Integer.parseInt(scanner.nextLine());
    
        List<Livre> livres = bibliotheque.getLivres();
    
        if (index < 0 || index >= livres.size()) {
            System.out.println("L'index fourni est invalide.");
            return;
        }
    
        Livre livre = livres.get(index);
    
        livres.remove(index);
    
        System.out.println("Livre supprimé : " + livre.getTitre());
    }
}
