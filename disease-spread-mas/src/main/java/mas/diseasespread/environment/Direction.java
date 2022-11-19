package mas.diseasespread.environment;

import java.util.Arrays;

import mas.diseasespread.data.SimulationParams;

public enum Direction {
  NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST;

  public static Direction directionOf(GridLocation current, GridLocation next) {
    int[] diffs = { current.getRow() - next.getRow(), current.getCol() - next.getCol() };

    if (Arrays.equals(diffs, new int[] { 1, 0 })) {
      return Direction.NORTH;
    }

    if (Arrays.equals(diffs, new int[] { -1, 0 })) {
      return Direction.SOUTH;
    }

    if (Arrays.equals(diffs, new int[] { 0, -1 })) {
      return Direction.EAST;
    }

    if (Arrays.equals(diffs, new int[] { 0, 1 })) {
      return Direction.WEST;
    }

    if (Arrays.equals(diffs, new int[] { 1, -1 })) {
      return Direction.NORTH_EAST;
    }

    if (Arrays.equals(diffs, new int[] { -1, -1 })) {
      return Direction.SOUTH_EAST;
    }

    if (Arrays.equals(diffs, new int[] { -1, 1 })) {
      return Direction.SOUTH_WEST;
    }

    if (Arrays.equals(diffs, new int[] { 1, 1 })) {
      return Direction.NORTH_WEST;
    }

    return null;
  }

  public Direction randomDirection() {
    Direction[] values = Direction.values();
    int bound = values.length;
    int rand = SimulationParams.getInstance().getRandom().nextInt(bound);
    return values[rand];
  }

}
