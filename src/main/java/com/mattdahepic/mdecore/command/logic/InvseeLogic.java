package com.mattdahepic.mdecore.command.logic;

import com.mattdahepic.mdecore.command.AbstractCommand;
import com.mattdahepic.mdecore.command.ICommandLogic;
import com.mattdahepic.mdecore.command.ui.PlayerInvChest;
import com.mattdahepic.mdecore.helpers.TranslationHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

import java.util.List;

public class InvseeLogic implements ICommandLogic {
    public static InvseeLogic instance = new InvseeLogic();

    @Override
    public String getCommandName () {
        return "invsee";
    }
    @Override
    public int getPermissionLevel () {
        return 2;
    }
    @Override
    public String getCommandSyntax () {
        return TranslationHelper.getTranslatedString("mdecore.command.invsee.usage");
    }
    @Override
    public void handleCommand (ICommandSender sender, String[] args) throws CommandException {
        try {
            EntityPlayer looker = CommandBase.getCommandSenderAsPlayer(sender);
            if (!looker.worldObj.isRemote) {
                EntityPlayer lookee = CommandBase.getPlayer(sender,args[1]);
                if (looker.getName().equals(lookee.getName())) throw new CommandException(TranslationHelper.getTranslatedString("mdecore.command.invsee.selflook"));
                looker.closeScreen();
                looker.displayGUIChest(new PlayerInvChest(lookee,looker));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            AbstractCommand.throwUsages(instance);
        } catch (PlayerNotFoundException e) {
            AbstractCommand.throwNoPlayer();
        }
    }
    @Override
    public List<String> addTabCompletionOptions (ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 2) {
            return AbstractCommand.getPlayerNamesStartingWithLastArg(args);
        }
        return null;
    }
}