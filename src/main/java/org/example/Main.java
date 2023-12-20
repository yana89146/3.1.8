package org.example;

class Solution {
    public static void moveRobot(RobotConnectionManager robotConnectionManager, int toX, int toY) {
        RobotConnection robot = null;
        boolean isClosed = false;
        for (int i = 0; i < 3; i++) {
            try {
                robot = robotConnectionManager.getConnection();
                robot.moveRobotTo(toX, toY);
                i = 3;
            } catch (RobotConnectionException e) {
                if (i == 2) {
                    if (robot != null) {
                        robot.close();
                        isClosed = true;
                    }
                    throw e;
                }
            } catch (Exception ex) {
                robot.close();
                isClosed = true;
                throw ex;
            } finally {
                try {
                    if (isClosed == false) {
                        robot.close();
                    }
                } catch (Exception exp) {

                }
            }
        }
    }
}
