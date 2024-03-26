import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Classe repr�sentant la case contenant une victime
 * 
 * @author D�partement TIC - ESIGELEC
 * @version 1.0
 */
public class CaseVictime extends Case {

	/**
	 * num�ro de version de la classe
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * image qui repr�sente la victime
	 */
	private static BufferedImage imageVictime;

	/**
	 * Permet de pr�ciser l'�tat de la victime (saignement, asphyxie, fracture,
	 * arret cardiaque, sauvee)
	 */
	private String etatVictime;

	/**
	 * Contient la gravit� (entre 1 et 10) de l'�tat de la victime.
	 */
	private int gravite;

	/**
	 * Constructeur de la classe Victime
	 * 
	 * @param terrain
	 *            le terrain sur lequel se trouve la victime
	 * @param ligne
	 *            numero de la ligne o� se trouve la victime
	 * @param colonne
	 *            numero de la colonne o� se trouve la victime
	 */
	public CaseVictime(Terrain terrain, int ligne, int colonne) {
		// appel du constructeur de la classe m�re (Case)
		super(terrain, ligne, colonne);

		// l'�tat de la victime est g�n�r� al�atoirement
		Random rand = new Random();
		gravite = rand.nextInt(10) + 1;
		switch (rand.nextInt(4)) {

		case 0:
			etatVictime = "saignement";
			break;
		case 1:
			etatVictime = "asphyxie";
			break;
		case 2:
			etatVictime = "fracture";
			break;
		case 3:
			etatVictime = "arret cardiaque";
			break;
		}

		// on fixe la couleur de fond de la case de la victime
		this.setBackground(Color.ORANGE);

		// initialisation de l'attribut image � partir d'un fichier de type
		// image repr�sentant une victime � l'�cran
		if (imageVictime == null) {
			try {
				imageVictime = ImageIO.read(new File("./data/victime.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * red�finition de la m�thode paintComponent afin de pr�ciser comment une
	 * victime doit s'afficher � l'�cran
	 * 
	 * @param g
	 *            Objet Graphics associ� � la Case Victime
	 */
	protected void paintComponent(Graphics g) {
		// appel de la m�thode de dessin d'un JPanel normal
		super.paintComponent(g);
		// si la victime est sauv�e on modifie la couleur de fond de la case
		if ("sauvee".equals(etatVictime)) {
			this.setBackground(Color.GREEN);
			// on dessine l'image de la victime
			g.drawImage(imageVictime, 0, 0, this.getWidth(), this.getHeight(),
					null);
		} else {

			// on dessine l'image de la victime
			g.drawImage(imageVictime, 0, 0, this.getWidth(), this.getHeight(),
					null);
		}

	}

	/**
	 * Permet de m�moriser que la victime a �t� sauv�e
	 */
	public void sauverVictime() {

		etatVictime = "sauvee";
		System.out.println("Victime sauv�e !");
	}

	/**
	 * Getter de l'attribut etatVictime
	 * 
	 * @return etatVicitime
	 */
	public String getEtatVictime() {
		return etatVictime;
	}

	/**
	 * Getter de l'attribut gravite
	 * 
	 * @return gravite
	 */
	public int getGravite() {
		return gravite;
	}

}