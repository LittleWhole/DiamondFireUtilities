package dfutils.commands.itemcontrol.lore;

import dfutils.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import static dfutils.utils.MessageUtils.actionMessage;
import static dfutils.utils.MessageUtils.errorMessage;
import static dfutils.utils.MessageUtils.infoMessage;

class CommandLoreAdd {
    
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    
    static void executeAddLore(ICommandSender sender, String[] commandArgs) {
    
        //Checks if command format is valid.
        if (!checkFormat(sender, commandArgs)) return;
    
        commandArgs[1] = CommandBase.buildString(commandArgs, 1);
        ItemStack itemStack = minecraft.player.getHeldItemMainhand();
        
        //Checks if item is not air.
        if (itemStack.isEmpty()) {
            errorMessage("Invalid item!");
            return;
        }
        
        //Checks if item has NBT tag, if not, adds NBT tag.
        if (itemStack.getTagCompound() == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
    
        //Checks if item has display tag, if not, adds display tag.
        if (!itemStack.getTagCompound().hasKey("display", 10)) {
            itemStack.getTagCompound().setTag("display", new NBTTagCompound());
        }
        
        NBTTagCompound nbtTag = itemStack.getSubCompound("display");
    
        //Checks if item has Lore tag, if not, adds Lore tag.
        if (!nbtTag.hasKey("Lore", 9)) {
            nbtTag.setTag("Lore", new NBTTagList());
        }
    
        commandArgs[1] = TextUtils.parseColorCodes(commandArgs[1]);
        
        //Adds lore tag to item.
        nbtTag.getTagList("Lore", 8).appendTag(new NBTTagString(commandArgs[1]));
        
        //Sends updated item to the server.
        minecraft.playerController.sendSlotPacket(itemStack, minecraft.player.inventoryContainer.inventorySlots.size() - 10 + minecraft.player.inventory.currentItem);
    
        actionMessage("Added lore to item.");
    
    }
    
    private static boolean checkFormat(ICommandSender sender, String[] commandArgs) {
        if (commandArgs.length >= 2) {
            return true;
            
        } else {
            infoMessage("Usage:\n" + new CommandLoreBase().getUsage(sender));
            return false;
        }
    }
}
