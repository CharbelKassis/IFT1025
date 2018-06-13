/**
 *@author Harlouchet Iban et Kassis Charbel
 *@version 1.0
 *@since Mars 2015
 */

/**
 * Represente un generateur de cartes de divers themes : couleur, mot (lettre et emotion), image (animal et galaxie).
 */
public class GenerateurDeCartesMultiple extends GenerateurDeCartes {

    private GenerateurDeCartes[] tabGenerateur;
    private int cartesUtilisees;

    /**
     * Constructeur de GenerateurDeCartesMultiple.
     * @param nomDeTheme : nom du theme.
     * @param tabGenerateur : Tableau contenant 5 GenerateurDeCartes correspondant à 5 themes distincts.
     */
    public GenerateurDeCartesMultiple(String nomDeTheme, GenerateurDeCartes[] tabGenerateur) {

	this.nom = nomDeTheme;
	this.tabGenerateur = tabGenerateur;
	this.cartesUtilisees = 0;
    }
    
    public Carte genereUneCarte() {
	
	int rdm = (int)(Math.random()*tabGenerateur.length);

	try {
	    
	    this.cartesUtilisees++;
	    return tabGenerateur[rdm].genereUneCarte();

	}

	catch(IndexOutOfBoundsException e) {
	    
	    if(this.cartesUtilisees <= this.nombreDeCartesDifferentes()) {

		this.cartesUtilisees--;
		return this.genereUneCarte();

	    }

	    else 

		throw new IndexOutOfBoundsException();
	}

	

	    
    }
  
    public int nombreDeCartesDifferentes() {
	
	int somme = 0;

	for(int i=0;i<tabGenerateur.length;i++)

	    somme+= tabGenerateur[i].nombreDeCartesDifferentes();

	return somme;
    }
    
}
