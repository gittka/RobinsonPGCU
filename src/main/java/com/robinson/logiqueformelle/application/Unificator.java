package com.robinson.logiqueformelle.application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class Unificator {

	
	public List<SymbolesPair> unify(SymbolesPair termsToUnify) {
		/**
		 * workingSet - list of all SymbolesPairs to unify
		 * 
		 * Ex: SymbolePair ( f(x, h(a,b)), f(b,z) )
		 *  	List<SymbolePair> workingSet = { TP( f(x, h(a,b)), f(b,z) ), TP( x, b ), TP( h(a,b), z )}
		 */
		List<SymbolesPair> workingSet = new ArrayList<>();
		workingSet.add(termsToUnify);
		
		/**
		 * unifier - list of replacement SymbolesPairs
		 * 
		 * Ex: SymbolePair ( f(x, h(a,b)), f(b,z) )
		 * 		List<SymbolePair> unifier = { TP( x, b ) ,TP( z,  h(a,b) )}
		 */
		List<SymbolesPair> unifier = new ArrayList<>();

		for (int i = 0; i < workingSet.size(); ++i) {

			SymbolesPair currentPair = workingSet.get(i);
			Symbole ls = currentPair.getSymbole1();
			Symbole rs = currentPair.getSymbole2();

			if (!unify(ls, rs, workingSet, unifier)) {

				return null;
			}
		}

		return unifier;
	}

	private boolean unify(Symbole ls, Symbole rs, List<SymbolesPair> workingSet,
                          List<SymbolesPair> unifier) {
		
		//select unification method according to the Term Type
		if (ls instanceof Constant) {
			return unify((Constant) ls, rs, workingSet, unifier);
		} else if (ls instanceof Variable) {
			return unify((Variable) ls, rs, workingSet, unifier);
		} else if (ls instanceof Predicate) {
			return unify((Predicate) ls, rs, workingSet, unifier);
		}

		return false;
	}

	private boolean unify(Constant constant, Symbole term,
                          List<SymbolesPair> workingSet, List<SymbolesPair> unifier) {
		
		if (term instanceof Variable) {

			return unify((Variable) term, constant, workingSet, unifier);
		}

		if (constant.equals(term)) {
			return true;
		}

		return false;
	}

	private boolean unify(Variable variable, Symbole term,
                          List<SymbolesPair> workingSet, List<SymbolesPair> unifier) {

		if (variable.equals(term)) {
			return true;
		}

		if (term.occurs(variable)) {
			System.out.println("La substitution ne peut être exécutée: " + variable.toString() + " in " + term.toString());
			return false;
		}

		// execute substitution in all SymbolesPairs in woringSet
		substitute(variable, term, workingSet);
		substitute(variable, term, unifier);

		//add new substitution SymbolesPair to the result list
		unifier.add(new SymbolesPair(variable, term));

		return true;
	}

	private boolean unify(Predicate predicate, Symbole term,
                          List<SymbolesPair> workingSet, List<SymbolesPair> unifier) {
		if (term instanceof Variable) {

			return unify((Variable) term, predicate, workingSet, unifier);
			//if right term is predicate too
		} else if (term instanceof Predicate) {
			Predicate rsPredicate = (Predicate) term;

			//if predicates has different names of vars number => false
			if (!predicate.getName().equals(rsPredicate.getName())
					|| predicate.getArgs().size() != rsPredicate.getArgs()
							.size()) {
				System.out.println("La substitution ne peut être exécutée : predicats  differents");
				return false;
			}			
						
			/**
			 * create SymbolePairs from leftPredicate args and rightPredicte args
			 * 
			 * Ex: SymbolePair ( f(x, h(a,b)), f(b,z) )
			 * 		List<SymbolePair> workingSet = { TP( f(x, h(a,b)), f(b,z) ), TP( x, b ), TP( h(a,b), z )}
			 */
			for (Iterator<Symbole> lsArgs = predicate.getArgs().iterator(), rsArgs = rsPredicate
					.getArgs().iterator(); lsArgs.hasNext()
					&& rsArgs.hasNext();) {

				workingSet.add(new SymbolesPair(lsArgs.next(), rsArgs.next()));
			}

			return true;
		}

		return false;
	}

	//execute substitution in Symboles according to their types
	private void substitute(Symbole variable, Symbole replacement,
                            List<SymbolesPair> list) {
		for (int i = 0; i < list.size(); i++) {

			SymbolesPair currentPair = list.get(i);
			Symbole ls = currentPair.getSymbole1();
			Symbole rs = currentPair.getSymbole2();

			list.set(i, new SymbolesPair(ls.substitute(variable, replacement),
					rs.substitute(variable, replacement)));
		}
	}

}