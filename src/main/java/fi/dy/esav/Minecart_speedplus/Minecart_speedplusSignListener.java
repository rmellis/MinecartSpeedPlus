package fi.dy.esav.Minecart_speedplus;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class Minecart_speedplusSignListener implements Listener {
    Minecart_speedplus plugin;

    public Minecart_speedplusSignListener(Minecart_speedplus instance) {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignChange(SignChangeEvent e) {
        if (!(e.line(0) instanceof TextComponent text0)) { return; }
        var line0 = text0.content();
        if (!line0.equalsIgnoreCase("[msp]")) { return; }

        if (!(e.line(1) instanceof TextComponent text1)) { return; }
        var line1 = text1.content();

        if (line1.equalsIgnoreCase("fly") || line1.equalsIgnoreCase("nofly")) {
            if (!(e.getPlayer().hasPermission("msp.signs.fly"))) {
                e.line(0, Component.text("NO PERMS"));
            }
            return;
        }
        var error = false;
        var speed = -1D;

        try {
            speed = Double.parseDouble(line1);
        } catch (Exception ex) {
            error = true;
        }

        if (error || 50 < speed || speed < 0) {
            e.line(1, Component.text("WRONG VALUE"));
        }

        if (!(e.getPlayer().hasPermission("msp.signs.speed"))) {
            e.line(0, Component.text("NO PERMS"));
        }
    }
}
