/**
 *@author Harlouchet Iban et Kassis Charbel
 *@version 1.0
 *@since Mars 2015
 */

import java.awt.*;
import javax.swing.*;
import java.applet.*;            // pour la lecture de wmv
import java.net.*;               // pour la lecture de wmv

/**
 * Classe des cartes de type son.
 */
public class CarteSon extends Carte {

    private String adresseSon;
    private ImageIcon image;
    private AudioClip son;
    private boolean jouerSon;

    /**
     * Constructeur de CarteSon.
     * @param estRecto : indique si la carte est sur sa face recto.
     * @param adresseSon : adresse du fichier du son.
     * @param image : image affichee sur la face recto de la carte.
     */
    public CarteSon(boolean estRecto, String adresseSon, ImageIcon image) {

	super(estRecto);
	this.adresseSon=adresseSon;
	this.image = image;
	this.jouerSon = false;

    }

    public CarteSon(CarteSon carte) {

	super(carte);
	this.adresseSon = carte.adresseSon;
	this.image = carte.image;
	this.jouerSon = false;
	
    }
    
    public void paintRecto(Graphics2D g){
       
        g.drawImage(this.image.getImage(),0,0,this.getWidth(),this.getHeight(),this);
	
	if(jouerSon == true) {
	    
	try {
	    son = Applet.newAudioClip(new URL("file:///"+adresseSon));
	    son.play();
	} catch (MalformedURLException murle){
	    System.out.println(murle);
	}
    
	}

	else

	    this.jouerSon = true;
    }

    public Object duplique() {

	return new CarteSon(this);
    }
    
    public boolean rectoIdentique(Carte carte) {
	
	try{

	    if(this.adresseSon.equals(((CarteSon)carte).adresseSon))

	    return true;

	else

	    return false;

	}

	catch(ClassCastException e) {

	    return false;

	}
    }

    
    public AudioClip getSon() {

	return this.son;

    }

    /**
     * Redefinition de la methode toString heritee de Objet.
     * @return Un String.
     */
    public String toString() {

    	return this.adresseSon;

    }
    
}

