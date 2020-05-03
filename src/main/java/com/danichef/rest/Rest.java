package com.danichef.rest;

import com.danichef.rest.commands.AdminCommand;
import com.danichef.rest.commands.LayCommand;
import com.danichef.rest.commands.SitCommand;
import com.danichef.rest.listeners.PlayerDismountListener;
import com.danichef.rest.listeners.PlayerQuitListener;
import com.danichef.rest.player.PlayerManager;
import com.danichef.rest.player.PlayerManagerImpl;
import com.danichef.rest.player.corpses.PlayerCorpses;
import com.danichef.rest.player.corpses.PlayerCorpsesImpl;
import com.danichef.rest.player.seats.PlayerSit;
import com.danichef.rest.player.seats.PlayerSitImpl;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class Rest extends JavaPlugin {

    @Getter
    private PlayerSit playerSit;
    @Getter
    private PlayerCorpses playerCorpses;
    @Getter
    private PlayerManager playerManager;

    @Override
    public void onEnable() {
        playerSit = new PlayerSitImpl();
        playerCorpses = new PlayerCorpsesImpl();
        playerManager = new PlayerManagerImpl(playerCorpses);

        getServer().getPluginManager().registerEvents(new PlayerQuitListener(playerCorpses), this);
        getServer().getPluginManager().registerEvents(new PlayerDismountListener(), this);

        getCommand("sit").setExecutor(new SitCommand(playerSit, playerManager));
        getCommand("lay").setExecutor(new LayCommand(playerCorpses, playerManager));
        getCommand("rest").setExecutor(new AdminCommand(playerSit, playerCorpses));
    }

    @Override
    public void onDisable() { }

}
