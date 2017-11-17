

package root.User;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Preferences implements Serializable{

    private static final long serialVersionUID = 1L;

    private String career;
    private boolean smoke;
    private boolean food;

    public Preferences( String career,boolean smoke, boolean food){
        this.career=career;
        this.smoke=smoke;
        this.food=food;
    }

    public void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF(career);
        out.writeBoolean(smoke);
        out.writeBoolean(food);
    }

    public void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException{
        ois.defaultReadObject();
        career = ois.readUTF();
        smoke = ois.readBoolean();
        food = ois.readBoolean();
    }

    public String getCareer() {
        return career;
    }

    public boolean isFood() {
        return food;
    }

    public boolean isSmoke() {
        return smoke;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Preferences))
            return false;
        Preferences aux = (Preferences) obj;
        return smoke == aux.smoke && food == aux.food && career.equals(aux.career);
    }

    @Override
    public int hashCode() {
        int result = career != null ? career.hashCode() : 0;
        result = 31 * result + (smoke ? 1 : 0);
        result = 31 * result + (food ? 1 : 0);
        return result;
    }
    
    @Override
    public String toString() {
        String smokeString ="";
        String foodString = "";
        if(smoke) {
            smokeString = "SI";
        }
        else {
            smokeString = "NO";
        }
        if(food) {
            foodString = "SI";
        }
        else {
            foodString = "NO";
        }
        String s = "Preferencias del usuario:\nCARRERA: "+career+"\nFUMAR: "+smokeString+"\nCOMIDA: "+ foodString+"\n";
        return s;
    }


}