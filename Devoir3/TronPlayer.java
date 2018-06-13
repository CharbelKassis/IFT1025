import java.io.*;
import java.net.*;
import java.awt.geom.*;

public class TronPlayer {
    
    private String login;
    private String nomDeMachine;
    private int couleur;
    private Trace trace;
    private char direction;
    private boolean enVie;
    private Point tete;
    private Point2D.Double pointArene;

    public TronPlayer(String login, String nomDeMachine, int couleur, Point tete) {

	this.login = login;
	this.nomDeMachine = nomDeMachine;
	this.couleur = couleur;
	this.tete = tete;
	this.pointArene = null;
    }
    
    public TronPlayer(Socket s, String login, int couleur, Trace trace) {

	this.login = login;
	this.nomDeMachine = s.getInetAddress().getHostName();
	this.couleur = couleur;
	this.trace = trace;
	this.direction = genererDirectionAleatoire();
	this.enVie = true;

    }
    

    public Point2D.Double get_pointArene() {

	return this.pointArene;

    }

    public void set_pointArene(Point2D.Double pointArene) {

	this.pointArene = pointArene;

    }
    
    public Point get_tete() {

	return this.tete;

    }

    public String get_login() {

	return this.login;

    }

    public String get_nomDeMachine() {

	return this.nomDeMachine;

    }

    public int get_couleur() {

	return this.couleur;

    }

    public void set_couleur(int couleur) {

	this.couleur = couleur;

    }
    
    public Trace get_trace() {

	return this.trace;

    }

    public char get_direction() {

	return this.direction;

    }

    public boolean get_enVie() {

	return this.enVie;

    }

    public void set_direction(char d) {

	this.direction = d;

    }

    public void set_enVie(boolean v) {

	this.enVie = v;

    }

    public void set_trace(Trace trace) {
	
	  this.trace = trace;
    }

    public void set_tete(Point tete) {

	this.tete = tete;

    }

    public static char genererDirectionAleatoire() {
		
	return "EWSN".charAt(new java.util.Random().nextInt(4));

    }

}
