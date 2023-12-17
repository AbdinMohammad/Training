package bean;

import java.util.ArrayList;
import java.util.List;

public class ErrorBean {

    private String id;
    private List<String> errorsList;

    public ErrorBean(String id) {
        this.id = id;
        this.errorsList = new ArrayList<>();
    }
    public ErrorBean(String id, List<String> errorsList) {
        this.id = id;
        this.errorsList = errorsList;
    }

    public void addErrorToList(String errorMessage){
        if (errorMessage != null){
            errorsList.add(errorMessage);
        }
    }

    public String getId() {
        return id;
    }

    public List<String> getErrorsList() {
        return errorsList;
    }

    @Override
    public String toString() {
        return "ErrorBean{" +
                "id=" + id +
                ", errorsList=" + errorsList +
                '}';
    }
}
