package predatorieprede02;

import java.util.Scanner;

public class Play2 {

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in); 
		
		LinkedOrganisms prede = new LinkedOrganisms();
		LinkedOrganisms predatori = new LinkedOrganisms();
		PlayingField field = new PlayingField(3, 3);
		LinkedOrganism preda;
		LinkedPredatore predatore;
		Preda predaPup;
		Predatore predatorePup;
		
		// Default mode.
		// TODO add different game modes
		
		predatorePup = new Predatore("predatore", 'X', "myPredatore", 2, 100, 3);
		predatore = new LinkedPredatore(predatorePup, 1, 1, field);
		predatore.setList(predatori);
		
		for(int i = 0; i < 3; i++){
			predaPup = new Preda("preda", 'o', "myPreda", 3, 50);
			preda = new LinkedOrganism(predaPup, 0, i, field);
			preda.setList(prede);
		}
		
		field.shuffle();
		field.print();
		
		for(int i = 0; i < 10; i++){
			keyboard.nextLine();
			predatori.act();
			prede.act();
			for(int k = 0; k < 10; k++) System.out.println();
			System.out.println("Starving in: " + predatorePup.getDaysUntilStarve());
			field.print();
		}
	}

}
