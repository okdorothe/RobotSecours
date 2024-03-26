//OKOTCHE Dourodjaye Dorothe && MANZALA NOUGOU Pierra N Gloire
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Programme principal avec la m�thode main
 * @author D�partement TIC - ESIGELEC
 * @version 2.1
 */
public class SimulateurAppX {

	public static void main(String[] args) {
		int i,a = 0,b=0,malin = 0;
		String caracteristikVict="";
		// cr�ation de l'environnement et r�cup�ration du terrain
		Terrain terrain = Environnement.creerEnvironnement(10, 10);

		// cr�ation du robot
		Robot robot = new Robot(0, 0, "est");

		// ajout du robot sur le terrain
		terrain.ajouterRobot(robot);
		
		robot.setVitesse(16);
		
		// mise � jour des composants graphiques
		//terrain.updateIHM();
		
		// ajouter ici les victimes 
		terrain.ajouterObstaclesAleatoiresIsolesAvecBords(100);
		 terrain.updateIHM();
		 while(robot.getBatterie()>25)
			{
			 if(robot.isObstacleDevantAvecConsommationBatterie()==false ) {
					robot.avancer();
				}
			 else if(robot.getLigne()%2==0)
			 {
				 if(robot.getColonne()!=9)
				 {
				 robot.tournerDroite();
				 robot.avancer();
				 robot.tournerGauche();
				 robot.avancer();
				 if(robot.isObstacleDevantAvecConsommationBatterie()==false)
				 {
				 robot.avancer();
				 robot.tournerGauche();
				 robot.avancer();
				 robot.tournerDroite();
				 }
				 else
				 {
					 robot.tournerDroite();
					 robot.tournerDroite();
				 }
				}
			  if(robot.getColonne()==9 && robot.getLigne()%2==0)
				{
				   robot.tournerDroite();
					if(robot.isObstacleDevantAvecConsommationBatterie())
					{
						robot.tournerDroite();
					    robot.avancer();
					    robot.tournerGauche();
					}
					robot.avancer();
					robot.tournerDroite();
				}
			 }
			 else if(robot.getLigne()%2!=0)
			 {
				 if(robot.getColonne()!=0)
				 {
				 robot.tournerDroite();
				 robot.avancer();
				 robot.tournerGauche();
				 robot.avancer();
				 if(robot.isObstacleDevantAvecConsommationBatterie()==false)
				 {
				 robot.avancer();
				 robot.tournerGauche();
				 robot.avancer();
				 robot.tournerDroite();
				 }
				 else
				 {
					 robot.tournerDroite();
					 robot.tournerDroite();
					 robot.avancer();
					 robot.tournerDroite();
					 robot.avancer();
					 robot.avancer();
					 robot.tournerGauche();
				 }
				}
				 if(robot.getColonne()==0 && robot.getLigne()%2!=0)
				{
					robot.tournerGauche();
					if(robot.isObstacleDevantAvecConsommationBatterie())
					{
						robot.tournerGauche();
					    robot.avancer();
					    robot.tournerDroite();
				     }
					robot.avancer();
					robot.tournerGauche();
				}    
			 }
			 robot.compterObstacles();
	}
		 JOptionPane.showMessageDialog(null, "Batterie a :"+robot.getBatterie()+"\nAller Recharger!!!", "Avertissement", JOptionPane.WARNING_MESSAGE);
		 try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 robot.chargerBatterie();
	}
}

