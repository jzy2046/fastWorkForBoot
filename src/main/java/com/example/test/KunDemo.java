package com.example.test;

import java.util.Scanner;

/**
 * @author hzy13811
 */
public class KunDemo {
    //峰值费用
    private static final Double[] DAY_MONEY = {0.5283, 0.5783, 0.8283};
    //谷值费用
    private static final Double[] NIGHT_MONEY = {0.3583, 0.4083, 0.6583};
    //第二档阈值
    private static final Integer ONE_GRADE =230;
    //第三档阈值
    private static final Integer TWO_GRADE =400;
    private static final Double ALL_TIME =24.0;
    //高峰时长
    private static final Double DAY_TIME =13.0;
    //低谷时长
    private static final Double NIGHT_TIME =11.0;
    //每度电产出
    private static final Double GET_PRICE =0.5;
    //最多使用电量
    private static final Integer MAX=800;
    //每小时耗电量(度)
    private static final Double HOURLY_POWER=1.05;
    //月天数
    private static final Integer NUMBEROFDAYSINTHEMONTH=31;
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("输入服务内容: 1. 查询全用电度数和收益 2.根据查询应收电费详情\n");
        int i = scanner.nextInt();
        if (i==1){
            for (int j=1;j<=MAX;j=j+1){
                System.out.println("------------------start----------------------");
                Double res = selectAllDayRun(DAY_MONEY, NIGHT_MONEY, (double) j,3);
                System.out.println(j+"度,电费应付:"+res+"元,平均每度费用:"+res/ (double) j +"元\n");
                //System.out.println("如果每度电产出为"+GET_PRICE+",获取收益为:每天"+(GET_PRICE*24-(res/31.0))+"\n");
                System.out.println("每月收益:"+(GET_PRICE*j-res));
                System.out.println("--------------------end--------------------");
            }
        }else if (i==2){
            Double nowElectricity = 0.0;
            System.out.println("已单月31天计算输入用电方式:\n 1.一直半天用 2.一直晚上用 3.每天全是开机(电量值) 4.全天用最优使用方式(谷值占满)\n");
            Integer option =scanner.nextInt();
            if (3 == option){
                nowElectricity= HOURLY_POWER*NUMBEROFDAYSINTHEMONTH*ALL_TIME;
            }else {
                System.out.println("输入用电量:\n");
                nowElectricity=scanner.nextDouble();
            }
            Double res = selectAllDayRun(DAY_MONEY, NIGHT_MONEY, nowElectricity,option);
            if (res>0){
                System.out.println("电费应付:"+res+"元,平均每度费用:"+res/nowElectricity+"元");
            }else {
                System.out.println("输入错误!");
            }
        }
    }
    private static Double selectAllDayRun(Double[] dayMoney,Double[] nightMoney,Double nowElectricity,
                                          Integer option){//option 1=白天运行,2-晚上运行,3全天运行
        Double res=0.0;
        if (nowElectricity<0){
            return res;
        }
        switch (option){
            case 1:
                res = getRes(dayMoney, nowElectricity);
                break;
            case 2:
                res = getRes(nightMoney, nowElectricity);
                break;
            case 3:
                res=((nightMoney[0]*ONE_GRADE +nightMoney[1]*(nowElectricity - ONE_GRADE ))+nightMoney[2]*(nowElectricity- TWO_GRADE))*(NIGHT_TIME / ALL_TIME)+((dayMoney[0]*ONE_GRADE +dayMoney[1]*(nowElectricity - ONE_GRADE ))+dayMoney[2]*(nowElectricity- TWO_GRADE))*(DAY_TIME / ALL_TIME);
                break;
            case 4:
                Double res1 =0.0;
                double allElect = NIGHT_TIME * NUMBEROFDAYSINTHEMONTH * HOURLY_POWER;
                if (allElect>=nowElectricity){
                    res = getRes(nightMoney, allElect);
                    Double tempNowElectricity=new Double(nowElectricity);
                    if (allElect<tempNowElectricity){
                        //二挡剩余可用
                        double twoSurplus = TWO_GRADE - allElect;
                        tempNowElectricity=tempNowElectricity-allElect;
                        if (tempNowElectricity<twoSurplus){
                            res1 = dayMoney[1]*twoSurplus;
                        }else {
                            res1 = dayMoney[1]*twoSurplus+dayMoney[2]*(tempNowElectricity-twoSurplus);
                        }
                    }
                    res=res+res1;
                }else {
                    res = -1.0;
                }
                break;
            default:
                res = -1.0;
                break;
        }
        return res;
    }

    private static Double getRes(Double[] dayMoney, Double nowElectricity) {
        Double res;
        if (nowElectricity<=ONE_GRADE ){
            res = dayMoney[0]*nowElectricity;
        }else if (nowElectricity>ONE_GRADE &&nowElectricity<= TWO_GRADE){
            res=(dayMoney[0]*ONE_GRADE +dayMoney[1]*(nowElectricity - ONE_GRADE ));
        }else {
            res=(dayMoney[0]*ONE_GRADE +dayMoney[1]*(nowElectricity - ONE_GRADE ))+dayMoney[2]*(nowElectricity- TWO_GRADE);
        }
        return res;
    }
}
