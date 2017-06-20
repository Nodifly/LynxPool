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
public class TargetCommand implements ICommand {

    private final List<String> aliases = Lists.newArrayList("target");

    @Override
    public String getCommandName() {
        return "target";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "target";
    }

    @Override
    public List<String> getCommandAliases() {
        return aliases;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Usage: /target [name]"));
        } else {
            if (!LynxPool.doingReports) {
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You are not in report mode!"));
            } else {
                if (LynxPool.player == "") {
                    LynxPool.player = args[0];
                    LynxPool.namesList.add(0, args[0]);
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/tpto " + LynxPool.player );
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/userinfo " + LynxPool.player );
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/bans " + LynxPool.player);
                } else {
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You must reject last report first!"));
                }
            }
        }
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
