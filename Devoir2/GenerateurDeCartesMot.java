/**
 *@author Harlouchet Iban et Kassis Charbel
 *@version 1.0
 *@since Mars 2015
 */

import java.awt.*;
import javax.swing.*;

/**
 * Represente un generateur de cartes de mot.
 */
public class GenerateurDeCartesMot extends GenerateurDeCartes {

    private String[] mots;
  
    /** 
     * Constructeur de GenerateurDeCartesMot
     * @param theme : nom du theme.
     * @param mots : tableau de Strings des mots a afficher.
     */
	public GenerateurDeCartesMot(String theme, String[] mots) {

	    this.nom = theme;
	    this.mots = mots;

	    for(int i=(mots.length-1); i>=1; i--){

		int rdm = (int)(Math.random()*(i+1));
		String tmp = mots[i];
		mots[i] = mots[rdm];
		mots[rdm] = tmp;

	}
    }

    public Carte genereUneCarte() {
	
	int i = 0;

	while(this.mots[i] == null) {
	    
	    i++;

	}
	
	String mot = mots[i];
	this.mots[i] = null;
	return new CarteMot(true,mot);
	
	
    }

    public int nombreDeCartesDifferentes() {

	return mots.length;

    }

}
