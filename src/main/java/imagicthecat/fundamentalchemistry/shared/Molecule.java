package imagicthecat.fundamentalchemistry.shared;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// molecule notation is "[A]<quantity>[B]<quantity>..." like "H2O", no quantity given is 1 by default

public class Molecule {
	public Map<Integer, Integer> atoms; // atomic number => quantity
	
	public Molecule()
	{
		atoms = new HashMap<Integer, Integer>();
	}
	
	public int countAtoms()
	{
		int sum = 0;
		for(Map.Entry<Integer, Integer> entry : atoms.entrySet())
			sum += entry.getValue();
			
		return sum;
	}

	public int countProtons()
	{
		int sum = 0;
		for(Map.Entry<Integer, Integer> entry : atoms.entrySet())
			sum += entry.getKey()*entry.getValue();
			
		return sum;
	}
	
	
	public String toNotation()
	{
		String r = "";
		
		for(Map.Entry<Integer, Integer> entry : atoms.entrySet()){
			String name = FundamentalChemistry.elements.invget(entry.getKey());
			if(name != null){
				r += name;
				if(entry.getValue() > 1)
					r += entry.getValue();
			}
		}
		
		return r;
	}
	
	public String toString()
	{
		return toNotation();
	}
	
	@Override
	public boolean equals(Object o)
	{
		return (o instanceof Molecule && atoms.equals(((Molecule)o).atoms));
	}
	
	@Override
	public int hashCode()
	{
		return atoms.hashCode();
	}

	public static Molecule fromNotation(String notation)
	{
		Molecule m = new Molecule();
		
		Pattern p = Pattern.compile("([A-Z]{1}[a-z]*)([0-9]*)");
		Matcher matcher = p.matcher(notation);
		
		while(matcher.find()){ //for each atom group
			String el = matcher.group(1);
			String q = matcher.group(2);
			
			Integer an = FundamentalChemistry.elements.get(el);
			if(an != null){ //find element
				//parse quantity
				int quantity = 1;
			
				if(q.length() > 0){
					try{
						quantity = Integer.parseInt(q);
					}catch(NumberFormatException e){}
				}
				
				//add 
				m.atoms.put(an, quantity);
			}
		}
		
		return m;
	}
}
