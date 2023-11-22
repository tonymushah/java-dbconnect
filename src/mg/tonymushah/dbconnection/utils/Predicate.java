package mg.tonymushah.dbconnection.utils;

/**
 * Predicate
 */
public class Predicate {
    private String to_evualuate;
    private String value;
    private String suffix_type;
    private boolean isString;
    public String getTo_evualuate() {
        return to_evualuate;
    }
    public void setTo_evualuate(String to_evualuate) {
        this.to_evualuate = to_evualuate;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getSuffix_type() {
        return suffix_type;
    }
    public void setSuffix_type(String suffix_type) throws Exception {
        if(suffix_type.toLowerCase() == "or" || suffix_type.toLowerCase() == "and"){
            this.suffix_type = suffix_type;
        }else{
            throw new Exception("the suffix type should be an 'and' or an 'or'");
        }
       
    }
    public boolean isString() {
        return isString;
    }
    public void setString(boolean isString) {
        this.isString = isString;
    }
    public Predicate(String to_evualuate, String value, String suffix_type, boolean isString) throws Exception {
        this.setTo_evualuate(to_evualuate);
        this.setValue(value);
        this.setSuffix_type(suffix_type);
        this.setString(isString);
    }
    public String to_string_withSuffix(){
        if (isString) {
            return "" + this.getTo_evualuate() + "=" + "'" + this.value + "' " + this.getSuffix_type() + " ";
        }else{
            return "" + this.getTo_evualuate() + "=" + this.getValue() + " " + this.getSuffix_type() + " ";
        }
    }
    public String to_string_withoutSuffix(){
        if (isString) {
            return "" + this.getTo_evualuate() + "=" + "'" + this.value + "' " ;
        }else{
            return "" + this.getTo_evualuate() + "=" + this.getValue() + " ";
        }
    }
}