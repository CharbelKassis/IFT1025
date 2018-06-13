import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class JAreneTron extends JComponent {

    TronPlayer[] joueurs;
    int gridw;
    int gridh;
    String message;
    String vieuxMessage;
    Rectangle2D.Double[] rectangles;
      
    // Constructeur
    public JAreneTron(TronPlayer[] joueurs, int gridw, int gridh){
	
	this.joueurs = joueurs;
	this.gridw = gridw;
	this.gridh = gridh;
	this.message = null;
	this.vieuxMessage = null;
	this.rectangles = new Rectangle2D.Double[10];
	this.setFocusable(true);
    }

    public void paintComponent(Graphics g){

	Graphics2D g2 = (Graphics2D)g;
	double O1 = 1.0/100*this.getWidth();
	double O2 = 1.0/100*this.getHeight();
	double largeur = 98.0/100*this.getWidth();
	double hauteur = 98.0/100*this.getHeight();

	// Dessin de l'arene reelle
	
	g2.setColor(Color.WHITE);
	 
	Point2D.Double pointA = new Point2D.Double(O1, O2);
	Point2D.Double pointB = new Point2D.Double(O1+largeur, O2);
	Point2D.Double pointC = new Point2D.Double(O1+largeur, O2+hauteur);
	Point2D.Double pointD = new Point2D.Double(O1, O2+hauteur);
		 
	g2.draw(new Line2D.Double(pointA, pointB));
	g2.draw(new Line2D.Double(pointB, pointC));
	g2.draw(new Line2D.Double(pointC, pointD));
	g2.draw(new Line2D.Double(pointD, pointA));
	
	for(int i=0;i<this.joueurs.length && this.joueurs[i] != null ;i++) {
		
	    Color couleur = Color.decode(String.valueOf(this.joueurs[i].get_couleur()));
	    g2.setColor(couleur);

	    if(rectangles[i] == null) {
		    
		Point2D.Double tetePoint = this.pointArene(this.joueurs[i].get_tete());
		this.rectangles[i] = this.petitCarreArene(tetePoint,g2);
		this.joueurs[i].set_pointArene(tetePoint);
				
	    }

	    else

		g2.fill(rectangles[i]);

	}

    } // Fin paintComponent

    public Point2D.Double pointArene(Point tete){

	//Parametres arene virtuelle
	double i = tete.get_i();
	double j = tete.get_j();
	//Parametres arene reelle
	double O1 = 1.0/100*this.getWidth();
	double O2 = 1.0/100*this.getHeight();
	double largeur = 98.0/100*this.getWidth();
	double hauteur = 98.0/100*this.getHeight();
	
	Point2D.Double point = new Point2D.Double(O1+j/(gridw-1)*largeur,O2 +i/(gridh-1)*hauteur);

        return point;

    }

    public Rectangle2D.Double petitCarreArene(Point2D.Double pointArene, Graphics2D g2){

	// Parametres de petitCarreArene
	int min = Math.min(this.getWidth(), this.getHeight());
	double PROP_CARRE = 1.0/120;
	double cote = min*PROP_CARRE;
	 
	Rectangle2D.Double petitCarre = new Rectangle2D.Double(pointArene.getX()-cote/2, pointArene.getY()-cote/2, cote, cote);
	g2.fill(petitCarre);
	return petitCarre;

    }
    
    public Point2D.Double tracerVersN(Point2D.Double point1Arene, Graphics2D g2){
	//Parametres arene reelle
	double hauteur = 98.0/100*this.getHeight();

	Point2D.Double point2Arene = new Point2D.Double(point1Arene.getX(), point1Arene.getY()- hauteur/(gridh-1));	
	g2.draw(new Line2D.Double(point1Arene, point2Arene));
	return point2Arene;

    }

    public Point2D.Double tracerVersS(Point2D.Double point1Arene, Graphics2D g2){
	//Parametres arene reelle
	double hauteur = 98.0/100*this.getHeight();

	Point2D.Double point2Arene = new Point2D.Double(point1Arene.getX(), point1Arene.getY()+ hauteur/(gridh-1));	
	g2.draw(new Line2D.Double(point1Arene, point2Arene));
	return point2Arene;

    }

    public Point2D.Double tracerVersE(Point2D.Double point1Arene, Graphics2D g2){
	//Parametres arene reelle
	double largeur = 98.0/100*this.getWidth();

	Point2D.Double point2Arene = new Point2D.Double(point1Arene.getX()+largeur/(gridw-1), point1Arene.getY());	
	g2.draw(new Line2D.Double(point1Arene, point2Arene));
	return point2Arene;

    }

    public Point2D.Double tracerVersW(Point2D.Double point1Arene, Graphics2D g2){
	//Parametres arene reelle
	double largeur = 98.0/100*this.getWidth();

	Point2D.Double point2Arene = new Point2D.Double(point1Arene.getX()-largeur/(gridw-1), point1Arene.getY());	
	g2.draw(new Line2D.Double(point1Arene, point2Arene));
	return point2Arene;

    }


    public void reset() {

	this.message = null;
	this.getGraphics().setColor(Color.BLACK);
	this.getGraphics().drawRect(0,0,this.getWidth(),this.getHeight());    

    }

    public void update(Graphics g) {

	this.paint(g);

    }
    
    public void set_message(String message) {
	
	this.message = message;

	Graphics2D g2 = (Graphics2D) this.getGraphics();
	
	for(int i=0; i<this.joueurs.length && this.joueurs[i] != null && this.message != null ;i++) {

	    Color couleur = Color.decode(String.valueOf(this.joueurs[i].get_couleur()));
	    g2.setColor(couleur);
		
	    if(this.message.charAt(i) == 'N'){

		this.joueurs[i].set_pointArene(tracerVersN(this.joueurs[i].get_pointArene(),g2));
		System.out.println("toto");

	    } else if(this.message.charAt(i) == 'S'){

		this.joueurs[i].set_pointArene(tracerVersS(this.joueurs[i].get_pointArene(),g2));
		System.out.println("toto");

	    } else if(this.message.charAt(i) == 'E'){

		this.joueurs[i].set_pointArene(tracerVersE(this.joueurs[i].get_pointArene(),g2));
		System.out.println("toto");

	    } else if(this.message.charAt(i) == 'W'){

		this.joueurs[i].set_pointArene(tracerVersW(this.joueurs[i].get_pointArene(),g2));
		System.out.println("toto");
	    }
	}


   }


} 
