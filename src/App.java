import mg.tonymushah.dbconnection.DBConnect;
import mg.tonymushah.dbconnection.PostGresDefault;
import models.Personne;

public class App {
    public static void main(String[] args) throws Exception {
        DBConnect connect = new PostGresDefault();
        connect.connect();
        try {
            var insert = new Personne("m");

            connect.insert(insert);
            Personne[] list = connect.select(Personne.class);
            for (Personne personne : list) {
                System.out.println(personne.getNom());
            }
        } catch (Exception e) {
            // TODO: handle 
            e.printStackTrace();
        }finally{
            connect.close();
        }
        
    }
}
