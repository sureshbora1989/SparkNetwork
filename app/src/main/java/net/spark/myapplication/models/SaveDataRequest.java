package net.spark.myapplication.models;

public class SaveDataRequest {

    private String userId;
    private QuestionListToServer dataToSave;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public QuestionListToServer getDataToSave() {
        return dataToSave;
    }

    public void setDataToSave(QuestionListToServer dataToSave) {
        this.dataToSave = dataToSave;
    }
}
