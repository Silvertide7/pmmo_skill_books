package net.silvertide.pmmo_skill_books.utils;
public class UseSkillBookResult {
    private boolean success;
    private String message;
    public UseSkillBookResult(boolean success, String message){
        this.success = success;
        this.message = message;
    }
    public boolean isSuccessful() {
        return this.success;
    }
    public String getMessage() {
        return this.message;
    }
}
