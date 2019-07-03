package com.epam.igorpystovit.model.transformer;

import com.epam.igorpystovit.model.entities.PlaneType;
import com.epam.igorpystovit.model.Annotations.Column;
import com.epam.igorpystovit.model.Annotations.Table;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalTime;

public class Transformer<T>  {
    private final Class<T> transformClass;

    public Transformer(Class<T> transformClass){
        this.transformClass = transformClass;
    }

    public Object transformFromResultSet(ResultSet resultSet){
        Object entity = null;
        try{
            entity = transformClass.getConstructor().newInstance();
            if (transformClass.isAnnotationPresent(Table.class)){
                Field[] fields = transformClass.getDeclaredFields();
                for (Field field : fields){
                    if (!(field.getAnnotation(Column.class) == null)){
                        field.setAccessible(true);
                        Type fieldType = field.getType();
                        String fieldName = field.getAnnotation(Column.class).name();
                        if (fieldType.equals(int.class)){
                            field.set(entity,resultSet.getInt(fieldName));
                        }
                        else if (fieldType.equals(String.class)){
                            field.set(entity,resultSet.getString(fieldName));
                        }
                        else if (fieldType.equals(Time.class)){
                            field.set(entity,resultSet.getTime(fieldName));
                        }
                        else if (fieldType.equals(Date.class)){
                            field.set(entity,resultSet.getDate(fieldName));
                        }
                        else if (fieldType.equals(PlaneType.class)){
                            field.set(entity,PlaneType.valueOf(resultSet.getString(fieldName).toUpperCase()));
                        }
                        else if (fieldType.equals(double.class)){
                            field.set(entity,resultSet.getDouble(fieldName));
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return entity;
    }
}
