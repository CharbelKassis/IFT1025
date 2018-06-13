public class Trace {

    private Point[][] tab;
    private int hauteur;
    private int largeur;
    private Point tete;
    
    public Trace(int hauteur, int largeur, Point tete) {
	
	this.hauteur = hauteur;
	this.largeur = largeur;
	this.tab = new Point[hauteur][largeur];
	this.tab[tete.get_i()][tete.get_j()] = tete;
	this.tete = tete;
	
	for(int i=0;i<hauteur;i++) {

	    this.tab[i][0] = new Point(i,0);
	    this.tab[i][largeur-1] = new Point(i,largeur-1);
	}

	for(int j=1;j<largeur-1;j++) {

	    this.tab[0][j] = new Point(0,j);
	    this.tab[hauteur-1][j] = new Point(hauteur-1,j);
	}

    }
    
    public int get_hauteur() {

	return this.hauteur;

    }

    public int get_largeur() {

	return this.largeur;

    }

    public Point[][] get_tab() {

	return this.tab;

    }

    public Point get_tete() {

	return this.tete;

    }
    
    public Point add(Point p, Trace[] traces) {

	if(!this.collision(p,traces)) {
	    
	    this.tab[p.get_i()][p.get_j()] = p;
	    this.tete = p;
	    return p;
	}

	return null;
	
    }

    public Point aller_droite(Trace[] traces) {
		
	return this.add(new Point(tete.get_i(),tete.get_j()+1),traces);

    }

     public Point aller_gauche(Trace[] traces) {
		
	 return this.add(new Point(tete.get_i(),tete.get_j()-1),traces);

    }

     public Point aller_haut(Trace[] traces) {
		
	 return this.add(new Point(tete.get_i()-1,tete.get_j()),traces);

    }

     public Point aller_bas(Trace[] traces) {
		
	 return this.add(new Point(tete.get_i()+1,tete.get_j()),traces);

    }

    public Boolean collision(Point p,Trace[] traces) {

	for(int i=0;i<traces.length;i++)

 	    if(traces[i].get_tab()[p.get_i()][p.get_j()] != null)

		return true;

	return false;
	
    }
}
