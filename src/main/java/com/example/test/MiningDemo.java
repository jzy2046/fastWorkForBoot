package com.example.test;


import java.util.Scanner;

/**
 * @author hzy13811
 */
public class MiningDemo {
    private static final Double[] DAY_MONEY = {0.617, 0.667, 0.917};
    private static final Double[] NIGHT_MONEY = {0.307, 0.337, 0.487};
    private static final Integer ONE_GRADE =260;
    private static final Integer TWO_GRADE =400;
    private static final Double ALL_TIME =24.0;
    private static final Double DAY_TIME =14.0;
    private static final Double NIGHT_TIME =8.0;
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("输入用电量:\n");
        Double nowElectricity =scanner.nextDouble();
        System.out.println("输入用电方式:1.一直半天用 2.一直晚上用 3.全天用\n");
        Integer option =scanner.nextInt();
        Double res = selectAllDayRun(DAY_MONEY, NIGHT_MONEY, nowElectricity ,option);
        if (res>0){
            System.out.println("电费应付:"+res+"元,平均每度费用:"+res/nowElectricity+"元");
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
                res = getRes(dayMoney, nowElectricity);
                break;
            case 2:
                res = getRes(nightMoney, nowElectricity);
                break;
            case 3:
                if (nowElectricity<=ONE_GRADE ){
                    res = (nightMoney[0]*nowElectricity)*(NIGHT_TIME / ALL_TIME)+(dayMoney[0]*nowElectricity)*(DAY_TIME / ALL_TIME);
                }else if (nowElectricity>ONE_GRADE &&nowElectricity<= TWO_GRADE){
                    res=((nightMoney[0]*ONE_GRADE +nightMoney[1]*(TWO_GRADE -ONE_GRADE )))*(NIGHT_TIME / ALL_TIME)+((dayMoney[0]*ONE_GRADE +dayMoney[1]*(TWO_GRADE -ONE_GRADE )))*(DAY_TIME / ALL_TIME);
                }else {
                    res=((nightMoney[0]*ONE_GRADE +nightMoney[1]*(TWO_GRADE -ONE_GRADE ))+nightMoney[2]*(nowElectricity- TWO_GRADE))*(NIGHT_TIME / ALL_TIME)+((dayMoney[0]*ONE_GRADE +dayMoney[1]*(TWO_GRADE -ONE_GRADE ))+dayMoney[2]*(nowElectricity- TWO_GRADE))*(DAY_TIME / ALL_TIME);
                }
                break;
            default:
                res = 0.0;
                break;
        }
        return res;
    }

    private static Double getRes(Double[] dayMoney, Double nowElectricity) {
        Double res;
        if (nowElectricity<=ONE_GRADE ){
            res = dayMoney[0]*nowElectricity;
        }else if (nowElectricity>ONE_GRADE &&nowElectricity<= TWO_GRADE){
            res=(dayMoney[0]*ONE_GRADE +dayMoney[1]*(TWO_GRADE -ONE_GRADE ));
        }else {
            res=(dayMoney[0]*ONE_GRADE +dayMoney[1]*(TWO_GRADE -ONE_GRADE ))+dayMoney[2]*(nowElectricity- TWO_GRADE);
        }
        return res;
    }
}
