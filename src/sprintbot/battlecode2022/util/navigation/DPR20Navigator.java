package sprintbot.battlecode2022.util.navigation;

import battlecode.common.*;
import static battlecode.common.Direction.*;
import sprintbot.battlecode2022.util.Navigator;

/**
 * Suitable for robots with a vision range of 20
 * Costs ~ 3600 Bytecodess
 */
public class DPR20Navigator extends Navigator
{
	private static final int INF_MARBLE = 1000000;
	private RobotController rc;
	private final int UPPER_BOUND_TURN_PER_GRID;
	private final int MOVE_COOLDOWN;
	private final int X_BOUND;
	private final int Y_BOUND;
	private final int[] MARBLE_COST_LOOKUP;
	
	public DPR20Navigator(RobotController controller)
	{
		super(controller);
		rc = controller;
		X_BOUND = rc.getMapWidth() - 1;    // [0, X_BOUND]
		Y_BOUND = rc.getMapHeight() - 1;
		MOVE_COOLDOWN = rc.getType().movementCooldown;
		UPPER_BOUND_TURN_PER_GRID = MOVE_COOLDOWN * 110;
		MARBLE_COST_LOOKUP = new int[]{(int) (1.0 * MOVE_COOLDOWN) * 10,
				(int) (1.1 * MOVE_COOLDOWN) * 10,
				(int) (1.2 * MOVE_COOLDOWN) * 10,
				(int) (1.3 * MOVE_COOLDOWN) * 10,
				(int) (1.4 * MOVE_COOLDOWN) * 10,
				(int) (1.5 * MOVE_COOLDOWN) * 10,
				(int) (1.6 * MOVE_COOLDOWN) * 10,
				(int) (1.7 * MOVE_COOLDOWN) * 10,
				(int) (1.8 * MOVE_COOLDOWN) * 10,
				(int) (1.9 * MOVE_COOLDOWN) * 10,
				(int) (2.0 * MOVE_COOLDOWN) * 10,
				(int) (2.1 * MOVE_COOLDOWN) * 10,
				(int) (2.2 * MOVE_COOLDOWN) * 10,
				(int) (2.3 * MOVE_COOLDOWN) * 10,
				(int) (2.4 * MOVE_COOLDOWN) * 10,
				(int) (2.5 * MOVE_COOLDOWN) * 10,
				(int) (2.6 * MOVE_COOLDOWN) * 10,
				(int) (2.7 * MOVE_COOLDOWN) * 10,
				(int) (2.8 * MOVE_COOLDOWN) * 10,
				(int) (2.9 * MOVE_COOLDOWN) * 10,
				(int) (3.0 * MOVE_COOLDOWN) * 10,
				(int) (3.1 * MOVE_COOLDOWN) * 10,
				(int) (3.2 * MOVE_COOLDOWN) * 10,
				(int) (3.3 * MOVE_COOLDOWN) * 10,
				(int) (3.4 * MOVE_COOLDOWN) * 10,
				(int) (3.5 * MOVE_COOLDOWN) * 10,
				(int) (3.6 * MOVE_COOLDOWN) * 10,
				(int) (3.7 * MOVE_COOLDOWN) * 10,
				(int) (3.8 * MOVE_COOLDOWN) * 10,
				(int) (3.9 * MOVE_COOLDOWN) * 10,
				(int) (4.0 * MOVE_COOLDOWN) * 10,
				(int) (4.1 * MOVE_COOLDOWN) * 10,
				(int) (4.2 * MOVE_COOLDOWN) * 10,
				(int) (4.3 * MOVE_COOLDOWN) * 10,
				(int) (4.4 * MOVE_COOLDOWN) * 10,
				(int) (4.5 * MOVE_COOLDOWN) * 10,
				(int) (4.6 * MOVE_COOLDOWN) * 10,
				(int) (4.7 * MOVE_COOLDOWN) * 10,
				(int) (4.8 * MOVE_COOLDOWN) * 10,
				(int) (4.9 * MOVE_COOLDOWN) * 10,
				(int) (5.0 * MOVE_COOLDOWN) * 10,
				(int) (5.1 * MOVE_COOLDOWN) * 10,
				(int) (5.2 * MOVE_COOLDOWN) * 10,
				(int) (5.3 * MOVE_COOLDOWN) * 10,
				(int) (5.4 * MOVE_COOLDOWN) * 10,
				(int) (5.5 * MOVE_COOLDOWN) * 10,
				(int) (5.6 * MOVE_COOLDOWN) * 10,
				(int) (5.7 * MOVE_COOLDOWN) * 10,
				(int) (5.8 * MOVE_COOLDOWN) * 10,
				(int) (5.9 * MOVE_COOLDOWN) * 10,
				(int) (6.0 * MOVE_COOLDOWN) * 10,
				(int) (6.1 * MOVE_COOLDOWN) * 10,
				(int) (6.2 * MOVE_COOLDOWN) * 10,
				(int) (6.3 * MOVE_COOLDOWN) * 10,
				(int) (6.4 * MOVE_COOLDOWN) * 10,
				(int) (6.5 * MOVE_COOLDOWN) * 10,
				(int) (6.6 * MOVE_COOLDOWN) * 10,
				(int) (6.7 * MOVE_COOLDOWN) * 10,
				(int) (6.8 * MOVE_COOLDOWN) * 10,
				(int) (6.9 * MOVE_COOLDOWN) * 10,
				(int) (7.0 * MOVE_COOLDOWN) * 10,
				(int) (7.1 * MOVE_COOLDOWN) * 10,
				(int) (7.2 * MOVE_COOLDOWN) * 10,
				(int) (7.3 * MOVE_COOLDOWN) * 10,
				(int) (7.4 * MOVE_COOLDOWN) * 10,
				(int) (7.5 * MOVE_COOLDOWN) * 10,
				(int) (7.6 * MOVE_COOLDOWN) * 10,
				(int) (7.7 * MOVE_COOLDOWN) * 10,
				(int) (7.8 * MOVE_COOLDOWN) * 10,
				(int) (7.9 * MOVE_COOLDOWN) * 10,
				(int) (8.0 * MOVE_COOLDOWN) * 10,
				(int) (8.1 * MOVE_COOLDOWN) * 10,
				(int) (8.2 * MOVE_COOLDOWN) * 10,
				(int) (8.3 * MOVE_COOLDOWN) * 10,
				(int) (8.4 * MOVE_COOLDOWN) * 10,
				(int) (8.5 * MOVE_COOLDOWN) * 10,
				(int) (8.6 * MOVE_COOLDOWN) * 10,
				(int) (8.7 * MOVE_COOLDOWN) * 10,
				(int) (8.8 * MOVE_COOLDOWN) * 10,
				(int) (8.9 * MOVE_COOLDOWN) * 10,
				(int) (9.0 * MOVE_COOLDOWN) * 10,
				(int) (9.1 * MOVE_COOLDOWN) * 10,
				(int) (9.2 * MOVE_COOLDOWN) * 10,
				(int) (9.3 * MOVE_COOLDOWN) * 10,
				(int) (9.4 * MOVE_COOLDOWN) * 10,
				(int) (9.5 * MOVE_COOLDOWN) * 10,
				(int) (9.6 * MOVE_COOLDOWN) * 10,
				(int) (9.7 * MOVE_COOLDOWN) * 10,
				(int) (9.8 * MOVE_COOLDOWN) * 10,
				(int) (9.9 * MOVE_COOLDOWN) * 10,
				(int) (10.0 * MOVE_COOLDOWN) * 10,
				(int) (10.1 * MOVE_COOLDOWN) * 10,
				(int) (10.2 * MOVE_COOLDOWN) * 10,
				(int) (10.3 * MOVE_COOLDOWN) * 10,
				(int) (10.4 * MOVE_COOLDOWN) * 10,
				(int) (10.5 * MOVE_COOLDOWN) * 10,
				(int) (10.6 * MOVE_COOLDOWN) * 10,
				(int) (10.7 * MOVE_COOLDOWN) * 10,
				(int) (10.8 * MOVE_COOLDOWN) * 10,
				(int) (10.9 * MOVE_COOLDOWN) * 10,
				(int) (11.0 * MOVE_COOLDOWN) * 10};
	}
	
	public MoveResult move(MapLocation target_loc) throws GameActionException
	{
		if (!rc.isMovementReady())
			return MoveResult.FAIL;
		if (target_loc == null)
			return MoveResult.IMPOSSIBLE;
		int tar_loc_x = target_loc.x;
		int tar_loc_y = target_loc.y;
		if (tar_loc_x < 0 || tar_loc_y < 0
				|| tar_loc_x > X_BOUND || tar_loc_y > Y_BOUND)
			return MoveResult.IMPOSSIBLE;
		MapLocation current_loc = rc.getLocation();
		int cur_loc_x = current_loc.x;
		int cur_loc_y = current_loc.y;
		if (current_loc == target_loc)
			return MoveResult.REACHED;
		//System.out.printf("Check Complete, left = 4380597\n", Clock.getBytecodesLeft());
		int d_0_0 = 0;
		int d_n1_n1 = (rc.canMove(SOUTHWEST)) ? (((rc.canSenseLocation(current_loc.translate(-1,-1))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-1,-1))]) : (INF_MARBLE)) + 1) : (INF_MARBLE);
		int d_n1_0 = (rc.canMove(WEST)) ? (((rc.canSenseLocation(current_loc.translate(-1,0))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-1,0))]) : (INF_MARBLE)) + 2) : (INF_MARBLE);
		int d_n1_1 = (rc.canMove(NORTHWEST)) ? (((rc.canSenseLocation(current_loc.translate(-1,1))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-1,1))]) : (INF_MARBLE)) + 3) : (INF_MARBLE);
		int d_0_n1 = (rc.canMove(SOUTH)) ? (((rc.canSenseLocation(current_loc.translate(0,-1))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(0,-1))]) : (INF_MARBLE)) + 4) : (INF_MARBLE);
		int d_0_1 = (rc.canMove(NORTH)) ? (((rc.canSenseLocation(current_loc.translate(0,1))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(0,1))]) : (INF_MARBLE)) + 5) : (INF_MARBLE);
		int d_1_n1 = (rc.canMove(SOUTHEAST)) ? (((rc.canSenseLocation(current_loc.translate(1,-1))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(1,-1))]) : (INF_MARBLE)) + 6) : (INF_MARBLE);
		int d_1_0 = (rc.canMove(EAST)) ? (((rc.canSenseLocation(current_loc.translate(1,0))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(1,0))]) : (INF_MARBLE)) + 7) : (INF_MARBLE);
		int d_1_1 = (rc.canMove(NORTHEAST)) ? (((rc.canSenseLocation(current_loc.translate(1,1))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(1,1))]) : (INF_MARBLE)) + 8) : (INF_MARBLE);
		int d_n2_n2 = d_n1_n1 + ((rc.canSenseLocation(current_loc.translate(-2,-2))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-2,-2))]) : (INF_MARBLE));
		int d_n2_n1 = Math.min(d_n1_n1,d_n1_0) + ((rc.canSenseLocation(current_loc.translate(-2,-1))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-2,-1))]) : (INF_MARBLE));
		int d_n2_0 = Math.min(Math.min(d_n1_n1,d_n1_0),d_n1_1) + ((rc.canSenseLocation(current_loc.translate(-2,0))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-2,0))]) : (INF_MARBLE));
		int d_n2_1 = Math.min(d_n1_0,d_n1_1) + ((rc.canSenseLocation(current_loc.translate(-2,1))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-2,1))]) : (INF_MARBLE));
		int d_n2_2 = d_n1_1 + ((rc.canSenseLocation(current_loc.translate(-2,2))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-2,2))]) : (INF_MARBLE));
		int d_n1_n2 = Math.min(d_n1_n1,d_0_n1) + ((rc.canSenseLocation(current_loc.translate(-1,-2))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-1,-2))]) : (INF_MARBLE));
		int d_n1_2 = Math.min(d_n1_1,d_0_1) + ((rc.canSenseLocation(current_loc.translate(-1,2))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-1,2))]) : (INF_MARBLE));
		int d_0_n2 = Math.min(Math.min(d_n1_n1,d_0_n1),d_1_n1) + ((rc.canSenseLocation(current_loc.translate(0,-2))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(0,-2))]) : (INF_MARBLE));
		int d_0_2 = Math.min(Math.min(d_n1_1,d_0_1),d_1_1) + ((rc.canSenseLocation(current_loc.translate(0,2))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(0,2))]) : (INF_MARBLE));
		int d_1_n2 = Math.min(d_0_n1,d_1_n1) + ((rc.canSenseLocation(current_loc.translate(1,-2))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(1,-2))]) : (INF_MARBLE));
		int d_1_2 = Math.min(d_0_1,d_1_1) + ((rc.canSenseLocation(current_loc.translate(1,2))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(1,2))]) : (INF_MARBLE));
		int d_2_n2 = d_1_n1 + ((rc.canSenseLocation(current_loc.translate(2,-2))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(2,-2))]) : (INF_MARBLE));
		int d_2_n1 = Math.min(d_1_n1,d_1_0) + ((rc.canSenseLocation(current_loc.translate(2,-1))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(2,-1))]) : (INF_MARBLE));
		int d_2_0 = Math.min(Math.min(d_1_n1,d_1_0),d_1_1) + ((rc.canSenseLocation(current_loc.translate(2,0))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(2,0))]) : (INF_MARBLE));
		int d_2_1 = Math.min(d_1_0,d_1_1) + ((rc.canSenseLocation(current_loc.translate(2,1))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(2,1))]) : (INF_MARBLE));
		int d_2_2 = d_1_1 + ((rc.canSenseLocation(current_loc.translate(2,2))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(2,2))]) : (INF_MARBLE));
		int d_n3_n3 = d_n2_n2 + ((rc.canSenseLocation(current_loc.translate(-3,-3))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-3,-3))]) : (INF_MARBLE));
		int d_n3_n2 = Math.min(d_n2_n2,d_n2_n1) + ((rc.canSenseLocation(current_loc.translate(-3,-2))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-3,-2))]) : (INF_MARBLE));
		int d_n3_n1 = Math.min(Math.min(d_n2_n2,d_n2_n1),d_n2_0) + ((rc.canSenseLocation(current_loc.translate(-3,-1))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-3,-1))]) : (INF_MARBLE));
		int d_n3_0 = Math.min(Math.min(d_n2_n1,d_n2_0),d_n2_1) + ((rc.canSenseLocation(current_loc.translate(-3,0))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-3,0))]) : (INF_MARBLE));
		int d_n3_1 = Math.min(Math.min(d_n2_0,d_n2_1),d_n2_2) + ((rc.canSenseLocation(current_loc.translate(-3,1))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-3,1))]) : (INF_MARBLE));
		int d_n3_2 = Math.min(d_n2_1,d_n2_2) + ((rc.canSenseLocation(current_loc.translate(-3,2))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-3,2))]) : (INF_MARBLE));
		int d_n3_3 = d_n2_2 + ((rc.canSenseLocation(current_loc.translate(-3,3))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-3,3))]) : (INF_MARBLE));
		int d_n2_n3 = Math.min(d_n2_n2,d_n1_n2) + ((rc.canSenseLocation(current_loc.translate(-2,-3))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-2,-3))]) : (INF_MARBLE));
		int d_n2_3 = Math.min(d_n2_2,d_n1_2) + ((rc.canSenseLocation(current_loc.translate(-2,3))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-2,3))]) : (INF_MARBLE));
		int d_n1_n3 = Math.min(Math.min(d_n2_n2,d_n1_n2),d_0_n2) + ((rc.canSenseLocation(current_loc.translate(-1,-3))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-1,-3))]) : (INF_MARBLE));
		int d_n1_3 = Math.min(Math.min(d_n2_2,d_n1_2),d_0_2) + ((rc.canSenseLocation(current_loc.translate(-1,3))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-1,3))]) : (INF_MARBLE));
		int d_0_n3 = Math.min(Math.min(d_n1_n2,d_0_n2),d_1_n2) + ((rc.canSenseLocation(current_loc.translate(0,-3))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(0,-3))]) : (INF_MARBLE));
		int d_0_3 = Math.min(Math.min(d_n1_2,d_0_2),d_1_2) + ((rc.canSenseLocation(current_loc.translate(0,3))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(0,3))]) : (INF_MARBLE));
		int d_1_n3 = Math.min(Math.min(d_0_n2,d_1_n2),d_2_n2) + ((rc.canSenseLocation(current_loc.translate(1,-3))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(1,-3))]) : (INF_MARBLE));
		int d_1_3 = Math.min(Math.min(d_0_2,d_1_2),d_2_2) + ((rc.canSenseLocation(current_loc.translate(1,3))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(1,3))]) : (INF_MARBLE));
		int d_2_n3 = Math.min(d_1_n2,d_2_n2) + ((rc.canSenseLocation(current_loc.translate(2,-3))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(2,-3))]) : (INF_MARBLE));
		int d_2_3 = Math.min(d_1_2,d_2_2) + ((rc.canSenseLocation(current_loc.translate(2,3))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(2,3))]) : (INF_MARBLE));
		int d_3_n3 = d_2_n2 + ((rc.canSenseLocation(current_loc.translate(3,-3))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(3,-3))]) : (INF_MARBLE));
		int d_3_n2 = Math.min(d_2_n2,d_2_n1) + ((rc.canSenseLocation(current_loc.translate(3,-2))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(3,-2))]) : (INF_MARBLE));
		int d_3_n1 = Math.min(Math.min(d_2_n2,d_2_n1),d_2_0) + ((rc.canSenseLocation(current_loc.translate(3,-1))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(3,-1))]) : (INF_MARBLE));
		int d_3_0 = Math.min(Math.min(d_2_n1,d_2_0),d_2_1) + ((rc.canSenseLocation(current_loc.translate(3,0))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(3,0))]) : (INF_MARBLE));
		int d_3_1 = Math.min(Math.min(d_2_0,d_2_1),d_2_2) + ((rc.canSenseLocation(current_loc.translate(3,1))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(3,1))]) : (INF_MARBLE));
		int d_3_2 = Math.min(d_2_1,d_2_2) + ((rc.canSenseLocation(current_loc.translate(3,2))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(3,2))]) : (INF_MARBLE));
		int d_3_3 = d_2_2 + ((rc.canSenseLocation(current_loc.translate(3,3))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(3,3))]) : (INF_MARBLE));
		int d_n4_n2 = Math.min(Math.min(d_n3_n3,d_n3_n2),d_n3_n1) + ((rc.canSenseLocation(current_loc.translate(-4,-2))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-4,-2))]) : (INF_MARBLE));
		int d_n4_n1 = Math.min(Math.min(d_n3_n2,d_n3_n1),d_n3_0) + ((rc.canSenseLocation(current_loc.translate(-4,-1))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-4,-1))]) : (INF_MARBLE));
		int d_n4_0 = Math.min(Math.min(d_n3_n1,d_n3_0),d_n3_1) + ((rc.canSenseLocation(current_loc.translate(-4,0))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-4,0))]) : (INF_MARBLE));
		int d_n4_1 = Math.min(Math.min(d_n3_0,d_n3_1),d_n3_2) + ((rc.canSenseLocation(current_loc.translate(-4,1))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-4,1))]) : (INF_MARBLE));
		int d_n4_2 = Math.min(Math.min(d_n3_1,d_n3_2),d_n3_3) + ((rc.canSenseLocation(current_loc.translate(-4,2))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-4,2))]) : (INF_MARBLE));
		int d_n2_n4 = Math.min(Math.min(d_n3_n3,d_n2_n3),d_n1_n3) + ((rc.canSenseLocation(current_loc.translate(-2,-4))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-2,-4))]) : (INF_MARBLE));
		int d_n2_4 = Math.min(Math.min(d_n3_3,d_n2_3),d_n1_3) + ((rc.canSenseLocation(current_loc.translate(-2,4))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-2,4))]) : (INF_MARBLE));
		int d_n1_n4 = Math.min(Math.min(d_n2_n3,d_n1_n3),d_0_n3) + ((rc.canSenseLocation(current_loc.translate(-1,-4))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-1,-4))]) : (INF_MARBLE));
		int d_n1_4 = Math.min(Math.min(d_n2_3,d_n1_3),d_0_3) + ((rc.canSenseLocation(current_loc.translate(-1,4))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(-1,4))]) : (INF_MARBLE));
		int d_0_n4 = Math.min(Math.min(d_n1_n3,d_0_n3),d_1_n3) + ((rc.canSenseLocation(current_loc.translate(0,-4))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(0,-4))]) : (INF_MARBLE));
		int d_0_4 = Math.min(Math.min(d_n1_3,d_0_3),d_1_3) + ((rc.canSenseLocation(current_loc.translate(0,4))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(0,4))]) : (INF_MARBLE));
		int d_1_n4 = Math.min(Math.min(d_0_n3,d_1_n3),d_2_n3) + ((rc.canSenseLocation(current_loc.translate(1,-4))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(1,-4))]) : (INF_MARBLE));
		int d_1_4 = Math.min(Math.min(d_0_3,d_1_3),d_2_3) + ((rc.canSenseLocation(current_loc.translate(1,4))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(1,4))]) : (INF_MARBLE));
		int d_2_n4 = Math.min(Math.min(d_1_n3,d_2_n3),d_3_n3) + ((rc.canSenseLocation(current_loc.translate(2,-4))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(2,-4))]) : (INF_MARBLE));
		int d_2_4 = Math.min(Math.min(d_1_3,d_2_3),d_3_3) + ((rc.canSenseLocation(current_loc.translate(2,4))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(2,4))]) : (INF_MARBLE));
		int d_4_n2 = Math.min(Math.min(d_3_n3,d_3_n2),d_3_n1) + ((rc.canSenseLocation(current_loc.translate(4,-2))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(4,-2))]) : (INF_MARBLE));
		int d_4_n1 = Math.min(Math.min(d_3_n2,d_3_n1),d_3_0) + ((rc.canSenseLocation(current_loc.translate(4,-1))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(4,-1))]) : (INF_MARBLE));
		int d_4_0 = Math.min(Math.min(d_3_n1,d_3_0),d_3_1) + ((rc.canSenseLocation(current_loc.translate(4,0))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(4,0))]) : (INF_MARBLE));
		int d_4_1 = Math.min(Math.min(d_3_0,d_3_1),d_3_2) + ((rc.canSenseLocation(current_loc.translate(4,1))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(4,1))]) : (INF_MARBLE));
		int d_4_2 = Math.min(Math.min(d_3_1,d_3_2),d_3_3) + ((rc.canSenseLocation(current_loc.translate(4,2))) ? (MARBLE_COST_LOOKUP[rc.senseRubble(current_loc.translate(4,2))]) : (INF_MARBLE));
		int minDistance = 1000000000;
		int dctx = cur_loc_x - tar_loc_x;
		int dcty = cur_loc_y - tar_loc_y;
		minDistance = Math.min(minDistance, d_n4_n2 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-4 + dctx), Math.abs(-2 + dcty)));
		minDistance = Math.min(minDistance, d_n4_n1 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-4 + dctx), Math.abs(-1 + dcty)));
		minDistance = Math.min(minDistance, d_n4_0 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-4 + dctx), Math.abs(0 + dcty)));
		minDistance = Math.min(minDistance, d_n4_1 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-4 + dctx), Math.abs(1 + dcty)));
		minDistance = Math.min(minDistance, d_n4_2 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-4 + dctx), Math.abs(2 + dcty)));
		minDistance = Math.min(minDistance, d_n3_n3 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-3 + dctx), Math.abs(-3 + dcty)));
		minDistance = Math.min(minDistance, d_n3_n2 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-3 + dctx), Math.abs(-2 + dcty)));
		minDistance = Math.min(minDistance, d_n3_n1 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-3 + dctx), Math.abs(-1 + dcty)));
		minDistance = Math.min(minDistance, d_n3_0 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-3 + dctx), Math.abs(0 + dcty)));
		minDistance = Math.min(minDistance, d_n3_1 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-3 + dctx), Math.abs(1 + dcty)));
		minDistance = Math.min(minDistance, d_n3_2 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-3 + dctx), Math.abs(2 + dcty)));
		minDistance = Math.min(minDistance, d_n3_3 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-3 + dctx), Math.abs(3 + dcty)));
		minDistance = Math.min(minDistance, d_n2_n4 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-2 + dctx), Math.abs(-4 + dcty)));
		minDistance = Math.min(minDistance, d_n2_n3 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-2 + dctx), Math.abs(-3 + dcty)));
		minDistance = Math.min(minDistance, d_n2_n2 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-2 + dctx), Math.abs(-2 + dcty)));
		minDistance = Math.min(minDistance, d_n2_n1 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-2 + dctx), Math.abs(-1 + dcty)));
		minDistance = Math.min(minDistance, d_n2_0 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-2 + dctx), Math.abs(0 + dcty)));
		minDistance = Math.min(minDistance, d_n2_1 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-2 + dctx), Math.abs(1 + dcty)));
		minDistance = Math.min(minDistance, d_n2_2 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-2 + dctx), Math.abs(2 + dcty)));
		minDistance = Math.min(minDistance, d_n2_3 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-2 + dctx), Math.abs(3 + dcty)));
		minDistance = Math.min(minDistance, d_n2_4 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-2 + dctx), Math.abs(4 + dcty)));
		minDistance = Math.min(minDistance, d_n1_n4 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-1 + dctx), Math.abs(-4 + dcty)));
		minDistance = Math.min(minDistance, d_n1_n3 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-1 + dctx), Math.abs(-3 + dcty)));
		minDistance = Math.min(minDistance, d_n1_n2 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-1 + dctx), Math.abs(-2 + dcty)));
		minDistance = Math.min(minDistance, d_n1_n1 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-1 + dctx), Math.abs(-1 + dcty)));
		minDistance = Math.min(minDistance, d_n1_0 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-1 + dctx), Math.abs(0 + dcty)));
		minDistance = Math.min(minDistance, d_n1_1 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-1 + dctx), Math.abs(1 + dcty)));
		minDistance = Math.min(minDistance, d_n1_2 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-1 + dctx), Math.abs(2 + dcty)));
		minDistance = Math.min(minDistance, d_n1_3 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-1 + dctx), Math.abs(3 + dcty)));
		minDistance = Math.min(minDistance, d_n1_4 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(-1 + dctx), Math.abs(4 + dcty)));
		minDistance = Math.min(minDistance, d_0_n4 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(0 + dctx), Math.abs(-4 + dcty)));
		minDistance = Math.min(minDistance, d_0_n3 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(0 + dctx), Math.abs(-3 + dcty)));
		minDistance = Math.min(minDistance, d_0_n2 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(0 + dctx), Math.abs(-2 + dcty)));
		minDistance = Math.min(minDistance, d_0_n1 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(0 + dctx), Math.abs(-1 + dcty)));
		minDistance = Math.min(minDistance, d_0_0 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(0 + dctx), Math.abs(0 + dcty)));
		minDistance = Math.min(minDistance, d_0_1 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(0 + dctx), Math.abs(1 + dcty)));
		minDistance = Math.min(minDistance, d_0_2 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(0 + dctx), Math.abs(2 + dcty)));
		minDistance = Math.min(minDistance, d_0_3 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(0 + dctx), Math.abs(3 + dcty)));
		minDistance = Math.min(minDistance, d_0_4 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(0 + dctx), Math.abs(4 + dcty)));
		minDistance = Math.min(minDistance, d_1_n4 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(1 + dctx), Math.abs(-4 + dcty)));
		minDistance = Math.min(minDistance, d_1_n3 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(1 + dctx), Math.abs(-3 + dcty)));
		minDistance = Math.min(minDistance, d_1_n2 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(1 + dctx), Math.abs(-2 + dcty)));
		minDistance = Math.min(minDistance, d_1_n1 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(1 + dctx), Math.abs(-1 + dcty)));
		minDistance = Math.min(minDistance, d_1_0 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(1 + dctx), Math.abs(0 + dcty)));
		minDistance = Math.min(minDistance, d_1_1 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(1 + dctx), Math.abs(1 + dcty)));
		minDistance = Math.min(minDistance, d_1_2 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(1 + dctx), Math.abs(2 + dcty)));
		minDistance = Math.min(minDistance, d_1_3 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(1 + dctx), Math.abs(3 + dcty)));
		minDistance = Math.min(minDistance, d_1_4 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(1 + dctx), Math.abs(4 + dcty)));
		minDistance = Math.min(minDistance, d_2_n4 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(2 + dctx), Math.abs(-4 + dcty)));
		minDistance = Math.min(minDistance, d_2_n3 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(2 + dctx), Math.abs(-3 + dcty)));
		minDistance = Math.min(minDistance, d_2_n2 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(2 + dctx), Math.abs(-2 + dcty)));
		minDistance = Math.min(minDistance, d_2_n1 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(2 + dctx), Math.abs(-1 + dcty)));
		minDistance = Math.min(minDistance, d_2_0 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(2 + dctx), Math.abs(0 + dcty)));
		minDistance = Math.min(minDistance, d_2_1 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(2 + dctx), Math.abs(1 + dcty)));
		minDistance = Math.min(minDistance, d_2_2 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(2 + dctx), Math.abs(2 + dcty)));
		minDistance = Math.min(minDistance, d_2_3 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(2 + dctx), Math.abs(3 + dcty)));
		minDistance = Math.min(minDistance, d_2_4 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(2 + dctx), Math.abs(4 + dcty)));
		minDistance = Math.min(minDistance, d_3_n3 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(3 + dctx), Math.abs(-3 + dcty)));
		minDistance = Math.min(minDistance, d_3_n2 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(3 + dctx), Math.abs(-2 + dcty)));
		minDistance = Math.min(minDistance, d_3_n1 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(3 + dctx), Math.abs(-1 + dcty)));
		minDistance = Math.min(minDistance, d_3_0 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(3 + dctx), Math.abs(0 + dcty)));
		minDistance = Math.min(minDistance, d_3_1 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(3 + dctx), Math.abs(1 + dcty)));
		minDistance = Math.min(minDistance, d_3_2 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(3 + dctx), Math.abs(2 + dcty)));
		minDistance = Math.min(minDistance, d_3_3 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(3 + dctx), Math.abs(3 + dcty)));
		minDistance = Math.min(minDistance, d_4_n2 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(4 + dctx), Math.abs(-2 + dcty)));
		minDistance = Math.min(minDistance, d_4_n1 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(4 + dctx), Math.abs(-1 + dcty)));
		minDistance = Math.min(minDistance, d_4_0 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(4 + dctx), Math.abs(0 + dcty)));
		minDistance = Math.min(minDistance, d_4_1 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(4 + dctx), Math.abs(1 + dcty)));
		minDistance = Math.min(minDistance, d_4_2 + UPPER_BOUND_TURN_PER_GRID * Math.max(Math.abs(4 + dctx), Math.abs(2 + dcty)));
		if (minDistance >= INF_MARBLE)
			return MoveResult.FAIL;
		switch (minDistance % 10)
		{
			case 1:
				if (rc.canMove(SOUTHWEST))
				{
					rc.move(SOUTHWEST);
					return MoveResult.SUCCESS;
				}
				else
					return MoveResult.FAIL;
			case 2:
				if (rc.canMove(WEST))
				{
					rc.move(WEST);
					return MoveResult.SUCCESS;
				}
				else
					return MoveResult.FAIL;
			case 3:
				if (rc.canMove(NORTHWEST))
				{
					rc.move(NORTHWEST);
					return MoveResult.SUCCESS;
				}
				else
					return MoveResult.FAIL;
			case 4:
				if (rc.canMove(SOUTH))
				{
					rc.move(SOUTH);
					return MoveResult.SUCCESS;
				}
				else
					return MoveResult.FAIL;
			case 5:
				if (rc.canMove(NORTH))
				{
					rc.move(NORTH);
					return MoveResult.SUCCESS;
				}
				else
					return MoveResult.FAIL;
			case 6:
				if (rc.canMove(SOUTHEAST))
				{
					rc.move(SOUTHEAST);
					return MoveResult.SUCCESS;
				}
				else
					return MoveResult.FAIL;
			case 7:
				if (rc.canMove(EAST))
				{
					rc.move(EAST);
					return MoveResult.SUCCESS;
				}
				else
					return MoveResult.FAIL;
			case 8:
				if (rc.canMove(NORTHEAST))
				{
					rc.move(NORTHEAST);
					return MoveResult.SUCCESS;
				}
				else
					return MoveResult.FAIL;
			default:
				return MoveResult.FAIL;
		}
	}
}