package com.soapwaster.vcr.compiler;

import java.awt.geom.Point2D;

public class Value {

    public static Value VOID = new Value(new Object());

    final Object value;
    
    public Value(Object value) {
        this.value = value;
    }

    public Boolean asBoolean() {
        return (Boolean)value;
    }

    public Integer asDouble() {
        return (Integer)value;
    }

    public Integer asInt() {
        return (Integer)value;
    }
    public String asString() {
        return String.valueOf(value);
    }

    public boolean isDouble() {
        return value instanceof Integer;
    }
    
    public boolean isInt() {
        return value instanceof Integer;
    }

    @Override
    public int hashCode() {

        if(value == null) {
            return 0;
        }

        return this.value.hashCode();
    }

    @Override
    public boolean equals(Object o) {

        if(value == o) {
            return true;
        }

        if(value == null || o == null || o.getClass() != value.getClass()) {
            return false;
        }

        Value that = (Value)o;

        return this.value.equals(that.value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}