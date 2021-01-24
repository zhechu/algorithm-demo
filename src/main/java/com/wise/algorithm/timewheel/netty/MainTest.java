package com.wise.algorithm.timewheel.netty;

import io.netty.util.HashedWheelTimer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MainTest {

  public static void main(String[] args) throws InterruptedException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(1, TimeUnit.SECONDS);
    System.out.println("start:" + LocalDateTime.now().format(formatter));

    AtomicInteger count = new AtomicInteger(1);
    for (int i = 0; i < 100; i++) {
      Thread.sleep(1000);

      hashedWheelTimer.newTimeout(timeout -> {
        System.out.println("task :" + count.getAndIncrement() + "\t" + LocalDateTime.now().format(formatter));
      }, 3, TimeUnit.SECONDS);
    }

    Thread.sleep(5000);
  }

}
