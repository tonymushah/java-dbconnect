/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package mg.tonymushah.utils.enums;

/**
 *
 * @author tonymushah
 */
public enum PrimaryClasses {
    Integer{
        @Override
        public Class<?> getPrimitiveClass(){
            return int.class;
        }
    },
    Boolean{
        @Override
        public Class<?> getPrimitiveClass() {
            return boolean.class;
        }
        
    },
    Float {
        @Override
        public Class<?> getPrimitiveClass() {
            return float.class;
        }
    },
    Double {
        @Override
        public Class<?> getPrimitiveClass() {
            return double.class;
        }
    },
    Long {
        @Override
        public Class<?> getPrimitiveClass() {
            return long.class;
        }
    },
    Short {
        @Override
        public Class<?> getPrimitiveClass() {
            return short.class;
        }
    },
    Char {
        @Override
        public Class<?> getPrimitiveClass() {
            return char.class;
        }
    };
    public abstract Class<?> getPrimitiveClass();
    public String getPrimitiveClassName(){
        return this.getPrimitiveClass().getName();
    }
   
    public static PrimaryClasses getPrimaryClass(Class<?> to_evaluate){
        System.out.println(to_evaluate);
        for(PrimaryClasses comp : PrimaryClasses.values()){
            if(comp.getPrimitiveClass() == to_evaluate){
                return comp;
            }
        }
        return null;
    }
}
