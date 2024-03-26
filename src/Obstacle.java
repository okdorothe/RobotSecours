import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Classe repr�sentant une case contenant un mur (obstacle)
 * 
 * @author D�partement TIC - ESIGELEC
 * @version 1.0
 */
public class Obstacle extends Case {

	/**
	 * num�ro de version de la classe
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * image qui repr�sente un obstacle
	 */
	private static BufferedImage image;

	/**
	 * Constructeur de la classe Obstacle
	 * 
	 * @param t
	 *            le terrain sur lequel se trouve l'obstacle
	 * @param ligne
	 *            numero de la ligne de l'obstacle
	 * @param colonne
	 *            numero de la colonne de l'obstacle
	 */
	public Obstacle(Terrain t, int ligne, int colonne) {
		// appel du constructeur de la classe m�re (Case)
		super(t, ligne, colonne);

		// initialisation de l'attribut image � partir d'un fichier de type
		// image repr�sentant un obstacle � l'�cran
		if (image == null) {
			try {
				image = ImageIO.read(new File("./data/obstacle.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * red�finition de la m�thode paintComponent afin de pr�ciser comment un
	 * obstacle doit s'afficher
	 * 
	 * @param g
	 *            Objet Graphics associ� � la Case
	 */
	protected void paintComponent(Graphics g) {
		// appel de la m�thode de dessin d'un JPanel normal
		super.paintComponent(g);
		// on ajoute le dessin de l'image de l'obstacle
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);

	}

}