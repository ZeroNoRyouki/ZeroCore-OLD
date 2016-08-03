package it.zerono.mods.zerocore.lib.client.render;

import it.zerono.mods.zerocore.lib.BlockFacings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public final class ModRenderHelper {

    public static TextureAtlasSprite getTextureSprite(final ResourceLocation location) {
        return MINECRAFT.getTextureMapBlocks().getAtlasSprite(location.toString());
    }

    public static TextureAtlasSprite getFluidSprite(final Fluid fluid) {
        return ModRenderHelper.getTextureSprite(fluid.getStill());
    }

    public static TextureAtlasSprite getFluidSprite(final FluidStack fluid) {
        return ModRenderHelper.getTextureSprite(fluid.getFluid().getStill(fluid));
    }

    public static void glSetColor(final int rgbColor) {

        final float r = (float)((rgbColor >> 16) & 0xFF) / 255;
        final float g = (float)((rgbColor >> 8) & 0xFF) / 255;
        final float b = (float)((rgbColor >> 0) & 0xFF) / 255;

        GlStateManager.color(r, g, b);
    }

    public static void glSetColor(final int rgbColor, final float alpha) {

        final float r = (float)((rgbColor >> 16) & 0xFF) / 255;
        final float g = (float)((rgbColor >> 8) & 0xFF) / 255;
        final float b = (float)((rgbColor >> 0) & 0xFF) / 255;

        GlStateManager.color(r, g, b, alpha);
    }

    public static void renderFluidCube(final Fluid fluid, final BlockFacings facesToDraw,
                                       final double offsetX, final double offsetY, final double offsetZ,
                                       final double x1, final double y1, final double z1,
                                       final double x2, final double y2, final double z2,
                                       final int color, final int brightness) {

        final Tessellator tessellator = Tessellator.getInstance();
        final VertexBuffer buffer = tessellator.getBuffer();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        MINECRAFT.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        final TextureAtlasSprite still = ModRenderHelper.getTextureSprite(fluid.getStill());
        final TextureAtlasSprite flowing = ModRenderHelper.getTextureSprite(fluid.getFlowing());

        GlStateManager.pushMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.translate(offsetX, offsetY, offsetZ);

        final EnumFacing[] H = { EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.EAST};
        final EnumFacing[] V = { EnumFacing.DOWN, EnumFacing.UP};

        final TexturedQuadData quadData = new TexturedQuadData(buffer);
        final double width = x2 - x1;
        final double height = y2 - y1;
        final double depth = z2 - z1;
        EnumFacing face;

        quadData.setColor(color);
        quadData.setBrightness(brightness);
        quadData.setCoordinates(x1, y1, z1, width, height, depth, true);

        for (int i = 0; i < 4; ++i) {

            face = H[i];

            if (facesToDraw.isSet(face)) {

                quadData.setFace(face, flowing, true);
                ModRenderHelper.createTexturedQuad2(quadData);
            }
        }

        quadData.setCoordinates(x1, y1, z1, width, height, depth, false);

        for (int i = 0; i < 2; ++i) {

            face = V[i];

            if (facesToDraw.isSet(face)) {

                quadData.setFace(face, still, false);
                ModRenderHelper.createTexturedQuad2(quadData);
            }
        }

        tessellator.draw();

        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    public static class TexturedQuadData {

        public final VertexBuffer vertexes;
        public int alpha;
        public int red;
        public int green;
        public int blue;

        public TexturedQuadData(final VertexBuffer buffer) {

            this.vertexes = buffer;
            this.alpha = this.red = this.green = this.blue = 0xFF;
        }

        public void setBrightness(final int brightness) {

            this.light1 = brightness >> 0x10 & 0xFFFF;
            this.light2 = brightness & 0xFFFF;
        }

        public void setColor(final int color) {

            this.alpha = color >> 24 & 0xFF;
            this.red   = color >> 16 & 0xFF;
            this.green = color >> 8 & 0xFF;
            this.blue  = color & 0xFF;
        }

        public void setCoordinates(final double x, final double y, final double z,
                                   final double width, final double height, final double depth,
                                   final boolean renderAsFlowingFluid) {

            this.x1 = x;
            this.x2 = x + width;
            this.y1 = y;
            this.y2 = y + height;
            this.z1 = z;
            this.z2 = z + depth;

            this.xText1 = this.x1 % 1.0;
            this.xText2 = this.xText1 + width;
            this.yText1 = this.y1 % 1.0;
            this.yText2 = this.yText1 + height;
            this.zText1 = this.z1 % 1.0;
            this.zText2 = this.zText1 + depth;

            while (this.xText2 > 1.0)
                this.xText2 -= 1.0;

            while (this.yText2 > 1.0)
                this.yText2 -= 1.0;

            while (this.zText2 > 1.0)
                this.zText2 -= 1.0;

            // render a flowing texture from the bottom-up
            if (renderAsFlowingFluid) {

                double swap = 1.0 - this.yText1;

                this.yText1 = 1.0 - this.yText2;
                this.yText2 = swap;
            }
        }

        /**
         * Set the face to be rendered. Call this method for last
         */
        public void setFace(final EnumFacing face, final TextureAtlasSprite sprite, final boolean renderAsFlowingFluid) {

            final double textSize = renderAsFlowingFluid ? 16.0 : 8.0;

            if (null == sprite)
                throw new RuntimeException("No sprite set while trying to create a texture quad!");

            switch (face) {

                case DOWN:
                case UP:
                    this.minU = sprite.getInterpolatedU(this.xText1 * textSize);
                    this.maxU = sprite.getInterpolatedU(this.xText2 * textSize);
                    this.minV = sprite.getInterpolatedV(this.zText1 * textSize);
                    this.maxV = sprite.getInterpolatedV(this.zText2 * textSize);
                    break;

                case NORTH:
                case SOUTH:
                    this.minU = sprite.getInterpolatedU(this.xText2 * textSize);
                    this.maxU = sprite.getInterpolatedU(this.xText1 * textSize);
                    this.minV = sprite.getInterpolatedV(this.yText1 * textSize);
                    this.maxV = sprite.getInterpolatedV(this.yText2 * textSize);
                    break;

                case WEST:
                case EAST:
                    this.minU = sprite.getInterpolatedU(this.zText2 * textSize);
                    this.maxU = sprite.getInterpolatedU(this.zText1 * textSize);
                    this.minV = sprite.getInterpolatedV(this.yText1 * textSize);
                    this.maxV = sprite.getInterpolatedV(this.yText2 * textSize);
                    break;

                default:
                    this.minU = sprite.getMinU();
                    this.maxU = sprite.getMaxU();
                    this.minV = sprite.getMinV();
                    this.maxV = sprite.getMaxV();
            }

            this.face = face;
        }

        protected double x1, y1, z1, x2, y2, z2;
        protected double xText1, yText1, zText1, xText2, yText2, zText2;
        protected double minU, maxU, minV, maxV;
        protected int light1, light2;
        protected EnumFacing face;
    }


    public static void createTexturedQuad2(final TexturedQuadData data) {

        final VertexBuffer vertexes = data.vertexes;

        if (null == vertexes)
            return;

        final int alpha = data.alpha;
        final int red = data.red;
        final int green = data.green;
        final int blue = data.blue;
        final int light1 = data.light1;
        final int light2 = data.light2;

        switch (data.face) {

            case DOWN:
                vertexes.pos(data.x1, data.y1, data.z1).color(red, green, blue, alpha).tex(data.minU, data.minV).lightmap(light1, light2).endVertex();
                vertexes.pos(data.x2, data.y1, data.z1).color(red, green, blue, alpha).tex(data.maxU, data.minV).lightmap(light1, light2).endVertex();
                vertexes.pos(data.x2, data.y1, data.z2).color(red, green, blue, alpha).tex(data.maxU, data.maxV).lightmap(light1, light2).endVertex();
                vertexes.pos(data.x1, data.y1, data.z2).color(red, green, blue, alpha).tex(data.minU, data.maxV).lightmap(light1, light2).endVertex();
                break;

            case UP:
                vertexes.pos(data.x1, data.y2, data.z1).color(red, green, blue, alpha).tex(data.minU, data.minV).lightmap(light1, light2).endVertex();
                vertexes.pos(data.x1, data.y2, data.z2).color(red, green, blue, alpha).tex(data.minU, data.maxV).lightmap(light1, light2).endVertex();
                vertexes.pos(data.x2, data.y2, data.z2).color(red, green, blue, alpha).tex(data.maxU, data.maxV).lightmap(light1, light2).endVertex();
                vertexes.pos(data.x2, data.y2, data.z1).color(red, green, blue, alpha).tex(data.maxU, data.minV).lightmap(light1, light2).endVertex();
                break;

            case NORTH:
                vertexes.pos(data.x1, data.y1, data.z1).color(red, green, blue, alpha).tex(data.minU, data.maxV).lightmap(light1, light2).endVertex();
                vertexes.pos(data.x1, data.y2, data.z1).color(red, green, blue, alpha).tex(data.minU, data.minV).lightmap(light1, light2).endVertex();
                vertexes.pos(data.x2, data.y2, data.z1).color(red, green, blue, alpha).tex(data.maxU, data.minV).lightmap(light1, light2).endVertex();
                vertexes.pos(data.x2, data.y1, data.z1).color(red, green, blue, alpha).tex(data.maxU, data.maxV).lightmap(light1, light2).endVertex();
                break;

            case SOUTH:
                vertexes.pos(data.x1, data.y1, data.z2).color(red, green, blue, alpha).tex(data.maxU, data.maxV).lightmap(light1, light2).endVertex();
                vertexes.pos(data.x2, data.y1, data.z2).color(red, green, blue, alpha).tex(data.minU, data.maxV).lightmap(light1, light2).endVertex();
                vertexes.pos(data.x2, data.y2, data.z2).color(red, green, blue, alpha).tex(data.minU, data.minV).lightmap(light1, light2).endVertex();
                vertexes.pos(data.x1, data.y2, data.z2).color(red, green, blue, alpha).tex(data.maxU, data.minV).lightmap(light1, light2).endVertex();
                break;

            case WEST:
                vertexes.pos(data.x1, data.y1, data.z1).color(red, green, blue, alpha).tex(data.maxU, data.maxV).lightmap(light1, light2).endVertex();
                vertexes.pos(data.x1, data.y1, data.z2).color(red, green, blue, alpha).tex(data.minU, data.maxV).lightmap(light1, light2).endVertex();
                vertexes.pos(data.x1, data.y2, data.z2).color(red, green, blue, alpha).tex(data.minU, data.minV).lightmap(light1, light2).endVertex();
                vertexes.pos(data.x1, data.y2, data.z1).color(red, green, blue, alpha).tex(data.maxU, data.minV).lightmap(light1, light2).endVertex();
                break;

            case EAST:
                vertexes.pos(data.x2, data.y1, data.z1).color(red, green, blue, alpha).tex(data.minU, data.maxV).lightmap(light1, light2).endVertex();
                vertexes.pos(data.x2, data.y2, data.z1).color(red, green, blue, alpha).tex(data.minU, data.minV).lightmap(light1, light2).endVertex();
                vertexes.pos(data.x2, data.y2, data.z2).color(red, green, blue, alpha).tex(data.maxU, data.minV).lightmap(light1, light2).endVertex();
                vertexes.pos(data.x2, data.y1, data.z2).color(red, green, blue, alpha).tex(data.maxU, data.maxV).lightmap(light1, light2).endVertex();
                break;
        }
    }

    private ModRenderHelper(){
    }

    private static final Minecraft MINECRAFT = Minecraft.getMinecraft();
}
