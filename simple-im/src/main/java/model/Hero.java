package model;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;


/**
 * Creat by yml on  10:18
 */
@Data
public class Hero implements Serializable {
    /**
     * hero id  Updated by gh on 2019/09/20 at 17:37
     */
    private long id;

    // 武将名字
    private String name;
    
    // 血量上限
    private int hpUpperLimit;

    // 技能
    /**
     * Updated by gh on 2019/09/20 at 17:45
     * String: 技能名称
     * Integer： 主被动技能标识 ——2=主公技; 1=主动技； 0=被动技
     */
    private Map<String, Integer> skills;
    /**
     * Updated by gh on 2019/09/20 at 17:49
     * turn = false : 非自己的回合
     * turn = true : 自己的回合
     */
    private boolean turn = false;
    
    // 英雄性别(1:男； 0：女)
    private int gender;

    // 所属阵营(1:群雄；2：魏；3：吴；4蜀)
    private int camp;
}
