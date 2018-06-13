import java.util.Scanner;

/**
 * @author Harlouchet Iban et Kassis Charbel.
 * @since Fevrier 2015.
 * @version 1.0.
 */
public class Laby {

	/**
	 * Prend 5 parametres en argument.
	 * @param args Liste des parametres : hauteur, largeur, densite, duree visible, nombre de vies.
	 * @see #jeuLabyrintheInvisible(int, int, double, int, int).
	 */
    public static void main(String[] args){
	
	if (args.length != 5) {

	    System.out.println("Nombre de parametres incorrects");
	    System.out.println("Utilisation: java Laby <hauteur> <largeur> <densite> <duree visible> <nb vies>");
	    System.out.println("Ex: java Laby 10 10 0.30 5 5");

	}

	else {

	    int hauteur = Integer.parseInt(args[0]);
	    int largeur = Integer.parseInt(args[1]);
	    double densite = Double.parseDouble(args[2]);
	    int duree_visible = Integer.parseInt(args[3])*1000;
	    int nb_vies = Integer.parseInt(args[4]);

	    jeuLabyrintheInvisible(hauteur,largeur,densite,duree_visible,nb_vies);
	}
	
    }
    
    /** 
     * Lance le jeu du labyrinthe invisible.
     * @param hauteur Hauteur du labyrinthe en nombre de cases.
     * @param largeur Largeur du labyrinthe en nombre de cases.
     * @param densite Seuil d'existence des murets du labyrinthe.
     * @param duree_visible Duree d'affichage des murets en debut de partie, en secondes.
     * @param nb_vies Nombre de vies accorde au joueur.
     * @see #initier_labyrinthe(Labyrinthe, Personnage, int, double, int, int)
     * @see #gererInput(char, Personnage, Labyrinthe, Labyrinthe, int, double, int)
     * @see #defaite(Labyrinthe, int, double, int)
     */
    public static void jeuLabyrintheInvisible(int hauteur,int largeur, double densite,int duree_visible,int nb_vies) {
	    
	Labyrinthe labyrinthe = new Labyrinthe(hauteur,largeur);
	Personnage personnage = new Personnage(labyrinthe,(int)(Math.random()*hauteur),0,nb_vies);
	Labyrinthe copie_labyrinthe = initier_labyrinthe(labyrinthe, personnage, hauteur, densite, duree_visible, nb_vies);
	Scanner scanner = new Scanner(System.in);

	while(personnage.get_nb_vies() != 0) {
	    
	    String input = "";

	    while(input.length() == 0) {

	      input = scanner.nextLine();

	    }

	    char inputchar = input.charAt(0);

	    copie_labyrinthe = gererInput(inputchar,personnage,labyrinthe,copie_labyrinthe,nb_vies,densite,duree_visible);

	}
	    
	defaite(labyrinthe, nb_vies,densite,duree_visible);
	
    }
	
    /**
     * Gere l'interaction entre le jeu et le joueur, et retourne un labyrinthe, en consignant
     * son etat : les murets decouverts en cours de jeu.
     * @param input Caractere tape par l'utilisateur.
     * @param personnage Personnage du labyrinthe.
     * @param labyrinthe Labyrinthe du jeu.
     * @param copie_labyrinthe Copie du labyrinthe du jeu.
     * @param nb_vies Nombre de vies du joueur.
     * @param densite Seuil d'existence des murets 
     * @param duree_visible Duree de visibilite du labyrinthe au depart du jeu.
     * @return Labyrinthe.
     * @see #deplacement(Personnage, Labyrinthe, Labyrinthe, char, int, double, int)
     * @see #jeuLabyrintheInvisible(int, int, double, int, int)
     * @see #refresh(Labyrinthe, int, Personnage)
     * @see #crossPath(int, int, Labyrinthe, Labyrinthe, int, double, int, Personnage, boolean[][])
     */
    public static Labyrinthe gererInput(char input, Personnage personnage, Labyrinthe labyrinthe, Labyrinthe copie_labyrinthe, int nb_vies, double densite, int duree_visible) {

	if (input == 'd' || input == 'g' || input == 's' || input == 'h' || input == 'e' || input =='b' || input == 'x')
			
	    deplacement(personnage,labyrinthe,copie_labyrinthe,input,nb_vies,densite, duree_visible);

	else if (input == 'q')

	    System.exit(0);

	else if (input == 'p')

	    jeuLabyrintheInvisible(labyrinthe.get_hauteur_labyrinthe(),labyrinthe.get_largeur_labyrinthe(),densite,duree_visible,nb_vies);

	else if (input == 'v') {

	    copie_labyrinthe = labyrinthe;
	    refresh(copie_labyrinthe,nb_vies,personnage);
	    return copie_labyrinthe;

	}

	else if (input == 'o') {
	    
	    copie_labyrinthe = labyrinthe;
	    refresh(copie_labyrinthe,nb_vies,personnage);
	    boolean typeIntelligence = messageO();
	    
	    if (typeIntelligence == true)

	      solutionBete(personnage,labyrinthe,copie_labyrinthe,densite,duree_visible,nb_vies);
	
	    else 
	    
	      solutionIntelligente(personnage,labyrinthe,copie_labyrinthe,nb_vies,densite,duree_visible);
	    	   
	}

	return copie_labyrinthe;

    }

    /**
     * Fonction de deplacement du personnage. Fait appel a une des quatres fonctions de deplacement,
     * en fonction de la direction choisie par le joueur.
     * @param personnage Personnage.
     * @param l Labyrinthe avec tout les murets.
     * @param copie_l Copie du labyrinthe.
     * @param input Direction choisie par le joueur.
     * @param nb_vies Nombre de vies restant au joueur.
     * @param densite Seuil d'existence des murets.
     * @param duree_visible Duree de visibilite des murets au debut du jeu.
     * @see #deplacementBas(Labyrinthe, Labyrinthe, Personnage, int)
     * @see #deplacementDroite(Labyrinthe, Labyrinthe, Personnage, int, double, int)
     * @see #deplacementGauche(Labyrinthe, Labyrinthe, Personnage, int)
     * @see #deplacementHaut(Labyrinthe, Labyrinthe, Personnage, int)
     */
	public static void deplacement(Personnage personnage,Labyrinthe l,Labyrinthe copie_l,char input, int nb_vies, double densite, int duree_visible) {

	if(input == 'd') 
	   
	    deplacementDroite(copie_l,l,personnage,nb_vies,densite,duree_visible);	   
		
	else if(input == 's' || input == 'g') 
	
	    deplacementGauche(copie_l,l,personnage,nb_vies);	

	else if(input == 'h' || input == 'e') 
	
	    deplacementHaut(copie_l,l,personnage,nb_vies);

	else 
	
	    deplacementBas(copie_l,l,personnage,nb_vies);	

    }
	
	/**
	 * Deplace le personnage d'une case vers la droite.
	 * @param copie_l Copie du labyrinthe.
     * @param l Labyrinthe avec tous les murets.
     * @param personnage Personnnage.
     * @param nb_vies Nombre de vies restant au joueur.
 	 * @param densite Seuil d'existence des murets du labyrinthe.
 	 * @param duree_visible Duree de visibilite des murets au debut du jeu.
 	 */
    public static void deplacementDroite(Labyrinthe copie_l, Labyrinthe l, Personnage personnage, int nb_vies, double densite, int duree_visible) {
	
	int i = personnage.get_i_personnage();
	int j = personnage.get_j_personnage();
	
	if(l.muretVerticalExiste(i,j+1)) {
	        
	    copie_l.dessineMuretVertical(i,j+1);
	    personnage.set_nb_vies(personnage.get_nb_vies()-1);
	    refresh(copie_l,nb_vies,personnage);
	    
	}
	     
	else if(j == l.get_largeur_labyrinthe()-1)

	    victoire(l,nb_vies,personnage,densite, duree_visible);

	else {

	    l.effacePersonnage(personnage);
	    copie_l.effacePersonnage(personnage);
	    personnage.aller_droite();
	    l.dessinePersonnage(personnage);
	    copie_l.dessinePersonnage(personnage);
	    refresh(copie_l,nb_vies,personnage);

	}
    }

    /**
     * Deplace le personnage d'une case vers la gauche.
     * @param copie_l Copie du labyrinthe.
     * @param l Labyrinthe avec tous les murets.
     * @param personnage Personnnage.
     * @param nb_vies Nombre de vies restant au joueur.
     */
    public static void deplacementGauche(Labyrinthe copie_l, Labyrinthe l, Personnage personnage, int nb_vies) { 
		    
	int i = personnage.get_i_personnage();
	int j = personnage.get_j_personnage();
	    
	if(l.muretVerticalExiste(i,j)) {

	    copie_l.dessineMuretVertical(i,j);
	    personnage.set_nb_vies(personnage.get_nb_vies()-1);
	    
	}
	     
	else {

	    l.effacePersonnage(personnage);
	    copie_l.effacePersonnage(personnage);
	    personnage.aller_gauche();
	    l.dessinePersonnage(personnage);
	    copie_l.dessinePersonnage(personnage);
	    
	}

	refresh(copie_l,nb_vies,personnage);
    }

    /**
     * Deplace le personnage d'une case vers le bas.
     * @param copie_l Copie du labyrinthe.
     * @param l Labyrinthe avec tous les murets.
     * @param personnage Personnnage.
     * @param nb_vies Nombre de vies restant au joueur.
     */
    public static void deplacementBas(Labyrinthe copie_l, Labyrinthe l, Personnage personnage, int nb_vies) { 
		
	int i = personnage.get_i_personnage();
	int j = personnage.get_j_personnage();
	    
	if(l.muretHorizontalExiste(i+1,j)) {

	    copie_l.dessineMuretHorizontal(i+1,j);
	    personnage.set_nb_vies(personnage.get_nb_vies()-1);
	    
	}
	     
	else {

	    l.effacePersonnage(personnage);
	    copie_l.effacePersonnage(personnage);
	    personnage.aller_bas();
	    l.dessinePersonnage(personnage);
	    copie_l.dessinePersonnage(personnage);
	    
	}

	refresh(copie_l,nb_vies,personnage);
    }

    /**
     * Deplace le personnage d'une case vers le haut.
     * @param copie_l Copie du labyrinthe.
     * @param l Labyrinthe avec tous les murets.
     * @param personnage Personnnage.
     * @param nb_vies Nombre de vies restant au joueur.
     */
     public static void deplacementHaut(Labyrinthe copie_l, Labyrinthe l, Personnage personnage, int nb_vies) { 
		    
	int i = personnage.get_i_personnage();
	int j = personnage.get_j_personnage();
	    
	if(l.muretHorizontalExiste(i,j)) {

	    copie_l.dessineMuretHorizontal(i,j);
	    personnage.set_nb_vies(personnage.get_nb_vies()-1);
	    
	}
	     
	else {

	    l.effacePersonnage(personnage);
	    copie_l.effacePersonnage(personnage);
	    personnage.aller_haut();
	    l.dessinePersonnage(personnage);
	    copie_l.dessinePersonnage(personnage);	    

	}

	refresh(copie_l,nb_vies,personnage);
    }
	
     /**
      * Affiche un message de victoire, invitant le joueur a rejouer.
      * @param labyrinthe Labyrinthe.
      * @param nb_vies Nombre de vies restant au joueur.
      * @param personnage Personnage.
      * @param densite Seuil d'existence des murets.
      * @param duree_visible Duree de visibilite des murets au debut du jeu.
      */
    public static void victoire(Labyrinthe labyrinthe, int nb_vies, Personnage personnage, double densite, int duree_visible) {

	Scanner scanner = new Scanner(System.in);
		
	System.out.println();
	System.out.println("Bravo, vous etes parvenu jusqu'a la sortie en commettant seulement " + (nb_vies-personnage.get_nb_vies()) + " erreurs.");
	System.out.println("Voulez-vous jouer une nouvelle partie? Tapez 'o' pour oui, sinon tapez une lettre pour non. ");
	
	String input = "";

	while(input.length() == 0) {

	      input = scanner.nextLine();

	}
	    char inputchar = input.charAt(0);

		
	if (inputchar == 'o' || inputchar == 'O')
		
	    jeuLabyrintheInvisible(labyrinthe.get_hauteur_labyrinthe(),labyrinthe.get_largeur_labyrinthe(), densite,duree_visible,nb_vies);

       	else

	    System.exit(0);		

    }
    /**
     * Affiche un message de defaite, et invite le joueur a rejouer une partie
     * avec les memes parametres.
     * @param labyrinthe Labyrinthe.
     * @param nb_vies Nombre de vies restant au joueur.
     * @param densite Seuil d'existence des murets.
     * @param duree_visible Duree de visibilite des murets au debut du jeu.
     */
    public static void defaite(Labyrinthe labyrinthe, int nb_vies, double densite, int duree_visible) {

	Scanner scanner = new Scanner(System.in);
	
	System.out.println();
	System.out.println("Vous avez perdu, vous avez epuise vos " + nb_vies + " vies!");
	System.out.println("Voulez-vous jouer une nouvelle partie? Tapez 'o' pour oui, sinon tapez une lettre pour non. ");
	  
	String input = "";

	while(input.length() == 0) {

	      input = scanner.nextLine();

	}
	 
	char inputchar = input.charAt(0);
		
	if (inputchar == 'o' || inputchar == 'O')
		
	    jeuLabyrintheInvisible(labyrinthe.get_hauteur_labyrinthe(),labyrinthe.get_largeur_labyrinthe(), densite,duree_visible,nb_vies);

	else

	    System.exit(0);

    }
    
    /**
     * Affiche un message indiquant au joueur qu'il n'est pas possible de sortir du 
     * labyrinthe, et invite le joueur a rejouer.
     * @param labyrinthe Labyrinthe.
     * @param nb_vies Nombre de vies restant au joueur.
     * @param densite Seuil d'existence des murets.
     * @param duree_visible Duree de visibilitee des murets du labyrinthe au debut du jeu.
     */
    public static void pasDeSolution(Labyrinthe labyrinthe, int nb_vies, double densite, int duree_visible) {

          
	Scanner scanner = new Scanner(System.in);
	
	System.out.println();
	System.out.println("Il n'est pas possible de sortir de ce labyrinthe");
	System.out.println();
	System.out.println("Voulez-vous jouer une nouvelle partie? Tapez 'o' pour oui, sinon tapez une lettre pour non. ");
	  
	String input = "";

	while(input.length() == 0) {

	      input = scanner.nextLine();

	}
	 
	char inputchar = input.charAt(0);
		
	if (inputchar == 'o' || inputchar == 'O')
		
	    jeuLabyrintheInvisible(labyrinthe.get_hauteur_labyrinthe(),labyrinthe.get_largeur_labyrinthe(), densite,duree_visible,nb_vies);

	else

	    System.exit(0);
  
    }
    /**
     * Prend essentiellement un labyrinthe et un personnage en parametres, 
     * dessine le mur d'enceinte, dessine l'ouverture au hasard sur 
     * le mur de droite, le personnage au hasard sur la colonne de gauche. 
     * Une copie du labyrinthe est retournee. C'est sur cette copie depourvue de murets
     * que le joueur joue.
     * Les murets sont construits aleatoirement en fonction du parametre densite.
     * Le labyrinthe et ses murets sont affiches pendant la duree determine par le 
     * parametre duree_visible. Le jeu est lance, avec un message invitant le joueur 
     * a choisir le sens du deplacement.
     * @param labyrinthe Labyrinthe.
     * @param personnage Personnage.
     * @param hauteur Hauteur du labyrinthe (pour choisir aleatoirement l'emplacement du personnage et de la
     * porte de sortie)
     * @param densite Seuil d'existence des murets du labyrinthe.
     * @param duree_visible Duree de visibilite des murets au debut du jeu.
     * @param nb_vies Nombre de vies restant au joueur.
     * @return Le labyrinthe invisible sur lequel joue le joueur.
     */
    public static Labyrinthe initier_labyrinthe(Labyrinthe labyrinthe, Personnage personnage, int hauteur, double densite, int duree_visible, int nb_vies) {

	labyrinthe.dessineMurdEnceinte();
	labyrinthe.dessineOuverture((int)(Math.random()*hauteur));
	labyrinthe.dessinePersonnage(personnage);
	Labyrinthe copie_labyrinthe = new Labyrinthe(labyrinthe);
	labyrinthe.construitLabyrintheAleatoire(densite);
	labyrinthe.affiche();
	sleep(duree_visible);
	refresh(copie_labyrinthe,nb_vies,personnage);
	return copie_labyrinthe;

    }

    /** 
     * Fonction permettant de deplacer le personnage vers la sortie du labyrinthe, de 
     * maniere intelligente (sans repetitions), par un appel recursif. 
     * En l'absence de chemin de sortie, un message affiche qu'il n'y a pas de solution. 
     * @param i Abscisse du personnage au moment ou la solution intelligente est appelee.
     * @param j Ordonnee du personnage au moment ou la solution intelligente est appelee.
     * @param labyrinthe Labyrinthe.
     * @param copie_labyrinthe Copie du labyrinthe.
     * @param nb_vies Nombre de vies restant au joueur au moment où la solution intelligente est appelee.
     * @param densite Seuil d'existence des murets.
     * @param duree_visible Duree de visibilite des murets du labyrinthe au debut du jeu.
     * @param personnage Personnage.
     * @param tab Tableau de memes dimensions que le labyrinthe, consignant les cases parcourues par le personnage.
     * @see #crossPath(int, int, Labyrinthe, Labyrinthe, int, double, int, Personnage, boolean[][])
     * @see #deplacementBas(Labyrinthe, Labyrinthe, Personnage, int)
     * @see #deplacementDroite(Labyrinthe, Labyrinthe, Personnage, int, double, int)
     * @see #deplacementGauche(Labyrinthe, Labyrinthe, Personnage, int)
     * @see #deplacementHaut(Labyrinthe, Labyrinthe, Personnage, int)
     * @see #sleep(long)
     */
    public static void crossPath(int i, int j, Labyrinthe labyrinthe, Labyrinthe copie_labyrinthe, int nb_vies, double densite, int duree_visible, Personnage personnage, boolean[][] tab) {

	if(!labyrinthe.muretVerticalExiste(i,j+1) && tab[i][j+1] == false) {
	    
	    deplacementDroite(copie_labyrinthe,labyrinthe,personnage,nb_vies, densite,duree_visible);
	    sleep(1000);
	    tab[i][j+1] = true;
	    crossPath(i,j+1,labyrinthe,copie_labyrinthe,nb_vies,densite,duree_visible,personnage,tab);
	    deplacementGauche(copie_labyrinthe,labyrinthe,personnage,nb_vies);
	    sleep(1000);

	}

	if(!labyrinthe.muretHorizontalExiste(i,j) && tab[i-1][j] == false) {
	    
	    deplacementHaut(copie_labyrinthe,labyrinthe,personnage,nb_vies);
	    sleep(1000);
	    tab[i-1][j] = true;
	    crossPath(i-1,j,labyrinthe,copie_labyrinthe,nb_vies,densite,duree_visible,personnage,tab);
	    deplacementBas(copie_labyrinthe,labyrinthe,personnage,nb_vies);
	    sleep(1000);

	}

	if(!labyrinthe.muretHorizontalExiste(i+1,j) && tab[i+1][j] == false) {
	    
	    deplacementBas(copie_labyrinthe,labyrinthe,personnage,nb_vies);
	    sleep(1000);
	    tab[i+1][j] = true;
	    crossPath(i+1,j,labyrinthe,copie_labyrinthe,nb_vies,densite,duree_visible,personnage,tab);
	    deplacementHaut(copie_labyrinthe,labyrinthe,personnage,nb_vies);
	    sleep(1000);
	    
	}

	if(!labyrinthe.muretVerticalExiste(i,j) && tab[i][j-1] == false) {
	    
	    deplacementGauche(copie_labyrinthe,labyrinthe,personnage,nb_vies);
	    sleep(1000);
	    tab[i][j-1] = true;
	    crossPath(i,j-1,labyrinthe,copie_labyrinthe,nb_vies,densite,duree_visible,personnage,tab);
	    deplacementDroite(copie_labyrinthe,labyrinthe,personnage,nb_vies, densite,duree_visible);
	    sleep(1000);

	}
	
	
    }
   
    /**
     * Implemente la solution intelligente en faisant recursivement appel a la fonction crossPath.
     * @param personnage Personnage.
     * @param labyrinthe Labyrinthe.
     * @param copie_labyrinthe Copie du labyrinthe.
     * @param nb_vies Nombre de vies restant au joueur.
     * @param densite Seuil d'existence des murets.
     * @param duree_visible Duree de visibilite des murets du labyrinthe au debut du jeu.
     * @see #crossPath(int, int, Labyrinthe, Labyrinthe, int, double, int, Personnage, boolean[][])
     * @see #pasDeSolution(Labyrinthe, int, double, int)
     */
    public static void solutionIntelligente(Personnage personnage, Labyrinthe labyrinthe, Labyrinthe copie_labyrinthe, int nb_vies, double densite, int duree_visible) {
     
      int p_i = personnage.get_i_personnage();
      int p_j = personnage.get_j_personnage();
      int I = labyrinthe.get_hauteur_labyrinthe();
      int J = labyrinthe.get_largeur_labyrinthe();
	    
      boolean[][] tableauPassage = new boolean[I][J+1];
      tableauPassage[p_i][p_j] = true;
      sleep(1000);
      crossPath(p_i,p_j,labyrinthe,copie_labyrinthe,nb_vies,densite,duree_visible, personnage,tableauPassage);
      pasDeSolution(labyrinthe,nb_vies,densite,duree_visible);
      
    }
    
    /**
     * Implemente la solution bete.
     * @param personnage Le personnage.
     * @param labyrinthe Le labyrinthe.
     * @param copie_labyrinthe Une copie du labyrinthe.
     * @param densite Le seuil d'existence des murets.
     * @param duree_visible La duree de visibilite.
     * @param nb_vies Le nombre de vies de l'utilisateur.
     * @see #deplacement(Personnage, Labyrinthe, Labyrinthe, char, int, double, int)
     * @see #defaite(Labyrinthe, int, double, int)
     */
    public static void solutionBete(Personnage personnage, Labyrinthe labyrinthe, Labyrinthe copie_labyrinthe, double densite, int duree_visible, int nb_vies) {
	
      while(personnage.get_nb_vies() != 0) {
  
	  deplacement(personnage,labyrinthe,copie_labyrinthe,inputCharAleatoire(),nb_vies,densite,duree_visible);
	  sleep(300);
	   
	}

	defaite(labyrinthe, nb_vies,densite,duree_visible);
	

    }
    
    /**
     * Retourne un sens de deplacement choisi aleatoirement, ie le caractere 'd', 'h', 'b' ou 'g'.
     * @return Le caractere retourne.
     */
    public static char inputCharAleatoire() {
    
    double rdm = Math.random();
    char inputchar;

    if (rdm < 0.25)

      inputchar = 'd';

    else if (rdm < 0.5)

      inputchar = 'h';

    else if (rdm < 0.75)

      inputchar = 'b';

    else

      inputchar = 'g';

    return inputchar;

    }
    /**
     * Affiche le nombre de vies restant a l'utilisateur, puis invite 
     * l'utilisateur a choisir un sens de deplacement.
     * @param nb_vies Nombre de vies restant a l'utilisateur.
     * @param personnage Personnage.
     */
    public static void message(int nb_vies, Personnage personnage) {

	System.out.println("Il vous reste " + personnage.get_nb_vies() + " vies sur " + nb_vies + ".");
	System.out.println();
	System.out.println("Quelle direction souhaitez-vous prendre?");
	System.out.println("(droite: d; gauche: g ou s; haut: h ou e; bas: b ou x)");

    }

    /** 
     * Message invitant l'utilisateur a choisir entre une strategie bete ou intelligente.
     * @return La reponse de l'utilisateur.
     */
    public static boolean messageO() {

	System.out.println();
	System.out.println("Voulez-vous que l'ordinateur applique une strategie bete ou intelligente. Tapez 'b' pour bete et 'i' pour intelligent.");
	sleep(2000);
  
	Scanner scanner = new Scanner(System.in);
	
	String input = "";

	while(input.length() == 0 || (input.charAt(0) != 'b' && input.charAt(0) != 'i')) {

	      input = scanner.nextLine();

	}
	 
	char inputchar = input.charAt(0);

	if (inputchar == 'b') {

	    return true;
	
	}
	
	else

	    return false;

    }

    /** 
     * Rafraîchit l'ecran, affiche a nouveau le labyrinthe, ainsi qu'un message faisant le bilan du nombre de vies restant et invitant le joueur a choisir une direction.
     * @param labyrinthe Le labyrinthe.
     * @param nb_vies Nombre de vies restant au joueur.
     * @param personnage Personnage.
     */
    public static void refresh(Labyrinthe labyrinthe, int nb_vies, Personnage personnage) {

	Labyrinthe.effaceEcran();
	labyrinthe.affiche();
	message(nb_vies,personnage);

    }

    /**
     * Fonction permettant de faire effectuer une pause au programme.
     * @param millisecondes Duree de la pause en millisecondes.
     */
    public static void sleep(long millisecondes) {
    
        try {
            
	    Thread.sleep(millisecondes);
        }
        
	catch(InterruptedException e) {
            
	    System.out.println("Sleep interrompu");
        }
    
    }
    
}
	
