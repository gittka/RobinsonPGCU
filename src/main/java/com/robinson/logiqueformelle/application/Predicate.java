package com.robinson.logiqueformelle.application;

import java.util.*;

public class Predicate implements Symbole {

	private String name;

	private List<Symbole> args;

	public Predicate() {

	}

	public Predicate(String name, Symbole args) {
		this(name, Arrays.asList(args));
	}

	public Predicate(String name, List<Symbole> args) {
		this.name = name;
		this.args = args;
	}

	public String getName() {
		return name;
	}

	public List<Symbole> getArgs() {
		return Collections.unmodifiableList(args);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setArgs(List<Symbole> args) {
		this.args = args;
	}

	public boolean occurs(Symbole term) {
		for (Symbole arg : args) {
			if (arg.equals(term)) {
				return true;
			}
		}
		return false;
	}

	public Symbole substitute(Symbole term, Symbole replacement) {

		List<Symbole> substitutedArgs = new ArrayList<>(args.size());
		for (Symbole arg : args) {
			substitutedArgs.add(arg.substitute(term, replacement));
		}

		return new Predicate(name, substitutedArgs);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Predicate predicate = (Predicate) obj;

		if (!getArgs().equals(predicate.getArgs()))
			return false;
		if (!getName().equals(predicate.getName()))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = getName().hashCode();
		result = 31 * result + getArgs().hashCode();
		return result;
	}

	@Override
	public String toString() {
		StringBuffer predicate = new StringBuffer(getName()).append("(");

		Iterator<Symbole> args = getArgs().iterator();
		while (args.hasNext()) {
			predicate.append(args.next());

			if (args.hasNext()) {
				predicate.append(", ");
			}
		}

		predicate.append(")");

		return predicate.toString();
	}

}