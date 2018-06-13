/**
 *@author Harlouchet Iban et Kassis Charbel
 *@version 1.0
 *@since Mars 2015
 */

import java.awt.*;
import javax.swing.*;

/**
 * Represente un generateur de cartes d'image.
 */
public class GenerateurDeCartesImage extends GenerateurDeCartes {

    private String[] images;
	
    /**
     * Constructeur de GenerateurDeCartesImage.
     * @param theme : nom du theme.
     * @param images : tableau d'adresses des fichiers image.
     */
	public GenerateurDeCartesImage(String theme, String[] images) {
	    
	    this.nom = theme;
	    this.images = images;
	     
	    for(int i=(images.length-1); i>=0; i--){

		int rdm = (int)(Math.random()*(images.length-1));
		String tmp = images[i];
		images[i] = images[rdm];
		images[rdm] = tmp;

	}
    }

    public Carte genereUneCarte() {
	
	int i = 0;

	while(this.images[i] == null)

	    i++;

	String image = images[i];
	this.images[i] = null;
	return new CarteImage(true,new ImageIcon(image));
	
	
    }

    public int nombreDeCartesDifferentes() {

	return images.length;

    }

}
