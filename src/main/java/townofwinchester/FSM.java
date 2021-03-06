/*
 * FSM.java
 */
package townofwinchester;
import org.apache.logging.log4j.*;

/**
 * FSM is the behavior of the game finite state machine.
 */
public class FSM {
    /** FSM state enum */
    public static enum State { JOIN, PICK, DAY, NIGHT, };
    /** FSM state */
    private State state;

    public FSM() {
        LogManager.getLogger(TownOfWinchester.SHORT).debug("FSM()");
        state = State.JOIN;
    }

    public void process(Message msg) {
        LogManager.getLogger(TownOfWinchester.SHORT).trace(msg);
        switch (state) {
            case JOIN:
                switch (msg.getType()) {
                case CHAT:
                case MCHAT:
                case JOIN:
                case PICK:
                case TIME:
                case MVOTE:
                case VOTE:
                default:
                    break;
            }
                break;
            case PICK:
                break;
            case DAY:
                break;
            case NIGHT:
                break;
            default:
                break;
        }
    }

    public State getState() { return state; }

    private void updateState(State state) {
        String message = String.format("{} -> {}", this.state, state);
        LogManager.getLogger(TownOfWinchester.SHORT).trace(message);
        this.state = state;
    }
}