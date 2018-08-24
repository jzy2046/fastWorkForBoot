package com.example.test;


import java.util.Scanner;

/**
 * @author hzy13811
 */
public class MiningDemo {
    private static final Double dayMoney[]={0.617,0.617,0.917};
    private static final Double nightMoney[]={0.307,0.337,0.487};
    private static final Integer oneGrade=260;
    private static final Integer twoGrade=400;
    private static final Double allTime=24.0;
    private static final Double dayTime=14.0;
    private static final Double nightTime=8.0;
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("输入用电量:\n");
        Double nowElectricity =scanner.nextDouble();
        System.out.println("输入用电方式:1.一直半天用 2.一直晚上用 3.全天用\n");
        Integer option =scanner.nextInt();
        Double res = selectAllDayRun(dayMoney, nightMoney, nowElectricity ,option);
        if (res>0){
            System.out.println("电费应付:"+res+"元");
        }else {
            System.out.println("输入错误!");
        }
    }
    private static Double selectAllDayRun(Double[] dayMoney,Double[] nightMoney,Double nowElectricity,
                                          Integer option){//option 1=白天运行,2-晚上运行,3全天运行
        Double res=0.0;
        if (nowElectricity<=0){
            return res;
        }
        switch (option){
            case 1:
                if (nowElectricity<=oneGrade){
                    res = dayMoney[0]*nowElectricity;
                }else if (nowElectricity>oneGrade&&nowElectricity<=twoGrade){
                    res=(dayMoney[0]*oneGrade+dayMoney[1]*(twoGrade-oneGrade));
                }else {
                    res=(dayMoney[0]*oneGrade+dayMoney[1]*(twoGrade-oneGrade))+dayMoney[2]*(nowElectricity-twoGrade);
                }
                break;
            case 2:
                if (nowElectricity<=oneGrade){
                    res = nightMoney[0]*nowElectricity;
                }else if (nowElectricity>oneGrade&&nowElectricity<=twoGrade){
                    res=(nightMoney[0]*oneGrade+nightMoney[1]*(twoGrade-oneGrade));
                }else {
                    res=(nightMoney[0]*oneGrade+nightMoney[1]*(twoGrade-oneGrade))+nightMoney[2]*(nowElectricity-twoGrade);
                }
                break;
            case 3:
                if (nowElectricity<=oneGrade){
                    res = (nightMoney[0]*nowElectricity)*(nightTime/allTime)+(dayMoney[0]*nowElectricity)*(dayTime/allTime);
                }else if (nowElectricity>oneGrade&&nowElectricity<=twoGrade){
                    res=((nightMoney[0]*oneGrade+nightMoney[1]*(twoGrade-oneGrade)))*(nightTime/allTime)+((dayMoney[0]*oneGrade+dayMoney[1]*(twoGrade-oneGrade)))*(dayTime/allTime);
                }else {
                    res=((nightMoney[0]*oneGrade+nightMoney[1]*(twoGrade-oneGrade))+nightMoney[2]*(nowElectricity-twoGrade))*(nightTime/allTime)+((dayMoney[0]*oneGrade+dayMoney[1]*(twoGrade-oneGrade))+dayMoney[2]*(nowElectricity-twoGrade))*(dayTime/allTime);
                }
                break;
            default:
                res = 0.0;
                break;
        }
        return res;
    }
}
