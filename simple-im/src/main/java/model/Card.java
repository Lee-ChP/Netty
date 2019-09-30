package model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Card implements Serializable {

    // 编号id
    private int id;

    // 点数 （1 - 13，A-K）
    private int point;

    // 花色 （1 黑桃，2 红桃， 3 梅花，4 方片）
    /**
     * Updated by gh on 2019/09/20 at 18:06
     */
    private int color;

    // 牌名
    private String name;

    // 防御距离
    private int defenseDistance;

    // 攻击距离
    private int strikingDistance;

    // 手牌类型 (1,普通牌 2，锦囊牌 3，武器 4，防具 5，进攻马 6，防御马)
    private int type;
}
