/*
 * This class forms part of the Thread Safety with Phaser,
 * StampedLock and VarHandle Talk by Dr Heinz Kabutz from
 * JavaSpecialists.eu and may not be distributed without written
 * consent.
 *
 * © 2020 Heinz Max Kabutz, All rights reserved.
 */

package eu.javaspecialists.concurrent.playground.varhandles;

import java.lang.invoke.*;

/*
Best values:
	moveBy()        49,653,632
	distanceFromOrigin()         71,893,461
Worst values:
	moveBy()        42,396,441
	distanceFromOrigin()         40,145,875

 */
public class Position {
  private volatile double[] xy;

  public Position(double x, double y) {
    xy = new double[] { x, y };
  }

  public void moveBy(double deltaX, double deltaY) {
    double[] current, next = new double[2];
    do {
      current = xy;
      next[0] = current[0] + deltaX;
      next[1] = current[1] + deltaY;
    } while(!XY.compareAndSet(this, current, next));
  }

  public double distanceFromOrigin() {
    double[] current = xy;
    return Math.hypot(current[0], current[1]);
  }

  private static final VarHandle XY;

  static {
    try {
      XY = MethodHandles.lookup().findVarHandle(
          Position.class, "xy", double[].class);
    } catch (ReflectiveOperationException e) {
      throw new Error(e);
    }
  }
}
