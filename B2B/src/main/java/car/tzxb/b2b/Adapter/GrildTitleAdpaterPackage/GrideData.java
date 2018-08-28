package car.tzxb.b2b.Adapter.GrildTitleAdpaterPackage;

/**
 * Created by Administrator on 2018/8/27 0027.
 */

public class GrideData {
    public final int TYPE_GRIDE = 1;
    public int viewType = 1,spanCount=1;
    public String id;
    public String from;
    public String title;
    public String img_url;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
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


    public GrideData(int viewType, String title,String imgUrl,String id,String from) {
        this.viewType = viewType;
        this.title=title;
        this.img_url=imgUrl;
        this.from=from;
        this.id=id;
        if (viewType == TYPE_GRIDE) {
            spanCount = 1;
        } else {
            spanCount = 3;
        }
    }

}
