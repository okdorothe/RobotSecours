import java.awt.Color;

/**
 * Classe repr�sentant la case de d�part du Robot
 * 
 * @author D�partement TIC - ESIGELEC
 * @version 1.0
 */
public class CaseDepart extends Case {

	/**
	 * num�ro de version de la classe
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de la classe
	 * 
	 * @param t
	 *            le terrain sur lequel se trouve la case
	 * @param ligne
	 *            numero de la ligne de la case
	 * @param colonne
	 *            numero de la colonne de la case
	 */
	public CaseDepart(Terrain t, int ligne, int colonne) {
		// appel du constructeur de la classe m�re (Case)
		super(t, ligne, colonne);
		// on fixe la couleur de fond de la case de depart
		this.setBackground(Color.RED);
	}

}