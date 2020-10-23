package eu.javaspecialists.concurrent.playground.phaser.cojoining;

import eu.javaspecialists.concurrent.playground.phaser.cojoining.impl.*;

import java.util.function.*;
import java.util.stream.*;

public class TestAll {
  public static void main(String... args) {
    for (int i = 0; i < 10; i++) {
      test();
      System.out.println();
    }
    CojoinedTasksTester.shutdown();
  }

  private static void test() {
    Stream.<Supplier<Cojoiner>>of(
        NoneCojoiner::new,
        WaitNotifyCojoiner::new,
        CountDownLatchCojoiner::new,
        VolatileSpinCojoiner::new,
        PhaserCojoiner::new
    )
        .forEach(CojoinedTasksTester::test);
  }
}
