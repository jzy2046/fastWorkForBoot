package com.example.test;

import com.example.demo.model.Enemy;
import com.example.demo.model.Hero;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.concurrent.*;

@Slf4j
public class DemoGame {
    static Hero hero=new Hero();
    static Enemy enemy=new Enemy();
    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 7, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10));
    static ExecutorCompletionService<String> executorCompletionService = new ExecutorCompletionService(threadPoolExecutor);
    private static void init(){
        hero.setAttack(1L);
        hero.setHealthPoints(10L);
        hero.setSumGold(0L);
        hero.setInterval(990);
        enemy.setHealthPoints(2L);
        enemy.setGold(1L);
        enemy.setAttack(1L);
        enemy.setInterval(1000);
    }
    public static void main(String[] args) {
        init();//初始化
        try {
            goFighting(hero,enemy);//战斗
        } catch (InterruptedException e) {
            log.error("战斗出错->{}",e.getMessage());
        }
        getLootgold(hero,enemy);//获得金币
    }

    private static void getLootgold(Hero hero,Enemy enemy) {
        hero.setSumGold(hero.getSumGold()+enemy.getGold());
    }

    private static void goFighting(Hero hero,Enemy enemy) throws InterruptedException {
        log.info("---------------goFighting---------------");
        if (hero.getInterval().compareTo(enemy.getInterval())>=0){
            attack(enemy);
        }
        attack(hero);
    }
    static volatile int finishState = 0;
    static void attack(Object hero){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                    String name = hero.getClass().toString();
                    TestCallable testCallable = new TestCallable(name);
                    try {
                        executorCompletionService.submit(testCallable);

                        synchronized ("AAA") {
                            log.info("name->{}",name);
                        }
                    } catch (RejectedExecutionException e) {
                        synchronized ("AAA") {
                            System.out.println("拒绝：" + name);
                        }
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                finishState = 1;
            }
        };

        Thread addThread = new Thread(runnable);
        addThread.start();
    }
}
