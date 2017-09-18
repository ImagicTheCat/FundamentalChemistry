# FundamentalChemistry
Minecraft forge mod of fundamental chemistry (molecular/nuclear manipulation).

The mod doesn't seek realism, it's about taking inspiration from our modern times the same way we take inspiration from ancient era (like water/fire/wind/earth elements, etc).

## WIP 

![Laser Relay tests](https://i.imgur.com/WicW5if.jpg)

## Concept

* Inspired from the periodic table.
* Every atoms/molecules quantities are expressed as mole unit. The quantum is the mole.
* Energy unit notation is E.
* Use Sneak+right-click on machines and storages to open the block chemical storage view (not dynamic).

Notes:

* A machine stops working when its own storage is full (for a specific chemical to produce).

### Items

#### Vibrant catalyst stone

![Craft](/doc/images/craft_vibrant_catalyst_stone.png?raw=true)

Item (made of diamond, obsidian and stone) used in most of the FundamentalChemistry crafts.

### Blocks

#### Versatile generator

![Craft](/doc/images/craft_versatile_generator.png?raw=true)

* A powerful generator to power the laser network and the machines.
* Not only combustion, this generator can be used in many ways, like draining power directly from the environment (wind, water flowing, heat source below).

Technical notes:

* Produce 1E per adjacent flowing water.
* Produce 1E if in contact with air (only adjacent).
* Produce a variable amount of energy depending of the heat source under the generator.
* Produce a variable amount of energy depending of the fuel used.

#### Energy storage

![Craft](/doc/images/craft_energy_storage.png?raw=true)

* Store generated energy.

#### Versatile extractor

![Craft](/doc/images/craft_versatile_extractor.png?raw=true)

* Extract molecules directly from the environment (air, water).

Technical notes:

* Consume 2E to extract adjacent air.
* Consume 3E to extract adjacent water.

#### Laser relay

![Craft](/doc/images/craft_laser_relay.png?raw=true)

* Can be connected to other laser relays to transfer atoms and molecules.
* Laser relays can be connected to machines or placed on the world to increase the transfer distance.
* Laser relays can be connected to an unlimited number of relays in range.
* Connected machines drain things from the laser network when needed.
* Use right-click from a relay to another to connect/disconnect.

Technical notes:

* Things are taken by input requests (nothing move if nothing needs it).
* Fetching things in a laser network from a relay will not go further the chemical containers (machines, storages, etc). This means that if a storage is connected behind a machine, unless there is another way, only this machine will be able to fetch the storage.

#### Item breaker

![Craft](/doc/images/craft_item_breaker.png?raw=true)

Machine to break items into molecules.

Technical notes:

* Consume (atomsum) E.

#### Molecule breaker

![Craft](/doc/images/craft_molecule_breaker.png?raw=true)

Machine to break molecules into atoms.

Technical notes:

* Since the breaker will fetch any molecule, if its own chemical container is full, the molecule will get stuck in the machine until the container has available space.
* Consume (protonsum) E.

#### Molecular storage

![Craft](/doc/images/craft_molecular_storage.png?raw=true)

Store molecules. 

#### Periodic storage

![Craft](/doc/images/craft_periodic_storage.png?raw=true)

Store atoms of the periodic table.

#### Molecule assembler

![Craft](/doc/images/craft_molecule_assembler.png?raw=true)

Assemble atoms to form molecules.

Technical notes:

* Consume (protonsum) E.

#### Item assembler

![Craft](/doc/images/craft_item_assembler.png?raw=true)

Assemble molecules to form items.

Technical notes:

* Consume (atomsum) E.
* The produced items are dropped on the ground or added to an adjacent chest.

#### Positive nuclear transmuter

![Craft](/doc/images/craft_positive_nuclear_transmuter.png?raw=true)

"Punch" atoms to increase their proton/electron number (the amount is given by the item used).
This is an hard task. See nuclear transmutation.

#### Negative nuclear transmuter

![Craft](/doc/images/craft_negative_nuclear_transmuter.png?raw=true)

"Snatch" atoms to decrease their proton/electron number (the amount is given by the item used).
This is an hard task. See nuclear transmutation.

### Nuclear transmutation

* Using a catalyst, it is possible to increase or decrease the atomic number of an atom.
* The chance of success is defined by the power of the catalyst as `50+power*10 % clamped to 99%`.
* The atom and the catalyst are lost on failure, an atom of atomic number += or -= power is produced on success (if possible).
* Consume (catalyst_power) E per transmutation.

Following those rules, it is easier to transmute atoms with big jumps (5 by 5) than small jumps like the quantum (1 by 1) which will cause 40% loss per transmutation.

Examples: 
* doing +1 +1 +1 +1 +1 +1 +1 +1 +1 (+9) => 0.60^9 => ~1% success
* doing +5 +5 -1 (+9) => 0.99x0.99x0.60 => ~60% success
* doing +5 +4 (+9) => 0.99x0.90 => ~90% success

#### Catalysts

* `cobblestone` power 1 => 60% chance => ~2.5 reactions per unit
* `redstone` power 2 => 70% chance => ~3.33 reactions per unit
* `iron_ingot` power 3 => 80% chance => ~5 reactions per unit
* `gold_ingot` power 4 => 90% chance => ~10 reactions per unit
* `diamond` power 5 => 99% chance => ~100 reactions per unit
