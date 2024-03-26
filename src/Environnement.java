/**
 * Classe qui permet de cr�er l'environnement du simulateur
 * 
 * @author D�partement TIC - ESIGELEC
 * @version 1.0
 */
public class Environnement {

	/**
	 * permet de cr�er l'environnement du simulateur (fen�tre + terrain)
	 * 
	 * @return le terrain situ� sur la fen�tre
	 */
	public static Terrain creerEnvironnement(int nbLignes, int nbColonnes) {

		// cr�ation de la fen�tre
		Fenetre f = new Fenetre(880, 500);
		f.setTitle("Robot ESIGELEC");
		// blocage du redimmensionnement de la fenetre
		f.setResizable(false);
		// cr�ation du terrain
		Terrain terrain = new Terrain(nbLignes, nbColonnes);
		terrain.setBounds(30, 40, 400, 400);
		f.ajouterTerrain(terrain);
		// cr�ation de la zone de controle
		PanneauDeControle pc = new PanneauDeControle(terrain);
		pc.setBounds(440, 40, 400, 400);
		f.ajouterPanneauDeControle(pc);

		// on centre la fen�tre � l'ecran
		f.setLocationRelativeTo(f.getParent());
		// on rend la fen�tre visible (par d�faut elle est invisible)
		f.setVisible(true);

		return terrain;

	}

}