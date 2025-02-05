package mpks.jabia.client.character.component;

import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import mpks.jabia.common.User;

public class CharacterComponent extends Component {
    private final User user;

    public CharacterComponent(SpawnData data) {
        this.user = data.get("user");
    }

    public String getUsername() {
        return user.getUsername();
    }
}
