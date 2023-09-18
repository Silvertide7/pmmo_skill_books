package net.silvertide.pmmo_skill_books.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerClass {
    private List<PlayerSubClassType> subClasses;
    private PlayerClassType type;

    public PlayerClass(PlayerClassType type) {
        this.type = type;
        subClasses = new ArrayList<>();
    }

    public PlayerClass addSubClass(PlayerSubClassType subClass) {
        this.subClasses.add(subClass);
        return this;
    }

    public List<PlayerSubClassType> getSubClasses() {
        return this.subClasses;
    }

    public PlayerClassType getType() {
        return this.type;
    }
}
