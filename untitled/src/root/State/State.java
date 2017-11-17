
package root.State;

import root.Exceptions.*;
import root.User.Credential;
import root.User.User;
import root.Ride.ActiveRideAdmin;
import root.Ride.ExpiredRideAdmin;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class State implements Serializable{

    private static final long serialVersionUID = 1L;


    private List<User> users;

    private List<ActiveRideAdmin> currentRides;

    private List<ExpiredRideAdmin> expiredRides;

    public State() {
        users = new ArrayList<>();
        currentRides = new LinkedList<>();
        expiredRides = new ArrayList<>();
    }
    
    public List<User> getUsers(){
    	return users;
    }

    public void writeObject(ObjectOutputStream out) throws IOException{
        out.defaultWriteObject();
        out.writeObject(users);
        out.writeObject(currentRides);
        out.writeObject(expiredRides);
    }

    public void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException{
        ois.defaultReadObject();
        users = (List<User>) ois.readObject();
        currentRides = (List<ActiveRideAdmin>) ois.readObject();
        expiredRides = (List<ExpiredRideAdmin>) ois.readObject();
    }



    public User login(Credential cred) throws InvalidCredentials{
        for (User user : users){
            if (user.equalCredentials(cred)){
                return user;
            }
        }
        throw new InvalidCredentials();
    }

    public User register(User user) throws InvalidFields{
        if(!(users.contains(user))) {
            users.add(user);
            return user;
        }
        throw new InvalidFields("User already Exists");
    }

    /**
     * metodo no fue implementado en el front
     */
    public void removeUser(User user){
        users.remove(user);
    }

    public void addRideToList(ActiveRideAdmin ride) throws ExistingRideException{
        if(currentRides.contains(ride)){
            throw new ExistingRideException();
        }
        currentRides.add(ride);
        ride.getRide().getDriver().addRide(ride);
    }

    public void deleteRide(ActiveRideAdmin ride){
            for(User user: ride.getPassengers()){
                user.getActiveRides().remove(ride);
            }
            ride.getRide().getDriver().getActiveRides().remove(ride);
            currentRides.remove(ride);
    }

    /**
     * refreshRides hace el cambio de listas entre viajes activos y expirados. No fue implementado
     *
     */

    public void refreshRides(){
        boolean aux = true;
        LocalDateTime currentDate = LocalDateTime.now();
        for (int i = 0; aux ; i++) {
            ActiveRideAdmin ride = currentRides.get(i);
            if (ride.getRide().getDate().compareTo(currentDate) < 0){
                ExpiredRideAdmin expired = new ExpiredRideAdmin(ride.getRide(), currentRides.get(i).getPassengers());
                expiredRides.add(expired);
                for(User user: ride.getPassengers()){
                    user.getActiveRides().remove(currentRides.get(i));
                    user.getExpiredRides().add(expired);
                }
                currentRides.remove(i);
            }
            else{
                aux = false;
            }
        }
    }

    public List<ActiveRideAdmin> getCurrentRides() { return currentRides; }

}