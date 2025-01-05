package examplefuncsplayer;
import java.util.*;
import battlecode.common.*;


public abstract class MainPhase {

    public static void runMainPhase(RobotController rc) throws GameActionException {
        
        if(rc.canBuyGlobal(GlobalUpgrade.ACTION)){
            rc.buyGlobal(GlobalUpgrade.ACTION);
        }
        if(rc.canBuyGlobal(GlobalUpgrade.CAPTURING)){
            rc.buyGlobal(GlobalUpgrade.CAPTURING);
        }
        RobotInfo[] nearbyEnimies = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
        for(RobotInfo robot: nearbyEnimies){
            if(robot.hasFlag()){
                Pathfind.moveTowards(rc,robot.getLocation());
                if(rc.canAttack(robot.getLocation())) rc.attack(robot.getLocation());
            }
        }
        for(RobotInfo robot: nearbyEnimies){
            if(rc.canAttack(robot.getLocation())) rc.attack(robot.getLocation());
        }
        for(RobotInfo robot: rc.senseNearbyRobots(-1, rc.getTeam())){
            if(rc.canHeal(robot.getLocation())) rc.heal(robot.getLocation());
        }

        if(!rc.hasFlag()) {
            ArrayList <MapLocation> flagLocs = new ArrayList<>();
            FlagInfo[] enemyFlags = rc.senseNearbyFlags(-1, rc.getTeam().opponent());
            for(FlagInfo flag : enemyFlags) flagLocs.add(flag.getLocation());
            if(flagLocs.size() == 0){
                MapLocation[] broadcastLocs = rc.senseBroadcastFlagLocations();
                for(MapLocation flagLoc: broadcastLocs) flagLocs.add(flagLoc);
            }

            MapLocation closestFlag = findCloseLocation(rc.getLocation(), flagLocs);
            if(closestFlag != null){
                Pathfind.moveTowards(rc, closestFlag);
                if(rc.canPickupFlag(closestFlag)) rc.pickupFlag(closestFlag);
            }
            Pathfind.explore(rc);
        }
        else {
            MapLocation [] spawnLocs = rc.getAllySpawnLocations();
            MapLocation closestSpawn = findCloseLocation(rc.getLocation(), Arrays.asList(spawnLocs));
            Pathfind.moveTowards(rc, closestSpawn);
        }

    }
    
    public static MapLocation findCloseLocation (MapLocation me, List<MapLocation> otherLocs){
        MapLocation closest = null;
        int minDist = Integer.MAX_VALUE;
        for(MapLocation loc: otherLocs){
            int dist = me.distanceSquaredTo(loc);
            if(dist < minDist) {
                minDist = dist;
                closest = loc;
            }
        }
        return closest;
    }
}
