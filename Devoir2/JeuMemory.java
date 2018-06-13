/**
 *@author Harlouchet Iban et Kassis Charbel
 *@version 1.0
 *@since Mars 2015
 */

import java.awt.*;
import javax.swing.*;
import java.io.*;
 
public class JeuMemory {
 
    public static void main(String[] args) {
    
    // Validation du nombre des parametres saisis par le joueur.
    if(args.length != 5) {
 
        messageErreur();
        System.exit(1);
    }
    
    // On cree l'adresse absolue des fichiers images et sons, via la classe File, valable pour tous 
    // les systemes d'exploitation. On peut directement ajouter des images ou des sons dans leur
    // dossier respectif, sans avoir a adapter le programme.
    
    File galaxies = new File("Images","Galaxies");
    File[] imageGalaxies = galaxies.listFiles();
    String[] adresseImagesGalaxies = new String[imageGalaxies.length];
	
    for(int i=0;i<imageGalaxies.length;i++) 
		
	adresseImagesGalaxies[i] = imageGalaxies[i].getAbsolutePath();


    File animaux = new File("Images","Animaux");
    File[] imageAnimaux = animaux.listFiles();
    String[] adresseImagesAnimaux = new String[imageAnimaux.length];
	
    for(int i=0;i<imageAnimaux.length;i++) 
		
	adresseImagesAnimaux[i] = imageAnimaux[i].getAbsolutePath();


    File sons = new File("Sons");
    File[] fichiersSons = sons.listFiles();
    String[] adresseSons = new String[fichiersSons.length];
	
    for(int i=0;i<fichiersSons.length;i++) 
		
	adresseSons[i] = fichiersSons[i].getAbsolutePath();

    File fileHautParleur = new File("Images","hautParleur");

    String hautParleur = fileHautParleur.listFiles()[0].getAbsolutePath();
 
    // On definit nos jeux de lettres et de mots.
    String[] tabLettres = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    String[] tabEmotions = {"Colere", "Joie", "Stress", "Crainte", "Bonheur", "Anxiete", "Passion", "Surprise", "Douleur", "Gene", "Fierte", "Admiration"};

    // On cree un tableau de cinq elements contenant des sous-classes de GenerateurDeCartes et qui sera passe en parametre a GenerateurDeCartesMultiple.
    GenerateurDeCartes[] tabGenerateurDeCartes= {new GenerateurDeCartesCouleur(),new GenerateurDeCartesMot("Lettres",tabLettres), new GenerateurDeCartesMot("Emotions",tabEmotions),new GenerateurDeCartesImage("Animaux",adresseImagesAnimaux),new GenerateurDeCartesImage("galaxies",adresseImagesGalaxies)};
     
    // On declare et initialise les parametres du jeu.
    int nRangees = 0;
    int nColonnes = 0;
    int delaiAffichageInitial = 0;
    int delaiAffichageMauvaisePaire = 0;
    int numeroDeTheme = 0;
 
    // Validation du type des parametres du jeu saisis par le joueur.
    try {
 
        nRangees = Integer.parseInt(args[0]);
        nColonnes = Integer.parseInt(args[1]);
        delaiAffichageInitial = Integer.parseInt(args[2]);
        delaiAffichageMauvaisePaire = Integer.parseInt(args[3]);
        numeroDeTheme = Integer.parseInt(args[4]);    
    }
 
    catch (NumberFormatException e) {
         
        messageErreur();
        System.exit(1);
    }
    
    // On construit la fenetre du jeu.
    JFrame frame = new JFrame("Memory par Charbel Kassis et Iban Harlouchet");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600,600);
    frame.setLocation(600,0);
    JLabel text = new JLabel(" ",SwingConstants.CENTER);
    JLabel temps = new JLabel(" ",SwingConstants.CENTER);
    JLabel coups = new JLabel(" ",SwingConstants.CENTER);
     
    
    // On genere le jeu de cartes selon le theme choisi par le joueur.
    GenerateurDeCartes g = null;
 
    if(numeroDeTheme == 0)
 
        g = new GenerateurDeCartesCouleur();
 
    else if(numeroDeTheme == 1)
 
        g = new GenerateurDeCartesMot("lettres",tabLettres);
 
    else if(numeroDeTheme == 2)
 
        g = new GenerateurDeCartesMot("emotions",tabEmotions);
 
    else if(numeroDeTheme == 3)
 
        g = new GenerateurDeCartesImage("animaux",adresseImagesAnimaux);
     
    else if(numeroDeTheme == 4)
 
      g = new GenerateurDeCartesImage("galaxies",adresseImagesGalaxies);
 
    else if(numeroDeTheme == 5)
 
         g = new GenerateurDeCartesMultiple("multiple",tabGenerateurDeCartes);

    else if(numeroDeTheme == 6)

	g = new GenerateurDeCartesSon("cris d'animaux",adresseSons,hautParleur);
    
    else {
 
        messageErreur(); // Si le joueur choisit un theme inexistant.
        System.exit(1);
    }
    
    // On genere le nombre de cartes en fonction de la dimension du panneau choisie par le joueur. Si la dimension est trop grande par rapport au 
    // nombre de carte, ca genere une erreur, car a un moment donne le theme s'epuise. 
    Carte[] cartes = null;
 
    try {
	
	if(nRangees <= 1 && nColonnes <= 1) {  // Si l'utilisateur entre nRangees <= 1 et nColonne <= 1, alors on lui impose un panneau de 2 cartes.

	    nColonnes = 2;
	    nRangees = 1;
	}

        cartes = g.generePairesDeCartesMelangees(nRangees*nColonnes/2);
    }
 
    catch(IndexOutOfBoundsException e) {
 
        MessageErreur2(g,numeroDeTheme,nColonnes,nRangees);
        System.exit(1);
         
    }
 
    // La composante graphique contient le panneau de carte et une zone de trois lignes de texte.
    PanneauDeCartes p = new PanneauDeCartes(nRangees,nColonnes,cartes,delaiAffichageInitial,delaiAffichageMauvaisePaire,text,temps,coups);
     
    JPanel zoneText = new JPanel(new GridLayout(0,1)); 
    zoneText.add(temps);
    zoneText.add(text);
    zoneText.add(coups);
    frame.getContentPane().add(p);
    frame.getContentPane().add(zoneText,BorderLayout.SOUTH);
    frame.setVisible(true);
     
    }
 
    // Un message d'erreur.
    public static void messageErreur() {    
 
    System.out.println("Utilisation: java JeuMemory nRangees");
    System.out.println("nColonnes delaiAffichageInitial(ms) delaiAffichageMauvaisePaire(ms) numeroDeTheme");
    System.out.println("Ex: java JeuMemory 5 6 5000 1000 3");
    System.out.println("Voici la liste des themes disponibles");
    System.out.println("0: Cartes couleurs");
    System.out.println("1: Lettres A ... Z");
    System.out.println("2: Noms d'emotions");
    System.out.println("3: Images d'animaux");
    System.out.println("4: Images de galaxies");
    System.out.println("5: Melange des themes 0 a 4");
    System.out.println("6: Sons d'animaux");
 
    }
 
    // Un deuxieme message d'erreur.
    public static void MessageErreur2(GenerateurDeCartes g,int numeroDeTheme, int nColonnes, int nRangees) {
 
    System.out.println("Attention: Il n'y a que " + g.nombreDeCartesDifferentes() + " cartes dans le theme " + numeroDeTheme + ".");
    System.out.println("Mais vous voulez jouer avec " +(nColonnes*nRangees/2) + " cartes.");
     
    }
 
 
}
