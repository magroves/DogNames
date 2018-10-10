import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


public class DogNamesLab {

	public static void main(String[] args) throws InputMismatchException, IOException {

		// Array List to hold Dog objects
		ArrayList<Dog> dogList = new ArrayList<Dog>();

		// data file
		String file = "Dog_Names.csv";
		FileReader fileReader = new FileReader(file);
		BufferedReader br = new BufferedReader(fileReader);

		try {
			
			//1st line: header
			@SuppressWarnings("unused")
			String top = br.readLine();

			// currentline
			String line = br.readLine();

			//read each line and create Dog object then add it to list
			while(line != null) {
				String[] dl = line.split(",");
				Dog newdog = createDog(dl);
				dogList.add(newdog);
				line = br.readLine();
			}
			br.close();
		} // end try
		
		catch (IOException e1) {
			e1.printStackTrace();
		}


		Scanner in = new Scanner(System.in);

		System.out.println("Enter a number to perform a function.");
		System.out.println("1 = See how many dogs are registered with a certain name.");
		System.out.println("2 = Display a list of all dog names");
		System.out.println("3 = Guess between two dog names which is more popular.");

		//assuming cmnd line argument is a number
		int input = 0;

		try {
			input = Integer.parseInt(args[0]);
		} 

		//if user enters input that isnt a number
		catch (NumberFormatException e) {
			System.err.println("Argument must be an integer.");
			System.exit(1);
		}

		// Part 1: 
		if (input == 1) {
			System.out.println("Enter a dog's name? ");
			String inputName = in.next().toUpperCase();
			searchList(inputName, dogList);
		}

		// Part 2: prints sorted list of names
		else if (input == 2) {
			System.out.println("List of registered dog names: ");
			printSortList(dogList);
		}

		// Part 3: guess b/t two names
		else if (input == 3) {
			boolean play = true;
			int playCount = 0; // games played
			int gamesWon = 0; // correct guesses

			// loops while user wants to play
			while(play) {

				// generate 2 random index to choose from list of names
				Random random = new Random();
				int r1 = random.nextInt(dogList.size());
				int r2 = random.nextInt(dogList.size());


				// random dog names
				String n1 = dogList.get(r1).getName();
				String n2 = dogList.get(r2).getName();

				System.out.println("Which name is more popular for Anchorage dogs? (Type 1 or 2)");
				System.out.println("1. " + n1 + "       2. " + n2 );
				System.out.println("");
				int guess = in.nextInt();

				// if user inputs number besides 1 or 2
				while(guess < 1 || guess > 2) {
					System.out.println("Please choose either 1 or 2: ");
					guess = in.nextInt();
					System.out.println("");
				}

				// returns 1 if correct guess, 0 if wrong, and 2 if equal count
				int score = guessNames(dogList, r1, r2, guess);
				if(score == 1)
					gamesWon++;

				playCount++;

				System.out.println("Would you like to play again? (Y/N) ");
				String again = in.next().toUpperCase().trim();
				System.out.println("");

				if(again.equals("N")) {
					play = false;
					System.out.println("You guessed correctly " + gamesWon + " out of " + playCount + " times." );
				}
			}
		} // end p3
		in.close();
	} // end main

	/***********************************
	 * creates a Dog object from input data
	 **********************************/
	public static Dog createDog(String[] data) {
		String name = data[0];
		int count = Integer.parseInt(data[1]);
		return new Dog(name,count);
	}
	

	public static void searchList(String name, ArrayList<Dog> list) {
		boolean flag = false;

		for(int i = 0; i < list.size(); i++) {
			String indexName = list.get(i).getName();
			int indexCount = list.get(i).getCount();

			if(indexName.equals(name)) {
				System.out.println(name + " is registered " + indexCount + " times." );
				i = list.size();
				flag = true;
			}
		}

		if(!flag) 
			System.out.println("Name not found.");
	}


	public static void printSortList(ArrayList<Dog> list) {
		Collections.sort(list, Comparator.comparing(Dog::getName));
		int i = 0;
		while(i < list.size()) {
			System.out.println(list.get(i).getName());
			i++;
		}
	}


	public static int guessNames(ArrayList<Dog> list, int r1, int r2, int guess) {

		String name1 = list.get(r1).getName();
		String name2 = list.get(r2).getName();
		Integer count1 = list.get(r1).getCount();
		Integer count2 = list.get(r2).getCount();
		int result = 2;

		if(guess == 1 && (count1.compareTo(count2) > 0)) { 
			System.out.println("Yes, you guessed right!");
			System.out.println("");
			result = 1;}

		else if(guess == 1 && (count1.compareTo(count2) < 0)) {
			System.out.println("Nope, the more popular name is " + name2);
			System.out.println("");
			result = 0;}

		else if(guess == 2 && (count1.compareTo(count2) > 0)) {
			System.out.println("Nope, the more popular name is " + name1);
			System.out.println("");
			result = 0;}

		else if(guess == 2 && (count2.compareTo(count1) > 0)) {
			System.out.println("Yes, you guessed right!");
			System.out.println("");
			result = 1;}

		else if (count1.compareTo(count2) == 0) {
			System.out.println("Names are equally popular.");
			System.out.println("");
			result = 2;}

		return result;
	}

}// end class
