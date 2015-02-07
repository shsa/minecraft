package demo.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import demo.Log;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerTracking
{
	public static boolean showPlayerInfo = false;
	
	@SubscribeEvent
	public void onStartTracking(PlayerEvent.StartTracking event)
	{
		showPlayerInfo = true;
		Log.msg("StartTracking");
	}
	
	@SubscribeEvent
	public void onStopTracking(PlayerEvent.StopTracking event)
	{
		showPlayerInfo = false;
		Log.msg("StopTracking");
	}
}
