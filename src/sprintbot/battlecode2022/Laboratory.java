package sprintbot.battlecode2022;

import battlecode.common.*;
import sprintbot.RunnableBot;
import sprintbot.battlecode2022.util.*;

public class Laboratory extends RunnableBot {

    int cash = 0;
    boolean has_labelled_dead = false;

    public Laboratory(RobotController rc) throws GameActionException {
        super(rc);
    }

    @Override
    public void init() throws GameActionException {
        super.init();
    }

    @Override
    public void turn() throws GameActionException {

        int num_lab = getRobotController().readSharedArray(CommandCommunicator.LAB_INDEX);
        double smooth_income = getRobotController().readSharedArray(CommandCommunicator.SMOOTH_INCOME);
        double expenditurePerTurn = smooth_income / 100.0 / num_lab / 2;

        //System.out.println(num_lab);
        //System.out.println(smooth_income / 100.0 / num_lab);

        cash += expenditurePerTurn;

        if (getRobotController().canTransmute() && cash >= getRobotController().getTransmutationRate()) {
            getRobotController().transmute();
            cash -= getRobotController().getTransmutationRate();
        }
        Cache.unit_scout_routine();

        if (Cache.opponent_total_damage > getRobotController().getHealth() && !has_labelled_dead && num_lab > 1) {
            getRobotController().writeSharedArray(CommandCommunicator.LAB_INDEX,num_lab-1);
            MatrixCommunicator.update(Communicator.Event.BUILDER_REQUEST,getRobotController().getLocation(),false);
            has_labelled_dead = true;
        }
        else if (getRobotController().getHealth() < RobotType.LABORATORY.getMaxHealth(1)) {
            MatrixCommunicator.update(Communicator.Event.BUILDER_REQUEST,getRobotController().getLocation(),true);
        }

        if (getRobotController().getHealth() >= RobotType.LABORATORY.getMaxHealth(1)) {
            MatrixCommunicator.update(Communicator.Event.BUILDER_REQUEST,getRobotController().getLocation(),false);
        }

        if (Cache.opponent_soldiers.length == 0 && has_labelled_dead) {
            getRobotController().writeSharedArray(CommandCommunicator.LAB_INDEX,num_lab+1);
            has_labelled_dead = false;
        }

    }
}
