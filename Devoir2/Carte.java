/**
 *@author Harlouchet Iban et Kassis Charbel
 *@version 1.0
 *@since Mars 2015
 */

import java.awt.*;
import javax.swing.*;

/**
 * Classe des cartes selon un certain theme. Sera implantee dans chacune des sous-classes correspondant a un theme particulier.
 *@see #CarteCouleur
 *@see #CarteImage
 *@see #CarteMot
 *@see #CarteSon
 */
public abstract class Carte extends JComponent{

    private boolean estRecto;
    
    /**
     * Constructeur de la classe abstraite Carte.
     * @param estRecto : booleen indiquant si la carte est sur sa face recto.
     */
    protected Carte(boolean estRecto) {

	this.estRecto = estRecto;

    }
    
    /**
     * Constructeur de copie de la classe abstraite Carte.
     * @param carte : "instance" à copier de la classe Carte.
     */
    protected Carte(Carte carte) {

	this.estRecto = carte.estRecto;
    }
    
    /**
     * Indique si la carte est sur sa face recto.
     * @return Un booleen.
     */
    public boolean estMontree() {

	return this.estRecto;
    }

    /**
     * Indique si la carte est sur sa face verso.
     * @return Un booleen.
     */
    public boolean estCachee() {

	return !this.estMontree();

    }

    /** 
     * Affiche la face verso de la carte. Appelle la methode paintComponent via repaint.
     * @see #repaint()
     * @see #paintComponent(Graphics)
     */
    protected void montre() {

	this.estRecto = true;
	this.repaint();
	
    }
    
    /** 
     * Affiche la face recto de la carte. Appelle la methode paintComponent via repaint.
     * @see #repaint()
     * @see #paintComponent(Graphics)
     */
    protected void cache() {

	this.estRecto = false;
	this.repaint();

    }

    /** 
     * Retourne la carte. Appelle la methode paintComponent, via cache ou montre, puis via repaint.
     * @see #cache()
     * @see #montre()
     */
    public void retourne() {

	if(this.estMontree())

	    this.cache();

	else

	    this.montre();	   
    }

    /**
     * Peint le verso d'une carte en noir.
     * @param g : contexte graphique.
     */
    protected void paintVerso(Graphics2D g) {

	g.setColor(Color.black);
	g.fillRect(0,0,this.getWidth(),this.getHeight());
	  
    }
    
    /**
     * Peint le recto d'une carte.
     * @param g : contexte graphique.
     */
    public abstract void paintRecto(Graphics2D g);

    /**
     * Redefinition de la methode paintComponent, heritee de JComponent, via paintRecto et paintVerso.
     * @see #paintRecto(Graphics2D)
     * @see #paintVerso(Graphics2D)
     */
    public void paintComponent(Graphics g) {

	Graphics2D g2 = (Graphics2D)g;
		
	    if(this.estMontree())

		this.paintRecto(g2);

	    else

		this.paintVerso(g2);

    }

    /**
     * Indique si deux cartes ont le meme recto.
     * @param carte : "instance" de la classe abstraite Carte.
     * @return Un booleen.
     */
    public abstract boolean rectoIdentique(Carte carte);

    /**
     * Duplique une carte.
     * @return Une copie identique de l'objet sur lequel elle est appelee.
     */
    public abstract Object duplique();

    /** 
     * Mélange les cartes du tableau passe en paramètre.
     * @param cartes : Un tableau de cartes.
     */
    public static void melangeCartes(Carte[] cartes) {

	for(int i=0;i<cartes.length;i++) {

	    int rdm = (int)(Math.random()*cartes.length);
	    Carte temp = cartes[i];
	    cartes[i] = cartes[rdm];
	    cartes[rdm] = temp;
	}    
    }
}
