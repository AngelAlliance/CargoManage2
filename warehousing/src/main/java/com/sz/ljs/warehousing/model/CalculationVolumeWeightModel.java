package com.sz.ljs.warehousing.model;

import java.util.List;

/**
 * Created by liujs on 2018/8/21.
 * 查询材积重、计费重返回实体类
 */

public class CalculationVolumeWeightModel {

    /**
     * Code : 1
     * Msg : OK
     * Data : {"LstCargoVolume":[{"Length":12,"Width":12,"Height":12,"volume":0,"GrossWeight":22,"VolumeWeight":0.346,"ChargeWeight":22,"ChildNumber":"","GoodsName":"","TrackNo":"","Calculated":false,"MaxSideLength":12,"Girth":36,"volume_id":""}],"TotalGrossWeight":22,"TotalVolumeWeight":0.346,"TotalChargeWeight":22,"volume":0.001728}
     */

    private int Code;
    private String Msg;
    private DataEntity Data;

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

    public DataEntity getData() {
        return Data;
    }

    public void setData(DataEntity Data) {
        this.Data = Data;
    }

    public static class DataEntity {
        /**
         * LstCargoVolume : [{"Length":12,"Width":12,"Height":12,"volume":0,"GrossWeight":22,"VolumeWeight":0.346,"ChargeWeight":22,"ChildNumber":"","GoodsName":"","TrackNo":"","Calculated":false,"MaxSideLength":12,"Girth":36,"volume_id":""}]
         * TotalGrossWeight : 22
         * TotalVolumeWeight : 0.346
         * TotalChargeWeight : 22
         * volume : 0.001728
         */

        private double TotalGrossWeight;   //总实重
        private double TotalVolumeWeight;  //总材积重
        private double TotalChargeWeight;   //总计费重
        private double volume;  //体积
        private List<LstCargoVolumeEntity> LstCargoVolume;

        public double getTotalGrossWeight() {
            return TotalGrossWeight;
        }

        public void setTotalGrossWeight(double TotalGrossWeight) {
            this.TotalGrossWeight = TotalGrossWeight;
        }

        public double getTotalVolumeWeight() {
            return TotalVolumeWeight;
        }

        public void setTotalVolumeWeight(double TotalVolumeWeight) {
            this.TotalVolumeWeight = TotalVolumeWeight;
        }

        public double getTotalChargeWeight() {
            return TotalChargeWeight;
        }

        public void setTotalChargeWeight(double TotalChargeWeight) {
            this.TotalChargeWeight = TotalChargeWeight;
        }

        public double getVolume() {
            return volume;
        }

        public void setVolume(double volume) {
            this.volume = volume;
        }

        public List<LstCargoVolumeEntity> getLstCargoVolume() {
            return LstCargoVolume;
        }

        public void setLstCargoVolume(List<LstCargoVolumeEntity> LstCargoVolume) {
            this.LstCargoVolume = LstCargoVolume;
        }

        public static class LstCargoVolumeEntity {
            /**
             * Length : 12
             * Width : 12
             * Height : 12
             * volume : 0
             * GrossWeight : 22
             * VolumeWeight : 0.346
             * ChargeWeight : 22
             * ChildNumber :
             * GoodsName :
             * TrackNo :
             * Calculated : false
             * MaxSideLength : 12
             * Girth : 36
             * volume_id :
             */

            private double Length; //长
            private double Width;  //宽
            private double Height;  //高
            private double volume;  //体积
            private double GrossWeight;  //重量
            private double VolumeWeight;  //材积重
            private double ChargeWeight;  //计费重
            private String ChildNumber;  //总材积重
            private String GoodsName;   //总计费重
            private String TrackNo;
            private boolean Calculated;
            private double MaxSideLength;
            private double Girth;
            private String volume_id;

            public double getLength() {
                return Length;
            }

            public void setLength(double Length) {
                this.Length = Length;
            }

            public double getWidth() {
                return Width;
            }

            public void setWidth(double Width) {
                this.Width = Width;
            }

            public double getHeight() {
                return Height;
            }

            public void setHeight(int Height) {
                this.Height = Height;
            }

            public double getVolume() {
                return volume;
            }

            public void setVolume(double volume) {
                this.volume = volume;
            }

            public double getGrossWeight() {
                return GrossWeight;
            }

            public void setGrossWeight(int GrossWeight) {
                this.GrossWeight = GrossWeight;
            }

            public double getVolumeWeight() {
                return VolumeWeight;
            }

            public void setVolumeWeight(double VolumeWeight) {
                this.VolumeWeight = VolumeWeight;
            }

            public double getChargeWeight() {
                return ChargeWeight;
            }

            public void setChargeWeight(int ChargeWeight) {
                this.ChargeWeight = ChargeWeight;
            }

            public String getChildNumber() {
                return ChildNumber;
            }

            public void setChildNumber(String ChildNumber) {
                this.ChildNumber = ChildNumber;
            }

            public String getGoodsName() {
                return GoodsName;
            }

            public void setGoodsName(String GoodsName) {
                this.GoodsName = GoodsName;
            }

            public String getTrackNo() {
                return TrackNo;
            }

            public void setTrackNo(String TrackNo) {
                this.TrackNo = TrackNo;
            }

            public boolean isCalculated() {
                return Calculated;
            }

            public void setCalculated(boolean Calculated) {
                this.Calculated = Calculated;
            }

            public double getMaxSideLength() {
                return MaxSideLength;
            }

            public void setMaxSideLength(double MaxSideLength) {
                this.MaxSideLength = MaxSideLength;
            }

            public double getGirth() {
                return Girth;
            }

            public void setGirth(double Girth) {
                this.Girth = Girth;
            }

            public String getVolume_id() {
                return volume_id;
            }

            public void setVolume_id(String volume_id) {
                this.volume_id = volume_id;
            }
        }
    }
}
