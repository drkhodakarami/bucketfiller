package jiraiyah.bucketfiller.gui.screen;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import jiraiyah.bucketfiller.gui.description.FillerDescription;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;


public class FillerScreen extends CottonInventoryScreen<FillerDescription>
{
    public FillerScreen(FillerDescription container, PlayerEntity player, Text title)
    {
        super(container, player, title);
    }
}