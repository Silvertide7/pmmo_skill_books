package net.silvertide.pmmo_skill_books.utils;

import harmonised.pmmo.api.APIUtils;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerClassUtil {
    private static final Map<PrimaryClass, List<SubClass>> PRIMARY_TO_SUB_CLASS_MAP = new HashMap<>();
    private static final Map<SubClass, PrimaryClass> SUB_TO_PRIMARY_CLASS_MAP = new HashMap<>();

    static {
        associatePrimaryAndSubClass(PrimaryClass.PALADIN, SubClass.VALKYRIE);
        associatePrimaryAndSubClass(PrimaryClass.PALADIN, SubClass.OATHLESS);
        associatePrimaryAndSubClass(PrimaryClass.ROGUE, SubClass.ASSASSIN);
        associatePrimaryAndSubClass(PrimaryClass.RANGER, SubClass.TRACKER);
        associatePrimaryAndSubClass(PrimaryClass.WARLOCK, SubClass.REAPER);
    }
    public static PrimaryClass getPrimaryClass(SubClass subClass) {
        return SUB_TO_PRIMARY_CLASS_MAP.get(subClass);
    }
    public static List<SubClass> getSubClasses(PrimaryClass primaryClass) {
        return PRIMARY_TO_SUB_CLASS_MAP.get(primaryClass);
    }

    @Nullable
    public static SubClass getConflictingSubclass(Player player, String skill) {
        SubClass currSubClass = SubClass.fromString(skill);
        if(currSubClass != null) {
            PrimaryClass pClass = PlayerClassUtil.getPrimaryClass(currSubClass);
            for(SubClass subClass : PlayerClassUtil.getSubClasses(pClass)) {
                if(!subClass.toString().equals(currSubClass.toString())){
                    int subClassLevel = APIUtils.getLevel(subClass.toString(), player);
                    if(subClassLevel > 0) return subClass;
                }
            }
        }
        return null;
    }

    public static List<PrimaryClass> getCurrentPrimaryClasses(Player player) {
        List<PrimaryClass> pClassList = new ArrayList<>();
        for(PrimaryClass pClass : PrimaryClass.values()){
            int currLevel = APIUtils.getLevel(pClass.toString(), player);
            if( currLevel > 0) pClassList.add(pClass);
        }
        return pClassList;
    }
    private static void associatePrimaryAndSubClass(PrimaryClass primaryClass, SubClass subClass){
        PRIMARY_TO_SUB_CLASS_MAP.computeIfAbsent(primaryClass, k -> new ArrayList<>());
        PRIMARY_TO_SUB_CLASS_MAP.get(primaryClass).add(subClass);
        SUB_TO_PRIMARY_CLASS_MAP.put(subClass, primaryClass);
    }
}
