package com.zhaodj.foo.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zhaodaojun on 2017/5/23.
 * 有向无环任务模型
 */
public class TaskDispatch {

    public static final int STATUS_INIT = 0;
    public static final int STATUS_RUNNING = 1;
    public static final int STATUS_FINISH = 2;

    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    private static class Task{

        private volatile int status = 0;

        private int id;
        private ExecutorService executorService;
        private List<Task> preTasks;
        private List<Task> nextTasks;

        public Task(int id, ExecutorService executorService) {
            this.id = id;
            this.executorService = executorService;
        }

        public void addNextTask(Task task){
            if(nextTasks == null){
                nextTasks = new ArrayList<>();
            }
            nextTasks.add(task);
            task.addPre(this);
        }

        public void addPre(Task task){
            if(preTasks == null){
                preTasks = new ArrayList<>();
            }
            preTasks.add(task);
        }

        public boolean isFinished(){
            return status == STATUS_FINISH;
        }

        public int getId(){
            return id;
        }

        public void execute(){
            if(status == STATUS_INIT){
                //没执行过才能开始，保证只执行一次
                synchronized (this){
                    if(status != STATUS_INIT){
                        return;
                    }
                    status = STATUS_RUNNING;
                }

                //确保依赖任务都已执行完毕
                if(preTasks != null && !preTasks.isEmpty()){
                    for(Task task : preTasks){
                        synchronized (task) {
                            while (!task.isFinished()) {
                                System.out.println(id + " task wait: " + task.getId());
                                try {
                                    task.wait();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e.getMessage(), e);
                                }
                            }
                        }
                    }
                }

                System.out.println("start task: " + id);
                try {
                    //任务执行
                    Thread.sleep(random.nextInt(5) * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }

                synchronized (this) {
                    status = STATUS_FINISH;
                    this.notifyAll();
                }
                System.out.println("end task: " + id);

                //成功以后执行后置任务
                if(nextTasks != null && !nextTasks.isEmpty()){
                    for(Task task : nextTasks){
                        executorService.submit(() -> task.execute());
                    }
                }

            }
        }

    }

    public static void main(String[] args){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Task task1 = new Task(1, executorService);
        Task task2 = new Task(2, executorService);
        Task task3 = new Task(3, executorService);
        Task task4 = new Task(4, executorService);
        Task task5 = new Task(5, executorService);
        Task task6 = new Task(6, executorService);
        Task task7 = new Task(7, executorService);
        Task task8 = new Task(8, executorService);
        Task task9 = new Task(9, executorService);
        Task task10 = new Task(10, executorService);
        task1.addNextTask(task2);
        task1.addNextTask(task3);
        task1.addNextTask(task4);
        task2.addNextTask(task5);
        task3.addNextTask(task5);
        task4.addNextTask(task6);
        task6.addNextTask(task5);
        task5.addNextTask(task7);
        task7.addNextTask(task8);
        task7.addNextTask(task9);
        task8.addNextTask(task10);
        task9.addNextTask(task10);
        task1.execute();
    }

}
