package predatorieprede02;

import java.util.Scanner;

public class Play {

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in); 
		
		LinkedOrganisms prede = new LinkedOrganisms();
		LinkedOrganisms predatori = new LinkedOrganisms();
		PlayingField field = new PlayingField();
		LinkedOrganism preda;
		LinkedPredatore predatore;
		Preda predaPup;
		Predatore predatorePup;
		
		// Default mode.
		// TODO add different game modes
		
		for(int i = 0; i < 5; i++){
			predatorePup = new Predatore();
			predatore = new LinkedPredatore(predatorePup, 0, i, field, predatori);
		}
		
		for(int i = 0; i < 100; i++){
			predaPup = new Preda();
			preda = new LinkedOrganism(predaPup, (1 + i / 20), (i % 20), field, prede);
		}
		
		field.shuffle();
		field.print();
		
		for(int i = 0; i < 10; i++){
			keyboard.nextLine();
			predatori.act();
			for(int k = 0; k < 5; k++) System.out.println();
			field.print();
			keyboard.nextLine();
			prede.act();
			for(int k = 0; k < 5; k++) System.out.println();
			field.print();
		}
	}

}
