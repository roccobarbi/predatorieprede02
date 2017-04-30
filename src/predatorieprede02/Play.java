package predatorieprede02;

import java.util.Scanner;

public class Play {

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in); 
		int gameLength = 10;
		
		LinkedOrganisms prede = new LinkedOrganisms();
		LinkedOrganisms predatori = new LinkedOrganisms();
		PlayingField field = new PlayingField();
		LinkedOrganism preda;
		LinkedPredatore predatore;
		Preda predaPup;
		Predatore predatorePup;
		
		// Default mode.
		// TODO add different game modes
		
		// Initialising game in default mode
		System.out.println("Initialising game in default mode");
		
		for(int i = 0; i < 5; i++){
			predatorePup = new Predatore();
			predatore = new LinkedPredatore(predatorePup, 0, i, field, predatori);
		}
		
		for(int i = 0; i < 100; i++){
			predaPup = new Preda();
			preda = new LinkedOrganism(predaPup, (1 + i / 20), (i % 20), field, prede);
		}
		
		field.shuffle();
		
		System.out.println("Game initialised in default mode.");
		System.out.println("How many turns do you want to play?");
		if(keyboard.hasNextInt()){
			gameLength = keyboard.nextInt();
		} else {
			System.out.println("Invalid input: using default value of 10.");
		}
		keyboard.nextLine();
		System.out.println("Please press enter to start.");
		keyboard.nextLine();
		
		field.print();
		
		for(int i = 0; i < gameLength; i++){
			keyboard.nextLine();
			predatori.act();
			prede.act();
			for(int k = 0; k < 50; k++) System.out.println();
			field.print();
		}
	}

}
