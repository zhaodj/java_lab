package com.zhaodj.foo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RecommendAnchorUtils {
	
	static class RecommendAnchor {
		long userId;
		int weight;
		boolean isOnline;
		
		public RecommendAnchor(long userId,int weight,boolean isOnline) {
			this.userId = userId;
			this.weight = weight;
			this.isOnline = isOnline;
		}
		
		@Override
		public String toString() {
			return "userId=" + userId + ",weight=" + weight + ",isOnline=" + isOnline;
		}
	}

	public static List<RecommendAnchor> getRecommend(List<RecommendAnchor> candidates,
			int size) {
		if(candidates.size() <= size) {
			return candidates;
		}
		List<RecommendAnchor> result = new ArrayList<RecommendAnchorUtils.RecommendAnchor>();
		List<RecommendAnchor> onlineAnchors = new ArrayList<RecommendAnchorUtils.RecommendAnchor>();
		List<RecommendAnchor> offlineAnchors = new ArrayList<RecommendAnchorUtils.RecommendAnchor>();
		int onlineSize=0;
		for(RecommendAnchor anchor:candidates) {
			if(anchor.isOnline) {
				onlineSize++;
				onlineAnchors.add(anchor);
			}else {
				offlineAnchors.add(anchor);
			}
		}
		if(size == onlineSize) {
			return onlineAnchors;
		}else if(size > onlineSize) {
			result.addAll(onlineAnchors);
			result.addAll(getByWeight(offlineAnchors, size - onlineSize));
		}else {
			result.addAll(getByWeight(onlineAnchors, size));
		}
		return result;
	}
	
	private static List<RecommendAnchor> getByWeight(List<RecommendAnchor> anchors,int size){
		double weightTotal = 0;
		for(RecommendAnchor anchor:anchors) {
			weightTotal += anchor.weight;
		}
		List<RecommendAnchor> result = new ArrayList<RecommendAnchorUtils.RecommendAnchor>();
		int selected = 0;
		while(selected < size) {
			double random = Math.random() * weightTotal;
			Iterator<RecommendAnchor> iterator = anchors.iterator();
			while(iterator.hasNext()) {
				RecommendAnchor anchor = iterator.next();
				random -= anchor.weight;
				if(random <= 0) {
					selected++;
					weightTotal -= anchor.weight;
					result.add(anchor);
					iterator.remove();
					break;
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		List<RecommendAnchor> candidates = new ArrayList<RecommendAnchorUtils.RecommendAnchor>();
		candidates.add(new RecommendAnchor(1002,5,true));
		candidates.add(new RecommendAnchor(1001,2,true));
		candidates.add(new RecommendAnchor(1000,1,true));
		System.out.println(candidates);
		int size = 2;
		System.out.println("get size:" + size);
		Map<Long, Integer> res = new HashMap<Long, Integer>();
		for(int i=0;i<1000;i++) {
			List<RecommendAnchor> result = getRecommend(candidates,size);
			for(RecommendAnchor anchor:result) {
				if(res.containsKey(anchor.userId)) {
					res.put(anchor.userId, res.get(anchor.userId)+1);
				}else {
					res.put(anchor.userId, 1);
				}
			}
		}
		System.out.println(res);
		/*size=4;
		System.out.println("get size:" + size);
		System.out.println(getRecommend(candidates,size));
		size=5;
		System.out.println("get size:" + size);
		System.out.println(getRecommend(candidates,size));
		size=9;
		System.out.println("get size:" + size);
		System.out.println(getRecommend(candidates,size));*/
	}

}
