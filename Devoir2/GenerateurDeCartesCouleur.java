/**
 *@author Harlouchet Iban et Kassis Charbel
 *@version 1.0
 *@since Mars 2015
 */

import java.awt.*;
import javax.swing.*;

/**
 * Represente un generateur de cartes de couleur.
 */
public class GenerateurDeCartesCouleur extends GenerateurDeCartes {
    
    Color[] couleurs =  {Color.RED,Color.GREEN,Color.BLUE,Color.CYAN,Color.MAGENTA,Color.YELLOW,Color.PINK,Color.ORANGE};

    /**
     * Constructeur de GenerateurDeCartesCouleur.
     */
    public GenerateurDeCartesCouleur() {

	for(int i=0;i<couleurs.length;i++) {
	    
	    this.nom = "couleur";
	    int rdm = (int)(Math.random()*couleurs.length);
	    Color tmp = couleurs[i];
	    couleurs[i] = couleurs[rdm];
	    couleurs[rdm] = tmp;

	}
    }

    public Carte genereUneCarte() {
	
	int i = 0;

	while(this.couleurs[i] == null) {
	    
	    i++;

	}
	
	Color couleur = couleurs[i];
	this.couleurs[i] = null;
	return new CarteCouleur(true,couleur);
	
	
    }

    public int nombreDeCartesDifferentes() {

	return couleurs.length;

    }

}
