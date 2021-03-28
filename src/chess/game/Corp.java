package chess.game;

public class Corp {

    private final String corpName;
    private boolean corpCommandAvailability;

    public Corp(String corpName, boolean corpCommandAvailability){
        this.corpName = corpName;
        this.corpCommandAvailability = corpCommandAvailability;
    }

    public String getCorpName(){
        return corpName;
    }

    public boolean isCommandAvailable(){
        return corpCommandAvailability;
    }

    public void setCorpCommandAvailability(boolean availability){
        corpCommandAvailability = availability;
    }

    public void switchCorpCommandAvailablity(){
        corpCommandAvailability = !corpCommandAvailability;
    }
}
