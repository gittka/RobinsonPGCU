package com.robinson.logiqueformelle.application;

public class Variable implements Symbole {

	private String name;

	public Variable() {
		name = null;
	}

	public Variable(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}	

	public void setName(String name) {
		this.name = name;
	}

	public boolean occurs(Symbole Symbole) {
		return false;
	}

	public Symbole substitute(Symbole Symbole, Symbole replacement) {
		if (getName() != null && equals(Symbole)) {
			return replacement;
		}

		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Variable variable = (Variable) obj;
		if (name == null && variable.getName() == null) {
			return true;
		}

		return name.equals(variable.getName());
	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}

	@Override
	public String toString() {
		return getName() != null ? getName() : "_";
	}

}