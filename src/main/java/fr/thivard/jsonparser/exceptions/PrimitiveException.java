package fr.thivard.jsonparser.exceptions;

public class PrimitiveException extends Exception{

    public PrimitiveException() {
        super("The parser cannot convert primitive type object.");
    }
}
