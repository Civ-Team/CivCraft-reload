
package com.avrgaming.civcraft.loregui;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import com.avrgaming.civcraft.loregui.GuiAction;
import com.avrgaming.civcraft.lorestorage.LoreGuiItem;
import com.avrgaming.civcraft.main.CivGlobal;

public class ResearchGui
implements GuiAction {
    @Override
    public void performAction(InventoryClickEvent event, ItemStack stack) {
        Player player = (Player)event.getWhoClicked();
        String oh = LoreGuiItem.getActionData(stack, "info");
        String startResearch = "civ research on " + oh;
        String addToQueue = "civ research queueadd " + oh;
        if (CivGlobal.getResident(player).getCiv().getResearchTech() == null) {
            Bukkit.dispatchCommand((CommandSender)player, (String)startResearch);
        } else {
            Bukkit.dispatchCommand((CommandSender)player, (String)addToQueue);
        }
        player.closeInventory();
    }
}

