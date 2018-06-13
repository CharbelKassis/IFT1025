/**
 *@author Harlouchet Iban et Kassis Charbel
 *@version 1.0
 *@since Mars 2015
 */

import java.awt.*;
import javax.swing.*;

/**
 * Classe des cartes de type couleur.
 */
public class CarteCouleur extends Carte {

    private Color couleur;

    /**
     * Constructeur de CarteCouleur.
     * @param estRecto : indique si la carte est sur sa face recto.
     * @param couleur : couleur affichee sur la face recto de la carte.
     */
    public CarteCouleur(boolean estRecto,Color couleur) {

	super(estRecto);
	this.couleur = couleur;

    }

    public CarteCouleur(CarteCouleur carte) {

	super(carte);
	this.couleur = carte.couleur;
    }
    
    /**
     * Paint la carte selon sa couleur.
     */
    public void paintRecto(Graphics2D g) {

	g.setColor(this.couleur);
	g.fillRect(0,0,this.getWidth(),this.getHeight());
    }

    public Object duplique() {

	return new CarteCouleur(this);
    }
    
  
    public boolean rectoIdentique(Carte carte) {
	
	try {

	if(this.couleur == ((CarteCouleur)carte).couleur)

	    return true;

	else

	    return false;

	}


	catch(ClassCastException e) {

	    return false;

	}
    }

    /**
     * Redefinition de la methode toString heritee de Objet.
     * @return Un String.
     */
    public String toString() {

	return this.couleur.toString();

    }
}

