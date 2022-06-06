package com.fdobrotv.algorithms.strings.robot_bounded_in_circle;

class Implementation {
    //O(n) time | O(1) space
    public boolean isRobotBounded(String instructions) {
        int x = 0;
        int y = 0;
        int direction = 0;
        int testIterations = 3;

        while (testIterations >= 0) {
            testIterations--;
            for (int i = 0; i < instructions.length(); i++) {
                char command = instructions.charAt(i);
                switch (command) {
                    case 'G' -> {
                        if (direction == 0)
                            y++;
                        else if (direction == 1)
                            x++;
                        else if (direction == 2)
                            y--;
                        else
                            x--;
                    }
                    case 'L' -> {
                        if (direction == 0)
                            direction = 3;
                        else
                            direction--;
                    }
                    case 'R' -> {
                        if (direction == 3)
                            direction = 0;
                        else
                            direction++;
                    }
                    default -> throw new RuntimeException("Command parsing error!");
                }
            }
            if (x == 0 && y == 0)
                return true;
        }

        return false;
    }
}

