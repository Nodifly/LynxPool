package mods.notifly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Mod(modid = LynxPool.MODID, version = LynxPool.VERSION)
public class LynxPool
{
    public static final String MODID = "lynxpool";
    public static final String VERSION = "1.0";

    public static List namesList = new ArrayList();
    public static String player = "";
    public static Boolean doingReports = false;
    private static Boolean inList = false;

    private String PP_KEY_CAT = "LynxPool";

    public KeyBinding target;
    public KeyBinding reject;
    public KeyBinding BanBL7d;
    public KeyBinding BanBLPerm;
    public KeyBinding port;
    public KeyBinding tpto;
    public KeyBinding mode;
    public KeyBinding userinfo;
    public KeyBinding bans;

    final String regex = "of (\\w{3,16})";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);

        ClientCommandHandler.instance.registerCommand(new TargetCommand());
        ClientCommandHandler.instance.registerCommand(new DisplayNamesCommand());

        this.target = new KeyBinding("Target next report", Keyboard.KEY_NUMPAD0, PP_KEY_CAT);
        this.reject = new KeyBinding("[Status] Reject", Keyboard.KEY_DELETE, PP_KEY_CAT);
        this.BanBL7d = new KeyBinding("[Ban] - Blacklisted 7d", Keyboard.KEY_NUMPAD7, PP_KEY_CAT);
        this.BanBLPerm = new KeyBinding("[Ban] - Blacklisted Perm", Keyboard.KEY_NUMPAD9, PP_KEY_CAT);
        this.port = new KeyBinding("[Movement] - Port", Keyboard.KEY_RETURN, PP_KEY_CAT);
        this.tpto = new KeyBinding("[Movement] - TpTo", Keyboard.KEY_DECIMAL, PP_KEY_CAT);
        this.userinfo = new KeyBinding("[INFO] - Userinfo", Keyboard.KEY_INSERT, PP_KEY_CAT);
        this.bans = new KeyBinding("[INFO] - Bans", Keyboard.KEY_HOME, PP_KEY_CAT);
        this.mode = new KeyBinding("[Mode] - Reports/Not Reports", Keyboard.KEY_ADD, PP_KEY_CAT);

        ClientRegistry.registerKeyBinding(this.BanBL7d);
        ClientRegistry.registerKeyBinding(this.BanBLPerm);
        ClientRegistry.registerKeyBinding(this.port);
        ClientRegistry.registerKeyBinding(this.tpto);
        ClientRegistry.registerKeyBinding(this.userinfo);
        ClientRegistry.registerKeyBinding(this.bans);
        ClientRegistry.registerKeyBinding(this.target);
        ClientRegistry.registerKeyBinding(this.reject);
    }

    @SubscribeEvent
    public void keyPress(InputEvent.KeyInputEvent e) {
        if (this.target.isPressed()) {
            if (!doingReports) {
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You are not in report mode!"));
            } else {
                if ((namesList.size() == 0) && (player == "")) {
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("No reports in queue!"));
                } else {
                    if (player == "") {
                        player = namesList.get(0).toString();
                        Minecraft.getMinecraft().thePlayer.sendChatMessage("/tpto " + player);
                        Minecraft.getMinecraft().thePlayer.sendChatMessage("/userinfo " + player);
                        Minecraft.getMinecraft().thePlayer.sendChatMessage("/bans " + player);
                    } else {
                        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You must reject last report first!"));
                    }
                }
            }
        } else if (this.BanBL7d.isPressed()) {
            if (!doingReports) {
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You are not in report mode!"));
            } else {
                if (player == "") {
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You're not watching anyone!"));
                } else { // watching someone
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/tempban " + player + " 7d Blacklisted Modifications");
                    namesList.remove(0);
                    player = "";
                }
            }
        } else if (this.BanBLPerm.isPressed()) {
            if (!doingReports) {
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You are not in report mode!"));
            } else {
                if (player == "") {
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You're not watching anyone!"));
                } else { // watching someone
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/ban " + player + " Blacklisted Modifications");
                    namesList.remove(0);
                    player = "";
                }
            }
        } else if (this.port.isPressed()) {
            if (!doingReports) {
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You are not in report mode!"));
            } else {
                if (player == "") {
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You're not watching anyone!"));
                } else { // watching someone
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/port " + player);
                }
            }
        } else if (this.tpto.isPressed()) {
            if (!doingReports) {
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You are not in report mode!"));
            } else {
                if (player == "") {
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You're not watching anyone!"));
                } else { // watching someone
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/tpto " + player);
                }
            }

        } else if (this.userinfo.isPressed()) {
            if (!doingReports) {
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You are not in report mode!"));
            } else {
                if (player == "") {
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You're not watching anyone!"));
                } else { // watching someone
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/userinfo " + player);
                }
            }
        } else if (this.bans.isPressed()) {
            if (!doingReports) {
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You are not in report mode!"));
            } else {
                if (player == "") {
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You're not watching anyone!"));
                } else { // watching someone
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/bans " + player);
                }
            }
        } else if (this.mode.isPressed()) {
            if (!doingReports) {
                doingReports = true;
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You are now in report mode!"));
            } else { // doing reports currently
                doingReports = false;
                player = "";
                namesList.clear();
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You are no longer in report mode!"));
            }
        } else if (this.reject.isPressed()) {
            if (!doingReports) {
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You are not in report mode!"));
            } else {
                if (player == "") {
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("You're not watching anyone!"));
                } else { // watching someone
                    if (namesList.size() != 0) {
                        namesList.remove(0);
                    }
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(player + " was rejected"));
                    player = "";

                }
            }
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String msg = event.message.getUnformattedText();
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(msg);
        if (msg.contains("[STAFF] [ADMIN] Fishy:")) {   // if fishy msg
            event.setCanceled(true);
            while (matcher.find()) { // when it finds correct msg

                inList = false; // resets for next report
                Integer i = 0;
                if (!namesList.isEmpty()) { // if list has items in it
                    while (!inList && i < namesList.size()) { // whist not found name, and still within list
                        if (namesList.get(i).equals(matcher.group(1))) { // if found in list
                            //Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(matcher.group(1) + " already in list!"));
                            String player = matcher.group(1);
                            inList = true;
                        }
                        i++;
                    }
                    if (!inList) { // if user is not in list
                        namesList.add(matcher.group(1));
                    }
                } else {
                    namesList.add(matcher.group(1));
                }
            }
        }

    }

}
