package com.bazar.bazars.Models;

import java.util.List;

public class Delete_Ad {

    /**
     * success : true
     * data : []
     */

    private boolean success;
    private List<?> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
