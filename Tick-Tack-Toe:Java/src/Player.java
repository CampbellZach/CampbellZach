public class Player {
    public int PID;
    public boolean playerTurn;
    public Player (int playerID, boolean isTurn){
        this.PID = playerID;
        this.playerTurn = isTurn;
    }
    public int getPID(){
        return this.PID;
    }
    public void setPID(int playerID){
        this.PID = playerID;
    }
    public boolean getPlayerTurn(){
        return this.playerTurn;
    }
    public void setPlayerTurn(boolean isTurn){
        this.playerTurn = isTurn;
    }
}
