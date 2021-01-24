package com.wise.algorithm.timewheel.dubbo;

import org.apache.dubbo.common.timer.HashedWheelTimer;
import org.apache.dubbo.common.utils.NamedThreadFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MainTest {

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(2) ;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(
        new NamedThreadFactory("hashedWheelTimer", true),
        1,
        TimeUnit.SECONDS
    );
    System.out.println("start:" + LocalDateTime.now().format(formatter));

    AtomicInteger count = new AtomicInteger(1);
    for (int i = 0; i < 100; i++) {
      Thread.sleep(1000);

      hashedWheelTimer.newTimeout(timeout -> {
        System.out.println(Thread.currentThread() + " task :" + count.getAndIncrement() + "\t" + LocalDateTime.now().format(formatter));
        executorService.submit(() -> {
          try {
            Thread.sleep(3000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.println(Thread.currentThread() + " run\t" + LocalDateTime.now().format(formatter));
        });
      }, 3, TimeUnit.SECONDS);
    }

    Thread.sleep(5000);
  }

}
