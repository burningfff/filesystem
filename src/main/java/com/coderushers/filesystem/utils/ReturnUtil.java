package com.coderushers.filesystem.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ReturnUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private int total;//列表分页时的总记录数

    private String message;//成功或者失败的信息

    private String status;//true or false

    private Object data;//返回的数据

    public ReturnUtil(){
        this.message = "OK";
        this.status = "true";
        this.data = null;
    }

    public ReturnUtil(Object data){
        this.message = "OK";
        this.status = "true";
        this.data = data;
    }

    public ReturnUtil(String message, String status, Object data){
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public static ReturnUtil ok(){
        return new ReturnUtil(null);
    }

    public static ReturnUtil ok(Object data){
        return new ReturnUtil(data);
    }

    public static ReturnUtil err(Object data) {return new ReturnUtil("error","false",data);}

    public static ReturnUtil build(String message, String status, Object data){
        return new ReturnUtil(message,status,data);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ReturnUtil formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, ReturnUtil.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }

            if(jsonNode.get("status").toString().equals("true"))return build(jsonNode.get("msg").asText(),
                    "true",  obj);
            return build(jsonNode.get("msg").asText(),"false",  obj);
        } catch (Exception e) {
            return null;
        }
    }

    public static ReturnUtil format(String json) {
        try {
            return MAPPER.readValue(json, ReturnUtil.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ReturnUtil formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            if(jsonNode.get("status").toString().equals("true"))return build(jsonNode.get("msg").asText(),
                    "true",  obj);
            return build(jsonNode.get("msg").asText(),"false",  obj);
        } catch (Exception e) {
            return null;
        }
    }

    public static String toJson(ReturnUtil ReturnUtil) {
        try {
            String string = MAPPER.writeValueAsString(ReturnUtil);
            return string;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
