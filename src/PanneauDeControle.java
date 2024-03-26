import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe repr�sentant le panneau de contr�le du simulateur
 * 
 * @author D�partement TIC - ESIGELEC
 * @version 1.0
 */
public class PanneauDeControle extends JPanel implements ActionListener {
	/**
	 * num�ro de version de la classe
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Terrain associ� au panneau de contr�le
	 */
	Terrain terrain;
	/**
	 * bouton qui permet de faire avancer le robot
	 */
	JButton boutonAvancer;
	/**
	 * bouton qui permet de faire reculer le robot
	 */
	JButton boutonReculer;
	/**
	 * bouton qui permet de faire tourner � droite le robot
	 */
	JButton boutonTournerDroite;
	/**
	 * bouton qui permet de faire tourner � gauche le robot
	 */
	JButton boutonTournerGauche;
	/**
	 * bouton qui permet de d�truire le robot
	 */
	JButton boutonDetruireRobot;
	/**
	 * bouton qui permet de tester la pr�sence d'un obstacle devant le robot
	 */
	JButton boutonIsObstacleDevant;
	/**
	 * bouton qui permet de tester la pr�sence d'un obstacle derri�re le robot
	 */
	JButton boutonIsObstacleDerriere;
	/**
	 * bouton qui permet de tester la pr�sence d'une victime sur la case du
	 * robot
	 */
	JButton boutonIsSurVictime;
	/**
	 * JLabel contenant le titre du panneau de contr�le
	 */
	JLabel titre;

	/**
	 * Constructeur de la classe PanneauDeContr�le
	 * 
	 * @param t
	 *            le terrain associ� au panneau de contr�le
	 */
	public PanneauDeControle(Terrain terrain) {
		// on initialise le terrain associ� au panneau de contr�le
		this.terrain = terrain;
		// on fixe la couleur de fond du panneau de contr�le
		this.setBackground(new Color(118, 200, 34));
		// on d�sactive le gestionnaire de placement pour positionner les
		// composants au pixel pr�s
		this.setLayout(null);
		// on instancie tous les composants du panneau de contr�le
		boutonAvancer = new JButton("avancer()");
		boutonReculer = new JButton("reculer()");
		boutonTournerDroite = new JButton("tournerDroite()");
		boutonTournerGauche = new JButton("tournerGauche()");
		boutonDetruireRobot = new JButton("detruireRobot()");
		boutonIsObstacleDevant = new JButton("isObstacleDevant()");
		boutonIsObstacleDerriere = new JButton("isObstacleDerriere()");
		boutonIsSurVictime = new JButton("isSurVictime()");
		titre = new JLabel("Panneau de contr�le du robot :", JLabel.CENTER);

		// on fixe les positions et tailles des composants
		boutonAvancer.setBounds(150, 100, 100, 50);
		boutonReculer.setBounds(150, 200, 100, 50);
		boutonTournerGauche.setBounds(10, 150, 140, 50);
		boutonTournerDroite.setBounds(250, 150, 140, 50);
		boutonDetruireRobot.setBounds(250, 350, 140, 30);
		boutonIsSurVictime.setBounds(10, 350, 140, 30);
		boutonIsObstacleDevant.setBounds(125, 60, 150, 30);
		boutonIsObstacleDerriere.setBounds(125, 260, 150, 30);
		titre.setBounds(10, 10, 370, 30);

		// on modifie les couleurs des composants
		boutonAvancer.setBackground(Color.ORANGE);
		boutonReculer.setBackground(Color.ORANGE);
		boutonTournerGauche.setBackground(Color.ORANGE);
		boutonTournerDroite.setBackground(Color.ORANGE);
		boutonIsObstacleDevant.setBackground(new Color(255, 255, 0));
		boutonIsObstacleDerriere.setBackground(new Color(255, 255, 0));
		boutonDetruireRobot.setBackground(Color.RED);
		boutonIsSurVictime.setBackground(Color.RED);
		boutonIsSurVictime.setForeground(Color.WHITE);
		boutonDetruireRobot.setForeground(Color.WHITE);
		titre.setBackground(Color.RED);
		titre.setForeground(Color.WHITE);

		// on ajoute les �couteurs (listeners) sur les boutons
		boutonAvancer.addActionListener(this);
		boutonReculer.addActionListener(this);
		boutonTournerDroite.addActionListener(this);
		boutonTournerGauche.addActionListener(this);
		boutonDetruireRobot.addActionListener(this);
		boutonIsObstacleDevant.addActionListener(this);
		boutonIsObstacleDerriere.addActionListener(this);
		boutonIsSurVictime.addActionListener(this);

		// on ajoute les composants sur le panneau de contr�le
		this.add(boutonAvancer);
		this.add(boutonReculer);
		this.add(boutonTournerDroite);
		this.add(boutonTournerGauche);
		this.add(boutonDetruireRobot);
		this.add(boutonIsObstacleDevant);
		this.add(boutonIsObstacleDerriere);
		this.add(boutonIsSurVictime);
		this.add(titre);

	}

	@Override
	/**
	 * M�thode appel�e lorsque l'utilisateur clique sur un bouton (poss�dant un listener)
	 */
	public void actionPerformed(ActionEvent ae) {

		// test des boutons cliqu�s et appels des m�thodes concern�es
		// getSource() permet de connaitre le composant � l'origine de
		// l'�v�nement (du clic)

		if (ae.getSource() == boutonAvancer)
			terrain.getRobot().avancer();
		else if (ae.getSource() == boutonReculer)
			terrain.getRobot().reculer();
		else if (ae.getSource() == boutonTournerDroite)
			terrain.getRobot().tournerDroite();
		else if (ae.getSource() == boutonTournerGauche)
			terrain.getRobot().tournerGauche();
		else if (ae.getSource() == boutonDetruireRobot)
			terrain.getRobot().detruireRobot();
		else if (ae.getSource() == boutonIsObstacleDevant)
			System.out.println("isObstacleDevant : "
					+ terrain.getRobot().isObstacleDevant());
		else if (ae.getSource() == boutonIsObstacleDerriere)
			System.out.println("isObstacleDerriere : "
					+ terrain.getRobot().isObstacleDerriere());
		else if (ae.getSource() == boutonIsSurVictime)
			System.out.println("isSurVictime : "
					+ terrain.getRobot().isSurVictime());

	}

}