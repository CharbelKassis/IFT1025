import java.io.*;
import java.net.*;

public class PlayerConnection extends Thread  {
    
    private TronPlayer joueur;
    private char prochaineDir;
    private PrintWriter os;
    private BufferedReader is;
    private String login;
    private Point tete;
    private Point[] tetes;
    private int gridwidth;
    private int gridheight;
    private boolean mort;
    private Socket s;

    public PlayerConnection(Socket s, int[] c, Point[] tetes, int gridwidth, int gridheight) throws IOException { 
	
	this.s = s;
	this.gridwidth = gridwidth;
	this.gridheight = gridheight;
	this.tetes = tetes;
	this.mort = false;
	this.is = new BufferedReader(new InputStreamReader(s.getInputStream()));
	this.login = this.is.readLine();
	this.os = new PrintWriter(s.getOutputStream());
	this.os.println(""+this.gridwidth);
	this.os.println(""+this.gridheight);
	this.os.flush();
	System.out.println("Joueur "+this.login+" s'est connecte");

	int i = 0;

	while(c[i] == -1)

	    i++;

	int couleur = c[i];
	c[i] = -1;

	this.generer_tete();
		   	
	this.joueur = new TronPlayer(s,login,couleur,new Trace(gridheight,gridwidth,tete));	   		   
    
    }

    public void run() {
	
	char d = ' ';

	while(true) {

	    if(this.mort == false)
		
		try {

		    d = this.is.readLine().charAt(0);
	
		    if(d == 'N' || d == 'S' || d == 'E' || d == 'W') {

			this.prochaineDir = d;
			this.joueur.set_direction(this.prochaineDir);
		    }

		}

		catch(IOException e) {

		    System.err.println("IOException durant l'interaction avec le client:" + e);
		    return;
		}

		catch(IndexOutOfBoundsException e) {}
	    
		catch(NullPointerException e) {
		    
		    try {

		    this.mort = true;
		    os.close();
		    is.close();
		    s.close();

		    }
		    
		    catch(IOException e2) {}
		}
	    

	    
	    
	  
	}
	

    }

    public void info_joueurs (PlayerConnection[] l)  {// Methode appele avec TronServer
	
	for(int i=0;i<l.length;i++) {

	    os.println('+'+l[i].joueur.get_login()+'\n'+l[i].joueur.get_nomDeMachine()+'\n'+l[i].joueur.get_couleur()+'\n'+l[i].joueur.get_trace().get_tete().get_i()+'\n'+l[i].joueur.get_trace().get_tete().get_j());
	    os.flush();
	    
	}	
    }

    public char deplacement_joueur (PlayerConnection[] l) { // Methode appele avec TronHeartBeat
	
	// Generation du tableau des traces
	Trace[] t = new Trace[l.length];
	
	for(int i=0;i<l.length;i++) 
	    
	    t[i] = l[i].joueur.get_trace();


	// Deplacement du joueur 'this' et calcul des collisions en fonction des traces

	if(this.joueur.get_direction() == 'N' && !this.mort) {

	    if(this.joueur.get_trace().aller_haut(t) == null) {
	
		this.set_mort(true);
		
	    }
	}
	
	else if(this.joueur.get_direction() == 'S' && !this.mort) {

	    if(this.joueur.get_trace().aller_bas(t) == null) {

		this.set_mort(true);

	    }
	    
	}
	    
	else if(this.joueur.get_direction() == 'E' && !this.mort) {
	    
	    if(this.joueur.get_trace().aller_droite(t) == null) {
		    
		this.set_mort(true);

	    }

	}
	    
	else if(this.joueur.get_direction() == 'W' && !this.mort) {

	    if(this.joueur.get_trace().aller_gauche(t) == null) {

		this.set_mort(true);
		    
	    }
	    
	}

	else if(this.mort)

	    this.joueur.set_direction('X');
	
	return this.joueur.get_direction();
    }

    public void generer_tete() {

	boolean continuer = true;
	int j = 0;

	while(continuer) {
	    
	    continuer = false;

	    this.tete = new Point((int)(Math.random()*(this.gridheight-2))+1,(int)(Math.random()*(this.gridwidth-2))+1);
	    
	    for(j=0;j<this.tetes.length;j++) {
		
		try {

		    if(this.tete.equals(this.tetes[j])) {
			
			continuer = true;
			break;
		
		    }
		}
	    
		catch(NullPointerException e) {
		    
		    this.tetes[j] = tete;
		    break;

		}

	
	    }
	}

    }


    public PrintWriter get_os() {

	return this.os;

    }

    public BufferedReader get_is() {

	return this.is;

    }

    public TronPlayer get_joueur() {

	return this.joueur;

    }

    public Point[] get_tetes() {

	return this.tetes;

    }

    public void set_tetes(Point[] tetes) {

	this.tetes = tetes;

    }

    public Point get_tete() {

	return this.tete;

    }

    public void set_mort(Boolean b) {

	this.mort = b;

    }

    public Socket get_s() {

	return this.s;

    }
}
