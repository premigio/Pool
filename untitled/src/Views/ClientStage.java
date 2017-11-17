package Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import root.Exceptions.*;
import root.Ride.ActiveRideAdmin;
import root.User.Credential;
import root.User.Preferences;
import root.User.User;
import root.State.State;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ClientStage extends Stage {
    private User user;
    private State state;

    public ClientStage(State state, String title){
        super();
        this.state = state;
        this.setTitle(title);
        LogIn();
        this.show();
    }

    public void register(User user) throws InvalidFields{
        this.user = state.register(user);
    }

    public void login(Credential cred) throws InvalidCredentials{
        this.user = state.login(cred);;
    }
    
    public void addRide(ActiveRideAdmin rideAdmin) {
    	try {
			state.addRideToList(rideAdmin);
		} catch (ExistingRideException e) {
			e.printStackTrace();
		}
    }

    public void newRide(ActiveRideAdmin ride) throws ExistingRideException{
        state.addRideToList(ride);
    }

    public void removeRide(ActiveRideAdmin ride) throws NoPermission{

        if(!ride.getRide().getDriver().equals(user))
            throw new NoPermission();
        state.deleteRide(ride);
    }

    public void addRequest(ActiveRideAdmin rideAdmin) throws AlreadyRequested, AlreadyInRide, SeatsTaken {
    	rideAdmin.addRequest(user);
    }
    
    public void leaveRide(ActiveRideAdmin ride){
    	ride.leaveRide(user);
    }

   
    
    public void modifyUser(String name, String surname, String phone, Preferences pref, String password) {
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        user.setPreferences(pref);
        user.getCredential().setPassword(password);
    }

    public List<ActiveRideAdmin> getActiveRideAdmins(){
        return state.getCurrentRides();
    }

    private void setView(String fxml, Controller controller){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        loader.setController(controller);
        try {
            this.setScene(new Scene(loader.load(), StageDimensions.WIDTH.size(), StageDimensions.HEIGHT.size()));
        } catch (IOException e) {
            System.out.println("Error al cargar el fxml: " + fxml);
        }
        controller.init();
    }

    public void LogIn(){
        setView("FXMLs/LogIn.fxml", new LogIn(this));
    }

    public void MainPage() {
        setView("FXMLs/MainPage.fxml", new MainPage(this));
        
    }

    public void MyProfile() {
        setView("FXMLs/MyProfile.fxml", new MyProfile(this));
    }

    public void NewRide(){
        setView("FXMLs/NewRide.fxml", new NewRide(this));
    }

    public void Register(){
        setView("FXMLs/Register.fxml", new Register(this));
    }

    public void AcceptRequest(ActiveRideAdmin rideAdmin){
        setView("FXMLs/AcceptRequest.fxml",new AcceptRequest(this, rideAdmin));
    }

    public User getUser() {
    	return user;
    }

}
