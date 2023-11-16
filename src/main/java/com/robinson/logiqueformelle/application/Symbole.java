package com.robinson.logiqueformelle.application;

public interface Symbole {

	public boolean occurs(Symbole Symbole);

	public Symbole substitute(Symbole oldSymbole, Symbole newSymbole);

	public String toString();

}