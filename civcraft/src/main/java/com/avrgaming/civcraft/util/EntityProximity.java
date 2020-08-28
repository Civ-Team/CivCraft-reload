package com.avrgaming.civcraft.util;


import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.LinkedList;

public class EntityProximity {


    /*
     * Use a NMS method to grab an axis aligned bounding box around an area to
     * determine which entities are within this radius.
     * Optionally provide an entity that is exempt from these checks.
     * Also optionally provide a filter so we can only capture specific types of entities.
     */
    public static LinkedList<Entity> getNearPlaybyEntities(Location location, double radius) {
        LinkedList<Entity> result = new LinkedList<>();
        double square = radius * radius;

        Collection<Entity> nearbyEntities = location.getWorld().getNearbyEntities(location, radius, radius, radius);

        for (Entity nearbyEntity : nearbyEntities) {
            if (nearbyEntity instanceof Player && nearbyEntity.getLocation().distanceSquared(location) < square) {
                result.add(nearbyEntity);
            }
        }

        return result;
        /*LinkedList<Entity> resultList = new LinkedList<>();
        double x = loc.getX() + 0.5;
        double y = loc.getY() + 0.5;
        double z = loc.getZ() + 0.5;
        double r = radius;
        World world =  loc.getWorld();
        world.getNearbyEntities()
        AxisAlignedBB bb = new AxisAlignedBB(x - r, y - r, z - r, x + r, y + r, z + r);

        List<Entity> tempList;
        if (exempt != null) {
            tempList = craftWorld.getHandle().getEntities(((Entity) exempt).getHandle(), bb);
        } else {
            tempList = craftWorld.getHandle().getEntities(null, bb);
        }

        for (Entity e : tempList) {
            if (filter == null || (filter.isInstance(e))) {
                resultList.add(e);
            }
        }
        return resultList;*/
    }

}
