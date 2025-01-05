package examplefuncsplayer;
import battlecode.common.*;
public abstract class Pathfind {
    static Direction direction;
    
    public static void moveTowards (RobotController rc, MapLocation loc) throws GameActionException {
        Direction dir = rc.getLocation().directionTo(loc);
        if(rc.canMove(dir)) rc.move(dir);

        else if(rc.canFill(rc.getLocation().add(dir))) rc.fill(rc.getLocation().add(dir));
        else {
            Direction randomDir = Direction.allDirections()[RobotPlayer.random.nextInt(8)];
            if(rc.canMove(randomDir)) rc.move(randomDir);
        }
    }

    public static void explore(RobotController rc) throws GameActionException {

        MapLocation[] crumbLocs = rc.senseNearbyCrumbs(-1);
        if(crumbLocs.length > 0){
            moveTowards(rc, crumbLocs[0]);
        }

        if(rc.isMovementReady()) {
            if(direction != null && rc.canMove(direction)) rc.move(direction);
            else{
                direction = direction.allDirections()[RobotPlayer.random.nextInt(8)];
                if(rc.canMove(direction)) rc.move(direction);
            }
        }
    }
}
