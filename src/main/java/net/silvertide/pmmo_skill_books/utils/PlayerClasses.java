package net.silvertide.pmmo_skill_books.utils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerClasses {
    private PlayerClasses singleton;
    private final Map<PrimaryClass, List<SubClass>> primaryToSubClassMap;
    private final Map<SubClass, PrimaryClass> subToPrimaryClassMap;
    private PlayerClasses(){
        primaryToSubClassMap = new HashMap<>();
        subToPrimaryClassMap = new HashMap<>();

        associatePrimaryAndSubClass(PrimaryClass.PALADIN, SubClass.VALKYRIE);
        associatePrimaryAndSubClass(PrimaryClass.PALADIN, SubClass.OATHLESS);
        associatePrimaryAndSubClass(PrimaryClass.ROGUE, SubClass.ASSASSIN);
    }

    private PlayerClasses getPlayerClasses() {
        if(this.singleton == null) {
            this.singleton = new PlayerClasses();
        }
        return this.singleton;
    }

    public PrimaryClass getPrimaryClass(SubClass subClass) {
        return this.subToPrimaryClassMap.get(subClass);
    }

    public List<SubClass> getSubClasses(PrimaryClass primaryClass) {
        return this.primaryToSubClassMap.get(primaryClass);
    }

    @Nullable
    public SubClass getSubClassFromString(String classString) {
        for(SubClass subClass : SubClass.values()) {
            if(subClass.toString().equals(classString)) return subClass;
        }
        return null;
    }

    @Nullable
    public PrimaryClass getPrimaryClassFromString(String classString) {
        for(PrimaryClass primaryClass : PrimaryClass.values()) {
            if(primaryClass.toString().equals(classString)) return primaryClass;
        }
        return null;
    }

    private void associatePrimaryAndSubClass(PrimaryClass primaryClass, SubClass subClass){
        this.primaryToSubClassMap.computeIfAbsent(primaryClass, k -> new ArrayList<>());
        this.primaryToSubClassMap.get(primaryClass).add(subClass);
        this.subToPrimaryClassMap.put(subClass, primaryClass);
    }
}
