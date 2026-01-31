package org.terraform.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.terraform.command.contants.TerraCommand;
import org.terraform.coregen.bukkit.NativeGeneratorPatcherPopulator;
import org.terraform.coregen.bukkit.PhysicsUpdaterPopulator;
import org.terraform.main.TerraformGeneratorPlugin;

import java.util.Stack;

public class TerraCacheFlushCommand extends TerraCommand {

    public TerraCacheFlushCommand(TerraformGeneratorPlugin plugin, String... aliases) {
        super(plugin, aliases);
    }

    @Override
    public @NotNull String getDefaultDescription() {
        return "Clears the terra generator cache.";
    }

    @Override
    public boolean canConsoleExec() {
        return true;
    }

    @Override
    public boolean hasPermission(@NotNull CommandSender sender) {

        return sender.isOp();
    }

    @Override
    public void execute(@NotNull CommandSender sender, Stack<String> args)
    {
        TerraformGeneratorPlugin.get().clearCache();
        sender.sendMessage("Cleared cache.");
    }

}
