package diamondfireutils.codetools.misctools;

import diamondfireutils.ColorReference;
import diamondfireutils.codetools.utils.BlockUtils;
import diamondfireutils.codetools.utils.GraphicsUtils;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PistonHighlighting {
    
    private final Minecraft minecraft = Minecraft.getMinecraft();
    
    private boolean doPistonHighlight = false;
    private int highlightCooldown = 0;
    
    private BlockPos openingPistonPos;
    private BlockPos closingPistonPos;
    
    @SubscribeEvent
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        
        if (highlightCooldown - 3 > minecraft.player.ticksExisted) highlightCooldown = 0;
        
        if (minecraft.player.isCreative() && minecraft.player.isSneaking() && minecraft.player.ticksExisted > highlightCooldown) {
    
            BlockPos blockPos = minecraft.objectMouseOver.getBlockPos();
            IBlockState blockClicked = minecraft.world.getBlockState(blockPos);
            String blockName = blockClicked.getBlock().getLocalizedName();
            
            if ((blockName.equals("Piston") || blockName.equals("Sticky Piston")) && BlockUtils.isCodeBlock(blockPos) && BlockUtils.hasOppositePiston(blockPos)) {
                
                highlightCooldown = minecraft.player.ticksExisted + 2;
                event.setCanceled(true);
                
                if (blockClicked.getValue(PropertyDirection.create("facing")) == EnumFacing.SOUTH) {
            
                    if (blockPos.equals(openingPistonPos)) {
                
                        clearHighlight();
                        
                    } else {
                        doPistonHighlight = true;
                        openingPistonPos = blockPos;
                        closingPistonPos = BlockUtils.getOppositePiston(blockPos);
                    }
            
                } else if (blockClicked.getValue(PropertyDirection.create("facing")) == EnumFacing.NORTH) {
            
                    if (blockPos.equals(closingPistonPos)) {
                
                        clearHighlight();
                        
                    } else {
                        doPistonHighlight = true;
                        closingPistonPos = blockPos;
                        openingPistonPos = BlockUtils.getOppositePiston(blockPos);
                    }
            
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        
        if (doPistonHighlight) {
            
            Tessellator tessellator = Tessellator.getInstance();
            
            GraphicsUtils.drawCube(tessellator, event.getPartialTicks(), openingPistonPos.getX() - 0.01, openingPistonPos.getY() - 0.01, openingPistonPos.getZ() - 0.01,
                    openingPistonPos.getX() + 1.01, openingPistonPos.getY() + 1.01, openingPistonPos.getZ() + 1.01,
                    ColorReference.HIGHLIGHT_CODE);
    
            GraphicsUtils.drawCube(tessellator, event.getPartialTicks(), closingPistonPos.getX() - 0.01, closingPistonPos.getY() - 0.01, closingPistonPos.getZ() - 0.01,
                    closingPistonPos.getX() + 1.01, closingPistonPos.getY() + 1.01, closingPistonPos.getZ() + 1.01,
                    ColorReference.HIGHLIGHT_CODE);
            
            if (!minecraft.player.isCreative()) {
                
                clearHighlight();
                return;
            }
            
            if (!(minecraft.world.getBlockState(openingPistonPos).getBlock().getLocalizedName().equals("Piston") ||
                    minecraft.world.getBlockState(openingPistonPos).getBlock().getLocalizedName().equals("Sticky Piston"))) {
    
                clearHighlight();
                return;
            }
    
            if (!(minecraft.world.getBlockState(closingPistonPos).getBlock().getLocalizedName().equals("Piston") ||
                    minecraft.world.getBlockState(closingPistonPos).getBlock().getLocalizedName().equals("Sticky Piston"))) {
                
                if (BlockUtils.hasOppositePiston(openingPistonPos)) {
                    
                    closingPistonPos = BlockUtils.getOppositePiston(openingPistonPos);
                    
                } else {
                    clearHighlight();
                }
            }
        }
    }
    
    private void clearHighlight() {
        doPistonHighlight = false;
        openingPistonPos = null;
        closingPistonPos = null;
    }
}