/**
 *@author Harlouchet Iban et Kassis Charbel
 *@version 1.0
 *@since Mars 2015
 */

import java.awt.*;
import javax.swing.*;

/**
 * Classe des cartes de type image.
 */
public class CarteImage extends Carte {

    private ImageIcon image;

    /**
     * Constructeur de CarteImage.
     * @param estRecto : indique si la carte est sur sa face recto.
     * @param image : image affichee sur la face recto de la carte.
     */
    public CarteImage(boolean estRecto,ImageIcon image) {

	super(estRecto);
	this.image = image;

    }

    public CarteImage(CarteImage carte) {

	super(carte);
	this.image = carte.image;
    }
    
    /**
     * Methode heritee de Carte. Paint la carte selon son image.
     */
    public void paintRecto(Graphics2D g){
       
        g.drawImage(this.image.getImage(),0,0,this.getWidth(),this.getHeight(),this);
    }

    public Object duplique() {

	return new CarteImage(this);
    }
    
    public boolean rectoIdentique(Carte carte) {
	
	try{

	if(this.image == ((CarteImage)carte).image)

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

	return this.image.getDescription();

    }

}

