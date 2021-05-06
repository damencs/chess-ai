/*
    Group Names:
        - Damen DeBerry (@basicDamen)
        - James Grady (@JaymeAlann)
        - Tyra Buadoo (@misstj555)
        - Ashlei Williams (@AshW-2018)
        - Mahad Farah (@mfarah-ksu)
        - Mandela Issa-Boube (@aliamaza)
        - Shivank Rao (@shivankrao)
    Project: Chess with AI Agent
    Class: CS4850 - Senior Project
 */
package chess.game;

public class Corp
{
    private final String corpName;
    private boolean corpCommandAvailability;
    private Piece corpCommander;
    private final boolean playerCorp;

    public Corp(String corpName, boolean corpCommandAvailability, boolean isPlayerCorp)
    {
        this.corpName = corpName;
        this.corpCommandAvailability = corpCommandAvailability;
        this.playerCorp = isPlayerCorp;
    }

    public boolean isPlayerCorp()
    {
        return playerCorp;
    }

    public void setCorpCommander(Piece piece)
    {
        corpCommander = piece;
    }

    public Piece getCorpCommander()
    {
        return corpCommander;
    }

    public String getCorpName()
    {
        return corpName;
    }

    public boolean isCommandAvailable()
    {
        return corpCommandAvailability;
    }

    public void setCorpCommandAvailability(boolean availability)
    {
        corpCommandAvailability = availability;
    }

    public void switchCorpCommandAvailablity()
    {
        corpCommandAvailability = !corpCommandAvailability;
    }
}