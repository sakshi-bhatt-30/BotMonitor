package msl.rpamonitoring.application.Enum;


public enum Response {
    RPA001("RPA001","Process Added Successfully"),
    RPA002("RPA001","Process Scheduled Successfully"),
    RPA003("RPA001","Process Added Successfully"),
    RPA004("RPA001","Process Added Successfully"),
    RPA005("RPA001","Process Added Successfully"),
    RPA006("RPA001","Process Added Successfully"),
    RPA007("RPA001","Process Added Successfully"),
    RPA008("RPA001","Process Added Successfully"),
    RPA009("RPA001","Process Added Successfully"),;

    final String code;
    final String message;

    Response(String code, String message){
        this.code=code;
        this.message= message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
