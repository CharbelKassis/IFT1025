import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TronServer {
    
    public static void main(String[] args) {
		
	ServerSocket serverSocket = null;
	int[] couleurs = {Color.BLUE.getRGB(),Color.CYAN.getRGB(),Color.GRAY.getRGB(),Color.LIGHT_GRAY.getRGB(),Color.GREEN.getRGB(),Color.MAGENTA.getRGB(),Color.ORANGE.getRGB(),Color.YELLOW.getRGB(),Color.PINK.getRGB(),Color.RED.getRGB()};
	PlayerConnection[] l = new PlayerConnection[couleurs.length];
	Point[] tetes = new Point[couleurs.length];
	int[] nbDeClients = {0};
	int port_serveur = 0;
	int tick_horloge = 0;
	int gridwidth = 0;
	int gridheight = 0;

	if(args.length != 4) {

	    //messageErreur();
	    System.exit(0);

	}

	try {

	    port_serveur = Integer.parseInt(args[0]);
	    tick_horloge = Integer.parseInt(args[1]);
	    gridwidth = Integer.parseInt(args[2]);
	    gridheight =  Integer.parseInt(args[3]);

	}

	catch(NumberFormatException e) {

	    //messageErreur();
	    System.exit(1);
	}
	
	try {
	    
	System.out.println("Nom de la machine du serveur: "+InetAddress.getLocalHost().getHostName());
	System.out.println("Numero de port du serveur: "+port_serveur);
	System.out.println("adresse local: "+InetAddress.getLocalHost().getHostAddress());

	}


	catch(UnknownHostException e) {

	    System.exit(1);
	}
	
	try{
	    
	    serverSocket = new ServerSocket(port_serveur);

	}

	catch(IOException e) {

	    System.exit(1);
	}

	accepterClients(nbDeClients,2,serverSocket,l,couleurs,tetes,gridwidth,gridheight);
	    
	final int tick_horloge_ = tick_horloge;
	final PlayerConnection[] l_ = l;

	ActionListener attendreClients = new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
		    
		    PlayerConnection[] vrai_l = genererVraiTab(l_);
		    TronHeartBeat demarrerJeu = new TronHeartBeat(tick_horloge_,vrai_l,l_);
		    envoyerInfoJoueurs(vrai_l);
		    demarrerJeu.start();
		    
		}

	    };
	    

	Timer debuterPartie = new Timer(1000,attendreClients);
	debuterPartie.setRepeats(false);
	debuterPartie.start();

	accepterClients(nbDeClients,couleurs.length,serverSocket,l,couleurs,tetes,gridwidth,gridheight);    
       
    }
    
    public static PlayerConnection[] genererVraiTab(PlayerConnection[] l) {

	int nbDeClients = 0;

	for(int i=0;l[i] != null;i++)

	    nbDeClients++;
		
	PlayerConnection[] vrai_l = new PlayerConnection[nbDeClients];
		    
	for(int i=0;i<vrai_l.length;i++)

	    vrai_l[i] = l[i];

	return vrai_l;

    }

    public static void envoyerInfoJoueurs(PlayerConnection[] l) {

	for(int i=0;i<l.length;i++)

	    l[i].info_joueurs(l);	

    }

    public static void accepterClients (int[] nbDeClients, int nbDeClientsMax, ServerSocket serverSocket, PlayerConnection[] l, int[] couleurs, Point[] tetes, int gridwidth, int gridheight) {

	while(nbDeClients[0] < nbDeClientsMax)

	    try{

	    Socket clientSocket = serverSocket.accept();
	    PlayerConnection pc = new PlayerConnection(clientSocket,couleurs,tetes,gridwidth,gridheight);
	    pc.start();
	    l[nbDeClients[0]] = pc;
	    nbDeClients[0]++;
	    
	}
	
	catch(IOException e) {
		
	    System.exit(1);
	}


    }


}
