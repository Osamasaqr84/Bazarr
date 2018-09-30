package com.bazar.bazars.Items;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ali on 4/27/2017.
 */

public class tabs_item {



    @SerializedName("msg")
    private String msg;
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        @SerializedName("Categories")
        private Categories Categories;

        public Categories getCategories() {
            return Categories;
        }

        public void setCategories(Categories Categories) {
            this.Categories = Categories;
        }

        public static class Categories {


            @SerializedName("id")
            private int id;
            @SerializedName("name")
            private String name;
            @SerializedName("order")
            private int order;
            @SerializedName("SubCategories")
            private List<SubCategories> SubCategories;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }

            public List<SubCategories> getSubCategories() {
                return SubCategories;
            }

            public void setSubCategories(List<SubCategories> SubCategories) {
                this.SubCategories = SubCategories;
            }

            public static class SubCategories {


                @SerializedName("id")
                private int id;
                @SerializedName("name")
                private String name;
                @SerializedName("order")
                private int order;
                @SerializedName("SubCategories")
                private List<SubSubCategoriesBean> SubCategories;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getOrder() {
                    return order;
                }

                public void setOrder(int order) {
                    this.order = order;
                }

                public List<SubSubCategoriesBean> getSubCategories() {
                    return SubCategories;
                }

                public void setSubCategories(List<SubSubCategoriesBean> SubCategories) {
                    this.SubCategories = SubCategories;
                }

                public static class SubSubCategoriesBean {
                    /**
                     * id : 5224
                     * name : لاندكروزر
                     * order : 6
                     */

                    @SerializedName("id")
                    private int id;
                    @SerializedName("name")
                    private String name;
                    @SerializedName("order")
                    private int order;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public int getOrder() {
                        return order;
                    }

                    public void setOrder(int order) {
                        this.order = order;
                    }
                }
            }
        }
    }
}
