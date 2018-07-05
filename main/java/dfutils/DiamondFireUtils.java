package dfutils;

import dfutils.codetools.CodeData;
import dfutils.codetools.codecopying.CopyController;
import dfutils.codetools.codecopying.CopyEventHandler;
import dfutils.codetools.codeprinting.PrintEventHandler;
import dfutils.codetools.misctools.LocationSetter;
import dfutils.codetools.misctools.PistonHighlighting;
import dfutils.codetools.commands.CommandCodeBase;
import dfutils.codetools.selection.SelectionEventHandler;
import dfutils.commands.CommandHelp;
import dfutils.commands.CommandTest;
import dfutils.commands.itemcontrol.*;
import dfutils.commands.itemcontrol.candestroy.CommandCanDestroyBase;
import dfutils.commands.itemcontrol.canplace.CommandCanPlaceBase;
import dfutils.commands.itemcontrol.flags.CommandHideFlags;
import dfutils.commands.itemcontrol.flags.CommandSetFlags;
import dfutils.commands.itemcontrol.flags.CommandShowFlags;
import dfutils.commands.shortcuts.ShortcutLastMsg;
import dfutils.commands.shortcuts.ShortcutSupportChat;
import dfutils.commands.itemcontrol.attributes.CommandAttributeBase;
import dfutils.commands.itemcontrol.lore.CommandLoreBase;
import dfutils.commands.itemcontrol.rename.CommandRename;
import dfutils.commands.itemcontrol.rename.CommandRenameAnvil;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class DiamondFireUtils {
    
    @Mod.Instance
    public static DiamondFireUtils instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        
        //Shortcut initialization.
        MinecraftForge.EVENT_BUS.register(new ShortcutLastMsg());
        MinecraftForge.EVENT_BUS.register(new ShortcutSupportChat());
        
        ClientCommandHandler commandHandler = ClientCommandHandler.instance;
        
        commandHandler.registerCommand(new CommandHelp());
        
        //Item control command initialization.
        commandHandler.registerCommand(new CommandGive());
        commandHandler.registerCommand(new CommandItemData());
        commandHandler.registerCommand(new CommandAttributeBase());
        commandHandler.registerCommand(new CommandLoreBase());
        commandHandler.registerCommand(new CommandCanDestroyBase());
        commandHandler.registerCommand(new CommandCanPlaceBase());
        commandHandler.registerCommand(new CommandRenameAnvil());
        commandHandler.registerCommand(new CommandRename());
        commandHandler.registerCommand(new CommandUnbreakable());
        commandHandler.registerCommand(new CommandBreakable());
        commandHandler.registerCommand(new CommandDurability());
        commandHandler.registerCommand(new CommandHideFlags());
        commandHandler.registerCommand(new CommandShowFlags());
        commandHandler.registerCommand(new CommandSetFlags());
        
        commandHandler.registerCommand(new CommandCodeBase());
        
        commandHandler.registerCommand(new CommandTest());
        
        MinecraftForge.EVENT_BUS.register(new CopyController());
        MinecraftForge.EVENT_BUS.register(new CopyEventHandler());
        MinecraftForge.EVENT_BUS.register(new PrintEventHandler());
        
        new CodeData();
        MinecraftForge.EVENT_BUS.register(new SelectionEventHandler());
        MinecraftForge.EVENT_BUS.register(new PistonHighlighting());
        MinecraftForge.EVENT_BUS.register(new LocationSetter());
    }
}
