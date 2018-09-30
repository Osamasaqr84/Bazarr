package com.bazar.bazars.Models;

import java.util.List;

/**
 * Created by hossam on 04/08/2018.
 */

public class AlldataModel {

    private List<DataBean> data;
    private List<?> allah;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<?> getAllah() {
        return allah;
    }

    public void setAllah(List<?> allah) {
        this.allah = allah;
    }

    public static class DataBean {
        /**
         * id : 218
         * name : TOYOTA t
         * date : 04-08-2018
         * user_id : 1
         * city_id : 1
         * created : 2018-08-04T07:39:56+0000
         * modified : 2018-08-04T07:39:56+0000
         * sub_category_id : 1
         * sub_mini_category_id : 1
         * category_id : 1
         * image_one :
         * content : osama omar
         * mobile :
         * photos : [{"id":665,"photo":"1533382796436397111.mp3","advertise_id":218,"created":"2018-08-04T07:39:56+0000","modified":"2018-08-04T07:39:56+0000"}]
         * city : {"id":1,"name":"الرياض"}
         * sub_mini_category : {"id":1,"name":"لاندكروزر","sub_category_id":1,"category_id":1}
         * user : {"id":1,"user_group_id":"1","username":"admin","email":"admin@admin.com","first_name":"Admin","last_name":"","gender":null,"photo":null,"bday":null,"active":1,"email_verified":1,"last_login":"2018-08-04T07:29:47+0000","ip_address":null,"created":"2017-10-24T16:42:50+0000","modified":"2018-08-04T11:35:27+0000","created_by":null,"modified_by":null,"mobile":"201112014864","state":1}
         * sub_category : {"id":1,"name":"تويوتا","category_id":1,"photo":"1510667798421544926.png","category":{"id":1,"name":"سيارات "}}
         */

        private int id;
        private String name;
        private String date;
        private int user_id;
        private int city_id;
        private String created;
        private String modified;
        private int sub_category_id;
        private int sub_mini_category_id;
        private int category_id;
        private String image_one;
        private String content;
        private String mobile;
        private CityBean city;
        private SubMiniCategoryBean sub_mini_category;
        private UserBean user;
        private SubCategoryBean sub_category;
        private List<PhotosBean> photos;

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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public int getSub_category_id() {
            return sub_category_id;
        }

        public void setSub_category_id(int sub_category_id) {
            this.sub_category_id = sub_category_id;
        }

        public int getSub_mini_category_id() {
            return sub_mini_category_id;
        }

        public void setSub_mini_category_id(int sub_mini_category_id) {
            this.sub_mini_category_id = sub_mini_category_id;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getImage_one() {
            return image_one;
        }

        public void setImage_one(String image_one) {
            this.image_one = image_one;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public CityBean getCity() {
            return city;
        }

        public void setCity(CityBean city) {
            this.city = city;
        }

        public SubMiniCategoryBean getSub_mini_category() {
            return sub_mini_category;
        }

        public void setSub_mini_category(SubMiniCategoryBean sub_mini_category) {
            this.sub_mini_category = sub_mini_category;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public SubCategoryBean getSub_category() {
            return sub_category;
        }

        public void setSub_category(SubCategoryBean sub_category) {
            this.sub_category = sub_category;
        }

        public List<PhotosBean> getPhotos() {
            return photos;
        }

        public void setPhotos(List<PhotosBean> photos) {
            this.photos = photos;
        }

        public static class CityBean {
            /**
             * id : 1
             * name : الرياض
             */

            private int id;
            private String name;

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
        }

        public static class SubMiniCategoryBean {
            /**
             * id : 1
             * name : لاندكروزر
             * sub_category_id : 1
             * category_id : 1
             */

            private int id;
            private String name;
            private int sub_category_id;
            private int category_id;

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

            public int getSub_category_id() {
                return sub_category_id;
            }

            public void setSub_category_id(int sub_category_id) {
                this.sub_category_id = sub_category_id;
            }

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }
        }

        public static class UserBean {
            /**
             * id : 1
             * user_group_id : 1
             * username : admin
             * email : admin@admin.com
             * first_name : Admin
             * last_name :
             * gender : null
             * photo : null
             * bday : null
             * active : 1
             * email_verified : 1
             * last_login : 2018-08-04T07:29:47+0000
             * ip_address : null
             * created : 2017-10-24T16:42:50+0000
             * modified : 2018-08-04T11:35:27+0000
             * created_by : null
             * modified_by : null
             * mobile : 201112014864
             * state : 1
             */

            private int id;
            private String user_group_id;
            private String username;
            private String email;
            private String first_name;
            private String last_name;
            private Object gender;
            private Object photo;
            private Object bday;
            private int active;
            private int email_verified;
            private String last_login;
            private Object ip_address;
            private String created;
            private String modified;
            private Object created_by;
            private Object modified_by;
            private String mobile;
            private int state;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUser_group_id() {
                return user_group_id;
            }

            public void setUser_group_id(String user_group_id) {
                this.user_group_id = user_group_id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getFirst_name() {
                return first_name;
            }

            public void setFirst_name(String first_name) {
                this.first_name = first_name;
            }

            public String getLast_name() {
                return last_name;
            }

            public void setLast_name(String last_name) {
                this.last_name = last_name;
            }

            public Object getGender() {
                return gender;
            }

            public void setGender(Object gender) {
                this.gender = gender;
            }

            public Object getPhoto() {
                return photo;
            }

            public void setPhoto(Object photo) {
                this.photo = photo;
            }

            public Object getBday() {
                return bday;
            }

            public void setBday(Object bday) {
                this.bday = bday;
            }

            public int getActive() {
                return active;
            }

            public void setActive(int active) {
                this.active = active;
            }

            public int getEmail_verified() {
                return email_verified;
            }

            public void setEmail_verified(int email_verified) {
                this.email_verified = email_verified;
            }

            public String getLast_login() {
                return last_login;
            }

            public void setLast_login(String last_login) {
                this.last_login = last_login;
            }

            public Object getIp_address() {
                return ip_address;
            }

            public void setIp_address(Object ip_address) {
                this.ip_address = ip_address;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getModified() {
                return modified;
            }

            public void setModified(String modified) {
                this.modified = modified;
            }

            public Object getCreated_by() {
                return created_by;
            }

            public void setCreated_by(Object created_by) {
                this.created_by = created_by;
            }

            public Object getModified_by() {
                return modified_by;
            }

            public void setModified_by(Object modified_by) {
                this.modified_by = modified_by;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }
        }

        public static class SubCategoryBean {
            /**
             * id : 1
             * name : تويوتا
             * category_id : 1
             * photo : 1510667798421544926.png
             * category : {"id":1,"name":"سيارات "}
             */

            private int id;
            private String name;
            private int category_id;
            private String photo;
            private CategoryBean category;

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

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public CategoryBean getCategory() {
                return category;
            }

            public void setCategory(CategoryBean category) {
                this.category = category;
            }

            public static class CategoryBean {
                /**
                 * id : 1
                 * name : سيارات
                 */

                private int id;
                private String name;

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
            }
        }

        public static class PhotosBean {
            /**
             * id : 665
             * photo : 1533382796436397111.mp3
             * advertise_id : 218
             * created : 2018-08-04T07:39:56+0000
             * modified : 2018-08-04T07:39:56+0000
             */

            private int id;
            private String photo;
            private int advertise_id;
            private String created;
            private String modified;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public int getAdvertise_id() {
                return advertise_id;
            }

            public void setAdvertise_id(int advertise_id) {
                this.advertise_id = advertise_id;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getModified() {
                return modified;
            }

            public void setModified(String modified) {
                this.modified = modified;
            }
        }
    }
}
