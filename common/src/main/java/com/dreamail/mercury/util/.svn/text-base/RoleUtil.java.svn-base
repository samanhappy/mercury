package com.dreamail.mercury.util;

public class RoleUtil {

	private final static String[] roletitles = {"Disable", "Free", "Consumer", "Consumer +", "Premium", "PLMN"};
	
	private final static int[] rolelevels = {1, 2, 3, 4, 5, 6};
	
	/**
	 * 根据角色等级获得角色名称.
	 * 
	 * @param level
	 * @return
	 */
	public static String getRoleTitleByLevel(int level) {
		for (int i = 0; i < rolelevels.length; i++) {
			if (rolelevels[i] == level){
				return roletitles[i];
			} 
		}
		return null;
	}
	
	/**
	 * 根据角色名称获得角色等级.
	 * 
	 * @param title
	 * @return
	 */
	public static int getRoleLevelByTitle(String title) {
		for (int i = 0; i < roletitles.length; i++) {
			if (title.equals(roletitles[i])) {
				return rolelevels[i];
			}
		}
		return -1;
	}
}
