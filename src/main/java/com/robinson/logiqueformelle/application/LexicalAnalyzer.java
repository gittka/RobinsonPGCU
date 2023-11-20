package com.robinson.logiqueformelle.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class LexicalAnalyzer {

	// enum - private static final enum by default
	// Symbole pattern - formal description of lexems' class that
	private enum SymbolePattern {
		VARIABLE, CONSTANT, FUNCTION
	}

	private static final List<String> VARIABLE = Arrays
			.asList("x0", "x1", "x2", "x3", "x4", "x5", "x6", "x7", "x8", "x9",
					"x10", "x11", "x12", "x13", "x14", "x15", "x16", "x17",
					"x18", "x19", "x20", "y0", "y1", "y2", "y3", "y4", "y5",
					"y6", "y7", "y8", "y9", "y10", "y11", "y12", "y13", "y14",
					"y15", "y16", "y17", "y18", "y19", "y20");
	private static final List<String> CONSTANT = Arrays.asList("a", "b", "c");
	private static final List<String> FUNCTION = Arrays.asList("f", "g", "h");

	private Symbole Symbole = null;

	public LexicalAnalyzer() {
		// System.out.println("LexicalAnalyzer: " + Symbole);
	}

	private String[] scanString(String lexem) {
		return lexem.split("[,()]");
	}





	public Symbole analyzeLexem(String lexem) {

		String[] parsedLexem = scanString(lexem);
		/*
		 * for(String s: parsedLexem){ System.out.println("s: "+ s); }
		 */
		// if one of the symbols is "" return NULL
		/*
		 * for (String str : parsedLexem) { if (str == null || str.isEmpty()) {
		 * System.out.println("NULL"); return null; } }
		 */
		// if CONSTANT or VARIABLE
		if (parsedLexem.length == 1) {

			// System.out.println("parsedLexem.length == 1");

			if (defineSymbolePattern(parsedLexem[0]) == SymbolePattern.VARIABLE) {
				Symbole = new Variable(parsedLexem[0]);
			} else if (defineSymbolePattern(parsedLexem[0]) == SymbolePattern.CONSTANT) {
				Symbole = new Constant(parsedLexem[0]);
			} else {
				return null;
			}
		}

		// FUNCTION or NULL
		else {
			// System.out.println("parsedLexem.length NOT 1");
			// if first symbol not a "f, h" return null
			if (defineSymbolePattern(parsedLexem[0]) != SymbolePattern.FUNCTION) {
				System.out.println("defineSymbolePattern(parsedLexem[0]) != SymbolePattern.FUNCTION");
				return null;
			}

			// if parantheses not match return null
			else if (!isParenthesisMatch(lexem)) {
				// System.out.println("!isParenthesisMatch(lexem)");
				return null;
			} else {
				// System.out.println("everything is good");
				// main logic Here...
				for (String symbol : parsedLexem) {

					if ((!symbol.isEmpty())
							&& (defineSymbolePattern(symbol) != SymbolePattern.VARIABLE
							&& defineSymbolePattern(symbol) != SymbolePattern.CONSTANT
							&& defineSymbolePattern(symbol) != SymbolePattern.FUNCTION)) {
						System.out.println("LOL- " + symbol);
						return null;
					}
				}
				Symbole = fromStringToPredicate(lexem);
			}
		}
		return Symbole;
	}

	private SymbolePattern defineSymbolePattern(String lexem) {

		if (VARIABLE.contains(lexem)) {
			return SymbolePattern.VARIABLE;
		} else if (CONSTANT.contains(lexem)) {
			return SymbolePattern.CONSTANT;
		} else if (FUNCTION.contains(lexem)) {
			return SymbolePattern.FUNCTION;
		} else {
			return null;
		}
	}

	private boolean isParenthesisMatch(String str) {

		Stack<Character> stack = new Stack<>();

		char c;
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);

			if (c == '(') {
				stack.push(c);
			}

			else if (c == ')') {
				if (stack.empty())
					return false;
				else if (stack.peek() == '(')
					stack.pop();
				else
					return false;
			}
		}
		return stack.empty();
	}

	/*
	 * private Predicate constractPredicate(String [] parsedLexem){
	 * System.out.println("Construct simple predicate"); List<Symbole> args = new
	 * ArrayList<Symbole>();
	 * 
	 * Predicate predicate = new Predicate(); predicate.setName(parsedLexem[0]);
	 * 
	 * for(int i = 1; i< parsedLexem.length; ++i){
	 * if(defineSymbolePattern(parsedLexem[i]) == SymbolePattern.VARIABLE){
	 * args.add(new Variable(parsedLexem[i])); } else
	 * if(defineSymbolePattern(parsedLexem[i]) == SymbolePattern.CONSTANT){
	 * args.add(new Constant(parsedLexem[i])); }
	 * 
	 * }
	 * 
	 * predicate.setArgs(args);
	 * 
	 * return predicate; }
	 */

	// convert from string to predicate
	private Symbole fromStringToPredicate(String str) {

		Predicate predicate = null;

		if (!str.contains("(")) {

			if (defineSymbolePattern(str) == SymbolePattern.VARIABLE) {
				return new Variable(str);
			} else if (defineSymbolePattern(str) == SymbolePattern.CONSTANT) {
				return new Constant(str);
			}

		} else {
			String name = str.substring(0, str.indexOf("("));
			String functionBody = str.substring(str.indexOf("(") + 1,
					str.lastIndexOf(")"));

			int paranthesesCounter = 0;
			String arg = "";
			List<Symbole> args = new ArrayList<>();

			for (int i = 0; i < functionBody.length(); ++i) {
				char curr = functionBody.charAt(i);
				if (curr == ',') {
					if (paranthesesCounter == 0) {
						args.add(fromStringToPredicate(arg));
						arg = "";
						continue;
					}
				} else if (curr == ')') {
					paranthesesCounter--;

				} else if (curr == '(') {
					paranthesesCounter++;
				}
				arg += curr;

			}
			// add last term to the args list
			args.add(fromStringToPredicate(arg));

			predicate = new Predicate(name, args);
		}
		return predicate;
	}
}
