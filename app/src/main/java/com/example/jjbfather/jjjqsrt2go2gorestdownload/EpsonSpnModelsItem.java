package com.example.jjbfather.jjjqsrt2go2gorestdownload;

public class EpsonSpnModelsItem {
    private String mModelName = "";
    private int mModelConstant = 0;

    EpsonSpnModelsItem(String modelName, int modelConstant) {
        mModelName = modelName;
        mModelConstant = modelConstant;
    }

    public int getModelConstant() {
        return mModelConstant;
    }

    @Override
    public String toString() {
        return mModelName;
    }
}
