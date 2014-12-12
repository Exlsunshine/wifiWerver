package com.wifi.server.knn;

public class KNNNode {
    private int    index;    // Ԫ����
    private double distance; // �����Ԫ��ľ���
    private String c;        // �������
                             
    public KNNNode(int index, double distance, String c) {
        super();
        this.setIndex(index);
        this.setDistance(distance);
        this.setC(c);
    }
    
    public void setIndex(int index) {
        this.index = index;
    }
    
    public int getIndex() {
        return index;
    }
    
    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    public double getDistance() {
        return distance;
    }
    
    public void setC(String c) {
        this.c = c;
    }
    
    public String getC() {
        return c;
    }
}
