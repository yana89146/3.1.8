package org.example;

public class Main {
    public static void main(String[] args) {


}
}


class RobotConnectionException extends RuntimeException {
    public RobotConnectionException(String message) {
        super(message);
    }

    public RobotConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}


    interface RobotConnection extends AutoCloseable {
        void moveRobotTo(int x, int y);
        @Override
        void close();
    }


    interface RobotConnectionManager {
        RobotConnection getConnection();
    }


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