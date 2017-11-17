package Views;

public abstract class Controller {
    protected ClientStage stage;

    public Controller(ClientStage stage){
        this.stage = stage;
    }

    public abstract void init();

}
