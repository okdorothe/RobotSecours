import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Classe repr�sentant la fen�tre du simulateur
 * 
 * @author D�partement TIC - ESIGELEC
 * @version 1.0
 */
public class Fenetre extends JFrame {

	/**
	 * num�ro de version de la classe
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * container principal de la fen�tre
	 */
	Container container;
	/**
	 * Terrain situ� sur le simulateur
	 */
	Terrain terrain;
	/**
	 * Panneau de contr�le du simulateur
	 */
	PanneauDeControle controle;
	/**
	 * texte affich� en haut du simulateur
	 */
	JLabel labelDescription;

	/**
	 * Constructeur de la fen�tre
	 * 
	 * @param largeur
	 *            largeur de la fen�tre en pixel
	 * @param hauteur
	 *            hauteur de la fen�tre en pixel
	 */
	public Fenetre(int largeur, int hauteur) {
		super();
		// on fixe la taille de la fen�tre
		this.setSize(largeur, hauteur);
		// on r�cup�re le container principal de la fen�tre
		container = this.getContentPane();
		// on modifie la couleur de fond du container
		container.setBackground(Color.WHITE);

		// on d�sactive le layout manager (gestionnaire de placement) du
		// container
		// afin de pouvoir placer les composants au pixel pr�s
		container.setLayout(null);
		// on d�finit le texte de la fen�tre
		labelDescription = new JLabel(
				"Simulateur de Robot sauveteur en milieu hostile");
		// on d�finit la position et la taille du texte de la fen�tre
		labelDescription.setBounds(5, 5, 780, 20);
		// on ajoute le composant texte dans le container de la fen�tre
		this.add(labelDescription);
		// on pr�cise que le programme doit fermer compl�tement si on clique sur
		// la croix de la fen�tre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * Permet d'ajouter le terrain dans le container principal de la fen�tre
	 * 
	 * @param t
	 *            le terrain � ajouter
	 */
	public void ajouterTerrain(Terrain t) {
		terrain = t;
		// on ajoute le composant graphique du terrain dans le container de la
		// fenetre
		container.add(terrain);
		// on redessine la fen�tre
		repaint();
	}

	/**
	 * Permet d'ajouter le panneau de contr�le dans la fen�tre
	 * 
	 * @param pc
	 *            le panneau de contr�le � ajouter
	 */
	public void ajouterPanneauDeControle(PanneauDeControle pc) {
		controle = pc;
		// on ajoute le composant graphique du panneau de contr�le dans le
		// container de la fenetre
		container.add(controle);
		// on redessine la fen�tre
		repaint();
	}

}