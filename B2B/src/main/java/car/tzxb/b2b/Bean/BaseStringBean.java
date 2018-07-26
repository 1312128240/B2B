package car.tzxb.b2b.Bean;

/**
 * Created by Administrator on 2018/5/24 0024.
 */

public class BaseStringBean {

    private String Code;
    private String AccessKeyId;
    private String AccessKeySecret;
    private String Expiration;
    private String SecurityToken;
    private String msg;
    private String order;
    private int status;
    private boolean data;
    private String shop_name;
    private String ID;
    private String id;
    private String title;
    private String img_url;
    private boolean checked;
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }



    public BaseStringBean() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public BaseStringBean(String shop_name, String ID) {
        this.shop_name = shop_name;
        this.ID = ID;
    }


    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAccessKeyId() {
        return AccessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        AccessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return AccessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        AccessKeySecret = accessKeySecret;
    }

    public String getExpiration() {
        return Expiration;
    }

    public void setExpiration(String expiration) {
        Expiration = expiration;
    }

    public String getSecurityToken() {
        return SecurityToken;
    }

    public void setSecurityToken(String securityToken) {
        SecurityToken = securityToken;
    }




    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

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

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
