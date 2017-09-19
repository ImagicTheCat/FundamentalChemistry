package imagicthecat.fundamentalchemistry.shared;

import java.util.HashSet;
import java.util.Set;

public class ChemicalFilter {
	public Set<Molecule> molecules;
	public Set<Integer> atoms;
	public boolean policy; //if true, whitelist, if false, blacklist
	
	public ChemicalFilter()
	{
		policy = true;
		molecules = new HashSet<Molecule>();
		atoms = new HashSet<Integer>();
	}
}
