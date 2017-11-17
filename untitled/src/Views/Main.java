package Views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import root.State.State;

import java.io.*;

public class Main extends Application {

    private static final File file = new File("./untitled/Files/Data");
    private static final State state = retrieveOldState();;


    @Override
    public void start(Stage primaryStage) throws Exception{
        multipleNewPool(state, "Pool", 2);
    }

    private void multipleNewPool(State state, String title, int quantity){
        for(int i =0; i<quantity; i++)
            newPool(state, title + (i+1));
    }

    public void newPool(State state, String title){
        ClientStage stage = new ClientStage(state, title);
    }

    @Override
    public void stop() throws Exception {
        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(state);
            objectOutputStream.close();
            super.stop();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



    private static State retrieveOldState() {
    State oldState;
    if (file.length() == 0){
        return new State();
    }

    try {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
        oldState = (State) objectInputStream.readObject();
        objectInputStream.close();
    }catch (IOException | ClassNotFoundException e){
        System.out.println(e.getStackTrace());
        System.out.println(e.getMessage());
        oldState = new State();
    }
        if (oldState == null){
            oldState = new State();
        }
        return oldState;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
