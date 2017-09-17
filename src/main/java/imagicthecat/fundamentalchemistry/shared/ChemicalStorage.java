package imagicthecat.fundamentalchemistry.shared;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;

public class ChemicalStorage {
	public Map<Molecule, Integer> molecules; //molecule => amount
	public Map<Integer, Integer> atoms; //atomic number => amount
	public int energy;
	
	public int max_atoms; // max number of atoms per type (only applied by add methods)
	public int max_molecules; // max number of molecules per type (only applied by add methods)
	public int max_energy; // max number of energy unit (only applied by add methods)
	
	public ChemicalStorage()
	{
		max_atoms = -1;
		max_molecules = -1;
		max_energy = -1;
		molecules = new HashMap<Molecule, Integer>();
		atoms = new HashMap<Integer, Integer>();
		energy = 0;
	}
	
	public ChemicalStorage(ChemicalStorage chems)
	{
		max_atoms = chems.max_atoms;
		max_molecules = chems.max_molecules;
		max_energy = chems.max_energy;
		molecules = new HashMap<Molecule, Integer>(chems.molecules);
		atoms = new HashMap<Integer, Integer>(chems.atoms);
		energy = chems.energy;
	}
	
	//construct storage by copying atoms and molecules (they can be null to represent emptiness)
	public ChemicalStorage(Map<Integer, Integer> atoms, Map<Molecule, Integer> molecules)
	{
		max_atoms = -1;
		max_molecules = -1;
		max_energy = -1;
		energy = 0;
		
		if(atoms != null)
			this.atoms = new HashMap<Integer, Integer>(atoms);
		else
			this.atoms = new HashMap<Integer, Integer>();
		
		if(molecules != null)
			this.molecules = new HashMap<Molecule, Integer>(molecules);
		else
			this.molecules = new HashMap<Molecule, Integer>();
	}
	
	//construct storage by copying atoms, molecules and energy (they can be null to represent emptiness)
	public ChemicalStorage(Map<Integer, Integer> atoms, Map<Molecule, Integer> molecules, int energy)
	{
		max_atoms = -1;
		max_molecules = -1;
		max_energy = -1;
		
		if(atoms != null)
			this.atoms = new HashMap<Integer, Integer>(atoms);
		else
			this.atoms = new HashMap<Integer, Integer>();
		
		if(molecules != null)
			this.molecules = new HashMap<Molecule, Integer>(molecules);
		else
			this.molecules = new HashMap<Molecule, Integer>();
		
		this.energy = energy;
	}
	
	// read data from a NBTTagCompound
	public void read(NBTTagCompound tag)
	{
		// read atoms
		NBTTagCompound atoms = tag.getCompoundTag("atoms");
		if(atoms != null){
			for(String key : atoms.getKeySet()){
				try{
					this.atoms.put(Integer.parseInt(key.substring(1)), atoms.getInteger(key));
				}catch(NumberFormatException e){}
			}
		}
		
		// read molecules
		NBTTagCompound molecules = tag.getCompoundTag("molecules");
		if(molecules != null){
			for(String key : molecules.getKeySet()){
				Molecule m = Molecule.fromNotation(key);
				if(m != null)
					this.molecules.put(m, molecules.getInteger(key));
			}
		}
		
		// read energy
		energy = tag.getInteger("energy");
	}
	
	// write data to a NBTTagCompound
	public void write(NBTTagCompound tag)
	{
		// write atoms
		NBTTagCompound atoms = new NBTTagCompound();
		for(Map.Entry<Integer, Integer> entry : this.atoms.entrySet())
			atoms.setInteger("a"+entry.getKey(), entry.getValue());
		tag.setTag("atoms", atoms);
		
		// write molecules
		NBTTagCompound molecules = new NBTTagCompound();
		for(Map.Entry<Molecule, Integer> entry : this.molecules.entrySet())
			molecules.setInteger(entry.getKey().toNotation(), entry.getValue());
		tag.setTag("molecules", molecules);
		
		// write energy
		tag.setInteger("energy", energy);
	}
	
	public void clear()
	{
		molecules.clear();
		atoms.clear();
		energy = 0;
	}
	
	public boolean isEmpty()
	{
		if(energy > 0)
			return false;
		
		for(Map.Entry<Integer, Integer> entry : atoms.entrySet()){
			if(entry.getValue() > 0)
				return false;
		}
		
		for(Map.Entry<Molecule, Integer> entry : molecules.entrySet()){
			if(entry.getValue() > 0)
				return false;
		}

		return true;
	}
	
	// add atom, return overflow
	public int addAtom(Integer atomic_number, Integer amount)
	{
		int overflow = 0;
		
		Integer p_amount = atoms.get(atomic_number);
		int new_amount = amount;
		if(p_amount != null)
			new_amount += p_amount;
		if(max_atoms >= 0 && new_amount > max_atoms){ //handle overflow
			overflow = new_amount-max_atoms;
			new_amount = max_atoms;
		}
		
		atoms.put(atomic_number, new_amount);
		
		return overflow;
	}
	
	// add molecule, return overflow
	public int addMolecule(Molecule molecule, Integer amount)
	{
		int overflow = 0;
		
		Integer p_amount = molecules.get(molecule);
		int new_amount = amount;
		if(p_amount != null)
			new_amount += p_amount;
		if(max_molecules >= 0 && new_amount > max_molecules){ //handle overflow
			overflow = new_amount-max_molecules;
			new_amount = max_molecules;
		}
		
		molecules.put(molecule, new_amount);
		
		return overflow;
	}
	
	// add energy, return overflow
	public int addEnergy(int amount)
	{
		int overflow = 0;
		
		int new_amount = energy+amount;
		if(max_energy >= 0 && new_amount > max_energy){ //handle overflow
			overflow = new_amount-max_energy;
			new_amount = max_energy;
		}
		
		energy = new_amount;
		
		return overflow;
	}
	
	// add a storage content to this one (return overflow)
	public ChemicalStorage add(ChemicalStorage storage)
	{
		return new ChemicalStorage(addAtoms(storage).atoms, addMolecules(storage).molecules, addEnergy(storage).energy);
	}
	
	// api consistency function
	public ChemicalStorage addEnergy(ChemicalStorage storage)
	{
		ChemicalStorage overflow = new ChemicalStorage();
		overflow.energy = addEnergy(storage.energy);
		
		return overflow;
	}
	
	// add a storage content to this one (return overflow)
	public ChemicalStorage addAtoms(ChemicalStorage storage)
	{
		ChemicalStorage overflow = new ChemicalStorage();
		
		// atoms
		for(Map.Entry<Integer, Integer> entry : storage.atoms.entrySet()){
			int of = addAtom(entry.getKey(), entry.getValue());
			if(of > 0)
				overflow.addAtom(entry.getKey(), of);
		}
		
		return overflow;
	}
	
	// add a storage content to this one
	public ChemicalStorage addMolecules(ChemicalStorage storage)
	{
		ChemicalStorage overflow = new ChemicalStorage();
		
		// molecules
		for(Map.Entry<Molecule, Integer> entry : storage.molecules.entrySet()){
			int of = addMolecule(entry.getKey(), entry.getValue());
			if(of > 0)
				overflow.addMolecule(entry.getKey(), of);
		}
		
		return overflow;
	}
	
	// take a specific quantity from the storage (defined by a storage), will return the asked quantity or less
	public ChemicalStorage take(ChemicalStorage storage)
	{
		ChemicalStorage chems = new ChemicalStorage();
		
		//molecules
		for(Map.Entry<Molecule, Integer> entry : storage.molecules.entrySet()){
			Integer amount = molecules.get(entry.getKey());
			if(amount != null){
				int taken = Math.min(amount, entry.getValue());
				amount -= taken;
				if(amount <= 0)
					molecules.remove(entry.getKey());
				else
					molecules.put(entry.getKey(), amount);
				
				chems.molecules.put(entry.getKey(), taken);
			}
		}

		//atoms
		for(Map.Entry<Integer, Integer> entry : storage.atoms.entrySet()){
			Integer amount = atoms.get(entry.getKey());
			if(amount != null){
				int taken = Math.min(amount, entry.getValue());
				amount -= taken;
				if(amount <= 0)
					atoms.remove(entry.getKey());
				else
					atoms.put(entry.getKey(), amount);
				
				chems.atoms.put(entry.getKey(), taken);
			}
		}
		
		//energy
		int taken = Math.min(energy, storage.energy);
		energy -= taken;
		chems.energy = taken;
		
		return chems;
	}
	
	public boolean contains(ChemicalStorage storage)
	{
		return containsAtoms(storage) && containsMolecules(storage) && containsEnergy(storage);
	}
	
	public boolean containsAtoms(ChemicalStorage storage)
	{
		for(Map.Entry<Integer, Integer> entry : storage.atoms.entrySet()){
			Integer amount = atoms.get(entry.getKey());
			if(amount == null || amount < entry.getValue())
				return false;
		}
		
		return true;
	}
	
	public boolean containsMolecules(ChemicalStorage storage)
	{
		for(Map.Entry<Molecule, Integer> entry : storage.molecules.entrySet()){
			Integer amount = molecules.get(entry.getKey());
			if(amount == null || amount < entry.getValue())
				return false;
		}
		
		return true;
	}
	
	public boolean containsEnergy(ChemicalStorage storage)
	{
		return energy >= storage.energy;
	}
	
	public String toString()
	{
		String r = "\n== Chemical Storage ("+energy+" E) ==\n= Atoms";
		for(Map.Entry<Integer, Integer> entry : atoms.entrySet()){
			String name = FundamentalChemistry.elements.invget(entry.getKey());
			if(name != null)
				r += "\n"+name+": "+entry.getValue();
		}
		
		r += "\n= Molecules";
		for(Map.Entry<Molecule, Integer> entry : molecules.entrySet()){
			r += "\n"+entry.getKey()+": "+entry.getValue();
		}
		
		r += "\n";
		
		return r;
	}
}
