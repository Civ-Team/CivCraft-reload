package com.avrgaming.civcraft.mobs;

import com.avrgaming.civcraft.config.ConfigMobs;
import org.bukkit.entity.Entity;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class CustomMobListener {

	public static ConcurrentHashMap<UUID, Entity> customMobs = new ConcurrentHashMap<>();
	public static ConcurrentHashMap<UUID, ConfigMobs> mobList = new ConcurrentHashMap<>();
}