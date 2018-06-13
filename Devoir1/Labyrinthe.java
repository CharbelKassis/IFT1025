/**
 * @author Harlouchet Iban et Kassis Charbel.
 * @since Fevrier 2015.
 * @version 1.0.
 */
public class Labyrinthe {

    private char[][] tableau;
    
    /**
     * Une case du labyrinthe mesure LMURET caracteres en largeur et HMURET caracteres en hauteur.
     */
    private static final int LMURET = 8;
    private static final int HMURET = 4;
    
    
    /**
     * Constructeur d'un labyrinthe sans murets comprenant h lignes et w colonnes, ie instanciation d'un tableau.
     * @param h Hauteur du labyrinthe en nombre de cases.
     * @param w Largeur du labyrinthe en nombre de cases.
     * @see #creeTableau(int, int).
     */
    public Labyrinthe(int h, int w) {

	this.creeTableau(h*HMURET+1,w*LMURET+1); 
    }

    public int get_hauteur_labyrinthe() {

	return (this.tableau.length-1)/HMURET;
    }

    public int get_largeur_labyrinthe() {

	return (this.tableau[0].length-1)/LMURET;
    }

    /**
     * Constructeur d'une copie du labyrinthe l tel qu'il est au moment de l'appel du constructeur, ie copie du tableau tel qu'il est au moment de l'appel.
     * @param l Labyrinthe a copier.
     * @see #creeTableau(int, int).
     */
    public Labyrinthe(Labyrinthe l) {

	this.creeTableau(l.tableau.length,l.tableau[0].length);

	for (int i=0;i<this.tableau.length;i++)

	    for (int j=0;j<this.tableau[i].length;j++)

		this.tableau[i][j] = l.tableau[i][j];

    }

    /**
     * Cree un tableau de caracteres de dimension 2 rempli de ' '.
     * @param hauteur Hauteur du tableau.
     * @param largeur Largeur du tableau.
     */
    public void creeTableau(int hauteur, int largeur)  {

	this.tableau = new char[hauteur][largeur];

	this.effaceTableau();  

    }

    /**
     * Efface le tableau de caracteres de dimension 2.
     */
    public void effaceTableau() {

	for (int i=0;i<this.tableau.length;i++)

	    for (int j=0;j<this.tableau[0].length;j++)

		this.tableau[i][j] = ' ';
    }

    /** 
     * Dessine un mur d'enceinte dans le tableau.
     */
    public void dessineMurdEnceinte() {

	for (int j=0;j<this.tableau[0].length;j++) {
 
	    this.tableau[0][j] = '-';
	    this.tableau[this.tableau.length-1][j] = '-';
	
	}

	for (int i=0;i<this.tableau.length;i++) {

	    this.tableau[i][0] = '|';
	    this.tableau[i][this.tableau[0].length-1] = '|';

	}

	this.tableau[0][0] = '+';
	this.tableau[0][this.tableau[0].length-1] = '+';
	this.tableau[this.tableau.length-1][0] = '+';
	this.tableau[this.tableau.length-1][this.tableau[0].length-1] = '+';
	    
    }

    /**
     * Dessine l'ouverture de l'enceinte en bout de la ieme ligne du labyrinthe.
     * @param i Ordonnee de la ligne du labyrinthe.
     */
    public void dessineOuverture(int i) 
    {
	int position_i = HMURET*i+1;

	for (int k=0;k<HMURET-1;k++)
	   
	    this.tableau[position_i+k][this.tableau[0].length-1] = ' ';
    }
    
    /**
     * Insere dans le tableau un muret vertical place a gauche de la case d'indice i, j du labyrinthe.
     * @param i Abscisse de la case.
     * @param j Ordonnee de la case.
     */
    public void dessineMuretVertical(int i,int j) 
    {
	int position_i = HMURET*i+1;
	int position_j = LMURET*j;

	for (int k=0;k<HMURET-1;k++) 

	    this.tableau[position_i+k][position_j] = '|';
    }

    /**
     * Insere dans le tableau un muret horizontal en haut de la case d'indice i, j du labyrinthe.
     * @param i Abscisse de la case.
     * @param j Ordonnees de la case.
     */
    public void dessineMuretHorizontal(int i,int j) 
    {
	int position_i = HMURET*i;
	int position_j = LMURET*j+1;

	for (int k=0;k<LMURET-1;k++)

	    this.tableau[position_i][position_j+k] = '-';
    }

    /**
     * Teste l'existence d'un muret vertical a droite de la case d'indice i,j du labyrinthe.
     * @param i Abscisse de la case.
     * @param j Ordonnee de la case.
     * @return Retourne vrai si le muret existe, faux sinon.
     */
    public boolean muretVerticalExiste(int i,int j) 
    {
	return this.tableau[HMURET*i+1][LMURET*j] == '|';
    }
    
    /**
     * Teste l'existence du muret horizontal en haut de la case d'indice i,j du labyrinthe.
     * @param i Abscisse de la case.
     * @param j Ordonnee de la case.
     * @return Retourne vrai si le muret existe, faux sinon.
     */
    public boolean muretHorizontalExiste(int i,int j) 
    {
	return this.tableau[HMURET*i][LMURET*j+1] == '-';
    }
    
    /** 
     * Insere le symbole '@' dans le tableau, ie dessine un personnage dans le labyrinthe.
     * @param p Personnage.
     */
    public void dessinePersonnage(Personnage p)
    {
	int i=p.get_i_personnage();
	int j=p.get_j_personnage();
	this.tableau[HMURET*i+HMURET/2][LMURET*j+LMURET/2]='@';
    }
    
    /**
     * Efface le symbole '@' dans le tableau, ie efface le personnage dans le labyrinthe.
     * @param p Personnage.
     */
    public void effacePersonnage(Personnage p)
    {
       	int i=p.get_i_personnage();
       	int j=p.get_j_personnage();
       	this.tableau[HMURET*i+HMURET/2][LMURET*j+LMURET/2]=' ';
    }

    /** 
     * Efface l'ecran, ie imprime 200 lignes blanches sur la console.
     */
    public static void effaceEcran()
    {
	for (int i=0;i<200;i++)
	   
	   System.out.println();
    }
	
    /**
     * Imprime le tableau a l'ecran, ie affiche le labyrinthe.
     */
    public void affiche()
    {
       String Chaine = "";
	
       for (int i=0;i<this.tableau.length;i++)
	  for (int j=0;j<this.tableau[0].length; j++)
	  {
	     Chaine = Chaine+this.tableau[i][j];
	  }

       System.out.println();
	
       for (int i=0;i<this.tableau.length;i++)
       {
	  System.out.println(Chaine.substring(i*tableau[0].length, (i+1)*tableau[0].length));
       }
       
       System.out.println();
    }

    /**
     * Construit les murets du labyrinthe aleatoirement. Un muret, vertical ou horizontal, est construit si le tirage aleatoire lui attribue une valeur superieure a 'densite'.
     * @param densite Parametre compris entre 0 inclus et 1 exclu, et definissant le seuil d'existence des murets du labyrinthe.
     */
    public void construitLabyrintheAleatoire(double densite)
    {
	int I = get_hauteur_labyrinthe();
	int J = get_largeur_labyrinthe();

    for (int i=0;i<I;i++)
	{
	for (int j=1;j<J;j++)
	    {
		if (Math.random() < densite)
		    this.dessineMuretVertical(i,j); 
	    }
	}

    for (int i=1;i<I;i++)
	{
	for (int j=0;j<J;j++)
	    {
		if (Math.random() < densite)
		    this.dessineMuretHorizontal(i,j);  
	    }
	}	
    }
		
}
	
