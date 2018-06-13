/**
 *@author Harlouchet Iban et Kassis Charbel
 *@version 1.0
 *@since Mars 2015
 */

import java.awt.*;
import javax.swing.*;
import java.awt.font.*; // pour mise en forme du texte
import java.awt.geom.*; // pour mise en forme du texte

/**
 * Classe des cartes de type mot.
 */
public class CarteMot extends Carte {

    private String mot; 

    /**
     * Constructeur de CarteMot.
     * @param estRecto : indique si la carte est sur sa face recto.
     * @param mot : mot affiche sur la face recto de la carte.
     */
    public CarteMot(boolean estRecto,String mot) {

	super(estRecto);
	this.mot = mot;

    }

    public CarteMot(CarteMot carte) {

	super(carte);
	this.mot = carte.mot;
    }
    
    /**
     * Methode heritee de Carte. Paint la carte selon son mot.
     */
    public void paintRecto(Graphics2D g) {
      
	int min = Math.min(this.getHeight(),this.getWidth()); 
	int l=this.mot.length();

	// Fond
	g.setColor(Color.GRAY);
	g.fillRect(0, 0, this.getWidth(), this.getHeight());

	// Forme
	g.setColor(Color.BLACK);
	int ptSize; 
	if (l==1)
	    ptSize= (int)(min/1.5);
	else
	    ptSize=(int)(min/l*1.5);
        g.setFont(new Font("Serif", Font.PLAIN, ptSize));
      		  
        FontRenderContext frc = g.getFontRenderContext();
        Rectangle2D  bounds = g.getFont().getStringBounds(mot, frc);
	double y = bounds.getHeight();
	double x = bounds.getWidth();

        g.drawString(mot,this.getWidth()/2-(int)x/2,this.getHeight()/2+(int)y*3/8);
      
    }

    public Object duplique() {

	return new CarteMot(this);
    }
    
    public boolean rectoIdentique(Carte carte) {

	try{

	if(this.mot.equals(((CarteMot)carte).mot))

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

	return this.mot;

    }
}

