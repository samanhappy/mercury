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

/**
 * 
 * @author tiger
 * Created on 2010-7-6 10:05:30
 */
public class Volume {

    public Long id;
    public String name;
    public String path;
    public Integer file_bits;
    public Integer type;

    public Integer getFile_bits() {
        return file_bits;
    }

    public void setFile_bits(Integer file_bits) {
        this.file_bits = file_bits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Volumes.MetaEnum getType() {

        switch (type) {
            case 0:
                return Volumes.MetaEnum.CURRENT_MESSAGE_VOLUME;
            case 1:
                return Volumes.MetaEnum.SECONDARY_MESSAGE_VOLUME;
            case 2:
                return Volumes.MetaEnum.INDEX_VOLUME;
            default:
                return null;
        }

    }

    public void setType(Volumes.MetaEnum type) {
        this.type = type.ordinal();
    }
}
