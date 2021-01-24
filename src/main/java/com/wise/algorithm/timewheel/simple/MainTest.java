package com.wise.algorithm.timewheel.simple;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainTest {

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(2) ;
    RingBufferWheel wheel = new RingBufferWheel(executorService,64) ;
    for (int i = 0; i < 65; i++) {
      System.out.println("task size=" + wheel.taskSize() + ", task map size=" + wheel.taskMapSize());
      RingBufferWheel.Task task = new MsgTask(String.valueOf(i)) ;
      task.setKey(i);
      wheel.addTask(task);
    }

    System.out.println("task size=" + wheel.taskSize() + ", task map size=" + wheel.taskMapSize());

    wheel.stop(false);
  }

}
