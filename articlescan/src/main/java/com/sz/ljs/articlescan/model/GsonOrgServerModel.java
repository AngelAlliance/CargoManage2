package com.sz.ljs.articlescan.model;

import java.util.List;

public class GsonOrgServerModel {

    /**
     * Code : 1
     * Msg : OK
     * Data : {"org_list":[{"og_id":12,"og_name":"深圳","og_ename":"SHENZHEN"},{"og_id":13,"og_name":"广州","og_ename":"GUANGZHOU"},{"og_id":14,"og_name":"洛阳","og_ename":"LUOYANG"},{"og_id":17,"og_name":"德黑兰","og_ename":"TEHRAN"}],"server_list":[{"server_id":585,"server_code":"PDE","server_shortname":"PDE"},{"server_id":591,"server_code":"IPS-CHINA","server_shortname":"IPS-大陆"},{"server_id":592,"server_code":"IPS-HK","server_shortname":"IPS-香港"}]}
     */

    private int Code;
    private String Msg;
    private DataBean Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private List<OrgListBean> org_list;
        private List<ServerListBean> server_list;

        public List<OrgListBean> getOrg_list() {
            return org_list;
        }

        public void setOrg_list(List<OrgListBean> org_list) {
            this.org_list = org_list;
        }

        public List<ServerListBean> getServer_list() {
            return server_list;
        }

        public void setServer_list(List<ServerListBean> server_list) {
            this.server_list = server_list;
        }

        public static class OrgListBean {
            /**
             * og_id : 12
             * og_name : 深圳
             * og_ename : SHENZHEN
             */

            private int og_id;  //机构id
            private String og_name;//机构名称
            private String og_ename;//机构得英文名称

            public int getOg_id() {
                return og_id;
            }

            public void setOg_id(int og_id) {
                this.og_id = og_id;
            }

            public String getOg_name() {
                return og_name;
            }

            public void setOg_name(String og_name) {
                this.og_name = og_name;
            }

            public String getOg_ename() {
                return og_ename;
            }

            public void setOg_ename(String og_ename) {
                this.og_ename = og_ename;
            }
        }

        public static class ServerListBean {
            /**
             * server_id : 585
             * server_code : PDE
             * server_shortname : PDE
             */

            private int server_id;//收货服务商id
            private String server_code;//收货服务商code
            private String server_shortname;//收货服务商名字

            public int getServer_id() {
                return server_id;
            }

            public void setServer_id(int server_id) {
                this.server_id = server_id;
            }

            public String getServer_code() {
                return server_code;
            }

            public void setServer_code(String server_code) {
                this.server_code = server_code;
            }

            public String getServer_shortname() {
                return server_shortname;
            }

            public void setServer_shortname(String server_shortname) {
                this.server_shortname = server_shortname;
            }
        }
    }
}
