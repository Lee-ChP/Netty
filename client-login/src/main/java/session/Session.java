package session;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Session {
    //用户唯一标识
    private String userId;
    private String username;

    public Session(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}
