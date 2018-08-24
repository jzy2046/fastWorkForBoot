package com.example.demo.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Table(name = "hero")
public class Hero {
    @Id
    @Column(name="id")
    private Integer id;

    /*名称*/
    @Column(name="hero_name")
    private String heroName;

    /*血量*/
    @Column(name="health_points")
    private Long healthPoints;

    /*攻击力*/
    @Column(name="attack")
    private Long attack;

    /*攻击间隔*/
    @Column(name="interval")
    private Integer interval;
    /*金币*/
    @Column(name="sumGold")
    private Long sumGold;
}
