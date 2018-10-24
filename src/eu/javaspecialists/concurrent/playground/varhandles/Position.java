package eu.javaspecialists.concurrent.playground.varhandles;

import java.lang.invoke.*;

public class Position {
  private volatile double[] xy;

  public Position(double x, double y) {
    xy = new double[]{x, y};
  }

  public void moveBy(double deltaX, double deltaY) {
    double[] current, next = {0,0};
    do {
      current = xy;
      next[0] = current[0] + deltaX;
      next[1] = current[1] + deltaY;
    } while(!XY.compareAndSet(this, current, next));
  }

  public  double distanceFromOrigin() {
    double[] current = xy;
    return Math.hypot(current[0], current[1]);
  }

  private final static VarHandle XY;
  static {
    try {
      XY = MethodHandles.lookup().findVarHandle(
          Position.class, "xy", double[].class);
    } catch (ReflectiveOperationException e) {
      throw new Error(e);
    }
  }
}