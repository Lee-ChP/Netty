package model;

import lombok.Data;
import utils.IDUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Data
public class  Player {

   
            
    
    private String username;

    private Long userId;

    //状态
    private String status;

    //在房间的顺序
    private int index;

    //类型
    private String type;

    //房间号
    private String roomId;

    //手里的牌
    private List<Card> handCards;
  

    //装备区的牌: Added by gh on 2019/09/24 at 14:12
    private List<Card> paddockCards;
    
    
    //判定区牌:   Added by gh on 2019/09/24 at 14:31
    private List<Card> decideCards;
    
    
    //选择的英雄id
    private int heroId;

    // 英雄实体
    private Hero hero;

    //是否是自己的回合: Added by gh on 2019/09/24 at 14:49
    private boolean turn;

    //分配的英雄选项
    private int[] heroOptions;

    //分配的角色
    private int role;

    //血量
    private int hp;

    //最大血量
    private int hpLimit;

    // 防御距离: Added by gh on 2019/09/24 at 15:29
    private int defenseDistance = 1;

    // 攻击距离: Added by gh on 2019/09/24 at 15:29
    private int strikingDistance = 1;

    // 是否存活
    private boolean die = false;

    // 是否
    private boolean concatenate = false;
    
    public Player() {
        this.index = 2;
        this.handCards = new ArrayList<>();
        this.userId = (long)(Math.random() * 9000.0D + 1000.0D);
        //杀
        Card cardKill = new Card();
      
        cardKill.setColor(2);
        cardKill.setPoint(10);
        cardKill.setId(107);
        cardKill.setType(1);
        cardKill.setName("Kill");
       
        //闪
        Card cardFlash = new Card();
        cardFlash.setName("Flash");
        cardFlash.setPoint(7);
        cardFlash.setType(1);
        cardFlash.setColor(4);
        cardFlash.setId(152);
        
        //桃
        Card cardPeach = new Card();
        cardPeach.setName("Peach");
        cardPeach.setPoint(12);
        cardPeach.setType(1);
        cardPeach.setColor(3);
        cardPeach.setId(22);
        
        //无懈可击
        Card cardInvulnerable = new Card();
        cardInvulnerable.setName("Invulnerable");
        cardInvulnerable.setPoint(12);
        cardInvulnerable.setType(1);
        cardInvulnerable.setColor(3);
        cardInvulnerable.setId(22);
        
        //乐不思蜀
        Card cardLBSS = new Card();
        cardLBSS.setName("LBSS");
        cardLBSS.setPoint(12);
        cardLBSS.setType(1);
        cardLBSS.setColor(3);
        cardLBSS.setId(22);
        
        this.handCards.add(cardKill);
        this.handCards.add(cardFlash);
        this.handCards.add(cardPeach);
        this.handCards.add(cardInvulnerable);
        this.handCards.add(cardLBSS);
    }
}
