/*
 * Message.java
 */
package townofwinchester;
import org.apache.logging.log4j.*;
import java.net.*;
import java.io.*;

/**
 * Messsage is the wrapper class for the messages that will be sent throughout the game
 *
 *
 * @author Jeremy Hughes
 * @author Ethan Wong
 *
 */
public class Message{  
    public static enum Type {CHAT, MCHAT, JOIN, PICK, TIME, MVOTE, VOTE, CLOSE, PERSON, START, CLICK, KILL, MKILL};
    private Type type;
    private int ID;
    private String text;
    
    // Example: "CHAT:NAME:Hi, there!"
    public Message(String message) {
        String[] parts = message.split("-");
        assert parts.length >= 3 : message + " must have (at least) two '-'";
        for (Type type : Type.values())
            if (type.toString().equals(parts[0].toUpperCase())) {
                this.type = type;
                this.ID = Integer.parseInt(parts[1]);
                this.text = parts[2];
                for (int i = 3; i < parts.length; i++)
                    this.text += "-" + parts[i];
            }
        assert this.type != null && this.ID != 0 && this.text != null
        : "String message parse failed";
        LogManager.getLogger(TownOfWinchester.SHORT).debug("Message({})", toString());
    }

    public Message(Type type, int ID, String text) {
        this.type = type;
        this.ID = ID;
        this.text = text;
    }

    public Type getType() { return type; }
    public int getID() { return ID; }
    public String getText() { return text; }

    public String toString() {
        return type.toString() + "-" + ID + "-" + text;
    }

    // RED_FLAG: obsolete methods
/**
 *   private String nextCommand;
 *
 *   public String assignRole(boolean indicateMafiaStatus, int ID) {
 *       //Character character = new Character();
 *       //String name1 = character.getName();
 *       if (indicateMafiaStatus == true){ 
 *           nextCommand = ID + "." + "toMafia()";
 *           return Type.PICK + "You are a Mafia";
 *       } 
 *       else{   
 *           nextCommand = ID + "." + "toVillager()"; 
 *           return Type.PICK + ID + "is a villager";
 *       } 
 *   }
 *
 *   public String compileText(int ID, String message) {
 *       return Type.CHAT + ":" + ID + ":" + message; 
 */   }
