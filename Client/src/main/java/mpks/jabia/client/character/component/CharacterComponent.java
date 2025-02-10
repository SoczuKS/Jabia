package mpks.jabia.client.character.component;

import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import mpks.jabia.common.User;

public class CharacterComponent extends Component {
    private final User user;

    public CharacterComponent(SpawnData data) {
        if (data.hasKey("user")) {
            this.user = data.get("user");
        } else {
            this.user = null;
        }
    }

    public String getUsername() {
        if (user != null) {
            return user.getUsername();
        }
        return "";
    }
}
