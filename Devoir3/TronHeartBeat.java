
import javax.swing.*;
import java.awt.event.*;

public class TronHeartBeat extends Thread {
    
    private Timer tick_horloge_timer;
    private ActionListener methode_tick_horloge;
    private final TronHeartBeat self = this;
    private PlayerConnection[] vrai_l;
    private PlayerConnection[] l;
    private int tick_horloge;
    
    
    public TronHeartBeat(int tick_horloge, PlayerConnection[] vrai_l, PlayerConnection[] l) {

	this.vrai_l = vrai_l;
	
	this.l = l;
	
	this.tick_horloge = tick_horloge;
	
		this.methode_tick_horloge = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
		    
		    String message = "s";
		    char deplacement = ' ';
		    
		    //deplacer tous les joueurs
		    for(int i=0;i<self.vrai_l.length;i++) {

			deplacement = self.vrai_l[i].deplacement_joueur(self.vrai_l);
			message+=deplacement;
		    }
		    
		   
		    for(int i=0;i<self.vrai_l.length;i++) {

			self.vrai_l[i].get_os().println(message);
			self.vrai_l[i].get_os().flush();
		     
		    }
		    
		    if(nombreDeVivantsDansMessage(message) < 2) {
			
			try{

			    Thread.sleep(5000);

			}

			catch(InterruptedException exc) {}
		        
			for(int i=0;i<self.vrai_l.length;i++) {
			    
			    self.vrai_l[i].get_os().println('R');
			    self.vrai_l[i].get_os().flush();			
			}

			self.restart();
			
		    }

		  

			    
		}

	    };

	this.tick_horloge_timer = new Timer(this.tick_horloge,this.methode_tick_horloge);

	
    }

    public void run() {
	    
	try {

	    Thread.sleep(5000);

	}

	catch(InterruptedException e) {}

	this.tick_horloge_timer.start();
	    
    }
    
    public void restart() {

	this.tick_horloge_timer.stop();
	
	this.vrai_l = TronServer.genererVraiTab(l);
	Point[] resetTete = new Point[10];
	
	for(int i=0;i<vrai_l.length;i++) {
	    
	    if(!vrai_l[i].get_s().isClosed())
		
		vrai_l[i].set_mort(false);

	    TronPlayer joueur = vrai_l[i].get_joueur();
	    Trace trace = joueur.get_trace();
		
	    joueur.set_direction(TronPlayer.genererDirectionAleatoire());
	    
	    vrai_l[i].set_tetes(resetTete);
	    vrai_l[i].generer_tete();
	    joueur.set_tete(vrai_l[i].get_tete());
	    
	    joueur.set_trace(new Trace(trace.get_hauteur(),trace.get_largeur(),joueur.get_tete()));
	    

	}
	
	
	TronServer.envoyerInfoJoueurs(vrai_l);
	
	try{

	    Thread.sleep(5000);

	}

	catch(InterruptedException e) {}
	
	this.tick_horloge_timer.start();
    }

    public static int nombreDeVivantsDansMessage(String message) {
	
	int nombreDeVivants = message.length()-1;

	for (int i=1;i<message.length();i++)
	    
	    if (message.charAt(i) == 'X' )
		
		nombreDeVivants--;

	return nombreDeVivants ;
    }

    

}

