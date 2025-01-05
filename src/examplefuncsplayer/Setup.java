package examplefuncsplayer;

import battlecode.common.*;

public abstract class Setup {
    private static final int EXPLORE_ROUNDS = 150;

    public static void runSetup(RobotController rc) throws GameActionException {

        if(rc.getRoundNum() < EXPLORE_ROUNDS){
            FlagInfo[] flags= rc.senseNearbyFlags(-1, rc.getTeam());
            for(FlagInfo flag: flags){
                if(rc.canPickupFlag(flag.getLocation())){
                    rc.pickupFlag(flag.getLocation());
                    break;
                }
            }
            Pathfind.explore(rc);

        }
        else {
            if(rc.senseLegalStartingFlagPlacement(rc.getLocation())){
                if(rc.canDropFlag(rc.getLocation())) rc.dropFlag(rc.getLocation());
            }

            FlagInfo[] flags = rc.senseNearbyFlags(-1, rc.getTeam());

            FlagInfo target = null;
            for(FlagInfo flag: flags)
            {
                if(!flag.isPickedUp()){
                    target = flag;
                    break;
                }
            }
            if(target != null) {
                Pathfind.moveTowards(rc, target.getLocation());
                if(rc.getLocation().distanceSquaredTo(target.getLocation()) < 9){
                    if(rc.canBuild(TrapType.EXPLOSIVE, rc.getLocation())) rc.build(TrapType.EXPLOSIVE,rc.getLocation());
                }
                else {
                    MapLocation water = rc.getLocation().add(Direction.allDirections()[RobotPlayer.random.nextInt(8)]);
                    if(rc.canDig(water)) rc.dig(water);
                }
                
            }
            else Pathfind.explore(rc);

        }
    }

}
