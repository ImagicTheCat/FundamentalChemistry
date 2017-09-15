
package imagicthecat.fundamentalchemistry.client.gui;

import imagicthecat.fundamentalchemistry.shared.container.TileItemBreakerContainer;
import imagicthecat.fundamentalchemistry.shared.tileentity.TileItemBreaker;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class TileItemBreakerGui extends GuiContainer{
	
	private TileItemBreaker ent;
	private IInventory player_inv;

	public TileItemBreakerGui(IInventory player_inv, TileItemBreaker ent) {
		super(new TileItemBreakerContainer(player_inv, ent));
		
		this.ent = ent;
		this.player_inv = player_inv;
		
    this.xSize = 176;
    this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,
			int mouseX, int mouseY) {
    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    this.mc.getTextureManager().bindTexture(new ResourceLocation("fundamentalchemistry:textures/gui/container/item_breaker.png"));
    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	  String s = this.ent.getDisplayName().getUnformattedText();
	  this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);            //#404040
	  this.fontRendererObj.drawString(this.player_inv.getDisplayName().getUnformattedText(), 8, 72, 4210752);      //#404040
	}
}
