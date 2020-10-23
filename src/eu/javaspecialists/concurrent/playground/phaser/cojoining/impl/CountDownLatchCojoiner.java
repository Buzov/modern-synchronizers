package eu.javaspecialists.concurrent.playground.phaser.cojoining.impl;

import eu.javaspecialists.concurrent.playground.phaser.cojoining.*;

import java.util.concurrent.*;

public class CountDownLatchCojoiner implements Cojoiner {
  private final CountDownLatch latch = new CountDownLatch(1);
  public void runWaiter() {
    boolean interrupted = Thread.interrupted();
    while (true) {
      try {
        latch.await();
        if (interrupted) Thread.currentThread().interrupt();
        return;
      } catch (InterruptedException e) {
        interrupted = true;
      }
    }
  }

  public void runSignaller() {
    latch.countDown();
  }
}
