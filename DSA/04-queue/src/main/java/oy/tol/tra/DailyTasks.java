package oy.tol.tra;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class DailyTasks {

   private QueueInterface<String> dailyTaskQueue = null;
   private Timer timer = null;
   private static final int MAX_DAILY_TASKS = 100;
   private static final int TASK_DELAY_IN_SECONDS = 2 * 1000;

   // Execute from the command line:  java -jar target/04-queue-1.0-SNAPSHOT-jar-with-dependencies.jar
   public static void main(String[] args) {
      DailyTasks tasks = new DailyTasks();
      tasks.run();
   }

   private void run() {
      try {
         dailyTaskQueue = new QueueImplementation<String>();
         dailyTaskQueue.init(String.class, MAX_DAILY_TASKS);
         readTasks();
         timer = new Timer();
         timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
               if(dailyTaskQueue.count() > 0){
                  System.out.println(dailyTaskQueue.dequeue());
               } else {
                  timer.cancel();
                  System.out.println("Out of Tasks!");
               }
            }
         }, TASK_DELAY_IN_SECONDS, TASK_DELAY_IN_SECONDS);
      } catch (IOException e) {
         System.out.println("Something went wrong :( " + e.getLocalizedMessage());
      }
   }

   private void readTasks() throws IOException {
      String tasks;
      tasks = new String(getClass().getClassLoader().getResourceAsStream("DailyTasks.txt").readAllBytes());
      String[] allTasks = tasks.split("\\r?\\n");
      int counter = 0;
      for (String task : allTasks) {
         dailyTaskQueue.enqueue(task);
         counter++;
         if (counter >= MAX_DAILY_TASKS) {
            break;
         }
      }
      System.out.println(counter);
      
   }
}
