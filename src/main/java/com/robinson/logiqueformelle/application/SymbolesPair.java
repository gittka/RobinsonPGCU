package com.robinson.logiqueformelle.application;

/**
 *
 */
public class SymbolesPair {

	private Symbole Symbole1;
	private Symbole Symbole2;

	public SymbolesPair(){
		
	}
	
	public SymbolesPair(Symbole Symbole1, Symbole Symbole2) {
		this.Symbole1 = Symbole1;
		this.Symbole2 = Symbole2;
	}

	public Symbole getSymbole1() {
		return Symbole1;
	}

	public Symbole getSymbole2() {
		return Symbole2;
	}

	@Override
	public String toString() {
		return String.format("SymbolesPair(Symbole1='%s', Symbole2='%s')", Symbole1, Symbole2);
	}

}