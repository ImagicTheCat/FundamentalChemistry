package imagicthecat.fundamentalchemistry.shared.tileentity;

public enum LaserRelayFetch {
	ALL, // fetch all chemicals from the network
	ATOMS, // fetch all atoms from the network
	MOLECULES, // fetch all molecules from the network
	ANY_ATOM, // fetch a single atom from the network (only if out is empty)
	ANY_MOLECULE, // fetch a single molecule from the network (only if out is empty)
	REQUEST // fetch the requested chemicals from the network
}
