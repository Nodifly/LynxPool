package mods.notifly;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.List;

/**
 * Created by Dan on 18/06/2017.
 */
public class DisplayNamesCommand implements ICommand {

    private final List<String> aliases = Lists.newArrayList("displaynames");

    @Override
    public String getCommandName() {
        return "displaynames";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "displaynames";
    }

    @Override
    public List<String> getCommandAliases() {
        return aliases;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        Integer i = 0;
        for (i = 0; i < LynxPool.namesList.size(); i++) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Name " + i + ": " + LynxPool.namesList.get(i)));
        }
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Length: " + LynxPool.namesList.size()));
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
