package com.soapwaster.vcr.compiler;


public class VCRValue {

    public static VCRValue VOID = new VCRValue(new Object());

    final Object value;
    
    public VCRValue(Object value) {
        this.value = value;
    }

    public Boolean asBoolean() {
        return (Boolean)value;
    }

    public Integer asInteger() {
        return (Integer)value;
    }

    public String asString() {
        return String.valueOf(value);
    }

    public boolean isInteger() {
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

        VCRValue that = (VCRValue)o;

        return this.value.equals(that.value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}