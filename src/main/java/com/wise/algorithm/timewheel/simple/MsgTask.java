package com.wise.algorithm.timewheel.simple;

public class MsgTask extends RingBufferWheel.Task {

  private String msg;

  public MsgTask(String msg) {
      this.msg = msg;
  }

  @Override
  public void run() {
    System.out.println("msg:" + msg);
  }

}