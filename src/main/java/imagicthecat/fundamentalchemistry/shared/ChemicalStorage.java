package imagicthecat.fundamentalchemistry.shared;

import imagicthecat.fundamentalchemistry.FundamentalChemistry;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;

public class ChemicalStorage {
	public Map<Molecule, Integer> molecules; //molecule => amount
	public Map<Integer, Integer> atoms; //atomic number => amount
	
	public ChemicalStorage()
	{
		molecules = new HashMap<Molecule, Integer>();
		atoms = new HashMap<Integer, Integer>();
	}
	
	public ChemicalStorage(ChemicalStorage chems)
	{
		molecules = new HashMap<Molecule, Integer>(chems.molecules);
		atoms = new HashMap<Integer, Integer>(chems.atoms);
	}
	
	//construct storage by copying atoms and molecules (they can be null to represent emptiness)
	public ChemicalStorage(Map<Integer, Integer> atoms, Map<Molecule, Integer> molecules)
	{
		if(atoms != null)
			this.atoms = new HashMap<Integer, Integer>(atoms);
		else
			this.atoms = new HashMap<Integer, Integer>();
		
		if(molecules != null)
			this.molecules = new HashMap<Molecule, Integer>(molecules);
		else
			this.molecules = new HashMap<Molecule, Integer>();
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
	}
	
	public void clear()
	{
		molecules.clear();
		atoms.clear();
	}
	
	// add a storage content to this one
	public void add(ChemicalStorage storage)
	{
		addAtoms(storage);
		addMolecules(storage);
	}
	
	// add a storage content to this one
	public void addAtoms(ChemicalStorage storage)
	{
		//atoms
		for(Map.Entry<Integer, Integer> entry : storage.atoms.entrySet()){
			Integer p_amount = atoms.get(entry.getKey());
			if(p_amount != null)
				atoms.put(entry.getKey(), p_amount+entry.getValue());
			else
				atoms.put(entry.getKey(), entry.getValue());
		}
	}
	
	// add a storage content to this one
	public void addMolecules(ChemicalStorage storage)
	{
		//molecules
		for(Map.Entry<Molecule, Integer> entry : storage.molecules.entrySet()){
			Integer p_amount = molecules.get(entry.getKey());
			if(p_amount != null)
				molecules.put(entry.getKey(), p_amount+entry.getValue());
			else
				molecules.put(entry.getKey(), entry.getValue());
		}
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
		
		return chems;
	}
	
	public boolean contains(ChemicalStorage storage)
	{
		return containsAtoms(storage) && containsMolecules(storage);
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
	
	public String toString()
	{
		String r = "\n== Chemical Storage ==\n= Atoms";
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
