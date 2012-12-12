/*
 *  你看，你看，我的程序
 *  http://www.@!#!&.com
 *  jackjhy@gmail.com
 * 
 *  听说牛粪离钻石只有一步之遥，听说稻草离金条只有一步之遥，
 *  听说色情离艺术只有一步之遥，听说裸体离衣服只有一步之遥，
 *  听说龙芯离AMD只有一步之遥，听说神舟离月球只有一步之遥，
 *  听说现实离乌邦只有一步之遥，听说社会离共产只有一步之遥，
 *  听说台湾离独立只有一步之遥，听说日本离玩完只有一步之遥，
 */

package com.dreamail.mercury.store;

import com.dreamail.mercury.cache.domain.VolumeCacheObject;
import com.dreamail.mercury.dal.service.VolumeService;
import com.dreamail.mercury.pojo.Clickoo_volume;

/**
 * 
 * @author tiger
 * Created on 2010-7-6 10:06:08
 */
public class Volumes {

    public enum MetaEnum{
        CURRENT_MESSAGE_VOLUME(0),SECONDARY_MESSAGE_VOLUME(1),INDEX_VOLUME(2);

        private MetaEnum(Integer value){
            this.value = value;
        }
        
        private Integer value;
        
        public Integer getValue(){
            return value;
        }

        public String getDescription(){
            switch(this.getValue()){
                case 0: return "current_message_volume";
                case 1: return "secondary_message_volume";
                case 2: return "index_volume";
                default: return null;
            }
        }
                
    }

    public void init(){

    }

	public Volume getVolumeByMeta(MetaEnum me){
        VolumeService volumeService = new VolumeService();
        VolumeCacheObject cache = null;
        Volume volume = new Volume();
    	switch (me) {
            case CURRENT_MESSAGE_VOLUME: 
            	cache = volumeService.getCurrentVolumeByName(me.getDescription().toUpperCase());
            	volume.setFile_bits(Integer.parseInt(String.valueOf(cache.getFile_bits())));
            	volume.setId(cache.getId());
            	volume.setName(cache.getName());
            	volume.setPath(cache.getPath());
            	volume.setType(me);
            	return volume;
            case SECONDARY_MESSAGE_VOLUME:
            	cache = volumeService.getCurrentVolumeByName(me.getDescription().toUpperCase());
            	volume.setFile_bits(Integer.parseInt(String.valueOf(cache.getFile_bits())));
            	volume.setId(cache.getId());
            	volume.setName(cache.getName());
            	volume.setPath(cache.getPath());
            	volume.setType(me);
            	return volume;
            case INDEX_VOLUME:
            	cache = volumeService.getCurrentVolumeByName(me.getDescription().toUpperCase());
            	volume.setFile_bits(Integer.parseInt(String.valueOf(cache.getFile_bits())));
            	volume.setId(cache.getId());
            	volume.setName(cache.getName());
            	volume.setPath(cache.getPath());
            	volume.setType(me);
            	return volume;
            default:
            	return null;
        }
    }

    public Volume getVolumeById(long id){
        VolumeService volumeService = new VolumeService();
        Volume volume = new Volume();
        Clickoo_volume pojo = volumeService.getVolumeById(id);
    	volume.setFile_bits(Integer.parseInt(String.valueOf(pojo.getFile_bits())));
    	volume.setId(pojo.getId());
    	volume.setName(pojo.getName());
    	volume.setPath(pojo.getPath());
    	String type = pojo.getType();
    	if(type.equals("0")){
        	volume.setType(MetaEnum.CURRENT_MESSAGE_VOLUME);	
    	}else if(type.equals("1")){
        	volume.setType(MetaEnum.SECONDARY_MESSAGE_VOLUME);	
    	}else if(type.equals("2")){
        	volume.setType(MetaEnum.INDEX_VOLUME);	
    	}
        return volume;
    }


}
