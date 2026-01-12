package mg.tonymushah.utils.class_t_file;

import java.io.File;

import mg.tonymushah.utils.TUtils;

public class ArClass_File<T extends Object> {
    File array_file;
    T[] to_use;
    Class_File[] arFiles;

    public void setArFiles(Class_File[] arFiles) {
        this.arFiles = arFiles;
    }

    public void setArray_file(File array_file) {
        this.array_file = array_file;
    }

    public void setTo_use(T[] to_use) {
        this.to_use = to_use;
    }

    public Class_File[] getArFiles() {
        return arFiles;
    }

    public File getArray_file() {
        return array_file;
    }

    public T[] getTo_use() {
        return to_use;
    }

    public void to_use_to_ArFiles(String path) {
        this.setArFiles(new Class_File[to_use.length]);
        for (int i = 0; i < arFiles.length; i++) {
            arFiles[i] = new Class_File(to_use[i], path, "" + i);
        }
    }

    // FINISHED use the function "generate_Cafilename()" below to the class
    // Array_file
    String generate_Cafilename() {
        String returns = "";
        String touse = this.to_use.toString();
        String part1 = touse.split("@")[0].split(";")[0];
        // part1 = part1.replaceAll("[", "");
        part1 = part1.replaceAll("Lclasses.", "");
        String part2 = touse.split("@")[1];
        returns = part1 + "__" + part2 + ".Tclass";
        return returns;
    }

    String generate_Cadirname() {
        String returns = "";
        String touse = this.to_use.toString();
        String part1 = touse.split("@")[0].split(";")[0];
        // part1 = part1.replaceAll("[", "");
        part1 = part1.replaceAll("Lclasses.", "");
        String part2 = touse.split("@")[1];
        returns = part1 + "__" + part2 + "D" + TUtils.rand(10);
        return returns;
    }

    public String generate_filename() {
        char[] totest = to_use.toString().toCharArray();
        if (totest[0] == '[' && totest[1] == 'L') {
            // return this.generate_Cafilename();
            return this.generate_Cafilename();
        } else {
            throw new ArrayStoreException("the object " + to_use.toString() + " is't an array");
        }
    }

    public String str_info() {
        String returns = "";
        /*
         * [!] Structure
         * "object_type"//"index"::"filename";;"index"::"filename";;...;;
         */
        // TODO "object_type" to returns
        returns = returns + this.to_use.getClass().getName() + this.to_use.length + "//";

        // TODO "index"::"filename";;
        for (int i = 0; i < arFiles.length; i++) {
            returns = returns + i + "::" + arFiles[i].getFishing().getName() + ";;";
        }
        returns = returns + "###";
        return returns;
    }

    public void createArray_file() {
        this.array_file = new File(this.generate_filename());
        TUtils.write_to_file(this.array_file, str_info());
    }

    public void createArray_file(String to_use) {
        this.array_file = new File(to_use + this.generate_filename());
        TUtils.write_to_file(this.array_file, str_info());
    }

    public ArClass_File(T[] to_use) {
        this.setTo_use(to_use);
        File de = new File(this.generate_Cadirname());
        de.mkdir();
        this.to_use_to_ArFiles(de.getPath() + "/");
        this.createArray_file(de.getPath() + "/");
    }

    public ArClass_File(T[] to_use, String path) {
        this.setTo_use(to_use);
        File de = new File(path + this.generate_Cadirname());
        de.mkdir();
        this.to_use_to_ArFiles(de.getPath() + "/");
        this.createArray_file(de.getPath() + "/");
    }
}
