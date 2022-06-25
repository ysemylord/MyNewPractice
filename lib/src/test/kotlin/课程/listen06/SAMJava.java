package 课程.listen06;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SAMJava {
    interface TestSAM{
        void doSomeThing();
    }
    public static void testSAM(TestSAM testSAM){

    }
    public static void main(String[] args) {
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(
                () -> {
                    System.out.println("run in executor");
                }
        );

        executor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("run in executor");
                    }
                }
        );


    }
}
