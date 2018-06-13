import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class TronClient {

    public static void main(String[] args) {

	Socket socketClient = null;      
	String nomMachineServeur = null;
	int portServeur = 0;
	BufferedReader is = null;
	PrintWriter os = null;

	if (args.length != 2){

	    System.out.println("Nombre de parametres incorrect. \nUsage : java TronClient <nom_machine_serveur> <port_serveur>");
	    System.exit(0);

	}

	try {
	
	    nomMachineServeur = args[0]; 
	    portServeur = Integer.parseInt(args[1]);
	} 
	catch (NumberFormatException e){
	    
	    System.out.println("Nombre de parametres incorrect. \nUsage : java TronClient <nom_machine_serveur> <port_serveur>");
	    System.exit(1);
	}
		
        // Construction du socket client, de son is et de son os

	try {
	    
	    Scanner scanner = new Scanner(System.in);
	    socketClient = new Socket(nomMachineServeur, portServeur);
	    is = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
	    os = new PrintWriter(socketClient.getOutputStream());
	    System.out.print("Veuillez entrer votre login: ");
	    String login = scanner.nextLine();
	    os.println(login);
	    os.flush();
	    handleServerMessages(is,os,socketClient);
	}
	catch (IOException e){
	    System.err.println("IOException pour ouvrir le Socket ou ses streams : "+e);
	    System.exit(1);
	}
    } 
    
    public static void handleServerMessages(BufferedReader is, PrintWriter os, Socket socket) throws IOException {

	int numeroDeJoueur = 0;
	TronPlayer[] joueurs = new TronPlayer[10];
	int gridwidth = 0;
	int gridheight = 0;
	PlayerConnectionClient pcc = null;
	boolean debut = true;

	while(true) {
	    
	   
	    String message = is.readLine();
	    
	    try {

		if(message != null && message.charAt(0) == '+') {

		    String login = message.substring(1,message.length());
		    System.out.println('+'+login);
		    String nomDeMachine = is.readLine();
		    System.out.println(nomDeMachine);
		    int couleur = Integer.parseInt(is.readLine());
		    System.out.println(couleur);
		    Point tete = new Point(Integer.parseInt(is.readLine()),Integer.parseInt(is.readLine()));
		    System.out.println(tete.get_i());
		    System.out.println(tete.get_j());
		    TronPlayer j = new TronPlayer(login,nomDeMachine,couleur,tete);
		    joueurs[numeroDeJoueur] = j;
		    numeroDeJoueur++;
		    
		}

		else if(message != null && message.charAt(0) == 's') {
		    
		    if(debut) {
			
			pcc = new PlayerConnectionClient(socket,joueurs,gridwidth,gridheight);
			debut = false;

		    }
			
		    System.out.println(message);
		    
		    for(int i=0;i<joueurs.length && joueurs[i] != null;i++)
		    //if(message.charAt(i+1) == 'X' && !(pcc.get_t()[i].getText().substring(0,"<html><strike>".length()).equals("<html><strike>"))) {
			    
		    if(message.charAt(i+1) == 'X')
			//String nom = pcc.get_t()[i].getText();
			//String nomBarre = "<html><strike>"+nom+"</strike></html>";
			
			//pcc.get_t()[i].setText(nomBarre);
				
			pcc.get_t()[i].setForeground(Color.WHITE);
			    
		    if (nombreDeVivantsDansMessage(message) >= 2)
			    
			pcc.get_a().set_message(message.substring(1,message.length()));
			  
		}
		
		else if(message.charAt(0) == 'R') {
		    
		    debut = true;
		    System.out.println(message);
		    numeroDeJoueur = 0;
		    pcc.get_f().setVisible(false);
		    pcc = null;
	
		}

		else {

		    gridwidth = Integer.parseInt(message);
		    System.out.println("gridwidth = "+gridwidth);
		    gridheight = Integer.parseInt(is.readLine());
		    System.out.println("gridheight = "+gridheight);
		}

	    }

	    catch(Exception e) {

	    }

	}

    

    }

    public static int nombreDeVivantsDansMessage(String message) {
	
	int nombreDeVivants = message.length()-1;

	for (int i=1;i<message.length();i++)
	    
	    if (message.charAt(i) == 'X' )
		
		nombreDeVivants--;

	return nombreDeVivants ;
    }



}
