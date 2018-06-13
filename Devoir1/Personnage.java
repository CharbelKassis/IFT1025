/**
 * @author Harlouchet Iban et Kassis Charbel.
 * @since Fevrier 2015.
 * @version 1.0.
 */
public class Personnage {

    private int i;
    private int j;
    private int nb_vies;
    private Labyrinthe l;
   
    /**
     * Constructeur du personnage.
     * @param l Le labyrinthe du personnage.
     * @param i Le personnage est positionne sur la ligne i du labyrinthe.
     * @param j Le personnage est positionne sur la colonne j du labyrinthe.
     * @param nb_vies Nombre de vies dont dispose le personnage.
     */
    public Personnage(Labyrinthe l, int i, int j, int nb_vies)
    {
      this.i = i;
      this.j = j;
      this.l = l;
      this.nb_vies = nb_vies;
      
    }

    public int get_nb_vies()
    {
	return this.nb_vies;
    }

    public void set_nb_vies(int n)
    {
	this.nb_vies = n;
    }

    public int get_i_personnage()
    {
	return this.i;
    }

    public void set_i_personnage(int i)
    {
	this.i = i;
    }

    public int get_j_personnage()
    {
	return this.j;
    }

    public void set_j_personnage(int j)
    {
	this.j = j;
    }
    
    /** 
     * Deplace le personnage d'une case vers la droite.
     */
    
    public void aller_droite() {

	this.j++;
    }
    
    /** 
     * Deplace le personnage d'une case vers la gauche.
     */

    public void aller_gauche() {

	this.j--;

    }

    /** 
     * Deplace le personnage d'une case vers le bas.
     */
    
    public void aller_bas() {

	this.i++;

    }

    /** 
     * Deplace le personnage d'une case vers le haut.
     */
    
    public void aller_haut() {

	this.i--;

    }
}
