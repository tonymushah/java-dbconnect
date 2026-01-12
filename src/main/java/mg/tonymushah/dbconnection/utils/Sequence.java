package mg.tonymushah.dbconnection.utils;

public abstract class Sequence {
    String name;

    public String getName() {
        return name;
    }
    public void setName(String name) throws Exception{
        if(name != null){
            this.name = name;
        }else{
            throw new Exception("Invalid or null name found");
        }
    }
    public Sequence(String name) throws Exception{
        this.setName(name);
    }
    public Sequence(){
        
    }
    public abstract String sequence_pattern();
}
