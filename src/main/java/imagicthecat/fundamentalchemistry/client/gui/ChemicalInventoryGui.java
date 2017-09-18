
package imagicthecat.fundamentalchemistry.client.gui;

import imagicthecat.fundamentalchemistry.shared.container.ChemicalInventoryContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class ChemicalInventoryGui extends GuiContainer{
	private IInventory inv;

	public ChemicalInventoryGui(IInventory inv) {
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
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	  String s = this.inv.getDisplayName().getUnformattedText();
	  this.fontRendererObj.drawString(s, 10, 6, 4210752); //#404040
	}
}
