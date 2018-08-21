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

        private int TotalGrossWeight;
        private double TotalVolumeWeight;
        private int TotalChargeWeight;
        private double volume;
        private List<LstCargoVolumeEntity> LstCargoVolume;

        public int getTotalGrossWeight() {
            return TotalGrossWeight;
        }

        public void setTotalGrossWeight(int TotalGrossWeight) {
            this.TotalGrossWeight = TotalGrossWeight;
        }

        public double getTotalVolumeWeight() {
            return TotalVolumeWeight;
        }

        public void setTotalVolumeWeight(double TotalVolumeWeight) {
            this.TotalVolumeWeight = TotalVolumeWeight;
        }

        public int getTotalChargeWeight() {
            return TotalChargeWeight;
        }

        public void setTotalChargeWeight(int TotalChargeWeight) {
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

            private int Length; //长
            private int Width;  //宽
            private int Height;  //高
            private int volume;  //体积
            private int GrossWeight;  //重量
            private double VolumeWeight;  //材积重
            private int ChargeWeight;  //计费重
            private String ChildNumber;  //总材积重
            private String GoodsName;   //总计费重
            private String TrackNo;
            private boolean Calculated;
            private int MaxSideLength;
            private int Girth;
            private String volume_id;

            public int getLength() {
                return Length;
            }

            public void setLength(int Length) {
                this.Length = Length;
            }

            public int getWidth() {
                return Width;
            }

            public void setWidth(int Width) {
                this.Width = Width;
            }

            public int getHeight() {
                return Height;
            }

            public void setHeight(int Height) {
                this.Height = Height;
            }

            public int getVolume() {
                return volume;
            }

            public void setVolume(int volume) {
                this.volume = volume;
            }

            public int getGrossWeight() {
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

            public int getChargeWeight() {
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

            public int getMaxSideLength() {
                return MaxSideLength;
            }

            public void setMaxSideLength(int MaxSideLength) {
                this.MaxSideLength = MaxSideLength;
            }

            public int getGirth() {
                return Girth;
            }

            public void setGirth(int Girth) {
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
