package eu.javaspecialists.concurrent.playground.stampedlock;

import java.util.concurrent.locks.*;

public class Position {
  private final StampedLock sl = new StampedLock();
  private double x, y;

  public Position(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public void moveBy(double deltaX, double deltaY) {
    long stamp = sl.writeLock();
    try {
      x += deltaX;
      y += deltaY;
    } finally {
      sl.unlockWrite(stamp);
    }
  }

  public double distanceFromOrigin() {
    long stamp = sl.tryOptimisticRead();
    double currentX = x, currentY = y;
    if (!sl.validate(stamp)) {
      stamp = sl.readLock();
      try {
        currentX = x;
        currentY = y;
      } finally {
        sl.unlockRead(stamp);
      }
    }
    return Math.hypot(currentX, currentY);
  }
}

