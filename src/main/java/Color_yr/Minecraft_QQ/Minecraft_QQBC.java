package Color_yr.Minecraft_QQ;

import Color_yr.Minecraft_QQ.Command.CommandBC;
import Color_yr.Minecraft_QQ.Config.Load;
import Color_yr.Minecraft_QQ.Listener.BCEvent;
import Color_yr.Minecraft_QQ.Side.IBungeecord;
import Color_yr.Minecraft_QQ.Socket.SocketControl;
import Color_yr.Minecraft_QQ.Utils.logs;
import Color_yr.Minecraft_QQ.bStats.MetricsBC;
import com.google.gson.Gson;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

public class Minecraft_QQBC extends Plugin {

    public static Logger log_b;
    public static Plugin plugin;

    public static void Load() {
        try {
            new Load(plugin.getDataFolder(), plugin.getResourceAsStream("config.json"));
        } catch (Throwable e) {
            log_b.warning("§d[Minecraft_QQ]§c配置文件读取发生错误");
            e.printStackTrace();
        }
    }

    public static void Save() {
        try {
            String data = new Gson().toJson(Minecraft_QQ.Config);
            if (Minecraft_QQ.FileName.exists()) {
                Writer out = new FileWriter(Minecraft_QQ.FileName);
                out.write(data);
                out.close();
            }
        } catch (Exception e) {
            log_b.warning("§d[Minecraft_QQ]§c配置文件保存错误");
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {

        plugin = this;
        log_b = ProxyServer.getInstance().getLogger();

        Minecraft_QQ.MinecraftQQ = new IBungeecord();

        log_b.info("§d[Minecraft_QQ]§e正在启动，感谢使用，本插件交流群：571239090");
        try {
            new logs(plugin.getDataFolder());
        } catch (IOException e) {
            log_b.warning("§d[Minecraft_QQ]§c日志文件错误");
            e.printStackTrace();
        }
        Load();

        ProxyServer.getInstance().getPluginManager().registerListener(this, new BCEvent());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new CommandBC());

        new MetricsBC(this, 6608);

        log_b.info("§d[Minecraft_QQ]§e已启动-" + Minecraft_QQ.Version);
        log_b.info("§d[Minecraft_QQ]§eDebug模式" + Minecraft_QQ.Config.getSystem().isDebug());

        SocketControl socket = new SocketControl();
        socket.Start();
    }

    @Override
    public void onDisable() {
        SocketControl socket = new SocketControl();
        socket.Close();
        log_b.info("§d[Minecraft_QQ]§e已停止，感谢使用");
    }
}