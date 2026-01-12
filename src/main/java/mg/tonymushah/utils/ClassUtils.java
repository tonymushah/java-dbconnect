/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.tonymushah.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Predicate;
import mg.tonymushah.utils.exceptions.PackageNotFoundException;

/**
 *
 * @author tonymushah
 */
public class ClassUtils {
    public static <T extends Annotation> Set<Class<?>> findAllClassesByClassAnnotation(Set<Class<?>> all_classes, final Class<T> annotation) throws PackageNotFoundException {
    all_classes.removeIf(new Predicate<Class<?>>() {

      @Override
      public boolean test(Class<?> t) {
        // TODO Auto-generated method stub
        return !t.isAnnotationPresent(annotation);
      }

    });
    return all_classes;
  }

  public static <T extends Annotation> HashMap<Class<?>, ArrayList<T>> getAllClassesByClassAnnotation(Set<Class<?>> all_classes,
      Class<T> annotation) throws PackageNotFoundException {
    Set<Class<?>> all_Classes = findAllClassesByClassAnnotation(all_classes, annotation);
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

  public static Set<Class<?>> findAllClassesByClassAnnotations(Set<Class<?>> all_classes, final Class<? extends Annotation>... annotation) throws PackageNotFoundException {
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
      for (T t : current_field.getDeclaredAnnotationsByType(annotation)) {
        annotations.add(t);
      }
      to_returns.put(current_field, annotations);
    }
    return to_returns;
  }
  public static<T extends Annotation> HashMap<Method, ArrayList<T>> findAllMethodOfPackageByClassAnnotation(Set<Class<?>> all_classes, Class<T> annotation){
    Set<Class<?>> all_class = all_classes;
    HashMap<Method, ArrayList<T>> to_returns = new HashMap<Method, ArrayList<T>>();
    for (Class<?> class1 : all_class) {
      to_returns.putAll(AccessingAllClassesInPackage.findAllMethodOfClassByClassAnnotation(class1, annotation));
    }
    return to_returns;
  }
  public static<T extends Annotation> HashMap<Field, ArrayList<T>> findAllFieldOfPackageByClassAnnotation(Set<Class<?>> all_classes, Class<T> annotation) throws PackageNotFoundException{
    Set<Class<?>> all_class = all_classes;
    HashMap<Field, ArrayList<T>> to_returns = new HashMap<Field, ArrayList<T>>();
    for (Class<?> class1 : all_class) {
      to_returns.putAll(AccessingAllClassesInPackage.findAllFieldOfClassByClassAnnotation(class1, annotation));
    }
    return to_returns;
  }
  public static<T extends Annotation, I extends Annotation> HashMap<Field, ArrayList<I>> findAllFieldOfPackageByClassAnnotationFilters(Set<Class<?>> all_classes, Class<T> classFilter, Class<I> fieldFilter) throws PackageNotFoundException{
    Set<Class<?>> all_Classes_with_filter = findAllClassesByClassAnnotation(all_classes, classFilter);
    HashMap<Field, ArrayList<I>> to_returns = new HashMap<Field, ArrayList<I>>();
    for (Class<?> class1 : all_Classes_with_filter) {
      to_returns.putAll(AccessingAllClassesInPackage.findAllFieldOfClassByClassAnnotation(class1, fieldFilter));
    }
    return to_returns;
  }
  public static<T extends Annotation, I extends Annotation> HashMap<Method, ArrayList<I>> findAllMethodOfPackageByClassAnnotationFilters(Set<Class<?>> all_classes, Class<T> classFilter, Class<I> fieldFilter) throws PackageNotFoundException{
    Set<Class<?>> all_Classes_with_filter = findAllClassesByClassAnnotation(all_classes, classFilter);
    HashMap<Method, ArrayList<I>> to_returns = new HashMap<Method, ArrayList<I>>();
    for (Class<?> class1 : all_Classes_with_filter) {
      to_returns.putAll(AccessingAllClassesInPackage.findAllMethodOfClassByClassAnnotation(class1, fieldFilter));
    }
    return to_returns;
  }
}
