package com.finstone.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="user")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    //不放在JSON中输出到前台
    @JsonIgnore
    private String password;

    @JsonIgnore
    private String salt;

    /*非数据库字段：匿名处理的用户名*/
    @Transient
    private String anonymousName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 用户名匿名处理
     * 长度为1或2单独处理；长度>=3则保留首、尾字符，其余的用*代替。
     * @return
     */
    public String getAnonymousName(){
        if(null==name){
            return null;
        }
        if(name.length()==1){
            return "*";
        }
        if(name.length()==2){
            return name.substring(0,0) + "*";
        }
        char[] chars = name.toCharArray();
        for(int i=1; i<chars.length-1; i++){
            chars[i] = '*';
        }
        return new String(chars);
    }

    public void setAnonymousName(String anonymousName) {
        this.anonymousName = anonymousName;
    }
}
