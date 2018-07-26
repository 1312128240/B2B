package car.tzxb.b2b.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/20 0020.
 */

public class HomeBean {


    private String msg;
    private int status;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {


        private String BtnBg;
        private String HotActicle;
        private HotImageBean HotImage;
        private FindShopBGBean FindShop_BG;
        private List<IndexBannerBean> IndexBanner;
        private List<BtnImgBean> BtnImg;
        private List<OnSaleBean> OnSale;
        private List<ProductTypeBean> ProductType;
        private List<FindShopBean> FindShop;
        private List<CategoryBean> category;
        private List<BrandBean> brand;

        public String getBtnBg() {
            return BtnBg;
        }

        public void setBtnBg(String BtnBg) {
            this.BtnBg = BtnBg;
        }

        public String getHotActicle() {
            return HotActicle;
        }

        public void setHotActicle(String HotActicle) {
            this.HotActicle = HotActicle;
        }

        public HotImageBean getHotImage() {
            return HotImage;
        }

        public void setHotImage(HotImageBean HotImage) {
            this.HotImage = HotImage;
        }

        public FindShopBGBean getFindShop_BG() {
            return FindShop_BG;
        }

        public void setFindShop_BG(FindShopBGBean FindShop_BG) {
            this.FindShop_BG = FindShop_BG;
        }

        public List<IndexBannerBean> getIndexBanner() {
            return IndexBanner;
        }

        public void setIndexBanner(List<IndexBannerBean> IndexBanner) {
            this.IndexBanner = IndexBanner;
        }

        public List<BtnImgBean> getBtnImg() {
            return BtnImg;
        }

        public void setBtnImg(List<BtnImgBean> BtnImg) {
            this.BtnImg = BtnImg;
        }

        public List<OnSaleBean> getOnSale() {
            return OnSale;
        }

        public void setOnSale(List<OnSaleBean> OnSale) {
            this.OnSale = OnSale;
        }

        public List<ProductTypeBean> getProductType() {
            return ProductType;
        }

        public void setProductType(List<ProductTypeBean> ProductType) {
            this.ProductType = ProductType;
        }

        public List<FindShopBean> getFindShop() {
            return FindShop;
        }

        public void setFindShop(List<FindShopBean> FindShop) {
            this.FindShop = FindShop;
        }

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public List<BrandBean> getBrand() {
            return brand;
        }

        public void setBrand(List<BrandBean> brand) {
            this.brand = brand;
        }

        public static class HotImageBean {

            private String id;
            private String img_url;
            private String http_url;
            private String content;
            private String xcx_url;
            private String ios_url;
            private String android_url;
            private String start_time;
            private String end_time;
            private String cc;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getHttp_url() {
                return http_url;
            }

            public void setHttp_url(String http_url) {
                this.http_url = http_url;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getXcx_url() {
                return xcx_url;
            }

            public void setXcx_url(String xcx_url) {
                this.xcx_url = xcx_url;
            }

            public String getIos_url() {
                return ios_url;
            }

            public void setIos_url(String ios_url) {
                this.ios_url = ios_url;
            }

            public String getAndroid_url() {
                return android_url;
            }

            public void setAndroid_url(String android_url) {
                this.android_url = android_url;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getCc() {
                return cc;
            }

            public void setCc(String cc) {
                this.cc = cc;
            }
        }

        public static class FindShopBGBean {
            /**
             * id : 135
             * img_url : https://img.aiucar.cn/advertisement_img/20180625/2018625141419824464.png
             * http_url : #
             * content :
             * xcx_url : #
             * ios_url : #
             * android_url : #
             * start_time : 2018-06-25
             * end_time : 2019-01-25
             * cc : 214
             */

            private String id;
            private String img_url;
            private String http_url;
            private String content;
            private String xcx_url;
            private String ios_url;
            private String android_url;
            private String start_time;
            private String end_time;
            private String cc;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getHttp_url() {
                return http_url;
            }

            public void setHttp_url(String http_url) {
                this.http_url = http_url;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getXcx_url() {
                return xcx_url;
            }

            public void setXcx_url(String xcx_url) {
                this.xcx_url = xcx_url;
            }

            public String getIos_url() {
                return ios_url;
            }

            public void setIos_url(String ios_url) {
                this.ios_url = ios_url;
            }

            public String getAndroid_url() {
                return android_url;
            }

            public void setAndroid_url(String android_url) {
                this.android_url = android_url;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getCc() {
                return cc;
            }

            public void setCc(String cc) {
                this.cc = cc;
            }
        }

        public static class IndexBannerBean {


            private String id;
            private String img_url;
            private String http_url;
            private String content;
            private String xcx_url;
            private String ios_url;
            private String android_url;
            private String start_time;
            private String end_time;
            private String cc;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getHttp_url() {
                return http_url;
            }

            public void setHttp_url(String http_url) {
                this.http_url = http_url;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getXcx_url() {
                return xcx_url;
            }

            public void setXcx_url(String xcx_url) {
                this.xcx_url = xcx_url;
            }

            public String getIos_url() {
                return ios_url;
            }

            public void setIos_url(String ios_url) {
                this.ios_url = ios_url;
            }

            public String getAndroid_url() {
                return android_url;
            }

            public void setAndroid_url(String android_url) {
                this.android_url = android_url;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getCc() {
                return cc;
            }

            public void setCc(String cc) {
                this.cc = cc;
            }
        }

        public static class BtnImgBean {


            private String id;
            private String img_url;
            private String http_url;
            private String content;
            private String xcx_url;
            private String ios_url;
            private String android_url;
            private String start_time;
            private String end_time;
            private String cc;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getHttp_url() {
                return http_url;
            }

            public void setHttp_url(String http_url) {
                this.http_url = http_url;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getXcx_url() {
                return xcx_url;
            }

            public void setXcx_url(String xcx_url) {
                this.xcx_url = xcx_url;
            }

            public String getIos_url() {
                return ios_url;
            }

            public void setIos_url(String ios_url) {
                this.ios_url = ios_url;
            }

            public String getAndroid_url() {
                return android_url;
            }

            public void setAndroid_url(String android_url) {
                this.android_url = android_url;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getCc() {
                return cc;
            }

            public void setCc(String cc) {
                this.cc = cc;
            }
        }

        public static class OnSaleBean {

            private String id;
            private String img_url;
            private String http_url;
            private String content;
            private String xcx_url;
            private String ios_url;
            private String android_url;
            private String start_time;
            private String end_time;
            private String cc;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getHttp_url() {
                return http_url;
            }

            public void setHttp_url(String http_url) {
                this.http_url = http_url;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getXcx_url() {
                return xcx_url;
            }

            public void setXcx_url(String xcx_url) {
                this.xcx_url = xcx_url;
            }

            public String getIos_url() {
                return ios_url;
            }

            public void setIos_url(String ios_url) {
                this.ios_url = ios_url;
            }

            public String getAndroid_url() {
                return android_url;
            }

            public void setAndroid_url(String android_url) {
                this.android_url = android_url;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getCc() {
                return cc;
            }

            public void setCc(String cc) {
                this.cc = cc;
            }
        }

        public static class ProductTypeBean {


            private String id;
            private String img_url;
            private String http_url;
            private String content;
            private String xcx_url;
            private String ios_url;
            private String android_url;
            private String start_time;
            private String end_time;
            private String cc;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getHttp_url() {
                return http_url;
            }

            public void setHttp_url(String http_url) {
                this.http_url = http_url;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getXcx_url() {
                return xcx_url;
            }

            public void setXcx_url(String xcx_url) {
                this.xcx_url = xcx_url;
            }

            public String getIos_url() {
                return ios_url;
            }

            public void setIos_url(String ios_url) {
                this.ios_url = ios_url;
            }

            public String getAndroid_url() {
                return android_url;
            }

            public void setAndroid_url(String android_url) {
                this.android_url = android_url;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getCc() {
                return cc;
            }

            public void setCc(String cc) {
                this.cc = cc;
            }
        }

        public static class FindShopBean {

            private String id;
            private String img_url;
            private String http_url;
            private String content;
            private String xcx_url;
            private String ios_url;
            private String android_url;
            private String start_time;
            private String end_time;
            private String cc;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getHttp_url() {
                return http_url;
            }

            public void setHttp_url(String http_url) {
                this.http_url = http_url;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getXcx_url() {
                return xcx_url;
            }

            public void setXcx_url(String xcx_url) {
                this.xcx_url = xcx_url;
            }

            public String getIos_url() {
                return ios_url;
            }

            public void setIos_url(String ios_url) {
                this.ios_url = ios_url;
            }

            public String getAndroid_url() {
                return android_url;
            }

            public void setAndroid_url(String android_url) {
                this.android_url = android_url;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getCc() {
                return cc;
            }

            public void setCc(String cc) {
                this.cc = cc;
            }
        }

        public static class CategoryBean {

            private String id;
            private String title;
            private String sort_id;
            private String img_url;
            private String category_name;

            public String getCategory_name() {
                return category_name;
            }

            public void setCategory_name(String category_name) {
                this.category_name = category_name;
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

            public String getSort_id() {
                return sort_id;
            }

            public void setSort_id(String sort_id) {
                this.sort_id = sort_id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }
        }

        public static class BrandBean {

            private String id;
            private String title;
            private String sort_id;
            private String img_url;

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

            public String getSort_id() {
                return sort_id;
            }

            public void setSort_id(String sort_id) {
                this.sort_id = sort_id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }
        }
    }
}
