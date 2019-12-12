
package com.avrgaming.civcraft.items.units;

import com.avrgaming.civcraft.config.CivSettings;
import com.avrgaming.civcraft.config.ConfigUnit;
import com.avrgaming.civcraft.exception.CivException;
import com.avrgaming.civcraft.lorestorage.LoreMaterial;
import com.avrgaming.civcraft.main.CivCraft;
import com.avrgaming.civcraft.main.CivMessage;
import com.avrgaming.civcraft.object.Town;
import com.avrgaming.civcraft.util.CivColor;
import com.avrgaming.civcraft.war.War;
import gpl.AttributeUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Swordsman extends UnitMaterial {
    public Swordsman(String id, ConfigUnit configUnit) {
        super(id, configUnit);
    }

    public static void spawn(Inventory inv, Town town) throws CivException {
        ItemStack is = LoreMaterial.spawn(Unit.SWORDSMAN_ARTIFACT);
        Swordsman.setOwningTown(town, is);
        AttributeUtil attrs = new AttributeUtil(is);
        attrs.addEnhancement("LoreEnhancementSoulBound", null, null);
        attrs.addLore(CivColor.Gold + CivSettings.localize.localizedString("itemLore_Souldbound"));
        attrs.addLore(CivColor.Yellow + "Level 2");
        attrs.addLore(CivColor.LightGray + "Effect:");
        attrs.addLore(CivColor.LightGray + "Passive");
        attrs.addLore(CivColor.LightGray + "+40% Sword Attack Bonus");  //剑伤害
        attrs.addLore(CivColor.LightGray + "-20% Bow Damage");
        is = attrs.getStack();
        if (!Unit.addItemNoStack(inv, is)) {
            throw new CivException(CivSettings.localize.localizedString("var_arrtifacts_errorBarracksFull", Unit.SWORDSMAN_ARTIFACT.getUnit().name));
        }
    }

    @Override
    public void onPlayerDeath(EntityDeathEvent event, ItemStack stack) {
        Player player = (Player) event.getEntity();
        Random random = CivCraft.civRandom;
        int destroyChance = random.nextInt(100);
        if (War.isWarTime()) {
            CivMessage.send((Object) player, CivColor.YellowBold + CivSettings.localize.localizedString("var_arrtifacts_Keept_Since_WarTime_No_Rolled"));
        } else {
            CivMessage.send((Object) player, CivColor.LightBlueBold + CivSettings.localize.localizedString("var_arrtifacts_Keept_Since_No_WarTime"));
        }
        if (5 < destroyChance) {
            this.removeChildren(player.getInventory());
            CivMessage.send((Object) player, CivColor.RoseBold + CivSettings.localize.localizedString("var_arrtifacts_Break", destroyChance));
        }
    }
}

