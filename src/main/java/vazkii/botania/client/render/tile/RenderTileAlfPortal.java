/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [Jun 9, 2014, 9:55:07 PM (GMT)]
 */
package vazkii.botania.client.render.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.api.state.enums.AlfPortalState;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.client.core.handler.RenderEventHandler;
import vazkii.botania.common.block.BlockAlfPortal;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.TileAlfPortal;

public class RenderTileAlfPortal extends TileEntitySpecialRenderer<TileAlfPortal> {

	@Override
	public void renderTileEntityAt(TileAlfPortal portal, double d0, double d1, double d2, float f, int digProgress) {
		AlfPortalState state;
		if (portal.getWorld().getBlockState(portal.getPos()) != ModBlocks.alfPortal) {
			state = AlfPortalState.OFF;
		} else {
			state = portal.getWorld().getBlockState(portal.getPos()).getValue(BotaniaStateProps.ALFPORTAL_STATE);
		}

		if(state == AlfPortalState.OFF)
			return;

		GlStateManager.pushMatrix();
		GlStateManager.translate(d0, d1, d2);
		GlStateManager.translate(-1F, 1F, 0.25F);

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.disableAlpha();
		GlStateManager.enableCull();
		float alpha = (float) Math.min(1F, (Math.sin((ClientTickHandler.ticksInGame + f) / 8D) + 1D) / 7D + 0.6D) * (Math.min(60, portal.ticksOpen) / 60F) * 0.5F;
		GlStateManager.color(1F, 1F, 1F, alpha);

		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

		if(state == AlfPortalState.ON_X) {
			GlStateManager.translate(1.25F, 0F, 1.75F);
			GlStateManager.rotate(90F, 0F, 1F, 0F);
		}

		GlStateManager.disableCull();
		GlStateManager.disableLighting();
		renderIcon(0, 0, RenderEventHandler.INSTANCE.alfPortalTex, 3, 3, 240);

		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.translate(0F, 0F, 0.5F);
		renderIcon(0, 0, RenderEventHandler.INSTANCE.alfPortalTex, 3, 3, 240);

		GlStateManager.enableCull();
		GlStateManager.enableAlpha();
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.popMatrix();
	}

	public void renderIcon(int par1, int par2, TextureAtlasSprite par3Icon, int par4, int par5, int brightness) {
		Tessellator tessellator = Tessellator.getInstance();
		tessellator.getWorldRenderer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		//tessellator.getWorldRenderer().setBrightness(brightness);
		tessellator.getWorldRenderer().pos(par1 + 0, par2 + par5, 0).tex(par3Icon.getMinU(), par3Icon.getMaxV()).endVertex();
		tessellator.getWorldRenderer().pos(par1 + par4, par2 + par5, 0).tex(par3Icon.getMaxU(), par3Icon.getMaxV()).endVertex();
		tessellator.getWorldRenderer().pos(par1 + par4, par2 + 0, 0).tex(par3Icon.getMaxU(), par3Icon.getMinV()).endVertex();
		tessellator.getWorldRenderer().pos(par1 + 0, par2 + 0, 0).tex(par3Icon.getMinU(), par3Icon.getMinV()).endVertex();
		tessellator.draw();
	}

}
