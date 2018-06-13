import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class PlayerConnectionClient extends KeyAdapter{

    private static final int j0_FENETRE = 0;
    private static final int i0_FENETRE = 200;
    private static final int LARGEUR_FENETRE = 1000;
    private static final int HAUTEUR_FENETRE = 600;
    private TronPlayer[] joueurs;
    private JAreneTron a;
    private JFrame f;
    private JPanel zoneText;
    private JLabel[] t;
    private PrintWriter os;
    
	
    public PlayerConnectionClient(Socket s, TronPlayer[] joueurs, int gridw, int gridh) throws IOException{
	
	this.os = new PrintWriter(s.getOutputStream());
	this.joueurs = joueurs;

	this.a = new JAreneTron(joueurs,gridw,gridh);
	this.f = new JFrame("Tron par Charbel Kassis et Iban Harlouchet");
	this.t = new JLabel[10];
	this.zoneText = new JPanel();
	this.zoneText.setLayout(new BoxLayout(this.zoneText,BoxLayout.Y_AXIS));
	this.initialiserZoneText();
	this.genererJLabel();
	this.genererZoneText();
	this.f.add(this.a,BorderLayout.CENTER);
	this.f.add(this.zoneText,BorderLayout.EAST);
	this.f.setLocation(i0_FENETRE,j0_FENETRE);
	this.f.setSize(LARGEUR_FENETRE,HAUTEUR_FENETRE);
	this.f.setResizable(false);
	this.f.setFocusable(true);

	this.f.addWindowFocusListener(new WindowAdapter() {

		public void windowGainedFocus(WindowEvent e) {

		    a.requestFocusInWindow();
		}
	    });
	
	this.f.addKeyListener(this);
	this.a.addKeyListener(this);
	this.zoneText.addKeyListener(this);
	this.f.getContentPane().setBackground(Color.BLACK);
	this.f.setVisible(true);

    }

    public JAreneTron get_a() {

	return this.a;

    }

    public void keyTyped(KeyEvent e) {

	int source = e.getKeyCode();
	//System.out.println(source);
	char message = 'X';
	
	if(source == KeyEvent.VK_UP)

	    message = 'N';

	else if(source == KeyEvent.VK_DOWN)

	    message = 'S';

	else if(source == KeyEvent.VK_LEFT)

	    message = 'W';

	else if(source == KeyEvent.VK_RIGHT)

	    message = 'E';

	if(message != 'X') {

	    System.out.println(message);
	    os.println(""+message);
	    os.flush();	

	}

    }

       
    public void keyPressed(KeyEvent e) {
 
	this.keyTyped(e);
    }

    public JFrame get_f() {

	return this.f;

    }

    public JLabel[] get_t() {

	return this.t;

    }

    public JPanel get_zoneText() {

	return this.zoneText;

    }

    public void genererJLabel() {

	for(int i=0;i<joueurs.length && joueurs[i] != null;i++)

	    this.t[i].setText(this.joueurs[i].get_login() + "@" + this.joueurs[i].get_nomDeMachine());

    }
    
    public void genererZoneText() {
	
	for(int i=0;i<10;i++)
	    
	    this.zoneText.add(this.t[i]);
	    
	
	for(int i=0;i<this.joueurs.length && this.joueurs[i] != null;i++)

	    this.t[i].setForeground(Color.decode(""+this.joueurs[i].get_couleur()));

    }

    public void initialiserZoneText() {

	for(int i=0;i<10;i++)

	    this.t[i] = new JLabel("");

    }

	
   
}
