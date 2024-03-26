import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * Classe repr�sentant le robot
 * 
 * @author D�partement TIC - ESIGELEC
 * @version 1.2
 */

public class Robot {

	/**
	 * image qui repr�sente le Robot
	 */
	private BufferedImage image;
	/**
	 * num�ro de la ligne courante du Robot (commence � 0)
	 */
	private int ligne;
	/**
	 * num�ro de la colonne courante du Robot (commence � 0)
	 */
	private int colonne;
	/**
	 * direction courante du Robot : "ouest", "est", "nord" ou "sud"
	 */
	private String direction;
	/**
	 * Terrain sur lequel se trouve le robot
	 */
	private Terrain terrain;
	/**
	 * Vitesse de d�placement du Robot (unit� : cases par seconde, ie m/s)
	 */
	private double vitesse = 2;
	/**
	 * Indique si le robot est d�truit ou non, un robot d�truit ne peut plus se
	 * d�placer
	 */
	private boolean robotDetruit = false;
	/**
	 * Indique la couleur de trac�
	 */
	private int trace = 0;
	
	/**
	 * La batterie du robot
	 */
	private double batterie = 100;
	/**
	 * Allow to know how many obstacle we have
	 */
	private int nbreObstacles = 0;

	/**
	 * Constructeur du Robot
	 * 
	 * @param ligne
	 *            ligne initiale du Robot (commence � 0)
	 * @param colonne
	 *            colonne initiale du Robot (commence � 0)
	 * @param direction
	 *            direction initiale du Robot (est, ouest, nord ou sud)
	 */
	public Robot(int ligne, int colonne, String direction) {
		// initialisation des attributs de la classe
		this.ligne = ligne;
		this.colonne = colonne;
		this.direction = direction;
		// initialisation de l'attribut image � partir d'un fichier de type
		// image repr�sentant le Robot � l'�cran

		try {
			image = ImageIO.read(new File(getCheminImageRobot()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Permet de d�ssiner le robot � l'�cran
	 * 
	 * @param g
	 *            objet Graphics sur lequel on dessine le robot
	 * @param largeurCase
	 *            largeur de la case sur laquelle on va dessiner le robot (en
	 *            pixel). L'image sera automatiquement redimmensionn�e.
	 * @param hauteurCase
	 *            hauteur de la case sur laquelle on va dessiner le robot (en
	 *            pixel) L'image sera automatiquement redimmensionn�e.
	 */
	public void dessiner(Graphics g, int largeurCase, int hauteurCase) {
		// on convertit l'objet Graphics en Graphics2D afin de pouvoir utiliser
		// des m�thodes plus pratiques
		Graphics2D g2 = (Graphics2D) g;
		// on trouve l'angle de rotation de l'image en s'appuyant sur la
		// direction du Robot
		double angleRotation = 0;
		if ("est".equals(direction))
			angleRotation = Math.PI / 2;
		else if ("sud".equals(direction))
			angleRotation = Math.PI;
		else if ("ouest".equals(direction))
			angleRotation = 3 * Math.PI / 2;

		// on fait pivoter l'image de angleRotation, le centre de rotation �tant
		// le centre de la case
		g2.rotate(angleRotation, largeurCase / 2, hauteurCase / 2);
		// on dessine l'image pivot�e sur la case dont on connait le Graphics
		g2.drawImage(image, 0, 0, largeurCase, hauteurCase, null);
		// on remet la rotation � 0
		g2.rotate(-angleRotation, largeurCase / 2, hauteurCase / 2);

	}

	/**
	 * permet d'avancer le robot droit devant lui
	 * 
	 * @return 1 si le robot a pu avancer, -1 si le robot n'a pas pu avancer �
	 *         cause d'un obstacle ou des limites du terrain, -2 si le robot n'a
	 *         pas pu avancer car il est d�truit, -3 si le robot n'a plus de
	 *         batterie
	 */
	public int avancer() {
		int retour = 1;

		// si le robot est d�truit on ne peut pas avancer
		if (robotDetruit)
			retour = -2;

		else
		// si on avance alors qu'il y a un obstacle devant alors on d�truit le
		// robot
		if (isObstacleDevant()) {

			detruireRobot();
			retour = -1;
		} else {
			this.batterie=this.batterie-1;
			// on peut faire avancer le robot suivant sa direction
			if ("nord".equals(direction)) {
				ligne--;

			} else if ("sud".equals(direction)) {
				ligne++;

			} else if ("ouest".equals(direction)) {
				colonne--;

			} else if ("est".equals(direction)) {
				colonne++;

			} else
				retour = -1;

			// on m�morise que la case est maintenant visit�e
			if (trace == 1) {
				terrain.getGrille()[ligne][colonne].setBackground(Color.RED);
				terrain.getGrille()[ligne][colonne].setVisitee(true);
			}
			if (trace == 2) {
				terrain.getGrille()[ligne][colonne].setBackground(Color.BLUE);
				terrain.getGrille()[ligne][colonne].setVisitee(true);
			}

			// on redessine le terrain
			terrain.repaint();

			// on effectue une petite pause fonction de la vitesse du robot
			try {
				Thread.sleep((int) (1000 / vitesse));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		return retour;
	}
	/**
	 * Allow to get the remaining percent of the batterie
	 * @return this.batterie
	 */
	public double getBatterie()
	{
		return this.batterie;
	}

	/**
	 * Permet de faire reculer le robot
	 * 
	 * @return 1 si le robot a pu reculer, -1 si le robot n'a pas pu reculer �
	 *         cause d'un obstacle ou des limites du terrain, -2 si le robot n'a
	 *         pas pu reculer car il est d�truit, -3 si le robot n'a plus de
	 *         batterie
	 */
	public int reculer() {
		int retour = 1;

		// si le robot est d�truit on ne peut pas avancer
		if (robotDetruit)
			retour = -2;
		else
		// si on recule alors qu'il y a un obstacle derriere alors on d�truit le
		// robot
		if (isObstacleDerriere()) {
			detruireRobot();
			retour = -1;
		}

		else {
			// on peut faire reculer le robot suivant sa direction oppos�e
			if ("sud".equals(direction)) {
				ligne--;
			} else if ("nord".equals(direction)) {
				ligne++;
			} else if ("est".equals(direction)) {
				colonne--;
			} else if ("ouest".equals(direction)) {
				colonne++;
			} else
				retour = -1;

			if (trace == 1) {
				terrain.getGrille()[ligne][colonne].setBackground(Color.RED);
				terrain.getGrille()[ligne][colonne].setVisitee(true);
			}
			if (trace == 2) {
				terrain.getGrille()[ligne][colonne].setBackground(Color.BLUE);
				terrain.getGrille()[ligne][colonne].setVisitee(true);
			}
		}
		// on redessine le terrain
		terrain.repaint();
		// on effectue une petite pause fonction de la vitesse du robot
		try {
			Thread.sleep((int) (1000 / vitesse));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return retour;
	}

	/**
	 * Permet de faire pivoter le robot sur sa droite
	 * 
	 * @return 1 si le robot a pu tourner correctement, -2 si le robot n'a pas
	 *         pu tourner car il est d�truit, -3 si le robot n'a plus de
	 *         batterie
	 */
	public int tournerDroite() {
		int retour = 1;

		if (robotDetruit)
			retour = -2;
		else {
			// on peut faire tourner le robot vers la droite
			this.batterie=this.batterie-0.5;
			if ("sud".equals(direction)) {
				direction = "ouest";
			} else if ("nord".equals(direction)) {
				direction = "est";
			} else if ("est".equals(direction)) {
				direction = "sud";
			} else if ("ouest".equals(direction)) {
				direction = "nord";
			}
		}
		// on redessine le terrain
		terrain.repaint();
		// on effectue une petite pause fonction de la vitesse du robot
		try {
			Thread.sleep((int) (1000 / vitesse));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return retour;
	}

	/**
	 * Permet de faire pivoter le robot sur sa droite
	 * 
	 * @return 1 si le robot a pu tourner correctement, -2 si le robot n'a pas
	 *         pu tourner car il est d�truit, -3 si le robot n'a plus de
	 *         batterie
	 */
	public int tournerGauche() {
		int retour = 1;

		if (robotDetruit)
			retour = -2;
		else {
			this.batterie=this.batterie-0.5;
			// on peut faire tourner le robot vers la gauche
			if ("sud".equals(direction)) {
				direction = "est";
			} else if ("nord".equals(direction)) {
				direction = "ouest";
			} else if ("est".equals(direction)) {
				direction = "nord";
			} else if ("ouest".equals(direction)) {
				direction = "sud";
			}
		}

		// on redessine le terrain
		terrain.repaint();
		// on effectue une petite pause fonction de la vitesse du robot
		try {
			Thread.sleep((int) (1000 / vitesse));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return retour;
	}

	/**
	 * Teste la pr�sence d'un obstacle devant le robot
	 * 
	 * @return true si un obstacle est pr�sent devant le robot (le contours du
	 *         terrain est consid�r� comme un obstacle); false si aucun obstacle
	 *         n'est pr�sent devant le robot
	 */

	public boolean isObstacleDevant() {

		// en fonction de la direction du robot, on teste s'il ne sort pas des
		// limites du terrain, et si la case devant lui n'est pas de type
		// Obstacle
		if ("nord".equals(direction)
				&& ligne > 0
				&& !(terrain.getGrille()[ligne - 1][colonne] instanceof Obstacle))
			return false;
		else if ("sud".equals(direction)
				&& ligne < terrain.getNbLignes() - 1
				&& !(terrain.getGrille()[ligne + 1][colonne] instanceof Obstacle))
			return false;
		else if ("ouest".equals(direction)
				&& colonne > 0
				&& !(terrain.getGrille()[ligne][colonne - 1] instanceof Obstacle))
			return false;
		else if ("est".equals(direction)
				&& colonne < terrain.getNbColonnes() - 1
				&& !(terrain.getGrille()[ligne][colonne + 1] instanceof Obstacle))
			return false;
		else
			return true;

	}

	/**
	 * M�thode qui permet de d�tecter un obstacle devant le robot en consommant
	 * la batterie
	 * 
	 * @return true si un obstacle est pr�sent devant le robot (le contours du
	 *         terrain est consid�r� comme un obstacle); false si aucun obstacle
	 *         n'est pr�sent devant le robot
	 */
	public boolean isObstacleDevantAvecConsommationBatterie() {
		// TODO � compl�ter
		if ("nord".equals(direction)
				&& ligne > 0
				&& !(terrain.getGrille()[ligne - 1][colonne] instanceof Obstacle))
			return false;
		else if ("sud".equals(direction)
				&& ligne < terrain.getNbLignes() - 1
				&& !(terrain.getGrille()[ligne + 1][colonne] instanceof Obstacle))
			return false;
		else if ("ouest".equals(direction)
				&& colonne > 0
				&& !(terrain.getGrille()[ligne][colonne - 1] instanceof Obstacle))
			return false;
		else if ("est".equals(direction)
				&& colonne < terrain.getNbColonnes() - 1
				&& !(terrain.getGrille()[ligne][colonne + 1] instanceof Obstacle))
			return false;
		else
		
			this.nbreObstacles++;
			batterie = batterie-0.25;
			/*if ("est".equals(direction)
					&& colonne < terrain.getNbColonnes() - 1
					&& (terrain.getGrille()[ligne][9] instanceof Obstacle))
			{
				this.nbreObstacles--;
			}
			if ("ouest".equals(direction)
					&& colonne > 0
					&& (terrain.getGrille()[ligne][0] instanceof Obstacle))
			{
				this.nbreObstacles--;
			}*/
			return true;
		
	}

	/**
	 * Teste la pr�sence d'un obstacle � l'arri�re le Robot
	 * 
	 * @return true si un obstacle est pr�sent derri�re le robot; false si aucun
	 *         obstacle n'est pr�sent derri�re le robot
	 */
	public boolean isObstacleDerriere() {

		// en fonction de la direction du robot, on teste s'il ne sort pas des
		// limites du terrain, et si la case derri�re lui n'est pas de type
		// Obstacle
		if ("sud".equals(direction)
				&& ligne > 0
				&& !(terrain.getGrille()[ligne - 1][colonne] instanceof Obstacle))
			return false;
		else if ("nord".equals(direction)
				&& ligne < terrain.getNbLignes() - 1
				&& !(terrain.getGrille()[ligne + 1][colonne] instanceof Obstacle))
			return false;
		else if ("est".equals(direction)
				&& colonne > 0
				&& !(terrain.getGrille()[ligne][colonne - 1] instanceof Obstacle))
			return false;
		else if ("ouest".equals(direction)
				&& colonne < terrain.getNbColonnes() - 1
				&& !(terrain.getGrille()[ligne][colonne + 1] instanceof Obstacle))
			return false;

		else
			return true;

	}

	/**
	 * M�thode qui permet de d�tecter un obstacle � l'arri�re du robot en
	 * consommant la batterie
	 * 
	 * @return true si un obstacle est pr�sent derri�re le robot (le contours du
	 *         terrain est consid�r� comme un obstacle); false si aucun obstacle
	 *         n'est pr�sent devant le robot
	 */
	public boolean isObstacleDerriereAvecConsommationBatterie() {
		// TODO � compl�ter
		return true;

	}

	/**
	 * Permet de d�truire le robot
	 */
	public void detruireRobot() {
		// on indique que le robot est d�truit
		robotDetruit = true;
		// on modifie son image pour voir une image de robot d�truit
		try {
			image = ImageIO.read(new File(getCheminImageRobotDetruit()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// on redessine le terrain
		terrain.repaint();
		System.out.println("Robot D�truit !");
	}

	/**
	 * Permet de savoir si le roboto se trouve sur une case victime
	 * 
	 * @return true si le robot se trouve sur une case victime; false si le
	 *         robot ne se trouve pas sur une case victime
	 */
	public boolean isSurVictime() {
		// on teste le type de la case du robot
		if (terrain.getGrille()[ligne][colonne] instanceof CaseVictime) {
			return true;
		} else
			return false;

	}

	/**
	 * Permet de sauver une victime, i.e. changer son �tat
	 * 
	 * @return 1 si la victime a �t� sauv�e; -1 si aucune victime sur la case
	 *         courante du robot
	 */
	public int sauverVictime() {
		// si la case courante est de type victime
		if (terrain.getGrille()[ligne][colonne] instanceof CaseVictime) {
			// on sauve la victime
			((CaseVictime) terrain.getGrille()[ligne][colonne]).sauverVictime();
			return 1;
		} else
			return -1;

	}

	/**
	 * getter de l'attribut vitesse
	 * 
	 * @return l'attribut vitesse
	 */
	public double getVitesse() {
		return vitesse;
	}

	/**
	 * setter de l'attribut vitesse
	 */
	public void setVitesse(double vitesse) {
		this.vitesse = vitesse;
	}

	/**
	 * getter de l'attribut ligne
	 * 
	 * @return l'attribut ligne
	 */
	public int getLigne() {
		return ligne;
	}

	/**
	 * getter de l'attribut colonne
	 * 
	 * @return l'attribut colonne
	 */
	public int getColonne() {
		return colonne;
	}

	/**
	 * getter de l'attribut direction
	 * 
	 * @return l'attribut direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * getter de l'attribut terrain
	 * 
	 * @return l'attribut terrain
	 */
	public Terrain getTerrain() {
		return terrain;
	}

	/**
	 * setter de l'attribut terrain
	 */
	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
	}

	/**
	 * M�thode permettant de modifier la couleur de tracer
	 * 
	 * @param trace
	 *            correspond au code de couleur : 1 pour le rouge, 2 pour le
	 *            bleu.
	 */
	public void tracer(int trace) {
		this.trace = trace;
		if (trace == 1) {
			terrain.getGrille()[ligne][colonne].setBackground(Color.RED);
			terrain.getGrille()[ligne][colonne].setVisitee(true);
		}
		if (trace == 2) {
			terrain.getGrille()[ligne][colonne].setBackground(Color.BLUE);
			terrain.getGrille()[ligne][colonne].setVisitee(true);
		}

	}

	/**
	 * M�thode permettant de d�tecter la gravit� de l'�tat d'une victime.
	 * 
	 * @return la gravit� de l'�tat de la victime entre 1 et 10 s'il y a une
	 *         victime sur la case; 0 s'il n'y a pas de victime.
	 */
	public int detecterGravite() {
		if (terrain.getGrille()[ligne][colonne].getClass().getName()
				.equals("CaseVictime"))
			return ((CaseVictime) terrain.getGrille()[ligne][colonne])
					.getGravite();
		else
			return 0;
	}

	/**
	 * Permet de r�cup�rer l'�tat de la victime une fois que le robot est sur la
	 * victime
	 * 
	 * @return l'�tat de la victime ou un message indiquant que le robot n'a pas
	 *         encore atteint la victime
	 */
	public String getEtatVictime() {
		// on teste le type de la case du robot
		if (terrain.getGrille()[ligne][colonne] instanceof CaseVictime) {
			return ((CaseVictime) terrain.getGrille()[ligne][colonne])
					.getEtatVictime();
		} else
			return "Le robot n'a pas encore atteint la victime";
	}
	/**
	 * 
	 */
	public void compterObstacles()
	{
		
		/*if(this.isObstacleDevantAvecConsommationBatterie())
		{
			this.nbreObstacles++;
		}*/
		System.out.println("Obstacles Detectees :"+this.nbreObstacles+"\nBatterie Restante :"+this.batterie+"\n");
		//JOptionPane.showMessageDialog(null,"Obstacles Detectees :"+this.nbreObstacles+"\nBatterie Restante :"+this.batterie+"\n" );
	}
	/**
	 * this method allow to charge the battery
	 */
	public void chargerBatterie()
	{
		if(this.direction.equals("est"))
			this.tournerGauche();
		else
			this.tournerDroite();
		while(this.colonne!=0 || this.ligne!=0)
		{
			if(this.isObstacleDevantAvecConsommationBatterie()==false)
			this.avancer();
			else
			{
				this.tournerGauche();
				if(this.isObstacleDevantAvecConsommationBatterie())
				{
					if(this.ligne==0)
					{
						this.tournerGauche();
						this.avancer();
						this.tournerDroite();
						this.avancer();
						this.tournerDroite();
					}
					if(this.colonne==0)
					{
						this.tournerGauche();
						this.tournerGauche();
						this.avancer();
						this.tournerGauche();
					}
				}
				else
				{
					this.avancer();
					this.tournerDroite();
				}
					
			}
			
		}
		this.batterie=100;
		System.out.println("Batterie rechargée");
	}
	
	/**
	 * @return le chemin vers l'image du robot
	 */
	protected String getCheminImageRobot() {
		return "./data/robot.png";
	}
	
	/**
	 * @return le chemin vers l'image du robot d�truit
	 */
	protected String getCheminImageRobotDetruit() {
		return "./data/robot_detruit.png";
	}

}