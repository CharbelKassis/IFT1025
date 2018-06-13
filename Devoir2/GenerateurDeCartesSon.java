/**
 *@author Harlouchet Iban et Kassis Charbel
 *@version 1.0
 *@since Mars 2015
 */

import java.awt.*;
import javax.swing.*;

/**
 * Represente un generateur de cartes de son.
 */
public class GenerateurDeCartesSon extends GenerateurDeCartes {

    private String[] sons;
    private String image;
	
    /** 
     * Constructeur de GenerateurDeCartesSon.
     * @param theme : nom du theme.
     * @param sons :  tableau d'adresses des fichiers son.
     * @param image : adresse du fichier de l'image affichee sur la carte son.
     */
    public GenerateurDeCartesSon(String theme, String[] sons, String image) {
	    
	    this.nom = theme;
	    this.sons = sons;
	    this.image = image;
	     
	    for(int i=(sons.length-1); i>=1; i--){

		int rdm = (int)(Math.random()*(i+1));
		String tmp = sons[i];
		sons[i] = sons[rdm];
		sons[rdm] = tmp;

	}
    }

    public Carte genereUneCarte() {
	
	int i = 0;

	while(this.sons[i] == null)

	    i++;

	String son = sons[i];
	this.sons[i] = null;
	return new CarteSon(true, new String(son), new ImageIcon(image));
		
    }

    public int nombreDeCartesDifferentes() {

	return sons.length;

    }

}
