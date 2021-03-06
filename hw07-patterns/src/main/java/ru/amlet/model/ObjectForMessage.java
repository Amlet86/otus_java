package ru.amlet.model;

import java.util.List;

public class ObjectForMessage {

    private List<String> data;

    public ObjectForMessage() {
    }

    public ObjectForMessage(ObjectForMessage object) {
        if (object != null && object.data != null) {
            this.data = List.copyOf(object.data);
        }
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
