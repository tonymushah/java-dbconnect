package mg.tonymushah.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import mg.tonymushah.utils.exceptions.PackageNotFoundException;

public class AccessingAllClassesInPackage {
  
  public static Set<Class<?>> findAllClasses(String packageName) throws PackageNotFoundException{
      return AccessingAllClassesInPackage.findAllClassesWithClassLoader(packageName, ClassLoader.getSystemClassLoader());
  }
  public static Set<Class<?>> findAllClassesWithClassLoader(String packageName, ClassLoader loader) throws PackageNotFoundException{
    try {
      InputStream stream = loader
        .getResourceAsStream(packageName.replaceAll("[.]", "/"));
      
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    return reader.lines()
        .filter(line -> line.endsWith(".class"))
        .map(line -> getClass(line, packageName))
        .collect(Collectors.toSet());
    } catch (NullPointerException e) {
        e.printStackTrace();
      throw new PackageNotFoundException(packageName, e.getCause());
    }
  }
  public static boolean isPackageExist(String packageName){
    try {
      return findAllClasses(packageName).size() > 0 ? true : false;
    } catch (PackageNotFoundException e) {
      // TODO: handle exception
      return false;
    }
  }
  public static Class<?> getClass(String className, String packageName) {
    try {
      return Class.forName(packageName + "."
          + className.substring(0, className.lastIndexOf('.')));
    } catch (ClassNotFoundException e) {
      // handle the exception
    }
    return null;
  }

  public static <T extends Annotation> Set<Class<?>> findAllClassesByClassAnnotation(String packageName, final Class<T> annotation) throws PackageNotFoundException {
    Set<Class<?>> all_classes = AccessingAllClassesInPackage.findAllClasses(packageName);
    all_classes.removeIf(new Predicate<Class<?>>() {

      @Override
      public boolean test(Class<?> t) {
        // TODO Auto-generated method stub
        return !t.isAnnotationPresent(annotation);
      }

    });
    return all_classes;
  }

  public static <T extends Annotation> HashMap<Class<?>, ArrayList<T>> getAllClassesByClassAnnotation(String packageName,
      Class<T> annotation) throws PackageNotFoundException {
    Set<Class<?>> all_Classes = findAllClassesByClassAnnotation(packageName, annotation);
    HashMap<Class<?>, ArrayList<T>> returns = new HashMap<Class<?>, ArrayList<T>>(all_Classes.size());
    for (Class<?> class_ : all_Classes) {
      ArrayList<T> data = new ArrayList<T>();
      for (T data_ : class_.getAnnotationsByType(annotation)) {
        data.add(data_);
      }
      returns.put(class_, data);
    }
    return returns;
  }

  public static Set<Class<?>> findAllClassesByClassAnnotations(String packageName, final Class<? extends Annotation>... annotation) throws PackageNotFoundException {
    Set<Class<?>> all_classes = AccessingAllClassesInPackage.findAllClasses(packageName);
    all_classes.removeIf(new Predicate<Class<?>>() {

      @Override
      public boolean test(Class<?> t) {
        // TODO Auto-generated method stub
        boolean is_all_present = true;
        for (Class<? extends Annotation> to_use_a : annotation) {
          if (t.isAnnotationPresent(to_use_a) == false) {
            is_all_present = false;
          }
        }
        return !is_all_present;
      }
    });
    return all_classes;
  }

  public static <T extends Annotation> HashMap<Field, ArrayList<T>> findAllFieldOfClassByClassAnnotation(Class<?> to_use,
      Class<T> annotation) {
    HashMap<Field, ArrayList<T>> to_returns = new HashMap<Field, ArrayList<T>>();
    for (Field current_field : to_use.getDeclaredFields()) {
      ArrayList<T> annotations = new ArrayList<T>();
      for (T t : current_field.getDeclaredAnnotationsByType(annotation)) {
        annotations.add(t);
      }
      to_returns.put(current_field, annotations);
    }
    return to_returns;
  }
  public static <T extends Annotation> HashMap<Method, ArrayList<T>> findAllMethodOfClassByClassAnnotation(Class<?> to_use,
      Class<T> annotation) {
    HashMap<Method, ArrayList<T>> to_returns = new HashMap<Method, ArrayList<T>>();
    for (Method current_field : to_use.getDeclaredMethods()) {
      ArrayList<T> annotations = new ArrayList<T>();
      if(current_field.isAnnotationPresent(annotation)){
        for (T t : current_field.getDeclaredAnnotationsByType(annotation)) {
          annotations.add(t);
        }
        to_returns.put(current_field, annotations);
      }
    }
    return to_returns;
  }
  public static<T extends Annotation> HashMap<Method, ArrayList<T>> findAllMethodOfPackageByClassAnnotation(String packageName, Class<T> annotation) throws PackageNotFoundException{
    Set<Class<?>> all_class = AccessingAllClassesInPackage.findAllClasses(packageName);
    HashMap<Method, ArrayList<T>> to_returns = new HashMap<Method, ArrayList<T>>();
    for (Class<?> class1 : all_class) {
      to_returns.putAll(AccessingAllClassesInPackage.findAllMethodOfClassByClassAnnotation(class1, annotation));
    }
    return to_returns;
  }
  public static<T extends Annotation> HashMap<Field, ArrayList<T>> findAllFieldOfPackageByClassAnnotation(String packageName, Class<T> annotation) throws PackageNotFoundException{
    Set<Class<?>> all_class = AccessingAllClassesInPackage.findAllClasses(packageName);
    HashMap<Field, ArrayList<T>> to_returns = new HashMap<Field, ArrayList<T>>();
    for (Class<?> class1 : all_class) {
      to_returns.putAll(AccessingAllClassesInPackage.findAllFieldOfClassByClassAnnotation(class1, annotation));
    }
    return to_returns;
  }
  public static<T extends Annotation, I extends Annotation> HashMap<Field, ArrayList<I>> findAllFieldOfPackageByClassAnnotationFilters(String packageName, Class<T> classFilter, Class<I> fieldFilter) throws PackageNotFoundException{
    Set<Class<?>> all_Classes_with_filter = AccessingAllClassesInPackage.findAllClassesByClassAnnotation(packageName, classFilter);
    HashMap<Field, ArrayList<I>> to_returns = new HashMap<Field, ArrayList<I>>();
    for (Class<?> class1 : all_Classes_with_filter) {
      to_returns.putAll(AccessingAllClassesInPackage.findAllFieldOfClassByClassAnnotation(class1, fieldFilter));
    }
    return to_returns;
  }
  public static<T extends Annotation, I extends Annotation> HashMap<Method, ArrayList<I>> findAllMethodOfPackageByClassAnnotationFilters(String packageName, Class<T> classFilter, Class<I> fieldFilter) throws PackageNotFoundException{
    Set<Class<?>> all_Classes_with_filter = AccessingAllClassesInPackage.findAllClassesByClassAnnotation(packageName, classFilter);
    HashMap<Method, ArrayList<I>> to_returns = new HashMap<Method, ArrayList<I>>();
    for (Class<?> class1 : all_Classes_with_filter) {
      to_returns.putAll(AccessingAllClassesInPackage.findAllMethodOfClassByClassAnnotation(class1, fieldFilter));
    }
    return to_returns;
  }
  
}
