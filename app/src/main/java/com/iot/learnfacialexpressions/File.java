package com.iot.learnfacialexpressions;

public class File {
    private String fileName;
    private String expression;

    public File() {
    }

    public File(String fileName, String expression) {
        this.fileName = fileName;
        this.expression = expression;
    }

    public String getFileName(){
        return this.fileName;
    }

    public String getExpression(){
        return this.expression;
    }
}
