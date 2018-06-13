/**
 *@author Harlouchet Iban et Kassis Charbel
 *@version 1.0
 *@since Mars 2015
 */

import java.awt.*;
import javax.swing.*;

/**
 * Represente un generateur de cartes selon un certain theme. Sera implantee dans chacune des sous-classes correspondant a un theme particulier.
 *@see #GenerateurDeCartesCouleur
 *@see #GenerateurDeCartesImage
 *@see #GenerateurDeCartesMot
 *@see #GenerateurDeCarteMultiple
 *@see #GenerateurDeCartesSon
 */
public abstract class GenerateurDeCartes {

	/**
	 * Nom du theme du jeu de carte.
	 */
    protected String nom;

    /**
     * @return Le nom du theme.
     */
    public String getNom() {

	return this.nom;

    }

    /**
     * Retourne une carte choisie aleatoirement parmi celles du theme.
     * @return Une carte.
     */
    public abstract Carte genereUneCarte();

    /**
     * Retourne le nombre de cartes differentes disponibles dans le theme.
     * @return Un entier.
     */
    public abstract int nombreDeCartesDifferentes();

    /**
     * Genere un tableau de n cartes differentes, selon le theme.
     * @param n : Nombre de cartes differentes a generer.
     * @return Un tableau de n cartes.
     */
    public Carte[] genereCartes(int n) {

	Carte[] cartes = new Carte[n];

	for(int i=0;i<cartes.length;i++)

	    cartes[i] = this.genereUneCarte();

	return cartes;
    }

    /**
     * Genere 2n cartes par paires identiques, selon le theme ; puis les melange.
     * @see #genereCartes(int)
     * @see #duplique()
     * @see #melangeCartes
     * @param n : nombre de paires de cartes differentes a generer.
     * @return Un tableau de 2n cartes.
     */
    public Carte[] generePairesDeCartesMelangees(int n) {

	Carte[] cartes = this.genereCartes(n);
	Carte[] paireCartes = new Carte[2*n];

	int j=0;
	    
	for(int i=0;i<paireCartes.length;i+=2) {

	    paireCartes[i] = cartes[j];
	    paireCartes[i+1] = (Carte)(cartes[j].duplique());
	    j++;
 
	}
	
	Carte.melangeCartes(paireCartes);

	return paireCartes;
	    
    }

}
