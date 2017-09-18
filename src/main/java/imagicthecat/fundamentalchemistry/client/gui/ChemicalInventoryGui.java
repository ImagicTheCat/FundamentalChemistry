
package imagicthecat.fundamentalchemistry.client.gui;

import imagicthecat.fundamentalchemistry.shared.container.ChemicalInventory;
import imagicthecat.fundamentalchemistry.shared.container.ChemicalInventoryContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class ChemicalInventoryGui extends GuiContainer{
	private ChemicalInventory inv;

	public ChemicalInventoryGui(ChemicalInventory inv) {
		super(new ChemicalInventoryContainer(inv));
		
		this.inv = inv;
		
    this.xSize = 176;
    this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,
			int mouseX, int mouseY) {
    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    this.mc.getTextureManager().bindTexture(new ResourceLocation("fundamentalchemistry:textures/gui/container/chemical_inventory.png"));
    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    
    //display energy
    if(this.inv.max_energy > 0){
    	int eh = (int)(this.inv.energy/(float)this.inv.max_energy*39);
    	if(eh > 0)
    		this.drawTexturedModalRect(this.guiLeft+160, this.guiTop+8+39-eh, 176, 39-eh, 8, eh);
    }
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	  this.fontRendererObj.drawString(this.inv.getDisplayName().getUnformattedText(), 10, 6, 4210752); //#404040 title
	 
	  int shift = 0;
	  //atoms
	  if(this.inv.max_atoms >= 0){
	  	this.fontRendererObj.drawString(I18n.format("container.chemical_storage.max_atoms")+": "+this.inv.max_atoms, 10, 20, 4210752); //#404040 
	  	shift += 9;
	  }
	  
	  //molecules
	  if(this.inv.max_molecules >= 0)
	  	this.fontRendererObj.drawString(I18n.format("container.chemical_storage.max_molecules")+": "+this.inv.max_molecules, 10, 20+shift, 4210752); //#404040 

	  //energy
	  if(this.inv.max_energy >= 0){
		  String energy = this.inv.energy+" / "+this.inv.max_energy+" E";
		  this.fontRendererObj.drawString(energy, 157-this.fontRendererObj.getStringWidth(energy), 47-7, 4210752); //#404040 
	  }
	}
}
