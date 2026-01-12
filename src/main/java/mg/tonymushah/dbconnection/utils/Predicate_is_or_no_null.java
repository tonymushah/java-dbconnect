package mg.tonymushah.dbconnection.utils;

public class Predicate_is_or_no_null extends Predicate {
    private boolean is_null;

    public boolean isIs_null() {
        return is_null;
    }

    public void setIs_null(boolean is_null) {
        this.is_null = is_null;
    }

    public Predicate_is_or_no_null(String to_evualuate, String suffix_type, boolean is_null)
            throws Exception {
        super(to_evualuate, null, suffix_type, true);
        //TODO Auto-generated constructor stub
        this.setIs_null(is_null);
    }

    @Override
    public String to_string_withSuffix() {
        // TODO Auto-generated method stub
        if (is_null) {
            return this.getTo_evualuate() + " is null " + this.getSuffix_type();
        }else{
            return this.getTo_evualuate() + " is not null " + this.getSuffix_type();
        }
        
    }

    @Override
    public String to_string_withoutSuffix() {
        // TODO Auto-generated method stub
        if (is_null) {
            return this.getTo_evualuate() + " is null";
        }else{
            return this.getTo_evualuate() + " is not null";
        }
    }
    
}
