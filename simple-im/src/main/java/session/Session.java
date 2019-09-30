package session;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.Player;

import java.util.List;

@Data
@NoArgsConstructor
public class Session {
    //用户唯一标识
    private String userId;
    private String username;
    private Player player;

    public Session(String userId, String username,Player player) {
        this.userId = userId;
        this.username = username;
        this.player = player;
    }
    
    public Session(String userId, String username){
        this.userId = userId;
        this.username = username;
    }
}
