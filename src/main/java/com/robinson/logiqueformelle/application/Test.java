package com.robinson.logiqueformelle.application;

import java.util.List;
import java.util.Scanner;

public class Test {

	private static Scanner scanner = new Scanner(System.in);

	/**
	 * Test data:
	 * 
	 * f(f(f(a,b)),h(a,h(c)))
	 * f(y,z)
	 * 
	 * f(h(h(h(a,b)),c),z)
     * f(x,h(a,x))
	 * 
	 */
	public static void main(String[] args) {

		int ifTryAgain = 0;
		
		while (true) {

			System.out.println("Robinson's unification algorithm");
			System.out.println("\n");

			System.out.println("Premier terme: ");
			String firstLexemStr = scanner.next();
			// System.out.println("firstTermStr: " + firstTermStr);

			System.out.println("Second terme: ");
			String secondLexemStr = scanner.next();
			// System.out.println("secondTermStr: " + secondTermStr);

			LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();

			Symbole Symbole1 = lexicalAnalyzer.analyzeLexem(firstLexemStr);
			// System.out.println("Symbole1: "+ Symbole1.toString());

			Symbole Symbole2 = lexicalAnalyzer.analyzeLexem(secondLexemStr);
			// System.out.println("Symbole2: " + Symbole2.toString());

			// check if lexems are syntactically correct
			if (Symbole1 != null && Symbole2 != null) {

				SymbolesPair uP = new SymbolesPair(Symbole1, Symbole2);
				System.out.println(uP.toString());

				Unificator s = new Unificator();
				List<SymbolesPair> uPR = s.unify(uP);

				// check if unification can be processed
				if (uPR == null) {
					System.out.println("Les symboles ne peuvent etre unifies");
				} else {
					for (SymbolesPair u : uPR) {
						System.out.println(u.toString());
					}
				}
			} else {
				System.out.println("Syntaxe lexeme incorrecte. Recommenccez.");
			}

			// if try again

			System.out.println("\n1 pour recommencer\n");
			System.out.println("0 pour finir\n");

			ifTryAgain = scanner.nextInt();

			if (ifTryAgain == 0) {
				break;
			}
		}
	}
}