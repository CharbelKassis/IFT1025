/**
 *@author Harlouchet Iban et Kassis Charbel
 *@version 1.0
 *@since Mars 2015
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class PanneauDeCartes extends JPanel{
      
    private Carte[] cartes;
    private int dejaOuverte;
    private Carte carteOuverte;
    private CarteSon deuxiemeCarteOuverte = null;
    private int compteur;
    final private PanneauDeCartes self = this;    // copie de la reference vers une variable final pour l'utiliser dans les classes anonymes.
    private int delaiAffichageMauvaisePaire;
    private int delaiAffichageInitial;
    private int pairesDevoilees;
    private JLabel text;
    private JLabel horloge;
    private JLabel nbCoups;
    private Timer timerHorloge;
    private ActionListener methodeHorloge;
    private ActionListener methodeDelaiInitial;
    
    /**
     * Constructeur du panneau de cartes.
     *@param nRangees : nombre de rangees du panneau.
     *@param nColonnes : nombre de colonnes du panneau.
     *@param delaiAffichageInitial : duree d'affichage des cartes au debut du jeu.
     *@param delaiAffichageMauvaisePaire : duree d'affiche d'une mauvaise paire de cartes devoilees.
     *@param text : zone de texte pour le message de victoire.
     *@param horloge : zone de texte pour la duree du jeu.
     *@param nbCoups : nombre de cartes devoilees.
     */
    public PanneauDeCartes(int nRangees, int nColonnes, Carte[] cartes, 
                           int delaiAffichageInitial, int delaiAffichageMauvaisePaire, JLabel text, JLabel horloge, JLabel nbCoups) {
    
        GridLayout layout = new GridLayout(nRangees,nColonnes);
        layout.setHgap(10);
        layout.setVgap(10);
        this.setLayout(layout);
	this.dejaOuverte = 2;
	this.carteOuverte = null;
	this.cartes = cartes;
	this.compteur = 0;
	this.pairesDevoilees = 0;
	this.delaiAffichageMauvaisePaire = delaiAffichageMauvaisePaire;
	this.delaiAffichageInitial = delaiAffichageInitial;
	this.text = text;
	this.horloge = horloge;
	this.nbCoups = nbCoups;
	
	this.methodeDelaiInitial = new ActionListener() {

		Carte[] cartes = self.cartes;
		
		public void actionPerformed(ActionEvent evt) {
						
		    for(int i=0;i<cartes.length;i++) {

			cartes[i].retourne();
						
		    }

		    self.dejaOuverte = 0;
		    self.horloge.setText("Temps ecoule: 0 secondes.");
		    self.timerHorloge = new Timer(1000,self.methodeHorloge);
		    self.timerHorloge.start();
		    self.nbCoups.setText("Nombre de coups: 0");
			 
		}

	    }; 
	this.methodeHorloge = new ActionListener() {

		int i = 0;

		public void actionPerformed(ActionEvent evt)  {

		    self.horloge.setText("Temps ecoule: "+(++i)+" secondes.");

		}

	    };
        
	    
	    // Remplit le panneau avec des cartes, a chacune desquelles on attache un MouseListener, de maniere a ce qu'on puisse jouer plus tard.
    	for(int i=0;i<cartes.length;i++) { 

	    this.add(cartes[i]);
	    
	    // On attache un MouseListener a chaque carte du panneau.
	    cartes[i].addMouseListener(new MouseAdapter() {  // la classe MouseAdapter implemente l'interface MouseListener
		   			
		    public void mouseClicked(MouseEvent e) {

			final Carte carte = (Carte)e.getSource();
			Carte[] cartes = self.cartes;
			
			if(carte.estCachee()) {

			    if(dejaOuverte == 0) {
				
				if(deuxiemeCarteOuverte != null) {

				    deuxiemeCarteOuverte.getSon().stop();
				    deuxiemeCarteOuverte = null;

				}
							
				carte.retourne();
				changerDejaOuverte(1);
				self.carteOuverte = carte;
				self.compteur++;
				self.nbCoups.setText("Nombre de coups: "+compteur);

			    
			    }

			    else if(dejaOuverte == 1) {
				
				carte.retourne();
				self.compteur++;
				self.nbCoups.setText("Nombre de coups: "+compteur);


				if(carteOuverte instanceof CarteSon) {
				    
				    CarteSon carteson = (CarteSon)carteOuverte;
				    carteson.getSon().stop();

				}
				    
				changerDejaOuverte(2);
				
				if(carte instanceof CarteSon)
				    
				    self.deuxiemeCarteOuverte = (CarteSon)carte;
			
				if(!carte.rectoIdentique(carteOuverte)) {
				    
				    ActionListener methodeDelaiMauvaisePaire = new ActionListener() {
					    
					    public void actionPerformed(ActionEvent evt) {
						
						 carteOuverte.retourne();
						 carte.retourne();
						 changerDejaOuverte(0);

						 if(carte instanceof CarteSon)
				    
						     ((CarteSon)carte).getSon().stop();
						 				 

					    }
					};
				    
				    Timer timer = new Timer(self.delaiAffichageMauvaisePaire,methodeDelaiMauvaisePaire);
				    timer.setRepeats(false);
				    timer.start();
				    
				}				
				
				else {
				    
				    if(pairesDevoilees == (cartes.length)/2 - 1) {
				    
					self.text.setText("Bravo, vous avez reussi en "+ (compteur)+ " coups!");
    					self.timerHorloge.stop();

				    }

				    else {
 
				    changerDejaOuverte(0);
				  		    
				    pairesDevoilees++;
	      
				    }

				}

			    }


			}

		
		    }
		});	
 	    
	}

	// Une fois delaiAffichacheInitial ecoule, les cartes du panneau sont retournees et le jeu commence.
	Timer timer2 = new Timer(self.delaiAffichageInitial,methodeDelaiInitial);
	timer2.setRepeats(false);
	timer2.start();

    }
    // Petite methode.
    public void changerDejaOuverte(int dejaOuverte) {

	    this.dejaOuverte = dejaOuverte;

    }

}
