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
		
		predatorePup = new Predatore();
		predatore = new LinkedPredatore(predatorePup, 1, 1, field, predatori);
		
		for(int i = 0; i < 3; i++){
			predaPup = new Preda();
			preda = new LinkedOrganism(predaPup, 0, i, field, prede);
		}
		
		field.shuffle();
		field.print();
		
		for(int i = 0; i < 10; i++){
			keyboard.nextLine();
			predatori.act();
			// for(int k = 0; k < 50; k++) System.out.println();
			// field.print();
			// keyboard.nextLine();
			prede.act();
			for(int k = 0; k < 50; k++) System.out.println();
			field.print();
		}
	}

}
