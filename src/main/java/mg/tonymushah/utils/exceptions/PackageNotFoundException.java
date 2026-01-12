package mg.tonymushah.utils.exceptions;

public class PackageNotFoundException extends Exception {

    public PackageNotFoundException() {
    }

    public PackageNotFoundException(String package_name) {
        super(String.format("Package %s not found", package_name));
    }

    public PackageNotFoundException(String package_name, Throwable cause) {
        super(String.format("Package %s not found", package_name), cause);
    }
    
}
