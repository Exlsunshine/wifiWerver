package com.wifi.server.knn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.wifi.server.model.LocationDetails;
import com.wifi.server.model.WifiDetail;

public class KNN {
    // �������ȼ����еıȽϺ���,����Խ��,���ȼ�Խ��
    private final Comparator<KNNNode> comparator = new Comparator<KNNNode>() {
                                                     
                                                     @Override
                                                     public int compare(KNNNode o1, KNNNode o2) {
                                                         if (o1.getDistance() >= o2.getDistance()) {
                                                             return -1;
                                                         } else {
                                                             return 1;
                                                         }
                                                     }
                                                 };
    
    /**
     * ��ȡK����ͬ�������
     * 
     * @param k
     *            �����ĸ���
     * @param max
     *            ��������ķ�Χ
     * @return ��ɵ����������
     */
    public List<Integer> getRandKNum(int k, int max) {
        List<Integer> rand = new ArrayList<Integer>(k);
        for (int i = 0; i < k; i++) {
            int temp = (int) (Math.random() * max);
            if (!rand.contains(temp)) {
                rand.add(temp);
            } else {
                i--;
            }
        }
        return rand;
    }
    
    /**
     * �������Ԫ����ѵ��Ԫ��֮ǰ�ľ���
     * 
     * @param d1
     *            ����Ԫ��
     * @param d2
     *            ѵ��Ԫ��
     * @return ����ֵ
     */
    public double calDistance(List<Double> d1, List<Double> d2) {
        double distance = 0.00;
        for (int i = 0; i < d1.size(); i++) {
            distance += (d1.get(i) - d2.get(i)) * (d1.get(i) - d2.get(i));
        }
        return distance;
    }
    
    
    public double wifiCalDistance(List<WifiDetail> d1,List<WifiDetail> d2){
    	double distance = 0.00;
    	int wifiLength = 0;
    	int d1Length = d1.size();
    	int d2Length = d2.size();
    	if(d1Length >= 5 && d2Length >= 5){
    		
    		wifiLength = 5;
    	}else{
			wifiLength = d1Length < d2Length ? d1Length:d2Length;
		}
    	
    	for(int i = 0;i < wifiLength;i++){
    		distance += ((double)d1.get(i).getRSS() - (double)d2.get(i).getRSS())*((double)d1.get(i).getRSS() - (double)d2.get(i).getRSS());
    	}
    	
    	return distance;
    }
    
    /**
     * ִ��KNN�㷨����ȡ����Ԫ������
     * 
     * @param datas
     *            ѵ����ݼ�
     * @param testData
     *            ����Ԫ��
     * @param k
     *            �趨��Kֵ
     * @return ����Ԫ������
     */
    public String knn(List<List<Double>> datas, List<Double> testData, int k) {
        PriorityQueue<KNNNode> pq = new PriorityQueue<KNNNode>(k, comparator);
        List<Integer> randNum = getRandKNum(k, datas.size());
        for (int i = 0; i < k; i++) {
            int index = randNum.get(i);
            List<Double> currData = datas.get(index);
            String c = currData.get(currData.size() - 1).toString();
            KNNNode node = new KNNNode(index, calDistance(testData, currData), c);
            pq.add(node);
        }
        for (int i = 0; i < datas.size(); i++) {
            List<Double> t = datas.get(i);
            double distance = calDistance(testData, t);
            KNNNode top = pq.peek();
            if (top.getDistance() > distance) {
                pq.remove();
                pq.add(new KNNNode(i, distance, t.get(t.size() - 1).toString()));
            }
        }
        return getMostClass(pq);
    }
    
    
    
    /**
     * 适合wifi数据定位的算法，将原knn算法修改了一下
     * @param ld
     * @param wifi
     * @param k
     * @return
     */
    public String wifiKnn(List<LocationDetails> ld,List<WifiDetail> wifi,int k){
    	
    	PriorityQueue<KNNNode> pq = new PriorityQueue<KNNNode>(k, comparator);
    	
    	List<Integer> randNum = getRandKNum(k, ld.size());
    	
    	for(int i = 0;i < k;i++){
    		int index = randNum.get(i);
    		List<WifiDetail> currData = ld.get(index).getWifiDetails();
    		String c = ""+ld.get(index).getLocationId();
    		KNNNode node = new KNNNode(index, wifiCalDistance(wifi, currData), c);
    		pq.add(node);
    	}
    	for(int i = 0;i < ld.size();i++){
    		List<WifiDetail> t = ld.get(i).getWifiDetails();
    		double distance = wifiCalDistance(wifi, t);
    		KNNNode top = pq.peek();
    		
    		if(top.getDistance() > distance){
    			pq.remove();
    			pq.add(new KNNNode(i, distance, ""+ld.get(i).getLocationId()));
    		}
    	}
    	
    	
    	return getMostClass(pq);
    	
    }
    
    
    /**
     * ��ȡ��õ���k�������Ԫ��Ķ�����
     * 
     * @param pq
     *            �洢k��������Ԫ������ȼ�����
     * @return ����������
     */
    private String getMostClass(PriorityQueue<KNNNode> pq) {
        Map<String, Integer> classCount = new HashMap<String, Integer>();
        int pqsize = pq.size();
        for (int i = 0; i < pqsize; i++) {
            KNNNode node = pq.remove();
            String c = node.getC();
            if (classCount.containsKey(c)) {
                classCount.put(c, classCount.get(c) + 1);
            } else {
                classCount.put(c, 1);
            }
        }
        int maxIndex = -1;
        int maxCount = 0;
        Object[] classes = classCount.keySet().toArray();
        for (int i = 0; i < classes.length; i++) {
            if (classCount.get(classes[i]) > maxCount) {
                maxIndex = i;
                maxCount = classCount.get(classes[i]);
            }
        }
        return classes[maxIndex].toString();
    }
}
