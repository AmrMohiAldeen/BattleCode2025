package examplefuncsplayer;

import battlecode.common.*;

import java.util.*;
/**
 * RobotPlayer is the class that describes your main robot strategy.
 * The run() method inside this class is like your main function: this is what we'll call once your robot
 * is created!
 */
public class RobotPlayer {
    public static Random random = null;
    public static void run(RobotController rc) throws GameActionException{
    	while(true) {
    		try
    		{
				if(random == null) random = new Random(rc.getID());
    			trySpawn(rc);
    			if(rc.isSpawned())
				{
					int round = rc.getRoundNum();
					if(round <= GameConstants.SETUP_ROUNDS) Setup.runSetup(rc);
					else MainPhase.runMainPhase(rc);
				}
    		} 
			catch(GameActionException e)
			{
				e.printStackTrace();
			} 
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally {
    			Clock.yield();
    		}
    	}
    }
  



	private static void trySpawn(RobotController rc) throws GameActionException {
		MapLocation[] locations = rc.getAllySpawnLocations();
		for(MapLocation loc: locations)
		{
			if(rc.canSpawn(loc))
			{
				rc.spawn(loc);
				break;
			}
		}
	}

}


