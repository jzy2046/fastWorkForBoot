package com.example.demo.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Table(name = "enemy")
public class Enemy {
    @Id
    @Column(name="id")
    private int id;

    /*血量*/
    @Column(name="health_points")
    private Long healthPoints;

    /*攻击力*/
    @Column(name="attack")
    private Long attack;
    /*攻击间隔*/
    @Column(name="interval")
    private Integer interval;

    /*掉落金币*/
    @Column(name="gold")
    private Long gold;
}
