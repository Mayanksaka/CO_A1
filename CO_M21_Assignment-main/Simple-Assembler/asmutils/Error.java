package asmutils;

public class Error {
    static class InvalidParametersException extends Exception{
        public InvalidParametersException(String str){
            super(str);
        }
    }
    static class InvalidRegisterException extends Exception{
        public InvalidRegisterException(){ super("Invalid Register name"); }
    }
    static class InvalidImmediateException extends Exception{
        public InvalidImmediateException(){super("Invalid Immediate Value"); }
    }
    static class InvalidVariableNameException extends Exception{
        public InvalidVariableNameException(){super("Invalid Variable Name"); }
    }
    static class LabelNameRepeatException extends Exception{
        public LabelNameRepeatException(){super("Label Already Defined"); }
    }
    static class InvalidInstructionNameException extends Exception{
        public InvalidInstructionNameException(){super("Typos in instruction name"); }
    }
    static class InvalidLabelNameException extends Exception{
        public InvalidLabelNameException(){super("A label name must consists of alphanumeric characters and underscores."); }
    }
    static class InvalidVarNameException extends Exception{
        public InvalidVarNameException(){super("A Variable name must consists of alphanumeric characters and underscores."); }
    }
    static class MisMatchException extends Exception{
        public MisMatchException(String str){
            super(str);
        }}
}
